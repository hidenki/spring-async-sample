package com.example.asyncsample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.beans.factory.annotation.Qualifier;

@SpringBootApplication
@EnableAsync
public class AsyncsampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncsampleApplication.class, args);
	}

    /**
     * Bean定義
     */
    @Bean
    @Qualifier("sleepTask")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(0);
        return executor;
	}
}
