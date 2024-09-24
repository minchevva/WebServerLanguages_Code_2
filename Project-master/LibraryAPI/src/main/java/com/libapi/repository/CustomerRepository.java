package com.libapi.repository;

import com.libapi.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link CustomerEntity} entities in the database.
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
