package the_ionian_bookshelf.service;

import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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
}
