package org.example.app.springbootwebcontacts.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.springbootwebcontacts.entity.customer.Customer;
import org.example.app.springbootwebcontacts.exceptions.custome_exception.CRUDException;
import org.example.app.springbootwebcontacts.exceptions.custome_exception.ValidationException;
import org.example.app.springbootwebcontacts.repository.customer.CheckCustomerEntity;
import org.example.app.springbootwebcontacts.repository.customer.CustomerRepository;
import org.example.app.springbootwebcontacts.service.DTO.CustomerDTO;
import org.example.app.springbootwebcontacts.utils.utils_obj.ResponseDataDTO;
import org.example.app.springbootwebcontacts.utils.validate.create_update_form.FormDataForValidate;
import org.example.app.springbootwebcontacts.utils.validate.create_update_form.ValidationFormProcessing;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service("customerService")
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Autowired
    private CheckCustomerEntity checkCustomerEntity;


    private static final Logger SERVICE_LOGGER =
            LogManager.getLogger(CheckCustomerEntity.class);
    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");



    public ResponseDataDTO<Customer> create(FormDataForValidate input, String fragmentName) {
        try {
            ValidationFormProcessing validationForm = new ValidationFormProcessing(checkCustomerEntity, input);
            if (!validationForm.isValidForm()) {
                throw new ValidationException("Validation create form failed", validationForm, "customer_add");
            }

            Customer customer = new Customer(UUID.randomUUID(), input.getFirstName(), input.getLastName(), input.getEmail(), input.getPhoneNumber());
                Customer queryResult = repository.save(customer);
                return new ResponseDataDTO<>(
                        true,
                        true,
                        List.of(queryResult),
                        null,
                        "Customer created successfully",
                        Collections.emptyList(),
                        null
                );
        } catch (Exception e) {
            if (e instanceof DataAccessException || e instanceof HibernateException) {
                SERVICE_LOGGER.error("Failed to create customer: {}", e.getMessage());
                CONSOLE_LOGGER.error("Failed to create customer: {}", e.getMessage());
                throw new CRUDException("Failed to create customer", fragmentName);
            }
            throw e;
        }
    }

        public ResponseDataDTO<CustomerDTO> getAll(String fragmentName) {
        try{
            Iterable<Customer> queryResult = repository.findAll();
            List<CustomerDTO> customerDTOList = StreamSupport.stream(queryResult.spliterator(), false)
                    .map(CustomerDTO::new)
                    .collect(Collectors.toList());

            return new ResponseDataDTO<>(
                    true,
                    true,
                    customerDTOList,
                    null,
                    "Customers received successfully",
                    Collections.emptyList(),
                    null
            );
        } catch (Exception e) {
            if (e instanceof DataAccessException || e instanceof HibernateException) {
                SERVICE_LOGGER.error("Failed to get customers: {}", e.getMessage(), e);
                CONSOLE_LOGGER.error("Failed to get customers: {}", e.getMessage(), e);
                throw new CRUDException("Failed to get customers", fragmentName);
            }
            throw e;
        }
    }


    public ResponseDataDTO<Customer> getById(Long id, String fragmentName) {
        try {
            Optional<Customer> getByIdResult = repository.findById(id);
            return getByIdResult.map(customer -> new ResponseDataDTO<>(
                    true,
                    true,
                    List.of(customer),
                    null,
                    "Customer received successfully",
                    Collections.emptyList(),
                    null
            )).orElseGet(() -> new ResponseDataDTO<>(
                    false,
                    false,
                    null,
                    null,
                    "Customer not found",
                    List.of("Customer not found"),
                    null
            ));
        } catch (Exception e) {
            if (e instanceof DataAccessException || e instanceof HibernateException) {
                SERVICE_LOGGER.error("Failed to get customer by id: {}", e.getMessage(), e);
                CONSOLE_LOGGER.error("Failed to get customer by id: {}", e.getMessage(), e);
                throw new CRUDException("Failed to get by id customer", fragmentName);
            }
            throw e;
        }
    }

    public ResponseDataDTO<Customer> update(FormDataForValidate input, String fragmentName) {
        try {
            Optional<Customer> customerData = repository.findById(input.getId());
            if (customerData.isEmpty()) {
                return new ResponseDataDTO<>(
                        false,
                        false,
                        null,
                        null,
                        "Customer not found",
                        List.of("Customer not found"),
                        null
                );
            }
            Customer customer = customerData.get();
            ValidationFormProcessing validationForm = new ValidationFormProcessing(checkCustomerEntity, input, customer.getId());
            if (!validationForm.isValidForm()) {
                throw new ValidationException("Validation update form failed", validationForm, fragmentName);
            }

            customer.setFirstName(input.getFirstName() == null ? customer.getFirstName() : input.getFirstName());
            customer.setLastName(input.getLastName() == null ? customer.getLastName() : input.getLastName());
            customer.setEmail(input.getEmail() == null ? customer.getEmail() : input.getEmail());
            customer.setPhoneNumber(input.getPhoneNumber() == null ? customer.getPhoneNumber() : input.getPhoneNumber());
            customer.setUpdatedAt(System.currentTimeMillis());
            Customer queryResult = repository.save(customer);
            return new ResponseDataDTO<>(
                    true,
                    true,
                    List.of(queryResult),
                    null,
                    "Customer updated successfully",
                    Collections.emptyList(),
                    null
            );

        } catch (Exception e) {
            if (e instanceof DataAccessException || e instanceof HibernateException) {
                SERVICE_LOGGER.error("Failed to update customer: {}", e.getMessage(), e);
                CONSOLE_LOGGER.error("Failed to update customer: {}", e.getMessage(), e);
                throw new CRUDException("Failed to update customer", fragmentName);
            }
            throw e;
        }
    }

    public ResponseDataDTO<Customer> delete(Long id, String fragmentName) {
        try {
            Optional<Customer> getByIdResult = repository.findById(id);
            if (getByIdResult.isEmpty()) {
                return new ResponseDataDTO<>(
                        false,
                        false,
                        null,
                        null,
                        "Customer not found",
                        List.of("Customer not found"),
                        null
                );
            }
            repository.deleteById(id);
            return new ResponseDataDTO<>(
                    true,
                    true,
                    List.of(getByIdResult.get()),
                    null,
                    "Customer deleted successfully",
                    Collections.emptyList(),
                    null
            );

        } catch (Exception e) {
            if (e instanceof DataAccessException || e instanceof HibernateException) {
                SERVICE_LOGGER.error("Failed to delete customer: {}", e.getMessage(), e);
                CONSOLE_LOGGER.error("Failed to delete customer: {}", e.getMessage(), e);
                throw new CRUDException("Failed to delete customer", fragmentName);
            }
            throw e;
        }
    }
}
