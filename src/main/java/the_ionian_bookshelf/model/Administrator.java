package the_ionian_bookshelf.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "administrators")
public class Administrator extends Actor {

}
