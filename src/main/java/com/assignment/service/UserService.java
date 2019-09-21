package com.assignment.service;

import java.util.List;

import com.assignment.model.User;

/**
 * User Service interface that connects the service layer and the Database layer
 */
public interface UserService {

	User findUserByEmail(String email);

	User saveNewUser(User user);

	User updateUserDetails(User user) throws Exception;

	String deleteUser(User user);

	List<User> getAllUsers();

	Boolean userLogin(String userNamePassword);

	User resetPassword(String userEmail, String userOldPassword, String userNewPassword);

	User forgotPassword(String userEmail, String userName, String region, String newPassword);
}