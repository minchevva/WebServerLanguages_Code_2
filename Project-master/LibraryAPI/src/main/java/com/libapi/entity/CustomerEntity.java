package com.libapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing customers who borrow books from a library.
 */
@Entity
@Getter
@Setter
@Table(name = "customer")
public class CustomerEntity {

    /**
     * Unique identifier for the customer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The name of the customer.
     */
    @Column(name = "name")
    private String name;

    /**
     * A one-to-many relationship with books borrowed by this customer.
     */
    @OneToMany(mappedBy = "customer")
    private List<BookEntity> books = new ArrayList<>();
}
