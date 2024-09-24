package com.libapi.service;

import com.libapi.entity.BookEntity;
import com.libapi.entity.CustomerEntity;
import com.libapi.repository.CustomerRepository;
import com.libapi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing customer-related operations.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookRepository bookRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, BookRepository bookRepository) {
        this.customerRepository = customerRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieve a list of all customers.
     *
     * @return List of CustomerEntity objects.
     */
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Retrieve a customer by their unique identifier.
     *
     * @param id The unique identifier of the customer.
     * @return CustomerEntity object or null if not found.
     */
    public CustomerEntity getCustomerById(Long id) {
        Optional<CustomerEntity> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    /**
     * Create a new customer.
     *
     * @param customerEntity The customer entity to be created.
     * @return Created CustomerEntity object.
     */
    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
        return customerRepository.save(customerEntity);
    }

    /**
     * Update an existing customer by their unique identifier.
     *
     * @param id             The unique identifier of the customer to be updated.
     * @param customerEntity The updated customer entity.
     * @return Updated CustomerEntity object or null if not found.
     */
    public CustomerEntity updateCustomer(Long id, CustomerEntity customerEntity) {
        if (customerRepository.existsById(id)) {
            customerEntity.setId(id);
            return customerRepository.save(customerEntity);
        } else {
            return null; // Handle not found error
        }
    }

    /**
     * Delete a customer by their unique identifier.
     *
     * @param id The unique identifier of the customer to be deleted.
     */
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    /**
     * Add a book to a customer's collection.
     *
     * @param customerId The unique identifier of the customer.
     * @param bookId     The unique identifier of the book to be added.
     * @return Updated CustomerEntity object or null if not found.
     */
    public CustomerEntity addBookToCustomer(Long customerId, Long bookId) {
        CustomerEntity customer = customerRepository.findById(customerId).orElse(null);
        BookEntity book = bookRepository.findById(bookId).orElse(null);

        if (customer != null && book != null) {
            customer.getBooks().add(book);
            book.setCustomer(customer);
            customerRepository.save(customer);
            bookRepository.save(book);

            return customer;
        } else {
            return null; // Handle not found error
        }
    }
}
