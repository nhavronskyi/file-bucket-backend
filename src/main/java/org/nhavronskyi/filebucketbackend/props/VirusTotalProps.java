package org.nhavronskyi.filebucketbackend.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("virustotal")
public record VirusTotalProps(String ApiKey) {
}
