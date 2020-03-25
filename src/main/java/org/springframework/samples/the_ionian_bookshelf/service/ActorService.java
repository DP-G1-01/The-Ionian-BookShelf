
package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Actor;
import org.springframework.samples.the_ionian_bookshelf.model.Authorities;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.repository.ActorRepository;
import org.springframework.samples.the_ionian_bookshelf.security.LoginService;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;

	@Autowired
	private AuthoritiesService authService;

	@Autowired
	private LoginService logService;

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
	 * @param authority Autoridad que queremos comprobar que el actor posee:
	 *                  administrator, summoner, reviewer
	 * @return True si el actor posee la autoridad pasada como par�metro o false si
	 *         no la posee
	 */
	public boolean checkAuthority(final Actor actor, final String authority) {

		assertNotNull(actor);
		assertNotNull(authority);

		final User ua = actor.getUser();
		final Authorities authorities = authService.findOne(ua.getUsername());

		return authority.equals(authorities.getAuthority());
	}

//	/**
//	 * Este m�todo sirve para sacar el actor logeado en el sistema Se usa como
//	 * m�todo auxiliar para sacar los distintos tipos de actores del sistema y hacer
//	 * comprobaciones respecto a sus authorities
//	 *
//	 * @return El actor logeado en el sistema
//	 */
//	public Actor findByPrincipal() {
//
//		Actor res;
//		final User ua = logService.getPrincipal();
//		assertNotNull(ua);
//		res = this.findByUsername(ua.getUsername());
//		return res;
//	}
////
//	public Actor findByUsername(final String username) {
//
//		assertNotNull(username);
//
//		final Actor res = this.actorRepository.findByUsername(username);
//		assertNotNull(res);
//
//		return res;
//	}

}