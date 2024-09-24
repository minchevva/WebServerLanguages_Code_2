package com.libapi.repository;

import com.libapi.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link BookEntity} entities in the database.
 */
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}