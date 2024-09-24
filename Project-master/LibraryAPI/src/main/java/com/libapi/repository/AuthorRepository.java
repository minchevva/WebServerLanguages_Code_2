package com.libapi.repository;

import com.libapi.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link AuthorEntity} entities in the database.
 */
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
}