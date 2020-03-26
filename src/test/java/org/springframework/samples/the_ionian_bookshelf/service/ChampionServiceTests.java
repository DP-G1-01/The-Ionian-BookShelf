package org.springframework.samples.the_ionian_bookshelf.service;

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
import org.springframework.samples.the_ionian_bookshelf.TheIonianBookshelfApplication;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.repository.ChampionRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RoleRepository;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.service.RoleService;
import org.springframework.test.context.ContextConfiguration;

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
	
	@Test
	@BeforeAll
	void testFindAll() {
		Collection<Champion> champions = championService.findAll();
		assertEquals(championRepository.count(), champions.size());
	}
	
 
	
	@Test
	@Transactional
	void testFindOne() {
		Champion i = championService.findChampionById(1);
		Champion ii = championRepository.findById(1).get();
		assertEquals(i, ii);
	} 
	
	
	@Test
	@Transactional
	void testFindOneError() {
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,()->championService.findChampionById(3472));
		assertEquals(NoSuchElementException.class, exception.getClass());
	}
	
	
//	@Test
//	@Transactional
//	void testRemoveChampionById() {
//		long l = this.championRepository.count();
//		this.championService.removeChampion(2);
//		long l2 = this.championRepository.count();
//		assertEquals((l-1), l2);
//	}
//	
//	@Test
//	@Transactional
//	void testRemoveChampionByIdError() {
//		NoSuchElementException exception = assertThrows(NoSuchElementException.class,()->championService.removeChampionById(4637));
//		assertEquals(NoSuchElementException.class, exception.getClass());
//	}
	
	@Test
	@Transactional
	void testSaveChampion() {
		
		Role r = new Role("rolTest", "testeoooooooooo", "https://www.google.es");
		this.roleRepository.save(r);
		Champion c = new Champion("Manolito Pies de Plata","desc",900.0,100.0,null,1.2,1.2,r);
		championService.save(c);
		Champion ii = championRepository.findById(c.getId()).get();
		assertEquals(c, ii);
	} 
	
	@Test
	@Transactional
	void testFindRoles() {
		Collection<Role> roles = this.roleService.findAll();
		assertEquals(roleRepository.count(), roles.size());
	}
}
