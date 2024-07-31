package org.c4marathon.assignment.data.repository;

import org.c4marathon.assignment.data.entity.UserLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLevelRepository extends JpaRepository<UserLevel, Long> {
}
