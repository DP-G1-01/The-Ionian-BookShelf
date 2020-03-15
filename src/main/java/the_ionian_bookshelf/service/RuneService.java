package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Optional;
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
	private BranchRepository branchRepository;
	
	@Autowired
	public RuneService(RuneRepository runeRepository, BranchRepository branchRepository) {
		this.runeRepository = runeRepository;
		this.branchRepository = branchRepository;
	}

	//Método para listar runas
	@Transactional
	public Set<Rune> findRunes() throws DataAccessException {
		Set<Rune> runes = new TreeSet<>();
		this.runeRepository.findAll().forEach(runes::add);
		return runes;
	}
	
	@Transactional
	public Iterable<Rune> findAll(){
		return runeRepository.findAll();
	}
	
	@Transactional
	public void saveRune(Rune rune) throws DataAccessException {
		this.runeRepository.save(rune);
	}
	
	@Transactional
	public void deleteRune(Rune rune) throws DataAccessException {
		this.runeRepository.delete(rune);
	}
	

	
	@Transactional
	public Set<Branch> findBranches() throws DataAccessException {
		Set<Branch> branches = new TreeSet<>();
		this.branchRepository.findAll().forEach(branches::add);
		return branches;
	}
	
	//Forma como está puesto el PetType
	@Transactional()
	public Collection<Branch> findBranchess() throws DataAccessException {
		return this.branchRepository.findAll();
	}
	
	@Transactional
	public Iterable<Branch> findAllB(){
		return branchRepository.findAll();
	}

	@Transactional
	public Rune findRuneById(final int id) throws DataAccessException {
		return runeRepository.findById(id).get();
	}

	
}
