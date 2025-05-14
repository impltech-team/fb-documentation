package io.limeup.flexbets.sport.cache;

import io.limeup.flexbets.sport.dto.EventDTO;
import io.limeup.flexbets.sport.dto.PaginatedResponse;
import io.limeup.flexbets.sport.dto.ParticipantDTO;
import io.limeup.flexbets.sport.dto.SubParticipantDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class EventBasedCacheAspect {

    private final RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(eventBasedCache)")
    public Object cacheHandler(ProceedingJoinPoint joinPoint, EventBasedCache eventBasedCache) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();

        SpelExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNames.length; i++) {
            context.setVariable(paramNames[i], args[i]);
        }

        String key = parser.parseExpression(eventBasedCache.key()).getValue(context, String.class);
        String fullKey = eventBasedCache.cacheName() + "::" + key;

        Object cached = redisTemplate.opsForValue().get(fullKey);
        if (cached != null) {
            if (!hasExpiredEvents(cached)) {
                log.debug("[Cache HIT] {}", fullKey);
                return cached;
            } else {
                log.debug("[Cache STALE, evicting] {}", fullKey);
                redisTemplate.delete(fullKey);
            }
        }

        Object result = joinPoint.proceed();
        Duration ttl = Duration.ofMinutes(eventBasedCache.ttlMinutes());

        redisTemplate.opsForValue().set(fullKey, result, ttl);
        log.debug("[Cache PUT] {} for {} min", fullKey, ttl.toMinutes());
        return result;
    }

    private boolean hasExpiredEvents(Object value) {
        return extractAllEventDates(value).stream()
                .anyMatch(d -> d.isBefore(LocalDateTime.now()));
    }

    private List<LocalDateTime> extractAllEventDates(Object value) {
        if (value == null) {
            return List.of();
        }
        if (value instanceof PaginatedResponse<?> paginated) {
            List<?> items = paginated.getItems();
            if (items == null || items.isEmpty()) return List.of();

            return items.stream()
                    .map(this::extractSingleEventDate)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        if (value instanceof List<?> list) {
            if (list.isEmpty()) return List.of();

            return list.stream()
                    .map(this::extractSingleEventDate)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        LocalDateTime single = extractSingleEventDate(value);
        return single != null ? List.of(single) : List.of();
    }

    private LocalDateTime extractSingleEventDate(Object obj) {
        try {
            if (obj instanceof EventDTO event) return event.getEventDate();
            if (obj instanceof SubParticipantDTO sp && sp.getNextEvent() != null)
                return sp.getNextEvent().getEventDate();
            if (obj instanceof ParticipantDTO p && p.getNextEvent() != null)
                return p.getNextEvent().getEventDate();
        } catch (Exception e) {
            log.warn("Failed to extract date from {}: {}", obj.getClass(), e.getMessage());
        }
        return null;
    }

}
