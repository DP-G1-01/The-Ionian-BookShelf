/*
 * LoginService.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package the_ionian_bookshelf.security;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import the_ionian_bookshelf.model.UserAccount;
import the_ionian_bookshelf.repository.UserAccountRepository;

@Service
@Transactional
public class LoginService implements UserDetailsService {

	// Managed repository -----------------------------------------------------

	@Autowired
	UserAccountRepository userAccountRepository;

	// Business methods -------------------------------------------------------

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		assertNotNull(username);

		UserDetails res;

		res = this.userAccountRepository.findByUsername(username);

		assertNotNull(res);

		// WARNING: The following sentences prevent lazy initialisation problems!
		assertNotNull(res.getAuthorities());
		// res.getAuthorities().size();

		return res;
	}

	public static UserAccount getPrincipal() {
		UserAccount res;
		SecurityContext context;
		Authentication authentication;
		Object principal;

		// If the asserts in this method fail, then you're
		// likely to have your Tomcat's working directory
		// corrupt. Please, clear your browser's cache, stop
		// Tomcat, update your Maven's project configuration,
		// clean your project, clean Tomcat's working directory,
		// republish your project, and start it over.

		context = SecurityContextHolder.getContext();
		assertNotNull(context);
		authentication = context.getAuthentication();
		assertNotNull(authentication);
		principal = authentication.getPrincipal();
		assertTrue(principal instanceof UserAccount);
		res = (UserAccount) principal;
		assertNotNull(res);
		assertTrue(res.getId() != 0);

		return res;
	}

}
