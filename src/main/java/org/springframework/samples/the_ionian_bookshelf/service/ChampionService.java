package org.springframework.samples.the_ionian_bookshelf.service;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.repository.ChampionRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChampionService {

	@Autowired
	private ChampionRepository championRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	public ChampionService(ChampionRepository championRepository, RoleRepository roleRepository) {
		this.championRepository = championRepository;
		this.roleRepository = roleRepository;
	}


	//Método para listar runas
	@Transactional
	public Set<Champion> findChampions() throws DataAccessException {
		Set<Champion> champions = new TreeSet<>();
		this.championRepository.findAll().forEach(champions::add);
		return champions;
	}
	
	
	@Transactional
	public Iterable<Champion> findAll() throws DataAccessException {
		return championRepository.findAll();
	}
	
	@Transactional
	public void saveChampion(Champion champion) throws DataAccessException {
		this.championRepository.save(champion);
	}
	
	@Transactional
	public void deleteChampion(Champion champion) throws DataAccessException {
		this.championRepository.delete(champion);
	}
	

	
	@Transactional
	public Set<Role> findRoles() throws DataAccessException {
		Set<Role> roles = new TreeSet<>();
		this.roleRepository.findAll().forEach(roles::add);
		return roles;
	}
	
	//Forma como está puesto el PetType
	
	@Transactional()
	public Collection<Role> findRoless() throws DataAccessException {
		return this.roleRepository.findAll();
	}
	
	@Transactional
	public Iterable<Role> findAllR(){
		return roleRepository.findAll();
	}

	@Transactional
	public Champion findChampionById(final int id) throws DataAccessException {
		return championRepository.findById(id).get();
	}
	
	//Metodos para los tests
	@Transactional()
	public Collection<Champion> findRuneByName(final String name) throws DataAccessException {
		return this.championRepository.findByName(name);
	}

//	public void deleteFromBuilds(Champion champ) {
//
//		Collection<Build> builds = this.buildService.findByChamp(champ);
//		for (Build build : builds) {
//			this.buildService.delete(build);
//		}
//	}

}
