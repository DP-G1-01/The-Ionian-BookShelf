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
import the_ionian_bookshelf.repository.BranchRepository;
import the_ionian_bookshelf.repository.RuneRepository;

@Service
public class RuneService {

	@Autowired
	private RuneRepository runeRepository;
	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private BranchRepository branchRepository;
	
	@Autowired
	public RuneService(RuneRepository runeRepository, BranchRepository branchRepository) {
		this.runeRepository = runeRepository;
		this.branchRepository = branchRepository;
	}

	//Método para listar runas
	@Transactional
	public Set<Rune> findAll() throws DataAccessException {
		Set<Rune> runes = new TreeSet<>();
		this.runeRepository.findAll().forEach(runes::add);
		return runes;
	}
	
	@Transactional
	public void save(Rune rune) throws DataAccessException {
		assertNotNull(rune);
		
		Actor principal = this.actorService.findByPrincipal();
		
		assertTrue(this.actorService.checkAuthority(principal, Authority.ADMINISTRATOR));
		this.runeRepository.save(rune);
	}
	
	@Transactional
	public void delete(Rune rune) throws DataAccessException {
		assertNotNull(rune);
		Actor principal = this.actorService.findByPrincipal();
		assertTrue(this.actorService.checkAuthority(principal, Authority.ADMINISTRATOR));
		this.runeRepository.delete(rune);
	}
	
	@Transactional
	public Set<Branch> findBranches() throws DataAccessException {
		Set<Branch> branches = new TreeSet<>();
		this.branchRepository.findAll().forEach(branches::add);
		return branches;
	}
	
	@Transactional
	public Rune findOne(int id) {

		assertTrue(id != 0);

		final Rune res = this.runeRepository.findById(id).get();
		assertNotNull(res);

		return res;

	}
	
	@Transactional
	public Rune create() {

		this.administratorService.findByPrincipal();
		Branch defaultBranch = this.branchRepository.findDefaultBranch();
		assertNotNull(defaultBranch);
		Rune res = new Rune();
		res.setName("New rune");
		res.setDescription("New description");
		res.setBranch(defaultBranch);
		res.setNode("1");
		return res;
	}
//	@Transactional
//	public void deleteRunePagesWithRune(Rune rune) {
//		Collection<RunePage> runePages = this.runePageService.findByRune(rune);
//		runePages.forEach(x->this.runePageService.delete(x));
//	}
}
