package org.c4marathon.assignment.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "level_id", nullable = false, columnDefinition = "bigint default 1")
    private Long levelId;
    private String name;
    private String key;
    @Column(name = "total_storage", columnDefinition = "bigint default 0")
    private Long totalStorage;

    @Builder
    public User(String name, String key){
        this.name = name;
        this.key = key;
    }

}
