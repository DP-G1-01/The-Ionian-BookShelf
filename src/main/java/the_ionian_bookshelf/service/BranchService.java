package the_ionian_bookshelf.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import the_ionian_bookshelf.model.Branch;
import the_ionian_bookshelf.model.Rune;
import the_ionian_bookshelf.repository.BranchRepository;
import the_ionian_bookshelf.repository.RuneRepository;

public class BranchService {
	
	@Autowired
	private BranchRepository branchRepository;
	
	@Autowired
	public BranchService(BranchRepository branchRepository) {
		this.branchRepository = branchRepository;
	}
	
	@Transactional
	public Iterable<Branch> findAll(){
		return branchRepository.findAll();
	}
	
	@Transactional
	public void saveBranch(Branch branch) throws DataAccessException {
		this.branchRepository.save(branch);
	}
	
	@Transactional
	public void deleteBranch(Branch branch) throws DataAccessException {
		this.branchRepository.delete(branch);
	}
	
	@Transactional
	public Branch findBranchById(final int id) throws DataAccessException {
		return branchRepository.findById(id).get();
	}

}
