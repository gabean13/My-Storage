package org.c4marathon.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
@AllArgsConstructor
public class FileDownloadResponseDto {
    private Resource resource;
    private String fileName;
    private String contentType;
    private String contentDisposition;
}
