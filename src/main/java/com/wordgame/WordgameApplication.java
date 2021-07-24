package com.wordgame;

import com.wordgame.core.CommonDatabaseCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@Slf4j
public class WordgameApplication {

    @Value("${mvn.project.version}")
    private String version;

    public static void main(String[] args) {
        SpringApplication.run(WordgameApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void afterInit() {
        log.info("LOAD WORDGAME SERVER APP V - {}", version);
    }

    @Bean
    public Docket swaggerStructureApi10() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.wordgame"))
                .paths(PathSelectors.any()).build()
                .apiInfo(new ApiInfoBuilder().version("1.0").title("wordgame API")
                        .description("Documentation wordgame API v1.0").build());
    }

    @Bean
    public CommonDatabaseCreator databaseCreator() {
        return new CommonDatabaseCreator();
    }

    @Bean
    public FlywayMigrationStrategy beforeMigrationStrategy(CommonDatabaseCreator databaseCreator) {
        return flyway -> {
            databaseCreator.createDatabase();
            flyway.migrate();
        };
    }

}
