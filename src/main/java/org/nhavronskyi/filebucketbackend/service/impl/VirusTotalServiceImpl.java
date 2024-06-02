package org.nhavronskyi.filebucketbackend.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.nhavronskyi.filebucketbackend.entities.files.Analysis;
import org.nhavronskyi.filebucketbackend.enums.FileStatus;
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

    private static JsonElement parseAnalysis(String res) {
        return new Gson()
                .fromJson(res, JsonObject.class)
                .get("data")
                .getAsJsonObject()
                .get("attributes")
                .getAsJsonObject()
                .get("stats");
    }

    private static void setAnalysisStatus(Analysis analysis) {
        if (analysis.getMalicious() > 0) {
            analysis.setFileStatus(FileStatus.MALICIOUS.name());
        } else if (analysis.getSuspicious() > 0) {
            analysis.setFileStatus(FileStatus.SUSPICIOUS.name());
        } else {
            analysis.setFileStatus(FileStatus.OK.name());
        }
    }

    private Analysis getAnalysis(String answer) {
        var res = getResultFromAswer(answer);

        Analysis analysis = new Gson().fromJson(parseAnalysis(res), Analysis.class);
        setAnalysisStatus(analysis);
        return analysis;
    }

    private String getResultFromAswer(String answer) {
        return restClient.get()
                .uri("https://www.virustotal.com/api/v3/analyses/" + answer)
                .accept(MediaType.APPLICATION_JSON)
                .header("x-apikey", virusTotalProps.ApiKey())
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }

    private String getAnswer(MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        return new Gson()
                .fromJson(getResultFromPost(body), JsonObject.class)
                .get("data")
                .getAsJsonObject()
                .get("id")
                .getAsString();
    }

    private String getResultFromPost(MultiValueMap<String, Object> body) {
        return restClient.post()
                .uri("https://www.virustotal.com/api/v3/files")
                .accept(MediaType.APPLICATION_JSON)
                .header("x-apikey", virusTotalProps.ApiKey())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }
}
