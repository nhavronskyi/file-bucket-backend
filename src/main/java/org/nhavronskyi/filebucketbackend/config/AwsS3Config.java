package org.nhavronskyi.filebucketbackend.config;

import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.props.AwsS3Props;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@RequiredArgsConstructor
public class AwsS3Config {

    private final AwsS3Props props;

    @Bean
    public S3Client s3Client() {
        var scp = StaticCredentialsProvider
                .create(AwsBasicCredentials
                        .create(props.key(), props.secret()));
        return S3Client.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(scp)
                .build();
    }
}
