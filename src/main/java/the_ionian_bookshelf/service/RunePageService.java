package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Actor;
import the_ionian_bookshelf.model.Authority;
import the_ionian_bookshelf.model.Branch;
import the_ionian_bookshelf.model.Rune;
import the_ionian_bookshelf.model.RunePage;
import the_ionian_bookshelf.repository.BranchRepository;
import the_ionian_bookshelf.repository.RunePageRepository;
import the_ionian_bookshelf.repository.RuneRepository;

@Service
public class RunePageService {

	@Autowired
	private RuneRepository runeRepository;
	
	@Autowired
	private RunePageRepository runePageRepository;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private BranchRepository branchRepository;
	
	@Autowired
	public RunePageService(RuneRepository runeRepository, BranchRepository branchRepository) {
		this.runeRepository = runeRepository;
		this.branchRepository = branchRepository;
	}

	//Método para listar runas
	@Transactional
	public Set<RunePage> findAllMine() throws DataAccessException {
		Set<RunePage> runePages = new TreeSet<>();
		Actor principal = this.actorService.findByPrincipal();
		this.runePageRepository.findAllByActorId(principal.getId()).forEach(runePages::add);
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
		Actor principal = this.actorService.findByPrincipal();
		assertTrue(principal.equals(runePage.getActor()));
		this.runePageRepository.delete(runePage);
	}
	
//	@Transactional
//	public Set<Branch> findBranches() throws DataAccessException {
//		Set<Branch> branches = new TreeSet<>();
//		this.branchRepository.findAll().forEach(branches::add);
//		return branches;
//	}
	
	@Transactional
	public RunePage findOne(int id) {

		assertTrue(id != 0);

		final RunePage res = this.runePageRepository.findById(id).get();
		assertNotNull(res);

		return res;

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
