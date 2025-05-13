package io.limeup.flexbets.sport.cache;

import io.limeup.flexbets.sport.dto.EventDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventBasedCacheAspectTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private EventBasedCacheAspect aspect;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @Test
    void shouldReturnCachedValueWhenCacheHitAndNotExpired() throws Throwable {
        EventDTO cachedEvent = new EventDTO();
        cachedEvent.setEventDate(LocalDateTime.now().plusDays(1));

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getParameterNames()).thenReturn(new String[]{"param"});
        when(joinPoint.getArgs()).thenReturn(new Object[]{1});

        EventBasedCache annotation = mock(EventBasedCache.class);
        when(annotation.key()).thenReturn("#param");
        when(annotation.cacheName()).thenReturn("eventsListCache");

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("eventsListCache::1")).thenReturn(cachedEvent);

        Object result = aspect.cacheHandler(joinPoint, annotation);

        assertThat(result).isEqualTo(cachedEvent);
        verify(redisTemplate, times(1)).opsForValue();
        verify(joinPoint, never()).proceed();
    }

    @Test
    void shouldEvictStaleCacheAndCallMethod() throws Throwable {
        EventDTO expired = new EventDTO();
        expired.setEventDate(LocalDateTime.now().minusDays(1));

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getParameterNames()).thenReturn(new String[]{"param"});
        when(joinPoint.getArgs()).thenReturn(new Object[]{2});

        EventBasedCache annotation = mock(EventBasedCache.class);
        when(annotation.key()).thenReturn("#param");
        when(annotation.cacheName()).thenReturn("eventsListCache");
        when(annotation.ttlMinutes()).thenReturn(60);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("eventsListCache::2")).thenReturn(expired);
        when(joinPoint.proceed()).thenReturn("fresh-result");

        Object result = aspect.cacheHandler(joinPoint, annotation);

        assertThat(result).isEqualTo("fresh-result");
        verify(redisTemplate).delete("eventsListCache::2");
        verify(redisTemplate, times(2)).opsForValue();
        verify(valueOperations, times(1)).set(eq("eventsListCache::2"), eq("fresh-result"), eq(Duration.ofMinutes(60)));
    }

    @Test
    void shouldCallMethodAndCacheWhenNoCacheExists() throws Throwable {
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getParameterNames()).thenReturn(new String[]{"param"});
        when(joinPoint.getArgs()).thenReturn(new Object[]{3});

        EventBasedCache annotation = mock(EventBasedCache.class);
        when(annotation.key()).thenReturn("#param");
        when(annotation.cacheName()).thenReturn("eventsListCache");
        when(annotation.ttlMinutes()).thenReturn(60);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("eventsListCache::3")).thenReturn(null);
        when(joinPoint.proceed()).thenReturn("new-data");

        Object result = aspect.cacheHandler(joinPoint, annotation);

        assertThat(result).isEqualTo("new-data");
        verify(redisTemplate, times(2)).opsForValue();
        verify(valueOperations).set(eq("eventsListCache::3"), eq("new-data"), eq(Duration.ofMinutes(60)));
    }
}

