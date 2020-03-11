package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import the_ionian_bookshelf.model.Administrator;
import the_ionian_bookshelf.model.Authority;
import the_ionian_bookshelf.model.UserAccount;
import the_ionian_bookshelf.repository.AdministratorRepository;
import the_ionian_bookshelf.security.LoginService;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository adminRepo;

	@Autowired
	private UserAccountService uaService;

	@Autowired
	private ActorService actorService;

	public Administrator create() {

		Administrator res;
		UserAccount ua;
		Authority auth;

		res = new Administrator();
		ua = this.uaService.create();

		auth = new Authority();
		auth.setAuthority(Authority.ADMINISTRATOR);
		ua.addAuthority(auth);

		res.setUserAccount(ua);

		return res;
	}

	public Collection<Administrator> findAll() {

		ArrayList<Administrator> res = new ArrayList<Administrator>();
		for (Administrator admin : adminRepo.findAll()) {
			res.add(admin);
		}

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

		this.findByPrincipal();

		PasswordEncoder encoder = this.passwordEncoder();

		admin.getUserAccount().setPassword(encoder.encode(admin.getUserAccount().getPassword()));

		final Administrator saved = this.adminRepo.save(admin);

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
		final UserAccount ua = LoginService.getPrincipal();
		assertNotNull(ua);

		res = this.findByUserAccountId(ua.getId());

		final boolean hasAuthority = this.actorService.checkAuthority(res, Authority.ADMINISTRATOR);
		assertTrue(hasAuthority);

		return res;
	}

	public Administrator findByUserAccountId(final int id) {

		assertTrue(id != 0);

		final Administrator res = this.adminRepo.findByUserAccountId(id);
		assertNotNull(res);

		return res;
	}

	private PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}
