package org.springframework.samples.the_ionian_bookshelf.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "leagues")
public class League extends BaseEntity {

	@NotBlank
	@Column(unique = true, name = "name")
	@Size(min = 1, max = 10)
	private String name;

	@Valid
	@OneToOne(optional = false, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "thread_id")
	private Thread thread;

}
