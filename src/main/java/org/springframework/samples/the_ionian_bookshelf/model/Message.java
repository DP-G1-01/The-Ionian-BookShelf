package org.springframework.samples.the_ionian_bookshelf.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class Message extends BaseEntity {

	@NotBlank
	@Column(name = "text")
	@Size(min = 10, max = 500)
	private String text;

	@NotNull
	@Column(name = "moment")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	private Date moment;

	@Valid
	@ManyToOne
	@NotNull
	@JoinColumn(name = "summoner_id")
	private Summoner summoner;

	@Valid
	@ManyToOne
	@NotNull
	@JoinColumn(name = "thread_id")
	private Thread thread;

}
