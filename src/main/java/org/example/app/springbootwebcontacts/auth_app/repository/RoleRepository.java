package org.example.app.springbootwebcontacts.auth_app.repository;

import org.example.app.springbootwebcontacts.auth_app.entitys.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories(basePackages = "org.example.app.springbootfxwebcontacts.auth.repository")
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
