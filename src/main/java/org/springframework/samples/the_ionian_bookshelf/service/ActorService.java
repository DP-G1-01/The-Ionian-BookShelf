
package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Actor;
import org.springframework.samples.the_ionian_bookshelf.model.Authority;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.UserAccount;
import org.springframework.samples.the_ionian_bookshelf.repository.ActorRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.SummonerRepository;
import org.springframework.samples.the_ionian_bookshelf.security.LoginService;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
	private SummonerRepository summonerRepository;
	
	public Summoner findSummonerByUserAccountId(int UAId) {
		
		return this.summonerRepository.findByUserAccountId(UAId);
	}

	public Actor create() {

		final Actor res = new Actor();

		return res;
	}

	public Collection<Actor> findAll() {

		final Collection<Actor> res = this.actorRepository.findAll();

		assertNotNull(res);

		return res;
	}

	public Actor findOne(final int id) {

		assertTrue(id != 0);

		final Actor res = this.actorRepository.findById(id).get();
		assertNotNull(res);

		return res;
	}

	/**
	 *
	 * @param actor     El actor cuya autoridad queremos comprobar
	 * @param authority Autoridad que queremos comprobar que el actor posee
	 * @return True si el actor posee la autoridad pasada como par�metro o false si
	 *         no la posee
	 */
	public boolean checkAuthority(final Actor actor, final String authority) {

		assertNotNull(actor);
		assertNotNull(authority);

		final UserAccount ua = actor.getUserAccount();
		final Collection<Authority> authorities = ua.getAuthorities();

		assertFalse(authorities.isEmpty());

		final Authority aux = new Authority();
		aux.setAuthority(authority);

		return authorities.contains(aux);
	}

	/**
	 * Este m�todo sirve para sacar el actor logeado en el sistema Se usa como
	 * m�todo auxiliar para sacar los distintos tipos de actores del sistema y hacer
	 * comprobaciones respecto a sus authorities
	 *
	 * @return El actor logeado en el sistema
	 */
	public Actor findByPrincipal() {

		Actor res;
		final UserAccount ua = LoginService.getPrincipal();
		assertNotNull(ua);
		res = this.findByUserAccountId(ua.getId());
		System.out.println(res);
		return res;
	}

	public Actor findByUserAccountId(final int id) {

		assertTrue(id != 0);

		final Actor res = this.actorRepository.findByUserAccountId(id);
		assertNotNull(res);

		return res;
	}

}
