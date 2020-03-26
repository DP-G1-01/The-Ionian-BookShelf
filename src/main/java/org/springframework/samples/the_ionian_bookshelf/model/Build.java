package org.springframework.samples.the_ionian_bookshelf.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "builds")
public class Build extends BaseEntity {

	@NotBlank
	@Size(min = 10, max = 40)
	@Column(name = "title")
	private String title;

	@NotBlank
	@Size(min = 20, max = 500)
	@Column(name = "description")
	private String description;

	@Column(name = "visibility")
	private boolean visibility;

	@Valid
	@ManyToMany
	@Size(min = 0, max = 6)
	@JoinTable(name = "build_items", joinColumns = @JoinColumn(name = "build_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
	private List<Item> items;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "champion_id")
	private Champion champion;

	@Valid
	@ManyToOne(optional = true)
	@JoinColumn(name = "rune_page_id")
	private RunePage runePage;

	@Valid
	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "thread_id")
	private Thread thread;

}