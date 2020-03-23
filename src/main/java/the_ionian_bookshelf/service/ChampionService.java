package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Actor;
import the_ionian_bookshelf.model.Authority;
import the_ionian_bookshelf.model.Build;
import the_ionian_bookshelf.model.Champion;
import the_ionian_bookshelf.model.Role;
import the_ionian_bookshelf.model.Summoner;
import the_ionian_bookshelf.repository.ChampionRepository;
import the_ionian_bookshelf.repository.RoleRepository;
import the_ionian_bookshelf.repository.RuneRepository;

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
	public Iterable<Champion> findAll(){
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

	@Autowired
	private AdministratorService adminService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private SummonerService summonerService;

	@Autowired
	private BuildService buildService;

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

	public Collection<Champion> findAll() {

		Collection<Champion> res = this.champRepo.findAll();
		assertNotNull(res);

		return res;
	}

	public Champion findOne(int id) {

		assertTrue(id != 0);

		final Champion res = this.champRepo.findById(id).get();
		assertNotNull(res);

		return res;

	}

	public Champion save(Champion champ) {

		assertNotNull(champ);

		Actor principal = this.actorService.findByPrincipal();

		assertTrue(this.actorService.checkAuthority(principal, Authority.ADMINISTRATOR)
				|| this.actorService.checkAuthority(principal, Authority.REVIEWER));

		return this.champRepo.save(champ);
	}

	public void delete(Champion champ) {

		assertNotNull(champ);
		this.adminService.findByPrincipal();
		
	}

	public void deleteFromMains(Champion champ) {

		Collection<Summoner> summoners = this.summonerService.findByChampion(champ);
		for (Summoner summ : summoners) {
			summ.getMains().remove(champ);
			this.summonerService.save(summ);
		}
	}

//	public void deleteFromBuilds(Champion champ) {
//
//		Collection<Build> builds = this.buildService.findByChamp(champ);
//		for (Build build : builds) {
//			this.buildService.delete(build);
//		}
//	}

}
