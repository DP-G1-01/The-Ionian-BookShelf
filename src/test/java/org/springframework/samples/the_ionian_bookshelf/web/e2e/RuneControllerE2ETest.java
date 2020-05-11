package org.springframework.samples.the_ionian_bookshelf.web.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
/*@TestPropertySource(
  locations = "classpath:application-mysql.properties")*/
public class RuneControllerE2ETest {
	@Autowired
	private MockMvc mockMvc;
	
	
	@WithMockUser(username="admin",authorities= {"admin"})
	@Test
	void testShowRuneListHtml() throws Exception {
		
		mockMvc.perform(get("/runes")).andExpect(status().isOk()).andExpect(model().attributeExists("runes"))
		.andExpect(model().attributeExists("branch"))
				.andExpect(view().name("runes/listadoRunas"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testInitCreationRuneFormAdmin() throws Exception {
		mockMvc.perform(get("/runes/new")).andExpect(status().is2xxSuccessful()).andExpect(model()
				.attributeExists("rune"))
				.andExpect(view().name("runes/editRune"));
	}

	@WithMockUser(username="summoner1",authorities= {"summoner"})
	@Test
	void testInitCreationRuneFormNoAdmin() throws Exception {
		mockMvc.perform(get("/runes/new")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
	
	
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessCreationRuneFormSuccess() throws Exception {
		mockMvc.perform(post("/runes/save").with(csrf()).param("name", "nombre runa").param("description", "Una descripción de runa")
				.param("branch", "Precision").param("node", "1"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/runes/"));
	}
	
	@WithMockUser(value = "summoner1", authorities = {"summoner1"})
	@Test
	void testProcessCreationRuneFormSuccessNotAdmin() throws Exception {
		mockMvc.perform(post("/runes/save").with(csrf()).param("name", "nombre runa").param("description", "Una descripción de runa")
				.param("branch", "Precision").param("node", "1"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/login"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessCreationRuneFormHasErrors() throws Exception {
		mockMvc.perform(post("/runes/save").with(csrf()).param("name", "").param("description", "Una descripción de runa")
				.param("branch", "Precision").param("node", "1"))
				.andExpect(status().isOk()).andExpect(view().name("runes/editRune"))
				.andExpect(model().attributeHasFieldErrors("rune", "name"));
			
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testDeleteRuneSuccess() throws Exception {
		mockMvc.perform(get("/runes/{runeId}/remove", 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/runes/"));
	}
	
	@WithMockUser(value = "summoner1", authorities = {"summoner"})
	@Test
	void testDeleteRuneWithoutLoginAsAdmin() throws Exception {
		mockMvc.perform(get("/runes/{runeId}/remove", 1)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
	
	
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testInitUpdateRuneFormAdmin() throws Exception {
		mockMvc.perform(get("/runes/1/edit")).andExpect(status().is2xxSuccessful()).andExpect(model()
				.attributeExists("rune"))
				.andExpect(view().name("runes/editRune"));
	}
	
	@WithMockUser(value = "summoner1", authorities = {"summoner1"})
	@Test
	void testInitUpdateRuneFormNotAdmin() throws Exception {
		mockMvc.perform(get("/runes/1/edit")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessUpdateRuneFormSuccess() throws Exception {
		mockMvc.perform(post("/runes/1/edit").with(csrf()).param("name", "newname").param("description", "Una descripción de runa")
				.param("branch", "Precision").param("node", "1"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/runes/"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessUpdateRuneFormError() throws Exception {
		mockMvc.perform(post("/runes/1/edit").with(csrf()).param("name", "").param("description", "Una descripción de runa")
				.param("branch", "Precision").param("node", "1"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/runes/editRune"));
	}
	
	
	
}
