package org.JamesWeb.repository;

import org.JamesWeb.model.User_roles;
import org.JamesWeb.model.users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRolesRepo extends JpaRepository<User_roles,Long> {
    List<User_roles> findByUsersId(Long id);
}
