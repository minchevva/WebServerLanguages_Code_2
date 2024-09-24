package com.libapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing authors of books.
 */
@Entity
@Getter
@Setter
@Table(name = "author")
public class AuthorEntity {

    /**
     * Unique identifier for the author.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The name of the author.
     */
    @Column
    private String name;

    /**
     * A many-to-many relationship with books authored by this author.
     */
    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(
                    name = "author_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "book_id")
    )
    @JsonIgnore
    private Set<BookEntity> books = new HashSet<>();
}
