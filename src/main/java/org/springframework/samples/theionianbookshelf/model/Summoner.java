package org.springframework.samples.theionianbookshelf.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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
	@ManyToMany()
	private Collection<Champion> mains;

	@Valid
	@ManyToOne(optional = false)
	private League ligue;

	@Valid
	@OneToMany
	private ChangeRequest changeRequest;
}
