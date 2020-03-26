package org.springframework.samples.the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "runes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rune extends BaseEntity {

	@Size(max = 30)
	@NotBlank
	@Column(name = "name")
	private String name;

	@Size(max = 250)
	@NotBlank
	@Column(name = "description")
	private String description;

//	@Valid
	@NotNull
//	@ManyToOne(optional = false)
//	@JoinColumn(name = "branch_id")
	@ManyToOne
	@JoinColumn(name = "branch_id")
	private Branch branch;

	@NotBlank
	@Pattern (regexp = "^(Key|1|2|3)$")
	@Column(name = "node")
	private String node;

}
