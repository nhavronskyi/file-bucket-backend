package org.nhavronskyi.filebucketbackend.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.nhavronskyi.filebucketbackend.entities.Analysis;
import org.nhavronskyi.filebucketbackend.entities.S3File;
import org.nhavronskyi.filebucketbackend.entities.S3SimpleFile;
import org.nhavronskyi.filebucketbackend.enums.FileStatus;
import org.nhavronskyi.filebucketbackend.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
public class FileTransferringController {

    private final FileService fileService;

    @SneakyThrows
    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Analysis saveFile(@RequestParam("file") MultipartFile file) {
        return fileService.save(file);
    }

    @SneakyThrows
    @PostMapping(value = "check", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Analysis checkFile(@RequestParam("file") MultipartFile file) {
        return fileService.checkFile(file);
    }

    @GetMapping("get-file")
    public S3File getS3File(@RequestParam String key) {
        return fileService.getFile(key);
    }

    @DeleteMapping
    public FileStatus deleteS3File(@RequestParam String key) {
        return fileService.deleteFile(key);
    }

    @GetMapping("get-all")
    public List<S3SimpleFile> getAllFiles() {
        return fileService.getAllFiles();
    }
}
