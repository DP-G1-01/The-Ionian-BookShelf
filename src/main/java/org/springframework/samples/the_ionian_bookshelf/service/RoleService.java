package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private AdministratorService adminService;

	public Role findDefaultRole() {

		this.adminService.findByPrincipal();

		Role res = this.roleRepo.findDefaultRole();
		assertNotNull(res);

		return res;
	}

	public Collection<Role> findAll() {
		return roleRepo.findAll();
	}

}
