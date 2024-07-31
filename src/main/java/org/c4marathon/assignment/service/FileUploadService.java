package org.c4marathon.assignment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.c4marathon.assignment.data.entity.File;
import org.c4marathon.assignment.data.entity.Metadata;
import org.c4marathon.assignment.data.entity.User;
import org.c4marathon.assignment.data.repository.FileRepository;
import org.c4marathon.assignment.data.repository.MetadataRepository;
import org.c4marathon.assignment.data.repository.UserRepository;
import org.c4marathon.assignment.exception.FileEmptyException;
import org.c4marathon.assignment.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final MetadataRepository metadataRepository;

    @Transactional
    public void uploadFileHandler (MultipartFile file, String userKey) throws IOException {
        if(file == null || file.isEmpty()){
            throw new FileEmptyException("파일이 비어 있습니다.");
        }

        User user = userRepository.findUserByKey(userKey).orElseThrow(() -> new UserNotFoundException("유저가 존재하지 않습니다"));

        String fileUUID = saveFileToStorage(user.getKey(), file);
        Long fileId = saveFileInfo(file.getOriginalFilename(), user, fileUUID);
        saveMetadataInfo(fileId, file);
    }

    /**
     * 실제 저장소에 파일 저장
     */
    protected String saveFileToStorage(String userUUID, MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString();

        String userPath = "storage" + "\\" + userUUID;

        Path uploadPath = Path.of(userPath);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    protected Long saveFileInfo(String fileName, User user, String fileUUID){
        String path = "storage" + "\\" + user.getKey();

        //TODO : 같은 이름의 파일 저장시 이름 저장 방법
        //List<File> sameNameFileList = fileRepository.findAllByUserIdAndName(userId, fileName);
        File file = new File(user.getId(), path, fileName, fileUUID);
        File savedFile = fileRepository.save(file);

        return savedFile.getId();
    }

    protected void saveMetadataInfo(Long fileId, MultipartFile file){
        long size = file.getSize();
        String contentType = file.getContentType();

        Metadata metadata = new Metadata(fileId, contentType, size, LocalDateTime.now());
        metadataRepository.save(metadata);
    }
}
