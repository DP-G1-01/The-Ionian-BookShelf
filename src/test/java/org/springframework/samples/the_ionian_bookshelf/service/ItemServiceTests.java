package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
import org.springframework.security.test.context.support.WithMockUser;
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
	

	@WithMockUser(username = "admin")
	@DisplayName("Find by Item ID")
	@ParameterizedTest(name = "\"{0}\": Represents Item's ID")
	@CsvSource({ "0", "1", "3","278494" })
	void findOneByItemIdTest(Integer itemId) {

		if (itemId == 0 || itemId == 278494) {
			assertThrows(AssertionError.class, () -> {
				this.itemService.findItemById(itemId);
			});
			
		} else {
			Item i = this.itemService.findItemById(itemId);
			String s = "titulo1";
			String ii = "Amuleto de las hadas";
			if(itemId==1) {
				assertEquals(i.getTitle(), s);
			}else {
				assertEquals(i.getTitle(), ii);
			}
		}

	}

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
	
	@WithMockUser(username = "admin")
	@DisplayName("Delete by Item ID")
	@ParameterizedTest(name = "\"{0}\": Represents Item's ID")
	@CsvSource({ "0", "1","278494" })
	void deleteByItemIdTest(Integer itemId) {

		if (itemId == 0 || itemId == 278494) {
			AssertionError exception = assertThrows(AssertionError.class, () -> {
				this.itemService.removeItemById(itemId);
				
			});
			assertEquals(AssertionError.class, exception.getClass());
		} else {
			this.itemService.removeItemById(itemId);
		}

	}
	
}
