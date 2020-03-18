package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Actor;
import the_ionian_bookshelf.model.Authority;
import the_ionian_bookshelf.model.Rune;
import the_ionian_bookshelf.model.RunePage;
import the_ionian_bookshelf.repository.RunePageRepository;
import the_ionian_bookshelf.repository.RuneRepository;

@Service
public class RunePageService {

	@Autowired
	private RunePageRepository runePageRepository;
	
//	@Autowired
//	private ActorService actorService;
	
	@Autowired
	private RuneRepository runeRepository;

	//Método para listar runas
	@Transactional
	public Iterable<RunePage> findAllMine() throws DataAccessException {
		List<RunePage> runePages = new ArrayList<>();
//		Actor principal = this.actorService.findByPrincipal();
//		this.runePageRepository.findAllByUserAccountId(principal.getUserAccount().getId()).forEach(runePages::add);
		this.runePageRepository.findAll().forEach(runePages::add);
		return runePages;
	}
	
	@Transactional
	public void save(RunePage runePage) throws DataAccessException {
		assertNotNull(runePage);
		
		Actor principal = this.actorService.findByPrincipal();
		
		assertTrue(this.actorService.checkAuthority(principal, Authority.ADMINISTRATOR) ||
				this.actorService.checkAuthority(principal, Authority.SUMMONER) ||
				this.actorService.checkAuthority(principal, Authority.REVIEWER));
		this.runePageRepository.save(runePage);
	}
	
	@Transactional
	public void delete(RunePage runePage) throws DataAccessException {
		assertNotNull(runePage);
//		Actor principal = this.actorService.findByPrincipal();
//		assertTrue(principal.getUserAccount().equals(runePage.getSummoner().getUserAccount()));
		this.runePageRepository.delete(runePage);
	}
	
	
	@Transactional
	public RunePage findOne(int id) {

		assertTrue(id != 0);

		final RunePage res = this.runePageRepository.findById(id).get();
		assertNotNull(res);

		return res;

	}

	public List<Rune> findRunes() {
		
		return this.runeRepository.findAll();
	}
	
//	@Transactional
//	public RunePage create() {
//
//		Actor principal = this.actorService.findByPrincipal();
//		assertTrue(this.actorService.checkAuthority(principal, Authority.ADMINISTRATOR) ||
//				this.actorService.checkAuthority(principal, Authority.SUMMONER) ||
//				this.actorService.checkAuthority(principal, Authority.REVIEWER));
//		RunePage res = new RunePage();
//		res.setName("New rune");
//		res.setActor(principal);
//		res.setDescription("New description");
//		res.setBranch(defaultBranch);
//		res.setNode("1");
//		return res;
//	}
//	@Transactional
//	public void deleteRunePagesWithRune(Rune rune) {
//		Collection<RunePage> runePages = this.runePageService.findByRune(rune);
//		runePages.forEach(x->this.runePageService.delete(x));
//	}
}
