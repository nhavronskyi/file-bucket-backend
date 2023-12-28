package org.nhavronskyi.filebucketbackend.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.entities.Analysis;
import org.nhavronskyi.filebucketbackend.props.VirusTotalProps;
import org.nhavronskyi.filebucketbackend.service.VirusTotalService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class VirusTotalServiceImpl implements VirusTotalService {

    private final VirusTotalProps virusTotalProps;
    private final RestClient restClient;

    public Analysis checkFile(MultipartFile file) {
        return getAnalysis(getAnswer(file));
    }

    private Analysis getAnalysis(String answer) {
        var res = restClient.get()
                .uri("https://www.virustotal.com/api/v3/analyses/" + answer)
                .accept(MediaType.APPLICATION_JSON)
                .header("x-apikey", virusTotalProps.ApiKey())
                .retrieve()
                .toEntity(String.class);

        var stats = new Gson()
                .fromJson(res.getBody(), JsonObject.class)
                .get("data")
                .getAsJsonObject()
                .get("attributes")
                .getAsJsonObject()
                .get("stats");

        return new Gson().fromJson(stats, Analysis.class);
    }


    private String getAnswer(MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        var answer = restClient.post()
                .uri("https://www.virustotal.com/api/v3/files")
                .accept(MediaType.APPLICATION_JSON)
                .header("x-apikey", virusTotalProps.ApiKey())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .toEntity(String.class);

        var data = new Gson()
                .fromJson(answer.getBody(), JsonObject.class)
                .get("data")
                .getAsJsonObject()
                .get("id");

        return new Gson().fromJson(data, String.class);
    }
}
