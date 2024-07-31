package org.c4marathon.assignment.data.repository;

import org.c4marathon.assignment.data.entity.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetadataRepository extends JpaRepository<Metadata, Long> {

    @Override
    Optional<Metadata> findById(Long id);
}
