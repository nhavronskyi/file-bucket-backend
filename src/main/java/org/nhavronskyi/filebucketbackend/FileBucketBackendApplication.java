package org.nhavronskyi.filebucketbackend;

import org.nhavronskyi.filebucketbackend.props.VirusTotalProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableConfigurationProperties({VirusTotalProps.class})
public class FileBucketBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileBucketBackendApplication.class, args);
    }

}
