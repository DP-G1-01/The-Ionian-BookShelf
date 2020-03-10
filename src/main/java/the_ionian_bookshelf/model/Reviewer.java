package the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="reviewers")
public class Reviewer extends Actor {
	
	@Column(name="puta")
	private String puta;

//	@OneToMany()
//	@ElementCollection
//	@Valid
//	private Collection<ChangeRequest> request;

}
