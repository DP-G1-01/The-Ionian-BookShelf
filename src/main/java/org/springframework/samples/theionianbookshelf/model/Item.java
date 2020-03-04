package org.springframework.samples.theionianbookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item extends BaseEntity {

	@Column(unique = true)
	@NotBlank
	@Size(min = 1, max = 20)
	private String title;

	@NotBlank
	@Size(min = 10, max = 500)
	private String description;

	@NotBlank
	private String role;

}
