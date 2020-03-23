package the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Check;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "champions")
@Check(constraints = "mana IS NOT NULL AND energy IS NULL" + " OR " + "mana IS NULL AND energy IS NOT NULL" + " OR "
		+ "mana IS NULL AND energy IS NULL")
public class Champion extends BaseEntity {

	@NotBlank
	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "description")
	private String description;

	@NotNull
	@Column(name = "health")
	private Double health;


	@Column(name = "mana")
	private Double mana;


	@Column(name = "energy")
	private Double energy;

	@NotNull
	@Column(name = "attack")
	private Double attack;

	@NotNull
	@Column(name = "speed")
	private Double speed;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "role_id")
	private Role role;
	
}
