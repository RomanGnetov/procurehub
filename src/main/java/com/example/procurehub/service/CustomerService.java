package com.example.procurehub.service;

import com.example.jooq.tables.pojos.Customer;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.jooq.tables.Customer.CUSTOMER;

@Service
public class CustomerService {
    private final DSLContext dsl;

    public CustomerService(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<Customer> findAll() {
        return dsl.selectFrom(CUSTOMER)
                .fetchInto(Customer.class);
    }

    public Optional<Customer> findByCode(String code) {
        return dsl.selectFrom(CUSTOMER)
                .where(CUSTOMER.CUSTOMER_CODE.eq(code))
                .fetchOptionalInto(Customer.class);
    }

    public Customer save(Customer customer) {
        dsl.insertInto(CUSTOMER)
                .set(dsl.newRecord(CUSTOMER, customer))
                .execute();
        return customer;
    }

    public Optional<Customer> update(String code, Customer customer) {
        int updated = dsl.update(CUSTOMER)
                .set(dsl.newRecord(CUSTOMER, customer))
                .where(CUSTOMER.CUSTOMER_CODE.eq(code))
                .execute();
        return updated == 1 ? Optional.of(customer) : Optional.empty();
    }

    public boolean delete(String code) {
        return dsl.deleteFrom(CUSTOMER)
                .where(CUSTOMER.CUSTOMER_CODE.eq(code))
                .execute() == 1;
    }
}
