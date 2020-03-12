package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.transaction.Transactional;

import the_ionian_bookshelf.model.Summoner;
import the_ionian_bookshelf.model.Authority;
import the_ionian_bookshelf.model.Champion;
import the_ionian_bookshelf.model.UserAccount;
import the_ionian_bookshelf.repository.SummonerRepository;
import the_ionian_bookshelf.security.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SummonerService {

	@Autowired
	private SummonerRepository summonerRepository;

	@Autowired
	private UserAccountService uaService;

	@Autowired
	private ActorService actorService;

	public Summoner create() {

		Summoner res;
		UserAccount ua;
		Authority auth;

		res = new Summoner();
		ua = this.uaService.create();

		auth = new Authority();
		auth.setAuthority(Authority.SUMMONER);
		ua.addAuthority(auth);

		res.setUserAccount(ua);

		return res;
	}

	public Collection<Summoner> findAll() {

		Collection<Summoner> res = this.summonerRepository.findAll();

		assertNotNull(res);
		return res;
	}

	public Summoner findOne(final int id) {

		assertTrue(id != 0);

		final Summoner res = this.summonerRepository.findById(id).get();
		assertNotNull(res);

		return res;
	}

	public Summoner save(final Summoner summ) {

		assertNotNull(summ);

		this.findByPrincipal();

		PasswordEncoder encoder = this.passwordEncoder();

		summ.getUserAccount().setPassword(encoder.encode(summ.getUserAccount().getPassword()));

		final Summoner saved = this.summonerRepository.save(summ);

		return saved;
	}

	/**
	 * This method finds the logged user that is using the application. Apart from
	 * this, it checks that the user is an Summoner
	 * 
	 * @return The logged user, an instance of Summoner
	 */
	public Summoner findByPrincipal() {

		Summoner res;
		final UserAccount ua = LoginService.getPrincipal();
		assertNotNull(ua);

		res = this.findByUserAccountId(ua.getId());

		final boolean hasAuthority = this.actorService.checkAuthority(res, Authority.SUMMONER);
		assertTrue(hasAuthority);

		return res;
	}

	public Summoner findByUserAccountId(final int id) {

		assertTrue(id != 0);

		final Summoner res = this.summonerRepository.findByUserAccountId(id);
		assertNotNull(res);

		return res;
	}

	private PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

	public Collection<Summoner> findByChampion(Champion champ) {

		assertNotNull(champ);
		assertTrue(champ.getId() != 0);

		Collection<Summoner> res = this.summonerRepository.findByChampion(champ);
		assertNotNull(res);

		return res;
	}

}
