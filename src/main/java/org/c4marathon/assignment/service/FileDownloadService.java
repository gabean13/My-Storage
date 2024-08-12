package org.c4marathon.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.c4marathon.assignment.data.entity.File;
import org.c4marathon.assignment.data.entity.User;
import org.c4marathon.assignment.data.repository.FileRepository;
import org.c4marathon.assignment.data.repository.MetadataRepository;
import org.c4marathon.assignment.data.repository.UserRepository;
import org.c4marathon.assignment.dto.FileDownloadResponseDto;
import org.c4marathon.assignment.exception.FileEmptyException;
import org.c4marathon.assignment.exception.UserNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileDownloadService {
    private final FileRepository fileRepository;
    private final MetadataRepository metadataRepository;
    private final UserRepository userRepository;
    public FileDownloadResponseDto fileDownloadHandler(String userKey, Long fileId) throws MalformedURLException, UnsupportedEncodingException {
        User user = userRepository.findUserByKey(userKey).orElseThrow(
                () -> new UserNotFoundException()
        );
        File file = fileRepository.findByIdAndUserId(fileId, user.getId()).orElseThrow(
                ()-> new FileEmptyException()
        );

        String fileName = URLEncoder.encode(file.getName(), "UTF-8");
        String contentDisposition = "attachment; filename=\"" + fileName +"\"" ;

        Path filePath = Path.of(file.getPath()).resolve(file.getUuid()).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if(resource.exists()|| resource.isReadable()){
            return new FileDownloadResponseDto(resource, file.getName(), getContentType(fileId), contentDisposition);
        }else{
            throw new FileEmptyException();
        }
    }

    protected String getContentType(Long fileId){
        return metadataRepository.findById(fileId).orElseThrow(
                () -> new FileEmptyException()
        ).getFileExtension();
    }
}
