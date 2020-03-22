package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import the_ionian_bookshelf.model.Authority;
import the_ionian_bookshelf.model.Reviewer;
import the_ionian_bookshelf.model.UserAccount;
import the_ionian_bookshelf.repository.ReviewerRepository;
import the_ionian_bookshelf.security.LoginService;

@Service
@Transactional
public class ReviewerService {

	@Autowired
	private ReviewerRepository reviewerRepository;

	@Autowired
	private UserAccountService uaService;

	@Autowired
	private ActorService actorService;

	public Reviewer create() {

		Reviewer res;
		UserAccount ua;
		Authority auth;

		res = new Reviewer();
		ua = this.uaService.create();

		auth = new Authority();
		auth.setAuthority(Authority.REVIEWER);
		ua.addAuthority(auth);

		res.setUserAccount(ua);

		return res;
	}

	public Collection<Reviewer> findAll() {

		Collection<Reviewer> res = this.reviewerRepository.findAll();

		assertNotNull(res);
		return res;
	}

	public Reviewer findOne(final int id) {

		assertTrue(id != 0);

		final Reviewer res = this.reviewerRepository.findById(id).get();
		assertNotNull(res);

		return res;
	}

	public Reviewer save(final Reviewer rev) {

		assertNotNull(rev);

		this.findByPrincipal();

		PasswordEncoder encoder = this.passwordEncoder();

		rev.getUserAccount().setPassword(encoder.encode(rev.getUserAccount().getPassword()));

		final Reviewer saved = this.reviewerRepository.save(rev);

		return saved;
	}

	/**
	 * This method finds the logged user that is using the application. Apart from
	 * this, it checks that the user is an Reviewer
	 * 
	 * @return The logged user, an instance of Reviewer
	 */
	public Reviewer findByPrincipal() {

		Reviewer res;
		final UserAccount ua = LoginService.getPrincipal();
		assertNotNull(ua);

		res = this.findByUserAccountId(ua.getId());

		final boolean hasAuthority = this.actorService.checkAuthority(res, Authority.REVIEWER);
		assertTrue(hasAuthority);

		return res;
	}

	public Reviewer findByUserAccountId(final int id) {

		assertTrue(id != 0);

		final Reviewer res = this.reviewerRepository.findByUserAccountId(id);
		assertNotNull(res);

		return res;
	}

	private PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}
