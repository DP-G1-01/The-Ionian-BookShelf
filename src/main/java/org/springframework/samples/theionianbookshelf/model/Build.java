package org.springframework.samples.theionianbookshelf.model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
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
public class Build extends BaseEntity {

	@NotBlank
	@Size(min = 10, max = 40)
	private String title;

	@NotBlank
	@Size(min = 20, max = 500)
	private String description;

	private boolean visibility;

	@Valid
	@ManyToMany
	private Item items;

	@Valid
	@ManyToOne(optional = false)
	private Champion champion;

}
