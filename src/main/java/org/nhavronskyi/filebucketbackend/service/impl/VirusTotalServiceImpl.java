package org.nhavronskyi.filebucketbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.props.VirusTotalProps;
import org.nhavronskyi.filebucketbackend.service.VirusTotalService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VirusTotalServiceImpl implements VirusTotalService {

    private final VirusTotalProps virusTotalProps;

    private final RestClient restClient;

    public String checkFile(MultipartFile file) {
        var res = restClient.post()
                .uri("https://www.virustotal.com/api/v3/files")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .header("x-apikey", virusTotalProps.ApiKey())
                .body(file)
                .retrieve()
                .toEntity(String.class);

        return res.getBody();
    }
}
