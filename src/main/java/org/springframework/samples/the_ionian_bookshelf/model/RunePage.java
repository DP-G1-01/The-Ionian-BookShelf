package org.springframework.samples.the_ionian_bookshelf.model;

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
//@Check(constraints = "((main_branch_id != secondary_branch_id) && (keyrune_id != mainrune1_id) && (keyrune_id != mainrune2_id)"
//		+ " && (keyrune_id != mainrune3_id) && (keyrune_id != secrune1_id) && (keyrune_id != secrune2_id) && (mainrune1_id != mainrune2_id)"
//		+ " && (mainrune1_id != mainrune3_id) && (mainrune1_id != mainrune3_id) && (mainrune1_id != secrune1_id) && (mainrune1_id != secrune2_id)"
//		+ " && (mainrune2_id != mainrune3_id) && (mainrune2_id != secrune1_id) && (mainrune2_id != secrune2_id) && (mainrune3_id != secrune1_id)"
//		+ " && (mainrune3_id != secrune2_id) && (secrune1_id != secrune2_id))")
@Table(name = "rune_pages")
public class RunePage extends BaseEntity {

	@NotBlank
	@Column(name = "name")
	private String name;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name="summoner_id")
	private Summoner summoner;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "main_branch_id")
	private Branch mainBranch;
	
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "secondary_branch_id")
	private Branch secondaryBranch;
	
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "keyrune_id")
	private Rune keyRune;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "mainrune1_id")
	private Rune mainRune1;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "mainrune2_id")
	private Rune mainRune2;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "mainrune3_id")
	private Rune mainRune3;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "secrune1_id")
	private Rune secRune1;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "secrune2_id")
	private Rune secRune2;

}