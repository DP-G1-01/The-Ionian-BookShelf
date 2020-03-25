package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.the_ionian_bookshelf.model.Authorities;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.repository.AuthoritiesRepository;
import org.springframework.samples.the_ionian_bookshelf.security.LoginService;
import org.springframework.stereotype.Service;

@Service
public class AuthoritiesService {

	private AuthoritiesRepository authoritiesRepository;

	@Autowired
	public AuthoritiesService(AuthoritiesRepository authoritiesRepository) {
		this.authoritiesRepository = authoritiesRepository;
	}

	@Autowired
	private LoginService loginService;

	@Transactional
	public Authorities findOne(String username) {

		assertNotNull(username);

		Authorities res = this.authoritiesRepository.findById(username).get();
		assertNotNull(res);

		return res;
	}

	@Transactional
	public void saveAuthorities(Authorities authorities) throws DataAccessException {
		authoritiesRepository.save(authorities);
	}

	@Transactional
	public void saveAuthorities(String username, String role) throws DataAccessException {
		Authorities authority = new Authorities();
		authority.setUsername(username);
		authority.setAuthority(role);
		authoritiesRepository.save(authority);
	}

	@Transactional
	public boolean checkAuthorities(String authority) {

		final User loggedIn = this.loginService.getPrincipal();
		Authorities auth = this.authoritiesRepository.findById(loggedIn.getUsername()).get();
		return auth.getAuthority().equals(authority);
	}
}