package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.repository.BuildRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ChampionRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChampionService {

	@Autowired
	private ChampionRepository championRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AdministratorService adminService;

	@Autowired
	private AuthoritiesService authService;
	
	@Autowired
	private BuildRepository buildRepository;



	@Transactional
	public Collection<Champion> findAll() throws DataAccessException {
		return championRepository.findAll();
	}

	public Champion save(Champion champ) {
		assertNotNull(champ);
		assertTrue(this.authService.checkAuthorities("administrator"));
		return this.championRepository.save(champ);
	}


	@Transactional
	public void deleteChampion(Champion champion) throws DataAccessException {
		assertNotNull(champion);
		assertTrue(this.authService.checkAuthorities("administrator"));
		Collection<Build> builds = this.buildRepository.findAllByChampion(champion.getId());//Peta en Ashe porque tiene asociado un change_request
		builds.forEach(x -> this.buildRepository.delete(x));
		this.championRepository.delete(champion);
	}

	@Transactional
	public Champion findChampionById(final int id) throws DataAccessException {
		return championRepository.findById(id).get();
	}



}
