package the_ionian_bookshelf.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import the_ionian_bookshelf.model.Role;
import the_ionian_bookshelf.repository.RoleRepository;

public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	
	@Transactional
	public Iterable<Role> findAll(){
		return roleRepository.findAll();
	}
	
	
	
	@Transactional
	public Role findBranchById(final int id) throws DataAccessException {
		return roleRepository.findById(id).get();
	}
}
