package org.example.app.springbootwebcontacts.contacts_app.repository;

import org.example.app.springbootwebcontacts.auth_app.entitys.entity.User;
import org.example.app.springbootwebcontacts.contacts_app.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactsRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByEmail(String email);
    Optional<Contact> findByEmailAndId(String email, Long id);
    Iterable<Contact> findByOwner(User user);
}
