package the_ionian_bookshelf.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Check;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "keyRune.branch EQUAL mainRune1.branch")
public class RunePage extends BaseEntity{
	
	@NotBlank
	private String name;

	@Valid
	@ManyToOne(optional = false)
	private Rune keyRune;
	
	@Valid
	@ManyToOne(optional = false)
	private Rune mainRune1;
	
	@Valid
	@ManyToOne(optional = false)
	private Rune mainRune2;
	
	@Valid
	@ManyToOne(optional = false)
	private Rune mainRune3;
	
	@Valid
	@ManyToOne(optional = false)
	private Rune secRune1;
	
	@Valid
	@ManyToOne(optional = false)
	private Rune secRune2;
	
}
