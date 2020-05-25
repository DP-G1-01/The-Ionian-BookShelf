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
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.repository.BuildRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ItemRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RoleRepository;
import org.springframework.samples.the_ionian_bookshelf.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
class ItemServiceTests {

	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected ItemRepository itemRepository;

	@Autowired
	protected BuildRepository buildRepository;
	
	@Autowired
	protected ItemService itemService;
	
	@Test
	@Transactional
	void testFindAll() {
		Collection<Item> items = itemService.findAll();
		assertEquals(itemRepository.count(), items.size());
	}
	

	
	@Test
	@Transactional
	void testFindOne() {
		Item i = this.itemService.findItemById(1);
		String ii = "titulo1";
		assertEquals(i.getTitle(), ii);
	} 
	
	
	@Test
	@Transactional
	void testFindOneError() {
		AssertionError exception = assertThrows(AssertionError.class,()->itemService.findItemById(3472));
		assertEquals(AssertionError.class, exception.getClass());
	}
	
	
//	@Test
//	@Transactional
//	void testRemoveItemById() {
//		long l = itemRepository.count();
//		itemService.removeItemById((int) (l-1));
//		long l2 = itemRepository.count();
//		assertEquals((l-1), l2);
//	}
//	
	@Test
	@Transactional
	void testRemoveItemByIdError() {
		AssertionError exception = assertThrows(AssertionError.class,()->itemService.removeItemById(4637));
		assertEquals(AssertionError.class, exception.getClass());
	}
	
	@Test
	@Transactional
	void testSaveItem() {
		Item item= new Item();
		List<String> attributes = new ArrayList<>();
		attributes.add("21");
		attributes.add("33");
		attributes.add("43");
		List<Role> roles = new ArrayList<>();
		Role r = new Role("Rol1", "Soy un rol de prueba ten paciencia", "https://www.youtube.com/");
		roles.add(r);
		
		item.setTitle("titulo test");
		item.setDescription("descripcion descriptiva");
		item.setAttributes(attributes);
		item.setRoles(roles);
		itemService.saveItem(item);
		
		Item ii = itemRepository.findItemById(item.getId());
		assertEquals(item,ii);
	} 
	
	@Test
	@Transactional
	void testFindRoles() {
		Collection<Role> roles = itemService.findRoles();
		assertEquals(roleRepository.count(), roles.size());
	}
//	@Test
//	@Transactional
//	@AfterAll
//	void testFindAllEmpty() {
//		buildRepository.deleteAll();
//		itemRepository.deleteAll();
//		assertEquals(0, itemRepository.count());
//	} 
}
