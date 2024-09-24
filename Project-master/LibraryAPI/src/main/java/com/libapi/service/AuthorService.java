package com.libapi.service;

import com.libapi.entity.AuthorEntity;
import com.libapi.entity.BookEntity;
import com.libapi.repository.AuthorRepository;
import com.libapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing author-related operations.
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieve a list of all authors.
     *
     * @return List of AuthorEntity objects.
     */
    public List<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Retrieve an author by their unique identifier.
     *
     * @param id The unique identifier of the author.
     * @return AuthorEntity object or null if not found.
     */
    public AuthorEntity getAuthorById(Long id) {
        Optional<AuthorEntity> author = authorRepository.findById(id);
        return author.orElse(null);
    }

    /**
     * Create a new author.
     *
     * @param authorEntity The author entity to be created.
     * @return Created AuthorEntity object.
     */
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    /**
     * Update an existing author by their unique identifier.
     *
     * @param id           The unique identifier of the author to be updated.
     * @param authorEntity The updated author entity.
     * @return Updated AuthorEntity object or null if not found.
     */
    public AuthorEntity updateAuthor(Long id, AuthorEntity authorEntity) {
        if (authorRepository.existsById(id)) {
            authorEntity.setId(id);
            return authorRepository.save(authorEntity);
        } else {
            return null; // Handle not found error
        }
    }

    /**
     * Delete an author by their unique identifier.
     *
     * @param id The unique identifier of the author to be deleted.
     */
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    /**
     * Associate a book with an author.
     *
     * @param authorId The unique identifier of the author.
     * @param bookId   The unique identifier of the book to be associated.
     * @return Updated AuthorEntity object or null if not found.
     */
    public AuthorEntity associateBookWithAuthor(Long authorId, Long bookId) {
        AuthorEntity author = authorRepository.findById(authorId).orElse(null);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (author != null && book != null) {
            author.getBooks().add(book);
            book.getAuthors().add(author);
            authorRepository.save(author);
            bookRepository.save(book);

            return author;
        } else {
            return null; // Handle not found error
        }
    }
}
