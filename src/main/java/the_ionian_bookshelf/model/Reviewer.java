package the_ionian_bookshelf.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reviewers")
public class Reviewer extends Actor {

//	@OneToMany()
//	@ElementCollection
//	@Valid
//	private Collection<ChangeRequest> request;

}
