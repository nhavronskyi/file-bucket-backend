package org.nhavronskyi.filebucketbackend.controller;

import lombok.SneakyThrows;
import org.nhavronskyi.filebucketbackend.enums.SavingStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("files")
public class FileTransferringController {

    @GetMapping
    public String test() {
        return "hello";
    }

    @SneakyThrows
    @GetMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SavingStatus saveFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            File newFile = new File("src/main/java/org/nhavronskyi/filebucketbackend/files/" + file.getOriginalFilename());
            try (OutputStream outStream = new FileOutputStream(newFile)) {
                outStream.write(file.getBytes());
            }
            return SavingStatus.SAVED;
        }
        return SavingStatus.ERROR;
    }
}
