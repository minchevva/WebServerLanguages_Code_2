package com.libapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing books in a library.
 */
@Entity
@Getter
@Setter
@Table(name = "book")
public class BookEntity {

    /**
     * Unique identifier for the book.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The title of the book.
     */
    @Column(name = "title")
    private String title;

    /**
     * A many-to-many relationship with authors who wrote the book.
     */
    @ManyToMany(mappedBy = "books")
    private Set<AuthorEntity> authors = new HashSet<>();

    /**
     * The library where the book is located.
     */
    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "library_id")
    private LibraryEntity library;

    /**
     * The customer who has borrowed the book.
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;
}
