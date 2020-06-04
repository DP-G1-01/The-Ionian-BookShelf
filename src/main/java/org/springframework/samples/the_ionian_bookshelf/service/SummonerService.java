package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.repository.SummonerRepository;
import org.springframework.samples.the_ionian_bookshelf.security.LoginService;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SummonerService {

	@Autowired
	private SummonerRepository summonerRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthoritiesService authService;

	@Autowired
	private LeagueService leagueService;

	@Autowired
	private LoginService loginService;

	public Summoner create() {

		Summoner res;
		User user = new User();

		res = new Summoner();

		res.setUser(user);

		res.setMains(new ArrayList<Champion>());
		res.setLeague(this.leagueService.findBasicLeague());

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

		Summoner saved = summonerRepository.save(summ);

		userService.saveUser(summ.getUser());

		authService.saveAuthorities(summ.getUser().getUsername(), "summoner");

		return saved;
	}
	
	public void banear(Summoner summ) {
		
		assertNotNull(summ);
		
		summ.setBanned(true);
	}
	
	public void desbanear(Summoner summ) {
		
		assertNotNull(summ);

		summ.setBanned(false);
	}

	/**
	 * This method finds the logged user that is using the application. Apart from
	 * this, it checks that the user is an Summoner
	 * 
	 * @return The logged user, an instance of Summoner
	 */
	public Summoner findByPrincipal() {

		Summoner res;
		final User ua = this.loginService.getPrincipal();
		assertNotNull(ua);

		res = this.findByUsername(ua.getUsername());
		assertNotNull(res);
		return res;
	}

	public Summoner findByUsername(String username) {
		assertNotNull(username);

		final Summoner res = this.summonerRepository.findByUsername(username);
		assertNotNull(res);

		return res;
	}

	public Collection<Summoner> findByChampion(Champion champ) {

		assertNotNull(champ);
		assertTrue(champ.getId() != 0);

		Collection<Summoner> res = this.summonerRepository.findByChampion(champ);
		assertNotNull(res);

		return res;
	}

}