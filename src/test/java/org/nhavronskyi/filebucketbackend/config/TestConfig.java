package org.nhavronskyi.filebucketbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import javax.sql.DataSource;

@Configuration
public class TestConfig {
    @MockitoBean
    DataSource dataSource;
}
