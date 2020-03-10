package the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="thread")
public class Thread {

	@NotBlank
	@Id
	@Column(name="title")
	public String title;
	
	@NotBlank
	@Column(name="description")
	public String description;
}
