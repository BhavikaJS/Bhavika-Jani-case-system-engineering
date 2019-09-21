package com.assignment.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import com.assignment.exception.ChangePasswordException;
import com.assignment.exception.IncorrectNameAndRegionException;
import com.assignment.exception.UserNotFoundException;
import com.assignment.exception.WrongCredentialsException;
import com.assignment.model.User;
import com.assignment.repository.UserDao;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UserServiceTest {

	@Mock
	private UserDao mockUserRepository;

	@Mock
	private UserServiceImpl userServiceUnderTest;

	@Autowired
	private User user;

	@Before
	public void setUp() {
		initMocks(this);
		userServiceUnderTest = new UserServiceImpl(mockUserRepository);
		user = new User("testId@gmail.com", "test@123", "mockTest", "mockTest", "Europe");

		List<User> users = new ArrayList<>();
		users.add(user);
		Mockito.when(mockUserRepository.save(user)).thenReturn(user);
		Mockito.when(mockUserRepository.findByEmail("testId@gmail.com")).thenReturn(user);
		Mockito.when(mockUserRepository.findAll()).thenReturn(users);
		Mockito.when(userServiceUnderTest.findUserByEmail(user.getEmail())).thenReturn(user);
	}

	@Test
	public void testFindUserByEmail() {
		String email = "testId@gmail.com";
		User result = userServiceUnderTest.findUserByEmail(email);
		assertEquals(email, result.getEmail());
	}

	@Test
	public void testSaveUser() {
		String email = "testId@gmail.com";
		User result = userServiceUnderTest.saveNewUser(user);
		assertEquals(email, result.getEmail());
	}

	@Test
	public void testGetAllUsers() {
		int expected = userServiceUnderTest.getAllUsers().size();
		assertEquals(expected, 1);
	}

	@Test(expected = UserNotFoundException.class)
	public void testUserLogin() {
		String email = "testd@gmail.com";
		String password = "test@123";
		String encodedString = Base64.getEncoder().encodeToString((email + password).getBytes());
		Mockito.when(userServiceUnderTest.userLogin(encodedString)).thenReturn(false);
		userServiceUnderTest.userLogin(encodedString);
	}

	@Test
	public void testUpdateUserDetails() {
		User updateUser = new User("testId@gmail.com", "test@123", "mockTest", "mockTest", "Netherlands");
		User newUserDetails = userServiceUnderTest.updateUserDetails(updateUser);
		assertEquals(updateUser.getEmail(), newUserDetails.getEmail());
	}

	@Test(expected = WrongCredentialsException.class)
	public void testUpdateUserDetailswithWrongPassword() {
		User updateUser = new User("testId@gmail.com", "wrongtest@123", "mockTest", "mockTest", "Europe");
		userServiceUnderTest.updateUserDetails(updateUser);
	}

	@Test(expected = UserNotFoundException.class)
	public void testUpdateUnknownUserException() {
		User updateUser = new User("testd@gmail.com", "newTest@123", "mockTest", "mockTest", "Europe");
		Mockito.when(userServiceUnderTest.findUserByEmail("testd@gmail.com")).thenReturn(null);
		userServiceUnderTest.updateUserDetails(updateUser);
	}

	@Test
	public void testDeleteUser() {
		User deleteUser = new User("testId@gmail.com", "test@123", "mockTest", "mockTest", "Europe");
		userServiceUnderTest.saveNewUser(deleteUser);
		userServiceUnderTest.deleteUser(deleteUser);
	}

	@Test(expected = UserNotFoundException.class)
	public void testDeleteUnknownUser() {
		User deleteUser = new User("testd@gmail.com", "newTest@123", "mockTest", "mockTest", "Europe");
		Mockito.when(userServiceUnderTest.findUserByEmail("testd@gmail.com")).thenReturn(null);
		userServiceUnderTest.deleteUser(deleteUser);
	}

	@Test(expected = WrongCredentialsException.class)
	public void testDeleteUserwithWrongCredentialsException() {
		User deleteUser = new User("testId@gmail.com", "newtest@123", "mockTest", "mockTest", "Europe");
		userServiceUnderTest.deleteUser(deleteUser);
	}

	@Test
	public void resetPasswordTest() {
		User user = userServiceUnderTest.resetPassword("testId@gmail.com", "test@123", "resetTest@123");
		String expected = "resetTest@123";
		assertEquals(expected, user.getPassword());
	}

	@Test(expected = ChangePasswordException.class)
	public void resetSamePasswordTest() {
		userServiceUnderTest.resetPassword("testId@gmail.com", "test@123", "test@123");
	}

	@Test(expected = UserNotFoundException.class)
	public void resetPasswordforUnknownUserTest() {
		Mockito.when(userServiceUnderTest.findUserByEmail("testd@gmail.com")).thenReturn(null);
		userServiceUnderTest.resetPassword("testd@gmail.com", "resetTest@123", "newChanged@123");
	}

	@Test
	public void forgotPasswordTest() {
		User user = userServiceUnderTest.forgotPassword("testId@gmail.com", "mockTest", "Europe", "resetTest@123");
		String expected = "resetTest@123";
		assertEquals(expected, user.getPassword());
	}

	@Test(expected = IncorrectNameAndRegionException.class)
	public void forgotPasswordExceptionTest() {
		userServiceUnderTest.forgotPassword("testId@gmail.com", "mockTest", "Asia", "resetTest@123");
	}

	@Test(expected = UserNotFoundException.class)
	public void forgotPasswordforUnknownUserTest() {
		Mockito.when(userServiceUnderTest.findUserByEmail("testd@gmail.com")).thenReturn(null);
		userServiceUnderTest.forgotPassword("testd@gmail.com", "mockTest", "Asia", "resetTest@123");
	}
}
