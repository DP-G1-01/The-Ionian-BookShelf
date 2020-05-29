package org.springframework.samples.the_ionian_bookshelf.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "summoners")
public class Summoner extends Actor {

	@NotNull
	@Valid
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "summoner_mains", joinColumns = @JoinColumn(name = "summoner_id"), inverseJoinColumns = @JoinColumn(name = "champion_id"))
	private Collection<Champion> mains;

	@Valid
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@JoinColumn(name = "league_id")
	private League league;

	private Boolean banned;
}
