package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
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
	 * Attribute used to ban an user. Will be a date, for ban an user of the system
	 * for a period of time .Change pending
	 */
	boolean enabled;

}
