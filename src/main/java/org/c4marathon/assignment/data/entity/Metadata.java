package org.c4marathon.assignment.data.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long file_id;
    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_saved_url")
    private String fileSavedUrl;

    @Column(name = "file_upload_date")
    private LocalDateTime fileUploadDate;  //date , time 저장

    @Column(name = "file_update_date")
    private LocalDateTime fileUpdateDate;

    @Column(name = "file_download_date")
    private LocalDateTime fileDownloadDate;
    @Builder
    public Metadata(String fileExtension, Long fileSize, String fileSavedUrl, LocalDateTime fileUploadDate) {
        this.fileExtension = fileExtension;
        this.fileSize = fileSize;
        this.fileSavedUrl = fileSavedUrl;
        this.fileUploadDate = fileUploadDate;
    }
}
