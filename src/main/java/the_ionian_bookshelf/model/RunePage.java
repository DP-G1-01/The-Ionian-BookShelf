package the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Check(constraints = "keyrune.branch EQUALS mainrune1.branch")?
@Table(name = "rune_pages")
public class RunePage extends BaseEntity {

	@NotBlank
	@Column(name = "name")
	private String name;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name="summoner_id")
	private Summoner summoner;
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "keyrune_id")
	private Rune keyRune;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "mainrune1_id")
	private Rune mainRune1;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "mainrune2_id")
	private Rune mainRune2;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "mainrune3_id")
	private Rune mainRune3;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "secrune1_id")
	private Rune secRune1;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "secrune2_id")
	private Rune secRune2;

}
