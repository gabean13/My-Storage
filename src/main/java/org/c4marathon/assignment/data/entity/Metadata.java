package org.c4marathon.assignment.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@Entity(name = "metadata")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Metadata {
    @Id
    private Long fileId;
    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "file_size")
    private Long fileSize;
    @Column(name = "file_upload_date")
    private LocalDateTime fileUploadDate;  //date , time 저장
    @Column(name = "file_update_date")
    private LocalDateTime fileUpdateDate;

    @Column(name = "file_download_date")
    private LocalDateTime fileDownloadDate;
    @Builder
    public Metadata(Long fileId, String fileExtension, Long fileSize, LocalDateTime fileUploadDate) {
        this.fileId = fileId;
        this.fileExtension = fileExtension;
        this.fileSize = fileSize;
        this.fileUploadDate = fileUploadDate;
    }
}
