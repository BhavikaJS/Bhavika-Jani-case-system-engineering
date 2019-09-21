package com.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.model.User;

/*
 * Data access object interface for accessing the database
 */
@Repository("UserDao")
public interface UserDao extends JpaRepository<User, Long> {
	User findByEmail(String email);

	Long deleteByEmail(String email);
}