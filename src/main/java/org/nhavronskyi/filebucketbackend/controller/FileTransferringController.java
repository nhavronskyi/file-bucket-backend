package org.nhavronskyi.filebucketbackend.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.nhavronskyi.filebucketbackend.enums.SavingStatus;
import org.nhavronskyi.filebucketbackend.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
public class FileTransferringController {

    private final FileService fileService;
    @SneakyThrows
    @GetMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SavingStatus saveFile(@RequestParam("file") MultipartFile file) {
        return fileService.save(file);
    }
}
