package org.springframework.samples.the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "authorities")
public class Authorities {

	@Id
	@Column(name = "username")
	@NotBlank
	String username;

	@NotBlank
	String authority;

}
