package com.assignment.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.assignment.model.User;
import com.assignment.service.UserServiceImpl;

/*
 * Controller for the Application
 */
@RestController
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

	/**
	 * Method to fetch all the users
	 * 
	 * @return HttpStatus with list of users present in database
	 */
	@GetMapping("/users")
	public ResponseEntity<List<User>> getUsers() {
		logger.info("Retrieving Users");
		List<User> list = userService.getAllUsers();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}

	/**
	 * Method enables user to register
	 * 
	 * @param user object with valid details
	 * @return HttpStatus with a String response
	 */
	@PostMapping(value = "/adduser", consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> addNewUser(@Valid @RequestBody User user) {
		logger.info("inserting new user");
		userService.saveNewUser(user);
		return new ResponseEntity<String>("User added", HttpStatus.CREATED);

	}

	/**
	 * Method enables user to view their details
	 * 
	 * @param email of the user
	 * @return HttpStatus with the user object
	 */
	@GetMapping(value = "/users/{email}")
	public ResponseEntity<User> getUserByID(@PathVariable("email") String email) {
		logger.info("Retrieving User for the " + email);
		User user = userService.findUserByEmail(email);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Method enables user to login
	 * 
	 * @param user credentials in encoded form
	 * @return HttpStatus with a String response if login was successful
	 */
	@PostMapping(value = "/login")
	public ResponseEntity<String> login(@RequestHeader("Authorization") String authorization) {
		boolean loginPossible = userService.userLogin(authorization);
		if (loginPossible == true) {
			return new ResponseEntity<String>("Login successful", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Unauthorized access", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * Method enables user to update the details
	 * 
	 * @param user object to be updated
	 * @return HttpStatus with the updated user object
	 */
	@PutMapping(value = "/updateuser")
	public ResponseEntity<User> updateDetails(@Valid @RequestBody User user) {
		logger.info("updating the user" + user.getEmail());
		User updateUser = userService.updateUserDetails(user);
		return new ResponseEntity<User>(updateUser, HttpStatus.OK);
	}

	/**
	 * Method enables user to delete account
	 * 
	 * @param user object to be deleted
	 * @return HttpStatus with a String response
	 */
	@DeleteMapping(value = "/deleteuser")
	public ResponseEntity<String> deleteUser(@RequestBody User user) {
		logger.info("deleting the user" + user.getEmail());
		String response = userService.deleteUser(user);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	/**
	 * Method enables user to reset their password
	 * 
	 * @param email       of the user
	 * @param oldPassword of the user
	 * @param newPassword of the user
	 * @return HttpStatus if updated successfully
	 */
	@PutMapping(value = "/resetpassword")
	public ResponseEntity<User> resetPassword(@RequestParam String email, @RequestParam String oldPassword,
			@RequestParam String newPassword) {
		logger.info("reset password for the user" + email);
		User updatePasswordUser = userService.resetPassword(email, oldPassword, newPassword);
		return new ResponseEntity<User>(updatePasswordUser, HttpStatus.OK);
	}

	/**
	 * Method enables user to set a new password if forgets oldPassword
	 * 
	 * @param email       of the user
	 * @param firstName   of the user
	 * @param Region      of the user
	 * @param newPassword to be updated
	 * @return HttpStatus with a String response
	 */
	@PutMapping(value = "/forgotpassword")
	public ResponseEntity<String> forgotPassword(@RequestParam String email, @RequestParam String firstName,
			@RequestParam String Region, @RequestParam String newPassword) {
		logger.info("setting new password for the user" + email);
		userService.forgotPassword(email, firstName, Region, newPassword);
		return new ResponseEntity<String>("Password is updated", HttpStatus.OK);
	}

}