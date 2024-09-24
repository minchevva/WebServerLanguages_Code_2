package com.libapi.controller;

import com.libapi.entity.BookEntity;
import com.libapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing book-related operations.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieve a list of all books.
     *
     * @return List of BookEntity objects.
     */
    @GetMapping
    public List<BookEntity> getAllBooks() {
        return bookService.getAllBooks();
    }

    /**
     * Retrieve a book by its unique identifier.
     *
     * @param id The unique identifier of the book.
     * @return BookEntity object.
     */
    @GetMapping("/{id}")
    public BookEntity getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    /**
     * Create a new book.
     *
     * @param bookEntity The book entity to be created.
     * @return Created BookEntity object.
     */
    @PostMapping
    public BookEntity createBook(@RequestBody BookEntity bookEntity) {
        return bookService.createBook(bookEntity);
    }

    /**
     * Update an existing book by its unique identifier.
     *
     * @param id           The unique identifier of the book to be updated.
     * @param bookEntity The updated book entity.
     * @return Updated BookEntity object.
     */
    @PutMapping("/{id}")
    public BookEntity updateBook(@PathVariable Long id, @RequestBody BookEntity bookEntity) {
        return bookService.updateBook(id, bookEntity);
    }

    /**
     * Delete a book by its unique identifier.
     *
     * @param id The unique identifier of the book to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
