package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Actor;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.repository.ChampionRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChampionService {

	@Autowired
	private ChampionRepository champRepo;

	@Autowired
	private AdministratorService adminService;

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

		assertTrue(this.authService.checkAuthorities("administrator") || this.authService.checkAuthorities("reviewer"));

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
