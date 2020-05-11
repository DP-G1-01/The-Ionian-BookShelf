package org.springframework.samples.the_ionian_bookshelf.web.e2e;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.the_ionian_bookshelf.model.Administrator;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.ItemService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-mysql.properties")
public class ItemControllerE2ETest {
	@Autowired
	private MockMvc mockMvc;
	
	
	@WithMockUser(username="summoner1",authorities= {"summoner"})
	@Test
	void testShowItemListHtml() throws Exception {
		
		mockMvc.perform(get("/items")).andExpect(status().isOk()).andExpect(model().attributeExists("items"))
		.andExpect(model().attributeExists("role"))
				.andExpect(view().name("items/itemsList"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testInitCreationFormAdmin() throws Exception {
		mockMvc.perform(get("/items/new")).andExpect(status().is2xxSuccessful()).andExpect(model()
				.attributeExists("item"))
				.andExpect(view().name("items/editItem"));
	}

	@WithMockUser(username="summoner1",authorities= {"summoner"})
	@Test
	void testInitCreationFormSumm() throws Exception {
		mockMvc.perform(get("/items/new")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
	}
	
	
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/items/save").with(csrf()).param("title", "titulo test").param("description", "Testeando items en vez de jugar al Doom")
				.param("attributes[0]", "43").param("attributes[1]", "10").param("attributes[2]", "23").param("roles[0]", "Tirador")
				.param("roles[1]", "Apoyo").param("roles[2]", "Tanque"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/items/"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/items/save").with(csrf()).param("title", "").param("description", "")
				.param("attributes[0]", "43").param("attributes[1]", "10").param("attributes[2]", "67").param("roles[0]", "Apoyo")
				.param("roles[1]", "Tirador").param("roles[2]", "Asesino"))
				.andExpect(status().isOk()).andExpect(view().name("items/editItem"))
				.andExpect(model().attributeHasFieldErrors("item", "description"))
				.andExpect(model().attributeHasFieldErrors("item", "title"));
			
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testDeleteItemSuccess() throws Exception {
		mockMvc.perform(get("/items/{itemId}/remove", 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/items"));
	}
	
	@WithMockUser(value = "summoner1", authorities = {"summoner"})
	@Test
	void testDeleteItemWithoutLoginAsAdmin() throws Exception {
		mockMvc.perform(get("/items/{itemId}/remove", 1)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/items"));
	}
}
