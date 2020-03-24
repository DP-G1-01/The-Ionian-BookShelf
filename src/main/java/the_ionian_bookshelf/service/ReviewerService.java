package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import the_ionian_bookshelf.model.Reviewer;
import the_ionian_bookshelf.model.User;
import the_ionian_bookshelf.repository.ReviewerRepository;
import the_ionian_bookshelf.security.LoginService;

@Service
@Transactional
public class ReviewerService {

	@Autowired
	private ReviewerRepository reviewerRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthoritiesService authService;

	@Autowired
	private LoginService loginService;

	public Reviewer create() {

		Reviewer res;
		User user = new User();

		res = new Reviewer();

		res.setUser(user);

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

		Reviewer saved = reviewerRepository.save(rev);

		userService.saveUser(rev.getUser());

		authService.saveAuthorities(rev.getUser().getUsername(), "reviewer");

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
		final User ua = this.loginService.getPrincipal();
		assertNotNull(ua);

		res = this.findByUsername(ua.getUsername());

		return res;
	}

	public Reviewer findByUsername(final String username) {

		assertNotNull(username);

		final Reviewer res = this.reviewerRepository.findByUsername(username);
		assertNotNull(res);

		return res;
	}

}
