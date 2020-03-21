package service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import the_ionian_bookshelf.TheIonianBookshelfApplication;
import the_ionian_bookshelf.model.Item;
import the_ionian_bookshelf.model.Role;
import the_ionian_bookshelf.repository.ItemRepository;
import the_ionian_bookshelf.repository.RoleRepository;
import the_ionian_bookshelf.service.ItemService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TheIonianBookshelfApplication.class})
@SpringBootTest
public class ItemServiceTests {

	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected ItemRepository itemRepository;

	@Autowired
	private ItemService itemService;
	
	@Test
	void testFindAll() {
		Collection<Item> items = itemService.findAll();
		assertEquals(itemRepository.count(), items.size());
	}
	
	@Test
	void testFindAllEmpty() {
		itemRepository.deleteAll();
		assertEquals(0, itemRepository.count());
	}
	
	@Test
	@Transactional
	void testFindOne() {
		Item i = itemService.findItemById(1);
		Item ii = itemRepository.findItemById(1);
		assertEquals(i, ii);
	}
	
	@Test
	@Transactional
	void testRemoveItemById() {
		long l = itemRepository.count();
		itemService.removeItemById(1);
		long l2 = itemRepository.count();
		assertEquals((l-1), l2);
	}
	
	@Test
	@Transactional
	void testSaveItem() {
		List <String> attributes = new ArrayList<>();
		attributes.add("atributo");
		List <Role> roles = new ArrayList<>();
		Role r = new Role("rolTest", "testeoooooooooo", "imagen");
		roles.add(r);
		Item i = new Item("test", "testeandoooooooo", attributes, roles);
		itemService.saveItem(i);
		Item ii = itemRepository.findItemById(2);
		assertEquals(i, ii);
	}
	
	@Test
	@Transactional
	void testFindRoles() {
		Collection<Role> roles = itemService.findRoles();
		assertEquals(roleRepository.count(), roles.size());
	}
}
