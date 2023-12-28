package org.nhavronskyi.filebucketbackend.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.nhavronskyi.filebucketbackend.service.VirusTotalService;
import org.nhavronskyi.filebucketbackend.service.impl.VirusTotalServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
public class FileTransferringController {

    private final VirusTotalService virusTotalService;

    @GetMapping
    public String test() {
        return "hello";
    }

    @SneakyThrows
    @GetMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public VirusTotalServiceImpl.Analysis saveFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            return virusTotalService.checkFile(file);
        }
        return null;
    }
}
