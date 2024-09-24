package com.libapi.controller;

import com.libapi.entity.AuthorEntity;
import com.libapi.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing author-related operations.
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Retrieve a list of all authors.
     *
     * @return List of AuthorEntity objects.
     */
    @GetMapping
    public List<AuthorEntity> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    /**
     * Retrieve an author by their unique identifier.
     *
     * @param id The unique identifier of the author.
     * @return AuthorEntity object.
     */
    @GetMapping("/{id}")
    public AuthorEntity getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    /**
     * Create a new author.
     *
     * @param authorEntity The author entity to be created.
     * @return Created AuthorEntity object.
     */
    @PostMapping
    public AuthorEntity createAuthor(@RequestBody AuthorEntity authorEntity) {
        return authorService.createAuthor(authorEntity);
    }

    /**
     * Update an existing author by their unique identifier.
     *
     * @param id           The unique identifier of the author to be updated.
     * @param authorEntity The updated author entity.
     * @return Updated AuthorEntity object.
     */
    @PutMapping("/{id}")
    public AuthorEntity updateAuthor(@PathVariable Long id, @RequestBody AuthorEntity authorEntity) {
        return authorService.updateAuthor(id, authorEntity);
    }

    /**
     * Delete an author by their unique identifier.
     *
     * @param id The unique identifier of the author to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

    /**
     * Add a book to an author's collection.
     *
     * @param authorId The unique identifier of the author.
     * @param bookId   The unique identifier of the book to be added.
     * @return Updated AuthorEntity object.
     */
    @PostMapping("/{authorId}/addBook/{bookId}")
    public AuthorEntity addBookToAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        return authorService.associateBookWithAuthor(authorId, bookId);
    }
}
