package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Branch;
import the_ionian_bookshelf.model.Rune;
import the_ionian_bookshelf.model.RunePage;
import the_ionian_bookshelf.repository.BranchRepository;
import the_ionian_bookshelf.repository.RunePageRepository;
import the_ionian_bookshelf.repository.RuneRepository;

@Service
public class RuneService {

	@Autowired
	private RuneRepository runeRepository;
	
	@Autowired
	private BranchRepository branchRepository;
	
	@Autowired
	private RunePageRepository runePageRepository;
	
	@Autowired
	public RuneService(RuneRepository runeRepository, BranchRepository branchRepository, RunePageRepository runePageRepository) {
		this.runeRepository = runeRepository;
		this.branchRepository = branchRepository;
		this.runePageRepository = runePageRepository;
	}

	//Método para listar runas
	@Transactional
	public Set<Rune> findRunes() throws DataAccessException {
		Set<Rune> runes = new TreeSet<>();
		this.runeRepository.findAll().forEach(runes::add);
		return runes;
	}
	
	@Transactional
	public Collection<Rune> findAll(){
		return runeRepository.findAll();
	}
	
	@Transactional
	public void saveRune(Rune rune) throws DataAccessException {
		assertNotNull(rune);
		this.runeRepository.save(rune);
	}
	
	@Transactional
	public void deleteRune(Rune rune) throws DataAccessException {
		assertNotNull(rune);
		Collection<RunePage> runePages = this.runePageRepository.findAllByRune(rune.getId());
		runePages.forEach(x->this.runePageRepository.delete(x));
		this.runeRepository.delete(rune);
	}
	
	//Forma como está puesto el PetType
	@Transactional()
	public Collection<Branch> findBranches() throws DataAccessException {
		return this.branchRepository.findAll();
	}

	@Transactional
	public Rune findRuneById(final int id) throws DataAccessException {
		return runeRepository.findById(id).get();
	}
	
	//Metodos para los tests
	@Transactional()
	public Collection<Rune> findRuneByName(final String name) throws DataAccessException {
		return this.runeRepository.findByName(name);
	}

	
}
