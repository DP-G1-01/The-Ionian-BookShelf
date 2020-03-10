package the_ionian_bookshelf.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rune extends BaseEntity {

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@Valid
	@NotBlank
	@ManyToOne(optional = false)
	private Branch branch;

	@NotBlank
	@Pattern(regexp = "^(KEY|1|2|3)$")
	private String node;

}
