package org.c4marathon.assignment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.c4marathon.assignment.dto.FileDownloadResponseDto;
import org.c4marathon.assignment.dto.ResponseDto;
import org.c4marathon.assignment.exception.FileEmptyException;
import org.c4marathon.assignment.exception.ParameterEmptyException;
import org.c4marathon.assignment.service.FileDeleteService;
import org.c4marathon.assignment.service.FileDownloadService;
import org.c4marathon.assignment.service.FileUploadService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileUploadService fileUploadService;
    private final FileDeleteService fileDeleteService;
    private final FileDownloadService fileDownloadService;

    @PostMapping("/file")
    public ResponseEntity<ResponseDto> uploadFile(@RequestParam(value = "file", required = false) MultipartFile[] fileList,
                                                  @RequestHeader(value = "X-USER-KEY") String userKey) throws IOException {
        if(fileList == null || fileList.length == 0) {
            throw new FileEmptyException();
        }

        ResponseDto responseDto = fileUploadService.uploadFileListHandler(fileList, userKey);
        return ResponseEntity.ok().body(responseDto);
    }

    @DeleteMapping("/file")
    public ResponseEntity<ResponseDto> deleteFile(@RequestParam(value = "file-id", required = false) Long fileId,
                                             @RequestHeader("X-USER-KEY") String userKey) throws IOException {
        if(fileId == null) {
            throw new ParameterEmptyException();
        }

        ResponseDto responseDto = fileDeleteService.deleteFileHandler(userKey, fileId);
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/file")
    public ResponseEntity<Object> downloadFile(@RequestParam(value = "file-id", required = false) Long fileId,
                                               @RequestHeader("X-USER-KEY") String userKey) throws MalformedURLException, UnsupportedEncodingException {
        if(fileId == null) {
            throw new ParameterEmptyException();
        }

        FileDownloadResponseDto fileDownloadResponseDto = fileDownloadService.fileDownloadHandler(userKey, fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, fileDownloadResponseDto.getContentDisposition())
                .header(HttpHeaders.CONTENT_TYPE, fileDownloadResponseDto.getContentType())
                .body(fileDownloadResponseDto.getResource());
    }
}

