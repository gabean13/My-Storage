package org.c4marathon.assignment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.c4marathon.assignment.service.FileDeleteService;
import org.c4marathon.assignment.service.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileUploadService fileUploadService;
    private final FileDeleteService fileDeleteService;
    @PostMapping("/file")
    public ResponseEntity<Object> uploadFile(@ModelAttribute("file") MultipartFile file,
                                             @RequestHeader("user_key") String userKey) throws IOException {
        fileUploadService.uploadFileHandler(file, userKey);
        return ResponseEntity.ok().body(file.getOriginalFilename() + " upload success");
    }

}
