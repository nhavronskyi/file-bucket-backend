package org.nhavronskyi.filebucketbackend.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.nhavronskyi.filebucketbackend.entities.files.Analysis;
import org.nhavronskyi.filebucketbackend.entities.files.S3SimpleFile;
import org.nhavronskyi.filebucketbackend.enums.FileStatus;
import org.nhavronskyi.filebucketbackend.service.FileService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<InputStreamResource> getS3File(@RequestParam String key) {
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
