package ru.spring.app.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.spring.app.engine.api.response.InitResponse;

@SpringBootApplication
@EnableConfigurationProperties(InitResponse.class)
public class BlogEngineApp {
    public static void main(String[] args) {
        SpringApplication.run(BlogEngineApp.class, args);
    }
}
