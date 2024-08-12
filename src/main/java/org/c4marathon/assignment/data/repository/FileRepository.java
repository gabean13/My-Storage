package org.c4marathon.assignment.data.repository;

import org.c4marathon.assignment.data.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByUserIdAndName(Long userId, String name);

    Optional<File> findByIdAndUserId(Long fileId, Long userId);
}
