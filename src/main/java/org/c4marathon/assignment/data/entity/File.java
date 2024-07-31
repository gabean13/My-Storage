package org.c4marathon.assignment.data.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Entity(name = "file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    private String path;
    private String name;
    private String uuid;

    @Builder
    public File(Long userId, String path, String name, String uuid) {
        this.userId = userId;
        this.path = path;
        this.name = name;
        this.uuid = uuid;
    }
}
