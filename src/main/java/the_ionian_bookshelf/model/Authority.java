package the_ionian_bookshelf.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
@Embeddable
public class Authority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String authority;

	public static final String ADMINISTRATOR = "ADMINISTRATOR";
	public static final String SUMMONER = "SUMMONER";
	public static final String REVIEWER = "REVIEWER";

	@NotBlank
	@Pattern(regexp = "^" + Authority.ADMINISTRATOR + "|" + Authority.SUMMONER + "|" + Authority.REVIEWER + "$")
	@Override
	public String getAuthority() {
		return this.authority;
	}

	public void setAuthority(final String authority) {
		this.authority = authority;
	}

	public static Collection<Authority> listAuthorities() {
		Collection<Authority> res;
		Authority authority;

		res = new ArrayList<Authority>();

		authority = new Authority();
		authority.setAuthority(Authority.ADMINISTRATOR);
		res.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.SUMMONER);
		res.add(authority);

		authority = new Authority();
		authority.setAuthority(Authority.REVIEWER);
		res.add(authority);

		return res;
	}

	// Object interface -------------------------------------------------------

	@Override
	public int hashCode() {
		return this.getAuthority().hashCode();
	}

	@Override
	public boolean equals(final Object other) {
		boolean result;

		if (this == other)
			result = true;
		else if (other == null)
			result = false;
		else if (!this.getClass().isInstance(other))
			result = false;
		else
			result = (this.getAuthority().equals(((Authority) other).getAuthority()));

		return result;
	}

	@Override
	public String toString() {
		return this.authority;
	}

}
