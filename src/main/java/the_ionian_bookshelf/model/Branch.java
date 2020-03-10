package the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Branch extends BaseEntity {

	@NotBlank
	@Size(min = 1, max = 20)
	@Column(name = "name")
	private String name;

	@NotBlank
	@Size(min = 10, max = 500)
	@Column(name = "description")
	private String description;

	@NotBlank
	@URL
	@Column(name = "image")
	private String image;
}
