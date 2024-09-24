package com.libapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.print.Book;
import java.util.List;

/**
 * Entity class representing a library that contains books.
 */
@Entity
@Getter
@Setter
@Table(name = "library")
public class LibraryEntity {

    /**
     * Unique identifier for the library.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The name of the library.
     */
    @Column
    private String name;

    /**
     * A one-to-many relationship with books stored in this library.
     */
    @OneToMany(mappedBy = "library")
    private List<BookEntity> books;
}
