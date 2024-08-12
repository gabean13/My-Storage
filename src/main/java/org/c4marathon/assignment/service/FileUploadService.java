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
import org.c4marathon.assignment.dto.ResponseDto;
import org.c4marathon.assignment.exception.FileExtensionInvalidException;
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
    private final ContentTypeToExtensionMapper contentTypeToExtensionMapper;

    public ResponseDto uploadFileListHandler (MultipartFile[] fileList, String userKey) throws IOException {
        User user = userRepository.findUserByKey(userKey).orElseThrow(() -> new UserNotFoundException());

        for(MultipartFile file : fileList){
           uploadFileHandler(file, user);
        }

        return new ResponseDto(fileList.length + "개 파일 업로드를 성공하였습니다");
    }

    @Transactional
    protected String uploadFileHandler(MultipartFile file, User user) throws IOException{
        if(!contentTypeToExtensionMapper.isContainsContentType(file.getContentType())){
            throw new FileExtensionInvalidException();
        }

        String fileUUID = saveFileToStorage(user.getKey(), file);
        Long fileId = saveFileInfo(file.getOriginalFilename(), user, fileUUID);
        saveMetadataInfo(fileId, file);

        return file.getOriginalFilename();
    }

    /**
     * 실제 저장소에 파일 저장
     */
    protected String saveFileToStorage(String userUUID, MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID().toString();

        String userPath = "storage" + java.io.File.separator + userUUID;

        Path uploadPath = Path.of(userPath);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    protected Long saveFileInfo(String fileName, User user, String fileUUID){
        String path = "storage" + java.io.File.separator + user.getKey();

        File file = File.builder()
                .name(fileName)
                .path(path)
                .userId(user.getId())
                .uuid(fileUUID).build();
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
