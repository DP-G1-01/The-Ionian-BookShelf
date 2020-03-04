package org.springframework.samples.theionianbookshelf.model;

import javax.persistence.Entity;
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
public class Thread extends BaseEntity {

	@NotBlank
	@Size(min = 5, max = 50)
	private String title;

	@NotBlank
	@Size(min = 20, max = 500)
	private String description;

}