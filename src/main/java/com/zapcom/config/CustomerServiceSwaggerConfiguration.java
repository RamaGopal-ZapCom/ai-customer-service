package com.zapcom.config;
import com.zapcom.constants.CustomerServiceSwaggerConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class CustomerServiceSwaggerConfiguration {
    @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .info(new Info()
                            .title(CustomerServiceSwaggerConstants.TITLE)
                            .version(CustomerServiceSwaggerConstants.VERSION)
                            .description(CustomerServiceSwaggerConstants.DESCRIPTION));
        }
 }