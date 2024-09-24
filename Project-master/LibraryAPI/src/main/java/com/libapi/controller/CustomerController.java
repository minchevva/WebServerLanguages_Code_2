package com.libapi.controller;

import com.libapi.entity.CustomerEntity;
import com.libapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing customer-related operations.
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieve a list of all customers.
     *
     * @return List of CustomerEntity objects.
     */
    @GetMapping
    public List<CustomerEntity> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * Retrieve a customer by their unique identifier.
     *
     * @param id The unique identifier of the customer.
     * @return CustomerEntity object.
     */
    @GetMapping("/{id}")
    public CustomerEntity getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    /**
     * Create a new customer.
     *
     * @param customerEntity The customer entity to be created.
     * @return Created CustomerEntity object.
     */
    @PostMapping
    public CustomerEntity createCustomer(@RequestBody CustomerEntity customerEntity) {
        return customerService.createCustomer(customerEntity);
    }

    /**
     * Update an existing customer by their unique identifier.
     *
     * @param id             The unique identifier of the customer to be updated.
     * @param customerEntity The updated customer entity.
     * @return Updated CustomerEntity object.
     */
    @PutMapping("/{id}")
    public CustomerEntity updateCustomer(@PathVariable Long id, @RequestBody CustomerEntity customerEntity) {
        return customerService.updateCustomer(id, customerEntity);
    }

    /**
     * Delete a customer by their unique identifier.
     *
     * @param id The unique identifier of the customer to be deleted.
     */
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    /**
     * Add a book to a customer's collection.
     *
     * @param customerId The unique identifier of the customer.
     * @param bookId     The unique identifier of the book to be added.
     * @return Updated CustomerEntity object.
     */
    @PostMapping("/{customerId}/addBook/{bookId}")
    public CustomerEntity addBookToCustomer(@PathVariable Long customerId, @PathVariable Long bookId) {
        return customerService.addBookToCustomer(customerId, bookId);
    }
}
