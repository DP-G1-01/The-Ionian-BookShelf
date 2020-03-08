package org.springframework.samples.theionianbookshelf.model;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rune extends BaseEntity{
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@Valid
	@NotBlank
	private Branch branch;
	
	@Valid
	@NotBlank
	private Node node;
	
}
