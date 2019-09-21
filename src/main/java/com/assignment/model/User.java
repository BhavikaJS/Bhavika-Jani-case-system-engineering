package com.assignment.model;

import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/*
 * User Entity represented in the database
 * It is the physical entity on which operation are performed
 * Validations applied on field level
 */
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "email")
	@Size(max = 50, message = "Email should be of maximum 50 characters")
	@NotEmpty(message = "*Email id is mandatory")
	@Pattern(regexp = "[^@]+@[^\\.]+\\..+", message = "*Please provide a valid Email")
	private String email;
	@Column(name = "password")
	@Size(max = 128, message = "Password must be of maximum 128 characters")
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Password is mandatory")
	private String password;
	@Column(name = "first_name")
	@Size(max = 50)
	@NotEmpty(message = "*Please provide your name")
	private String firstName;
	@Column(name = "last_name")
	@Size(max = 50)
	private String lastName;
	@Column(name = "region")
	@Size(max = 50)
	@NotEmpty(message = "*Please provide your Region")
	private String region;

	public User() {
	}

	public User(String email, String password, String firstName, String lastName, String region) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.region = region;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
