package org.springframework.samples.theionianbookshelf.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.URL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "configParams")
public class ConfigurationParameters extends BaseEntity {

	@NotBlank
	private String sysName;

	@NotBlank
	@URL
	private String banner;

	@NotBlank
	private String message;

//	@NotEmpty
//	private Collection<String> voidWords;

	@NotEmpty
	@Column(unique = true)
	private Collection<String> roles;

}
