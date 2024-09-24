package com.libapi.service;

import com.libapi.entity.BookEntity;
import com.libapi.entity.LibraryEntity;
import com.libapi.repository.BookRepository;
import com.libapi.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing library-related operations.
 */
@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    private final BookRepository bookRepository;

    @Autowired
    public LibraryService(
            LibraryRepository libraryRepository,
            BookRepository bookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieve a list of all libraries.
     *
     * @return List of LibraryEntity objects.
     */
    public List<LibraryEntity> getAllLibraries() {
        return libraryRepository.findAll();
    }

    /**
     * Retrieve a library by its unique identifier.
     *
     * @param id The unique identifier of the library.
     * @return LibraryEntity object or null if not found.
     */
    public LibraryEntity getLibraryById(Long id) {
        Optional<LibraryEntity> library = libraryRepository.findById(id);
        return library.orElse(null);
    }

    /**
     * Create a new library.
     *
     * @param libraryEntity The library entity to be created.
     * @return Created LibraryEntity object.
     */
    public LibraryEntity createLibrary(LibraryEntity libraryEntity) {
        return libraryRepository.save(libraryEntity);
    }

    /**
     * Update an existing library by its unique identifier.
     *
     * @param id              The unique identifier of the library to be updated.
     * @param libraryEntity The updated library entity.
     * @return Updated LibraryEntity object or null if not found.
     */
    public LibraryEntity updateLibrary(Long id, LibraryEntity libraryEntity) {
        if (libraryRepository.existsById(id)) {
            libraryEntity.setId(id);
            return libraryRepository.save(libraryEntity);
        } else {
            return null; // Handle not found error
        }
    }

    /**
     * Delete a library by its unique identifier.
     *
     * @param id The unique identifier of the library to be deleted.
     */
    public void deleteLibrary(Long id) {
        libraryRepository.deleteById(id);
    }

    /**
     * Associate a book with a library.
     *
     * @param libraryId The unique identifier of the library.
     * @param bookId     The unique identifier of the book to be associated with the library.
     * @return Updated LibraryEntity object or null if not found.
     */
    public LibraryEntity associateBookWithLibrary(
            Long libraryId,
            Long bookId) {
        LibraryEntity library = libraryRepository.findById(libraryId).orElse(null);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (library != null && book != null) {
            library.getBooks().add(book);
            book.setLibrary(library);
            libraryRepository.save(library);
            bookRepository.save(book);

            return library;
        } else {
            return null;
        }
    }
}
