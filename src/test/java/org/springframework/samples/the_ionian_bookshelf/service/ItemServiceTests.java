package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.repository.BuildRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ItemRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RoleRepository;
import org.springframework.samples.the_ionian_bookshelf.service.ItemService;
import org.springframework.ui.ModelMap;
@SpringBootTest
@TestInstance (Lifecycle.PER_CLASS)
public class ItemServiceTests {

	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected ItemRepository itemRepository;

	@Autowired
	protected BuildRepository buildRepository;
	
	@Autowired
	private ItemService itemService;
	
	@Test
	@BeforeAll
	void testFindAll() {
		Collection<Item> items = itemService.findAll();
		assertEquals(itemRepository.count(), items.size());
	}
	
	@Test
	@Transactional
	@AfterAll
	void testFindAllEmpty() {
		buildRepository.deleteAll();
		itemRepository.deleteAll();
		assertEquals(0, itemRepository.count());
	} 
	
//	@Test
//	@Transactional
//	void testFindOne() {
//		Item i = itemService.findItemById(1);
//		Item ii = itemRepository.findItemById(1);
//		assertEquals(i, ii);
//	} 
//	
	
	@Test
	@Transactional
	void testFindOneError() {
		AssertionError exception = assertThrows(AssertionError.class,()->itemService.findItemById(3472));
		assertEquals(AssertionError.class, exception.getClass());
	}
	
	
	@Test
	@Transactional
	void testRemoveItemById() {
		long l = itemRepository.count();
		itemService.removeItemById(2);
		long l2 = itemRepository.count();
		assertEquals((l-1), l2);
	}
	
	@Test
	@Transactional
	void testRemoveItemByIdError() {
		AssertionError exception = assertThrows(AssertionError.class,()->itemService.removeItemById(4637));
		assertEquals(AssertionError.class, exception.getClass());
	}
	
//	@Test
//	@Transactional
//	void testSaveItem() {
//		Item item= new Item();
//		List<String> attributes = new ArrayList<>();
//		attributes.add("21");
//		attributes.add("33");
//		attributes.add("43");
//		List<Role> roles = new ArrayList<>();
//		Role r = new Role("rolTest", "testeoooooooooo", "http://www.google.es");
//		roles.add(r);
//		
//		item.setTitle("titulo test");
//		item.setDescription("descripcion descriptiva");
//		item.setAttributes(attributes);
//		item.setRoles(roles);
//		long l2 = itemRepository.count();
//		itemService.saveItem(item);
//		
//		Item ii = itemRepository.findItemById((int) (l2));
//		assertEquals(item,ii);
//	} 
//	
	@Test
	@Transactional
	void testFindRoles() {
		Collection<Role> roles = itemService.findRoles();
		assertEquals(roleRepository.count(), roles.size());
	}
}
