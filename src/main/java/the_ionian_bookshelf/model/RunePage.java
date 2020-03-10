package the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Check;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "runePages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "keyRune.branch EQUAL mainRune1.branch")
public class RunePage extends BaseEntity{
	
	@NotBlank
	@Column(name = "name")
	private String name;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "key_rune_id")
	private Rune keyRune;
	
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "main_rune_1_id")
	private Rune mainRune1;
	
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "main_rune_2_id")
	private Rune mainRune2;
	
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "main_rune_3_id")
	private Rune mainRune3;
	
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "sec_rune_1_id")
	private Rune secRune1;
	
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "sec_rune_2_id")
	private Rune secRune2;
	
}
