package io.limeup.flexbets.sport.config;

import io.limeup.flexbets.sport.service.RedisEventMessageListener;
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
            RedisEventMessageListener redisEventMessageListener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);

        container.addMessageListener(redisEventMessageListener, new PatternTopic("events:stats_score"));

        return container;
    }
}