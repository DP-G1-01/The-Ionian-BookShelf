package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Actor;
import the_ionian_bookshelf.model.Champion;
import the_ionian_bookshelf.model.Item;
import the_ionian_bookshelf.model.Role;
import the_ionian_bookshelf.repository.ChampionRepository;
import the_ionian_bookshelf.repository.RoleRepository;

@Service
@Transactional
public class ChampionService {

	@Autowired
	private ChampionRepository championRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private SummonerService summonerService;

	@Autowired
	private AuthoritiesService authService;

	public Champion create() {

		this.adminService.findByPrincipal();

		Role defaultRole = this.roleService.findDefaultRole();

		Champion res = new Champion();
		res.setName("New champion");
		res.setDescription("New description");
		res.setHealth(0.);
		res.setMana(null);
		res.setEnergy(null);
		res.setAttack(0.);
		res.setSpeed(0.);

		res.setRole(defaultRole);

		return res;
	}


	//Método para listar runas
	@Transactional
	public Set<Champion> findChampions() throws DataAccessException {
		Set<Champion> champions = new TreeSet<>();
		this.championRepository.findAll().forEach(champions::add);
		return champions;
	}


	@Transactional
	public Collection<Champion> findAll() throws DataAccessException {
		return championRepository.findAll();
	}

	public Champion save(Champion champ) {

		assertNotNull(champ);

		assertTrue(this.authService.checkAuthorities("administrator") || this.authService.checkAuthorities("reviewer"));

		return this.champRepo.save(champ);
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

	public void removeChampionById(int champId) {
		Champion champion = championRepository.findById(champId).get();
		assertNotNull(champion);
		championRepository.delete(champion);
	}

//	public void deleteFromBuilds(Champion champ) {
//
//		Collection<Build> builds = this.buildService.findByChamp(champ);
//		for (Build build : builds) {
//			this.buildService.delete(build);
//		}
//	}

}
