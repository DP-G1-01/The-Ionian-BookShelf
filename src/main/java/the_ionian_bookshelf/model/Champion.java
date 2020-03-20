package the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="champions")
public class Champion extends BaseEntity{
	
	
	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "health")
	private Double health;


	@Column(name = "mana")
	private Double mana;


	@Column(name = "energy")
	private Double energy;

	@Column(name = "attack")
	private Double attack;

	@Column(name = "speed")
	private Double speed;

	@NotNull
	@ManyToOne()
	@JoinColumn(name = "role_id")
	private Role role;

}
