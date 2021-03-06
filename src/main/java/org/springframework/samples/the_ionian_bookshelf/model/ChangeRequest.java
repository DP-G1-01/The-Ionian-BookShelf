package org.springframework.samples.the_ionian_bookshelf.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Check;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "change_requests")
@Check(constraints = "champion_id IS NOT NULL AND item_id IS NULL" + " OR " + "champion_id IS NULL AND item_id IS NOT NULL" + " OR "
		+ "champion_id IS NULL AND item_id IS NULL")
public class ChangeRequest extends BaseEntity {

	@Valid
	@ManyToOne(optional = true)
	@JoinColumn(name = "champion_id")
	private Champion champion;

	@Valid
	@ManyToOne(optional = true)
	@JoinColumn(name = "item_id")
	private Item item;

	@NotBlank
	@Column(name = "title")
	@Size(max = 40)
	private String title;

	@NotBlank
	@Column(name = "description")
	@Size(min = 20, max = 500)
	private String description;

	// un array con valores positivos o negativos de los cambios que van a hacerse
	// a los atributos de Champion [Health, Mana, Energy, Attack, Speed]
	// ej: [0,-2,+50,0,0] -> -2 en Mana y +50 en Energy

	@ElementCollection
//	@Size(min = 4, max = 4)
	@Column(name = "change_champ")
	private List<String> changeChamp;

	@ElementCollection
//	@Size(min = 3, max = 3)
	@Column(name = "change_item")
	private List<String> changeItem; // cambios en los items

	@NotBlank
	@Pattern(regexp = "^(REJECTED|PENDING|ACCEPTED)$")
	@Column(name = "status")
	private String status;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "summoner_id")
	private Summoner summoner;
	
	@Valid
	@ManyToOne(optional = true)
	@JoinColumn(name = "reviewer_id")
	private Reviewer reviewer;

}
