package org.nhavronskyi.filebucketbackend.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.s3")
public record AwsS3Props(String key, String secret, String bucket) {
}
