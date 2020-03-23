package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import the_ionian_bookshelf.TheIonianBookshelfApplication;
import the_ionian_bookshelf.model.Champion;
import the_ionian_bookshelf.model.Item;
import the_ionian_bookshelf.model.Role;
import the_ionian_bookshelf.repository.ChampionRepository;
import the_ionian_bookshelf.repository.RoleRepository;
import the_ionian_bookshelf.service.ChampionService;
import the_ionian_bookshelf.service.ItemService;

@ContextConfiguration(classes = {TheIonianBookshelfApplication.class})
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ChampionServiceTests {

	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected ChampionRepository championRepository;

	@Autowired
	private ChampionService championService;
	
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
	
	
	@Test
	@Transactional
	void testRemoveChampionById() {
		long l = championRepository.count();
		championService.removeChampionById(2);
		long l2 = championRepository.count();
		assertEquals((l-1), l2);
	}
	
	@Test
	@Transactional
	void testRemoveChampionByIdError() {
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,()->championService.removeChampionById(4637));
		assertEquals(NoSuchElementException.class, exception.getClass());
	}
	
	@Test
	@Transactional
	void testSaveChampion() {
	
		Role r = new Role("rolTest", "testeoooooooooo", "https://www.google.es");
		this.roleRepository.save(r);
		Champion c = new Champion("Manolito Pies de Plata","desc",900.0,100.0,null,1.2,1.2,r);
		championService.saveChampion(c);
		Champion ii = championRepository.findById(c.getId()).get();
		assertEquals(c, ii);
	} 
	
	@Test
	@Transactional
	void testFindRoles() {
		Collection<Role> roles = championService.findRoless();
		assertEquals(roleRepository.count(), roles.size());
	}
}
