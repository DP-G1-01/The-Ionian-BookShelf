package org.springframework.samples.the_ionian_bookshelf.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.the_ionian_bookshelf.TheIonianBookshelfApplication;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.service.ItemService;
import org.springframework.samples.the_ionian_bookshelf.web.ItemController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
@SpringBootTest (classes = TheIonianBookshelfApplication.class)
class ItemControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ItemService itemService;
	
	//@WithMockUser(value = "spring")
	@Test
	void testShowItemListHtml() throws Exception {
		mockMvc.perform(get("/items")).andExpect(status().isOk()).andExpect(model().attributeExists("items"))
		.andExpect(model().attributeExists("role"))
				.andExpect(view().name("items/itemsList"));
	}
	

	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/items/new")).andExpect(status().isOk()).andExpect(model().attributeExists("item"))
				.andExpect(view().name("items/editItem"));
	}
	
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/items/new").param("title", "titulo test").param("description", "Testeando items en vez de jugar al Doom")
				.param("atributes[0]", "43").param("atributes[1]", "10").param("atributes[2]", "").param("roles[0]", "0"))
				.andExpect(status().is3xxRedirection());
	}
	
//	@Test
//	void testProcessCreationFormHasErrors() throws Exception {
//		mockMvc.perform(post("/items/new").param("title", "titulo test")
//				.param("atributes[0]", "43").param("atributes[1]", "10").param("atributes[2]", "").param("roles[0]", "0"))
//				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("item"))
//				.andExpect(model().attributeHasFieldErrors("item", "description"))
//				.andExpect(view().name("items/editItem"));
//	}
}
