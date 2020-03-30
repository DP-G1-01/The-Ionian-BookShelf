package org.springframework.samples.the_ionian_bookshelf.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.repository.ChampionRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RoleRepository;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.service.RoleService;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ChampionServiceTests {

	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected ChampionRepository championRepository;

	@Autowired
	protected ChampionService championService;
	
	@Autowired
	protected RoleService roleService;
	
	//Encontrar todos los campeones
	@Test
	@BeforeAll
	void testFindAll() {
		Collection<Champion> champions = championService.findAll();
		assertEquals(championRepository.count(), champions.size());
	}
	
	
	
	
	//Encontrar un campeon por su id
	@Test
	@Transactional
	void testFindChampionById() {
		Champion i = championService.findChampionById(1);
		Champion ii = championRepository.findById(1).get();
		assertEquals(i, ii);
	} 
	
	@Test
	@Transactional
	void testFindChampionByIdError() {
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,()->championService.findChampionById(3472));
		assertEquals(NoSuchElementException.class, exception.getClass());
	}
	
	
	//Guarda un nuevo campeon
	@WithMockUser(value = "admin")
	@Test
	@Transactional
	void testSaveChampion() {
		
		Role tirador = new Role("Tirador","Descripcion del rol","http://www.miimagendetirador.coms");
		this.roleRepository.save(tirador);
	
		String name = "nombre";
		String desc = "descripcion";
		Double health = 900.0;
		Double mana = 500.0;
		Double energy = null;
		Double attack = 1.2;
		Double speed = 1.0;
		Champion champion = new Champion(name, desc, health, mana, energy, attack, speed, tirador);
		championService.save(champion);
		Champion ii = championRepository.findById(champion.getId()).get();
		assertEquals(champion, ii);
	} 
	
	
	//Eliminar un campeon
	@WithMockUser(value = "admin")
	@Test
	@Transactional
	void testRemoveChampion() {
		Role tirador = new Role("Tirador","Descripcion del rol","http://www.miimagendetirador.coms");
		this.roleRepository.save(tirador);
	
		String name = "nombre";
		String desc = "descripcion";
		Double health = 900.0;
		Double mana = 500.0;
		Double energy = null;
		Double attack = 1.2;
		Double speed = 1.0;
		Champion champion = new Champion(name, desc, health, mana, energy, attack, speed, tirador);
		championService.save(champion);	//Creo un champion y lo guardo
		long l = championRepository.count(); //Cuento el numero en el repositorio
		
		championService.deleteChampion(champion); //Lo borro
		long l2 = championRepository.count(); //Vuelvo a contar el numero total
		assertEquals((l-1), l2); 
	}
	
	//@WithMockUser(value = "admin") Creo que no es necesario ya que realmente el delete no se realiza debido a que salta la excepcion NoSuchElementException primero.
	//Elimino un campeón que no existe
	@Test
	@Transactional
	void testRemoveInexistentChampion() {
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,()->championService.deleteChampion(championService.findChampionById(123123)));
		assertEquals(NoSuchElementException.class, exception.getClass());
	}
	
	
	//Update Champion role
		@WithMockUser(value = "admin")
		@Test
		@Transactional
		public void shouldUpdateChampionRole() throws Exception {
			Champion champion= this.championService.findChampionById(1);
			Role oldRole = champion.getRole();

			Role newRole = this.roleRepository.findAll().get(4);
			champion.setRole(newRole);
			this.championService.save(champion);
			champion = this.championService.findChampionById(champion.getId());
			assertThat(champion.getRole()).isEqualTo(newRole);
		}
		
	@Test
	@Transactional
	void testFindAllRoles() {
		Collection<Role> roles = this.roleService.findAll();
		assertEquals(roleRepository.count(), roles.size());
	}
	
	
	
	
	
	
	
	
	

	
	
	
}
