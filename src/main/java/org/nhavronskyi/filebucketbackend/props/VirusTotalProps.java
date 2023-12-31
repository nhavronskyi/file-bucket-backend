package org.nhavronskyi.filebucketbackend.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("virus-total")
public record VirusTotalProps(String ApiKey) {
}
