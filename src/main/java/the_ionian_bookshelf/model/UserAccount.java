package the_ionian_bookshelf.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "userAccounts")
public class UserAccount extends BaseEntity implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4601834758022261151L;

	@Column(unique = true)
	@NotBlank
	@Size(min = 5, max = 32)
	private String username;

	@NotBlank
	@Size(min = 5, max = 32)
	private String password;

	@NotBlank
	@Valid
	private Collection<Authority> authorities;

	/**
	 * Attribute used to ban an user. <br>
	 * Will be a date, for ban an user of the system for a period of time. <br>
	 * Change pending
	 */
	boolean enabled;

	// ATTRIBUTES OF USER ACCOUNT

	@NotEmpty
	@Valid
	@ElementCollection
	@Override
	public Collection<Authority> getAuthorities() {
		// WARNING: Should return an unmodifiable copy, but it's not possible with
		// hibernate!
		return this.authorities;
	}

	public void setAuthorities(final Collection<Authority> authorities) {
		this.authorities = authorities;
	}

	public void addAuthority(final Authority authority) {

		assertNotNull(authority);
		assertTrue(!this.authorities.contains(authority));

		this.authorities.add(authority);
	}

	public void removeAuthority(final Authority authority) {

		assertNotNull(authority);
		assertTrue(this.authorities.contains(authority));

		this.authorities.remove(authority);
	}

	// ATRIBUTOS DE CONFIGURACIÓN DE USER ACCOUNT

	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isEnabled() {
		return true;
	}

}
