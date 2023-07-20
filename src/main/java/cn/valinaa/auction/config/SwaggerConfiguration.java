package cn.valinaa.auction.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("auction")
                .packagesToScan("cn.valinaa.auction.controller")
                .packagesToScan("cn.valinaa.auction.bean")
                .pathsToMatch("/**")
                .build();
    }
    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(new Info().title("文档标题")
                .description("文档描述")
                .version("v1.0.0"));
    }
}
