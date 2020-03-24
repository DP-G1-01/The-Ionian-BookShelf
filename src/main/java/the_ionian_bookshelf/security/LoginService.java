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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import the_ionian_bookshelf.model.User;
import the_ionian_bookshelf.repository.AuthoritiesRepository;
import the_ionian_bookshelf.repository.UserRepository;
import the_ionian_bookshelf.service.UserService;

@Service
@Transactional
public class LoginService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserService userService;

	// Business methods -------------------------------------------------------

//	@Override
//	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
//
//		assertNotNull(username);
//
//		UserDetails res;
//
//		res = this.userRepository.findById(username).get();
//
//		assertNotNull(res);
//
//		// WARNING: The following sentences prevent lazy initialisation problems!
//		assertNotNull(res.getAuthorities());
//		// res.getAuthorities().size();
//
//		return res;
//	}

	public User getPrincipal() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		assertNotNull(auth);
		String username = auth.getName();
		User res = this.userService.findOne(username);

		return res;
	}

}
