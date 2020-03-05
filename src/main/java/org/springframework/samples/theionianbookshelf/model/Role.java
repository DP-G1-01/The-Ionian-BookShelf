package org.springframework.samples.theionianbookshelf.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Role extends BaseEntity {

	@NotBlank
	@Size(min = 1, max = 20)
	private String name;

	@NotBlank
	@Size(min = 10, max = 500)
	private String description;

	@NotBlank
	@URL
	private String image;
}
