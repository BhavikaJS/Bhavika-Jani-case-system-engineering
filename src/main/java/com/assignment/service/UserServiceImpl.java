package com.assignment.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.exception.ChangePasswordException;
import com.assignment.exception.IncorrectNameAndRegionException;
import com.assignment.exception.UserNotFoundException;
import com.assignment.exception.WrongCredentialsException;
import com.assignment.model.User;
import com.assignment.repository.UserDao;

/**
 * Implementation class for UserService with the business logic
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	public static String USER_DELETED = "User is successfully deleted";
	public static String CANNOT_DELETE_USER = "User can't be deleted";
	@Autowired
	private UserDao userDao;

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * Method is used to find the user in the database using the emailId
	 * 
	 * @param email to fetch the user
	 * @return user found in the database
	 * @throws UserNotFoundException
	 */
	@Override
	public User findUserByEmail(String email) {
		User userInDB = userDao.findByEmail(email);
		if (userInDB != null) {
			return userDao.findByEmail(email);
		} else {
			throw new UserNotFoundException();
		}
	}

	/**
	 * Method updates the User Details if the User exists and provides correct
	 * password
	 * 
	 * @param user object to be updated
	 * @return the user with updated details
	 * @throws WrongCredentialsException
	 */
	@Override
	public User updateUserDetails(User user) {
		User userToBeUpdated = findUserByEmail(user.getEmail());
		if (userToBeUpdated.getPassword().equals(user.getPassword())) {
			userToBeUpdated.setFirstName(user.getFirstName());
			userToBeUpdated.setPassword(user.getPassword());
			userToBeUpdated.setLastName(user.getLastName());
			userToBeUpdated.setRegion(user.getRegion());
			return userDao.save(userToBeUpdated);
		} else {
			throw new WrongCredentialsException();
		}
	}

	/**
	 * Method deletes the user if exists and has valid credentials
	 * 
	 * @param user object to be deleted
	 * @return string response if the object was successfully deleted
	 * @throws WrongCredentialsException
	 */
	@Override
	public String deleteUser(User user) {
		User updatedUser = findUserByEmail(user.getEmail());
		Long ifDeleted = 0L;
		if (updatedUser.getPassword().equals(user.getPassword())) {
			ifDeleted = userDao.deleteByEmail(user.getEmail());
			if (ifDeleted > 0L) {
				return USER_DELETED;
			}
		} else {
			throw new WrongCredentialsException();
		}
		return CANNOT_DELETE_USER;
	}

	/**
	 * Method provides the complete list of Users from the database
	 * 
	 * @param no parameter
	 * @return list of users present in the database
	 */
	@Override
	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	/**
	 * Method adds new user to the database
	 * 
	 * @param user object to be added
	 * @return new user added to the database
	 */
	@Override
	public User saveNewUser(User user) {
		return userDao.save(user);
	}

	/**
	 * Method provides the user login functionality, based on the basic
	 * authentication
	 * 
	 * @param base64encoded username:password string
	 * @return boolean response, if the user exists and passes validation
	 * @throws WrongCredentialsException
	 */
	@Override
	public Boolean userLogin(String userNamePassword) {
		String username = null;
		String password = null;
		if (userNamePassword != null && userNamePassword.toLowerCase().startsWith("basic")) {
			// Authorization: Basic base64credentials
			String base64Credentials = userNamePassword.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			// credentials = username:password
			final String[] values = credentials.split(":", 2);
			username = values[0];
			password = values[1];
		}
		User user = findUserByEmail(username);

		if (user.getPassword().equals(password)) {
			return true;
		} else {
			throw new WrongCredentialsException();
		}
	}

	/**
	 * Method will reset the password for the given user, if the user exists and the
	 * old credentials can be verified
	 * 
	 * @param email       of the user
	 * @param oldPassword of the user
	 * @param newPassword of the user
	 * @return user object with updated password
	 * @throws WrongCredentialsException, ChangePasswordException
	 */
	@Override
	public User resetPassword(String userEmail, String userOldPassword, String userNewPassword) {
		User user = findUserByEmail(userEmail);
		if (!userOldPassword.equals(userNewPassword)) {
			if (user.getPassword().equals(userOldPassword)) {
				user.setPassword(userNewPassword);
				userDao.save(user);
				return user;
			} else {
				throw new WrongCredentialsException();
			}
		} else {
			throw new ChangePasswordException();
		}

	}

	/**
	 * Method provides the functionality to the user to generate a new password if
	 * the combination of firstname and Region is valid
	 * 
	 * @param email     of the user
	 * @param firstName of the user
	 * @param region    of the user
	 * @return user object with updated details
	 * @throws IncorrectNameAndRegionException
	 */
	@Override
	public User forgotPassword(String userEmail, String firstName, String region, String newPassword) {
		User dbuser = findUserByEmail(userEmail);
		if (dbuser.getFirstName().equals(firstName) && dbuser.getRegion().equals(region)) {
			dbuser.setPassword(newPassword);
			userDao.save(dbuser);
			return dbuser;
		} else {
			throw new IncorrectNameAndRegionException();
		}
	}

}
