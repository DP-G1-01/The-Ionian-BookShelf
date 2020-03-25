package org.springframework.samples.the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(unique = true, name = "username")
	@NotBlank
	@Size(min = 5, max = 32)
	String username;

	@Column(unique = true, name = "password")
	@NotBlank
	@Size(min = 5, max = 32)
	String password;

	boolean enabled;
}
