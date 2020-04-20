package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.the_ionian_bookshelf.model.Administrator;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.ItemService;
import org.springframework.samples.the_ionian_bookshelf.service.RoleService;
import org.springframework.samples.the_ionian_bookshelf.web.ItemController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIntegrationTests {

	@Autowired
	private ItemController itemController;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private AdministratorService administratorService;
	

	@Test
	void testShowItemListHtml() throws Exception {
		ModelMap model = new ModelMap();
		String view=itemController.processFindForm(model);
		assertEquals(view,"items/itemsList");
		assertNotNull(model.get("items"));	
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testNewItemForm() throws Exception {
		ModelMap model = new ModelMap();
		String view=itemController.crearItem(model);
		assertEquals(view,"items/editItem");
		assertNotNull(model.get("item"));	
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testCreationForm() throws Exception {
		ModelMap model = new ModelMap();
		Item item= new Item();
		List<String> attributes = new ArrayList<>();
		attributes.add("21");
		attributes.add("33");
		attributes.add("43");
		Role rol1 = roleService.findOneById(1);
		Role rol2 =	roleService.findOneById(2);
		Role rol3 = roleService.findOneById(3);
		List<Role> roles = new ArrayList<>();
		roles.add(rol1);
		roles.add(rol2);
		roles.add(rol3);
		
		item.setTitle("titulo test");
		item.setDescription("descripcion descriptiva");
		item.setAttributes(attributes);
		item.setRoles(roles);
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view=itemController.salvarItem(item, bindingResult, model);
		assertEquals(view,"redirect:/items/");
		assertNotNull(model.getAttribute("message"));
	}
	
//	@WithMockUser(username = "admin", authorities = "admin")
//	@Test
//	void testRemoveItem() throws Exception {
//		ModelMap model = new ModelMap();
//		Item item= new Item();
//		List<String> attributes = new ArrayList<>();
//		attributes.add("21");
//		attributes.add("33");
//		attributes.add("43");
//		Role rol1 = roleService.findOneById(1);
//		Role rol2 =	roleService.findOneById(2);
//		Role rol3 = roleService.findOneById(3);
//		List<Role> roles = new ArrayList<>();
//		roles.add(rol1);
//		roles.add(rol2);
//		roles.add(rol3);
//		
//		item.setTitle("titulo test remove");
//		item.setDescription("descripcion descriptiva");
//		item.setAttributes(attributes);
//		item.setRoles(roles);
//		item.setId(6);
//		itemService.saveItem(item);
//		String view=itemController.removeItem(6, model);
//		assertEquals(view,"redirect:items/");
//		assertNotNull(model.get("message"));	
//	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testRemoveItemNotAdmin() throws Exception {
		ModelMap model = new ModelMap();
		String view=itemController.removeItem(1, model);
		assertEquals(view,"redirect:/items");
		assertNotNull(model.get("message"));	
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testCreationFormErrors() throws Exception {
		ModelMap model = new ModelMap();
		Item item= new Item();
		List<String> attributes = new ArrayList<>();
		attributes.add("21");
		attributes.add("33");
		attributes.add("43");
		Role rol1 = roleService.findOneById(1);
		Role rol2 =	roleService.findOneById(2);
		Role rol3 = roleService.findOneById(3);
		List<Role> roles = new ArrayList<>();
		roles.add(rol1);
		roles.add(rol2);
		roles.add(rol3);

		item.setTitle("");
		item.setDescription("");
		item.setAttributes(attributes);
		item.setRoles(roles);
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		bindingResult.reject("title", "no puede estar vacío");
		bindingResult.reject("title", "el tamaño tiene que estar entre 10 y 500");
		bindingResult.reject("description", "no puede estar vacío");
		String view=itemController.salvarItem(item, bindingResult, model);
		assertEquals(view,"items/editItem");
	}
	
}
