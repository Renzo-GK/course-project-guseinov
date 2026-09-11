package ru.ncfu.meetingroom.control;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI meetingRoomApi() {
        return new OpenAPI().info(new Info()
            .title("WorkSpace Booking API")
            .version("1.0")
            .description("REST API сервиса бронирования переговорных комнат и рабочих пространств."));
    }
}
