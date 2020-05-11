package org.springframework.samples.the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Check(constraints = "build_id IS NOT NULL AND thread_id IS NULL AND message_id IS NULL "
		+ "OR build_id IS NULL AND thread_id IS NOT NULL AND message_id IS NULL OR "
		+ "build_id IS NULL AND thread_id IS NULL AND message_id IS NOT NULL")
@Table(name = "votes")
public class Vote extends BaseEntity {

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "voter_id")
	private Summoner voter;

	@Valid
	@ManyToOne(optional = true)
	@JoinColumn(name = "build_id")
	private Build build;

	@Valid
	@ManyToOne(optional = true)
	@JoinColumn(name = "thread_id")
	private Thread thread;

	@Valid
	@ManyToOne(optional = true)
	@JoinColumn(name = "message_id")
	private Message message;

	/**
	 * If 1 -> Positive If 0 -> Negative
	 */
	@Column(name = "status")
	private boolean status;
}
