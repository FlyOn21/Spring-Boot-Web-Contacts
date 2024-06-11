package org.example.app.springbootwebcontacts.repository.customer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.app.springbootwebcontacts.entity.customer.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@AllArgsConstructor
@NoArgsConstructor
public class CheckCustomerEntity {

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger CRUD_LOGGER =
            LogManager.getLogger(CheckCustomerEntity.class);
    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");

    @Transactional
    public boolean isEmailExists(String email) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> cr = cb.createQuery(Long.class);
            Root<Customer> root = cr.from(Customer.class);
            cr.select(cb.count(root)).where(cb.equal(root.get("email"), email));
            Long resultCount = entityManager.createQuery(cr).getSingleResult();

            return resultCount > 0;
        } catch (Exception e) {
            CRUD_LOGGER.error(String.format("Error checking email existence: %s", e.getMessage()), e);
            CONSOLE_LOGGER.error(String.format("Error checking email existence: %s", e.getMessage()), e);
            throw new RuntimeException(String.format("Error checking email existence: %s", e.getMessage()), e);
        }
    }

    @Transactional
    public boolean isCustomerEmail(String email, Long id) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> cr = cb.createQuery(Long.class);
            Root<Customer> root = cr.from(Customer.class);
            cr.select(cb.count(root)).where(
                    cb.and(
                            cb.equal(root.get("email"), email),
                            cb.equal(root.get("id"), id)
                    )
            );
            Long resultCount = entityManager.createQuery(cr).getSingleResult();
            return resultCount > 0;
        } catch (NoResultException e) {
            return false;
        } catch (NonUniqueResultException e) {
            CRUD_LOGGER.error(String.format("Non-unique result for customer email: %s", e.getMessage()), e);
            CONSOLE_LOGGER.error(String.format("Non-unique result for customer email: %s", e.getMessage()), e);
            throw new RuntimeException(String.format("Non-unique result for customer email: %s", e.getMessage()), e);
        } catch (Exception e) {
            CRUD_LOGGER.error(String.format("Error checking customer email: %s", e.getMessage()), e);
            CONSOLE_LOGGER.error(String.format("Error checking customer email: %s", e.getMessage()), e);
            throw new RuntimeException(String.format("Error checking customer email: %s", e.getMessage()), e);
        }
    }

}
