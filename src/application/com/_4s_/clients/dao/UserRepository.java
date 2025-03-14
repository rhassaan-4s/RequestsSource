package com._4s_.clients.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com._4s_.clients.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user FROM User AS user INNER JOIN FETCH user.tenant"
            + " WHERE user.email = :email"
            + " AND user.tenant.slug = :slug")
    Optional<User> findUser(String email, String slug);

    @Query("SELECT user FROM User AS user"
            + " WHERE user.tenant IS NULL"
            + " AND user.email = :email"
            + " AND user.role = 0")
    Optional<User> findGeneralAdmin(String email);
}
