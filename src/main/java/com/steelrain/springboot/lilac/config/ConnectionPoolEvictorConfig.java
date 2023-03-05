package com.steelrain.springboot.lilac.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 스케쥴링 스레드풀의 설정
 */
@Configuration
public class ConnectionPoolEvictorConfig implements SchedulingConfigurer {

    @Value("${thread.pool.size}")
    private int EVICTOR_THREAD_POOL_SIZE;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(EVICTOR_THREAD_POOL_SIZE);
        scheduler.setThreadNamePrefix("hc-conn-회수-");
        scheduler.initialize();
        taskRegistrar.setTaskScheduler(scheduler);
    }
}
