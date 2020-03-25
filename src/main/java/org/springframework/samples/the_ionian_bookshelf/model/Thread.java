package org.springframework.samples.the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
@Table(name="threads")
public class Thread extends BaseEntity {

    @NotBlank
	@Size(min = 5, max = 50)
	@Column(name="title")
	public String title;

	@NotBlank
	@Size(min = 20, max = 500)
	@Column(name="description")
	public String description;

}
