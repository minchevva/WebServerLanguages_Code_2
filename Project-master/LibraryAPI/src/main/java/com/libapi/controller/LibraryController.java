package com.libapi.controller;

import com.libapi.entity.LibraryEntity;
import com.libapi.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing library-related operations.
 */
@RestController
@RequestMapping("/libraries")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     * Retrieve a list of all libraries.
     *
     * @return List of LibraryEntity objects.
     */
    @GetMapping
    public List<LibraryEntity> getAllLibraries() {
        return libraryService.getAllLibraries();
    }

    /**
     * Add a book to a library's collection.
     *
     * @param libraryId The unique identifier of the library.
     * @param bookId    The unique identifier of the book to be added.
     * @return Updated LibraryEntity object.
     */
    @PostMapping("/{libraryId}/addBook/{bookId}")
    public LibraryEntity addBookToLibrary(
            @PathVariable Long libraryId,
            @PathVariable Long bookId) {
        return libraryService.associateBookWithLibrary(
                libraryId,
                bookId);
    }

    /**
     * Retrieve a library by its unique identifier.
     *
     * @param id The unique identifier of the library.
     * @return LibraryEntity object.
     */
    @GetMapping("/{id}")
    public LibraryEntity getLibraryById(@PathVariable Long id) {
        return libraryService.getLibraryById(id);
    }

    /**
     * Create a new library.
     *
     * @param libraryEntity The library entity to be created.
     * @return Created LibraryEntity object.
     */
    @PostMapping
    public LibraryEntity createLibrary(@RequestBody LibraryEntity libraryEntity) {
        return libraryService.createLibrary(libraryEntity);
    }

    /**
     * Update an existing library by its unique identifier.
     *
     * @param id             The unique identifier of the library to be updated.
     * @param libraryEntity The updated library entity.
     * @return Updated LibraryEntity object.
     */
    @PutMapping("/{id}")
    public LibraryEntity updateLibrary(@PathVariable Long id, @RequestBody LibraryEntity libraryEntity) {
        return libraryService.updateLibrary(id, libraryEntity);
    }

    /**
     * Delete a library by its unique identifier.
     *
     * @param id The unique identifier of the library to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteLibrary(@PathVariable Long id) {
        libraryService.deleteLibrary(id);
    }
}
