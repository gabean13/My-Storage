package org.c4marathon.assignment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.c4marathon.assignment.data.entity.File;
import org.c4marathon.assignment.data.repository.FileRepository;
import org.c4marathon.assignment.data.repository.MetadataRepository;
import org.c4marathon.assignment.data.repository.UserRepository;
import org.c4marathon.assignment.exception.FileEmptyException;
import org.c4marathon.assignment.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileDeleteService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final MetadataRepository metadataRepository;
    @Transactional
    public String deleteFileHandler(String userKey, Long fileId) throws IOException {
        Long userId = userRepository.findUserByKey(userKey).orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다")).getId();
        File file = fileRepository.findByIdAndUserId(fileId, userId).orElseThrow(() -> new FileEmptyException("파일이 존재하지 않습니다"));

        String path = file.getPath();
        String fileUUID = file.getUuid();
        String name = file.getName();

        //메타 데이터 튜플 삭제
        metadataRepository.delete(
                metadataRepository.findById(fileId).orElseThrow(() -> new FileEmptyException("파일이 존재하지 않습니다"))
        );
        //파일 튜플 삭제
        fileRepository.delete(file);
        //실제 파일 삭제
        deleteRealFile(path, fileUUID);

        return name;
    }

    protected void deleteRealFile(String filePath, String fileUUID) throws IOException {
        Path deleteFilePath = Path.of(filePath, fileUUID);
        log.info("filePath : {} , fileName : {}", filePath, fileUUID);

        if(Files.exists(deleteFilePath)){
            Files.delete(deleteFilePath);
        }else{
            throw new FileEmptyException("실제 파일이 존재하지 않습니다");
        }
    }
}
