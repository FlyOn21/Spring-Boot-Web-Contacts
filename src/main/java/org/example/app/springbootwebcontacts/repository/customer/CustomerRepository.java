package org.example.app.springbootwebcontacts.repository.customer;

import org.example.app.springbootwebcontacts.entity.customer.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    
    
}
