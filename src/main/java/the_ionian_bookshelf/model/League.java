package the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
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
public class League extends BaseEntity {

	@NotBlank
	@Column(unique = true)
	@Size(min = 1, max = 10)
	private String name;

	@Valid
	@OneToOne(optional = false)
	private Thread thread;

}
