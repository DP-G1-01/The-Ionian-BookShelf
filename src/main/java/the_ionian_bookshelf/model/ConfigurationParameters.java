package the_ionian_bookshelf.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Table(name = "config_params")
public class ConfigurationParameters extends BaseEntity {

	@NotBlank
	@Column(name = "sys_name")
	private String sysName;

	@NotBlank
	@URL
	@Column(name = "banner")
	private String banner;

	@NotBlank
	@Column(name = "message")
	private String message;

//	@NotEmpty
//	private Collection<String> voidWords;

	@ElementCollection
	@NotEmpty
	@OneToMany
	@JoinColumn(name = "role_id")
	private Collection<Role> roles;

	@ElementCollection
	@NotEmpty
	@Column(unique = true, name = "branches")
	private Collection<String> branches;

	@ElementCollection
	@NotEmpty
	@Column(unique = true)
	private Collection<String> nodes;
}
