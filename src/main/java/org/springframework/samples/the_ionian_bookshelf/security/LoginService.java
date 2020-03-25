/*
 * LoginService.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package org.springframework.samples.the_ionian_bookshelf.security;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Authorities;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.repository.AuthoritiesRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.UserRepository;
import org.springframework.samples.the_ionian_bookshelf.service.AuthoritiesService;
import org.springframework.samples.the_ionian_bookshelf.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserService userService;

	@Autowired
	private AuthoritiesService authService;

	// Business methods -------------------------------------------------------

	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		assertNotNull(username);

		User user = this.userService.findOne(username);
		Authorities authorities = this.authService.findOne(username);

		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities.getAuthority());

		String password = user.getPassword();

		return new org.springframework.security.core.userdetails.User(username, password, auth);
	}

	public User getPrincipal() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		assertNotNull(auth);
		String username = auth.getName();
		User res = this.userService.findOne(username);

		return res;
	}

}
