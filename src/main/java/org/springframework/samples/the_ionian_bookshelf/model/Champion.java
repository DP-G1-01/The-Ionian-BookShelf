package org.springframework.samples.the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Check;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "champions")
@Check(constraints = "mana IS NOT NULL AND energy IS NULL" + " OR " + "mana IS NULL AND energy IS NOT NULL" + " OR "
		+ "mana IS NULL AND energy IS NULL")
public class Champion extends BaseEntity {

	@NotBlank
	@Column(name = "name", unique = true)
	@Size(min = 1, max = 20)
	private String name;

	@NotBlank
	@Column(name = "description")
	@Size(min = 10, max = 500)
	private String description;

	@NotBlank
	@Column(name = "health")
	private Double health;

	// Sin la anotación @NotBlank para poder seleccionar si tiene mana o energía.
	// Se hará la comprobación en los servicios
	@Min(0)
	@Column(name = "mana")
	private Double mana;

	// Sin la anotación @NotBlank para poder seleccionar si tiene mana o energía.
	// Se hará la comprobación en los servicios
	@Min(0)
	@Column(name = "energy")
	private Double energy;

	@NotBlank
	@Column(name = "attack")
	private Double attack;

	@NotBlank
	@Column(name = "speed")
	private Double speed;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "role_id")
	private Role role;
}
