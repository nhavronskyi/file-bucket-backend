package org.nhavronskyi.filebucketbackend.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class TestConfig {
    @MockBean
    DataSource dataSource;
}
