package com.libapi.repository;

import com.libapi.entity.LibraryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link LibraryEntity} entities in the database.
 */
public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
}