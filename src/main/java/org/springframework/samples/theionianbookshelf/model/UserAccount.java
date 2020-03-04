package org.springframework.samples.theionianbookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "userAccounts")
public class UserAccount extends BaseEntity {

	@Column(unique = true)
	@NotBlank
	@Size(min = 5, max = 32)
	private String username;

	@NotBlank
	@Size(min = 5, max = 32)
	private String password;

	@NotBlank
	@Valid
	private Authority authority;

	/**
	 * Attribute used to ban an user. <br>
	 * Will be a date, for ban an user of the system for a period of time. <br>
	 * Change pending
	 */
	boolean enabled;

}
