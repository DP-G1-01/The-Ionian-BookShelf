package org.springframework.samples.the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Check;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "champions")
@Check(constraints = "mana IS NOT NULL AND energy IS NULL" + " OR " + "mana IS NULL AND energy IS NOT NULL" + " OR "
		+ "mana IS NULL AND energy IS NULL")
public class Champion extends BaseEntity {

	@NotBlank
	@Column(name = "name", unique = true)
	@Size(max = 20)
	private String name;

	@NotBlank
	@Size(max=250)
	@Column(name = "description")
	private String description;

	@Min(0)
	@NotNull
	@Column(name = "health")
	private Double health;


	@Column(name = "mana")
	private Double mana;


	@Column(name = "energy")
	private Double energy;

	@Min(0)
	@NotNull
	@Column(name = "attack")
	private Double attack;

	@Min(0)
	@NotNull
	@Column(name = "speed")
	private Double speed;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "role_id")
	private Role role;
	
}
