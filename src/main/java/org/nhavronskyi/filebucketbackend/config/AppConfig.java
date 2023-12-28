package org.nhavronskyi.filebucketbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .messageConverters(httpMessageConverters -> {
                    httpMessageConverters.add(new FormHttpMessageConverter());
                    httpMessageConverters.add(new MappingJackson2HttpMessageConverter());
                })
                .build();
    }
}
