package org.nhavronskyi.filebucketbackend.controller;

import lombok.SneakyThrows;
import org.nhavronskyi.filebucketbackend.enums.SavingStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@RequestMapping("files")
public class FileTransferringController {

    @GetMapping
    public String test(){
        return "hello";
    }

    @SneakyThrows
    @PostMapping("save")
    public SavingStatus saveFile(@RequestParam MultipartFile file){
        if (!file.isEmpty()){
            file.transferTo(Path.of("src/main/java/org/nhavronskyi/filebucketbackend/files"));
        }
        return SavingStatus.SAVED;
    }
}
