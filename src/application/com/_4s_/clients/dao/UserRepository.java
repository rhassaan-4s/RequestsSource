package com._4s_.clients.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com._4s_.clients.model.Role;
import com._4s_.clients.model.User;

@Repository(value = "userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM com._4s_.clients.model.User u " +
	           "JOIN u.tenant t " +   // standard join, not necessarily fetch
	           "WHERE u.email = :email " +
	           "AND t.slug = :slug")
    Optional<User> findUser(@Param("email") String email, @Param("slug") String slug);

	@Query("SELECT u FROM com._4s_.clients.model.User u WHERE u.tenant IS NULL AND u.email = :email AND u.role = :role")
    Optional<User> findGeneralAdmin(@Param("email") String email, @Param("role") Role role);
}
