package com.example.procurehub.controller;

import com.example.jooq.tables.pojos.Customer;
import com.example.procurehub.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.findAll();
    }

    @GetMapping("/{code}")
    public ResponseEntity<Customer> getByCode(@PathVariable String code) {
        return customerService.findByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        Customer created = customerService.save(customer);
        URI location = URI.create(String.format("/api/customers/%s", created.getCustomerCode()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Customer> update(@PathVariable String code, @RequestBody Customer customer) {
        Optional<Customer> updated = customerService.update(code, customer);
        return updated
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        boolean deleted = customerService.delete(code);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}