package the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "runes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rune extends BaseEntity {

	@NotBlank
	@Column(name = "name")
	private String name;

	@NotBlank
	@Column(name = "description")

	private String description;

	@Valid
	@NotBlank
	@ManyToOne(optional = false)
	@JoinColumn(name = "branch_id")

	private Branch branch;

	@NotBlank
	@Pattern (regexp = "^(KEY|1|2|3)$")
	@Column(name = "node")
	private String node;

}
