package io.limeup.flexbets.sport.config;

import io.limeup.flexbets.sport.batch.prefetch.listener.RedisPreMatchEventMessageListener;
import io.limeup.flexbets.sport.batch.prefetch.listener.RedisSSEventMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisListenerConfig {

    @Bean
    public RedisMessageListenerContainer redisContainer(
            RedisConnectionFactory redisConnectionFactory,
            RedisSSEventMessageListener redisSSEventMessageListener,
            RedisPreMatchEventMessageListener redisPreMatchEventMessageListener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        container.addMessageListener(redisSSEventMessageListener, new PatternTopic("events:stats_score"));
        container.addMessageListener(redisPreMatchEventMessageListener, new PatternTopic("events:prematch")); // ✅

        return container;
    }
}