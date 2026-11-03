package org.JamesWeb.repository;

import org.JamesWeb.model.Role;
import org.JamesWeb.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepo extends JpaRepository<Roles,Long> {
    Optional<Roles> findByRole(Role role);
}
