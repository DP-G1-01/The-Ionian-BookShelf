package the_ionian_bookshelf.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	@Size(min = 10, max = 40)
	private String title;

	@NotBlank
	@Column(name = "description")
	@Size(min = 20, max = 500)
	private String description;

	// un array con valores positivos o negativos de los cambios que van a hacerse
	// a los atributos de Champion [Health, Mana, Energy, Attack, Speed]
	// ej: [0,-2,+50,0,0] -> -2 en Mana y +50 en Energy
	@ElementCollection
	@Size(min = 5, max = 5)
	@Column(name = "change_champ")
	private Collection<Double> changeChamp;

	@ElementCollection
	@Size(min = 1, max = 3)
	@Column(name = "change_item")
	private Collection<String> changeItem; // cambios en los items

	@NotBlank
	@Pattern(regexp = "^(DENIED|PENDING|ACCEPTED)$")
	@Column(name = "status")
	private String status;

	@Valid
	@ManyToOne(optional = true)
	@JoinColumn(name = "reviewer_id")
	private Reviewer reviewer;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "summoner_id")
	private Summoner summoner;

}
