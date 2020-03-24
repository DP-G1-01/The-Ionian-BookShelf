package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Champion;
import the_ionian_bookshelf.model.Role;
import the_ionian_bookshelf.repository.ChampionRepository;

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

	@Transactional
	public Collection<Champion> findAll() throws DataAccessException {
		return championRepository.findAll();
	}

	public Champion save(Champion champ) {

		assertNotNull(champ);

		assertTrue(this.authService.checkAuthorities("administrator") || this.authService.checkAuthorities("reviewer"));

		return this.championRepository.save(champ);
	}


	@Transactional
	public void deleteChampion(Champion champion) throws DataAccessException {
		assertNotNull(champion);
		assertTrue(this.authService.checkAuthorities("administrator") || this.authService.checkAuthorities("reviewer"));
		this.championRepository.delete(champion);
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
