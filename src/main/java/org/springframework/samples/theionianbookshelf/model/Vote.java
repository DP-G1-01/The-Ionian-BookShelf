package org.springframework.samples.theionianbookshelf.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;

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
@Check(constraints = "build IS NOT NULL AND thread IS NULL AND message IS NULL "
		+ "OR build IS NULL AND thread IS NOT NULL AND message IS NULL OR"
		+ "build IS NULL AND thread IS NULL AND message IS NOT NULL")
public class Vote extends BaseEntity {

	@Valid
	@OneToOne(optional = false)
	private Summoner voter;

	@Valid
	@ManyToOne(optional = true)
	private Build build;

	@Valid
	@ManyToOne(optional = true)
	private Thread thread;

	@Valid
	@ManyToOne(optional = true)
	private Message message;

	/**
	 * If 1 -> Positive <br>
	 * If 0 -> Negative
	 */

	private boolean status;
}
