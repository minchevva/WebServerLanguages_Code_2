package com.libapi.service;

import com.libapi.entity.BookEntity;
import com.libapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing book-related operations.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieve a list of all books.
     *
     * @return List of BookEntity objects.
     */
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieve a book by its unique identifier.
     *
     * @param id The unique identifier of the book.
     * @return BookEntity object or null if not found.
     */
    public BookEntity getBookById(Long id) {
        Optional<BookEntity> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    /**
     * Create a new book.
     *
     * @param bookEntity The book entity to be created.
     * @return Created BookEntity object.
     */
    public BookEntity createBook(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    /**
     * Update an existing book by its unique identifier.
     *
     * @param id           The unique identifier of the book to be updated.
     * @param bookEntity The updated book entity.
     * @return Updated BookEntity object or null if not found.
     */
    public BookEntity updateBook(Long id, BookEntity bookEntity) {
        if (bookRepository.existsById(id)) {
            bookEntity.setId(id);
            return bookRepository.save(bookEntity);
        } else {
            return null; // Handle not found error
        }
    }

    /**
     * Delete a book by its unique identifier.
     *
     * @param id The unique identifier of the book to be deleted.
     */
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
