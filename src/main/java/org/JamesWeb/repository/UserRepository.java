package org.JamesWeb.repository;

import org.JamesWeb.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<users, Long> {
    
    @Query("""
        SELECT u FROM users u 
        WHERE u.email = :email
    """)
    Optional<users> findByEmail(@Param("email") String email);
    
    boolean existsByEmail(String email);
}
