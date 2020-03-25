package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Administrator;
import org.springframework.samples.the_ionian_bookshelf.model.Authority;
import org.springframework.samples.the_ionian_bookshelf.model.UserAccount;
import org.springframework.samples.the_ionian_bookshelf.repository.AdministratorRepository;
import org.springframework.samples.the_ionian_bookshelf.security.LoginService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository adminRepo;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthoritiesService authService;
	
	@Autowired
	private LoginService loginService;

	public Administrator create() {

		Administrator res;
		User user = new User();

		res = new Administrator();

		res.setUser(user);

		return res;
	}

	public Collection<Administrator> findAll() {

		Collection<Administrator> res = this.adminRepo.findAll();

		assertNotNull(res);
		return res;
	}

	public Administrator findOne(final int id) {

		assertTrue(id != 0);

		final Administrator res = this.adminRepo.findById(id).get();
		assertNotNull(res);

		return res;
	}

	public Administrator save(final Administrator admin) {

		assertNotNull(admin);

		Administrator saved = adminRepo.save(admin);

		userService.saveUser(admin.getUser());

		authService.saveAuthorities(admin.getUser().getUsername(), "administrator");

		return saved;
	}

	/**
	 * This method finds the logged user that is using the application. Apart from
	 * this, it checks that the user is an Administrator
	 * 
	 * @return The logged user, an instance of Administrator
	 */
	public Administrator findByPrincipal() {

		Administrator res;
		final User ua = this.loginService.getPrincipal();
		assertNotNull(ua);

		res = this.findByUsername(ua.getUsername());

		return res;
	}

	private Administrator findByUsername(String username) {
		assertNotNull(username);

		final Administrator res = this.adminRepo.findByUsername(username);
		assertNotNull(res);

		return res;
	}

}
