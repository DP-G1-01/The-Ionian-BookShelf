package org.springframework.samples.the_ionian_bookshelf.web.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(
//locations = "classpath:application-mysql.properties")
public class RunePageControllerE2ETest {

	@Autowired
	private MockMvc mockMvc;
	
	
	@WithMockUser(username="summoner1",authorities= {"summoner"})
	@Test
	void testShowRunePageListHtml() throws Exception {
		
		mockMvc.perform(get("/runePages/mine")).andExpect(status().isOk()).andExpect(model().attributeExists("runePages"))
		.andExpect(model().attributeExists("branches")).andExpect(model().attributeExists("runes")).andExpect(model().attributeExists("secondaryRunes"))
				.andExpect(view().name("runePages/listadoPaginasRunas"));
	}
	
	@WithMockUser(value = "summoner1", authorities = {"summoner"})
	@Test
	void testInitCreationRunePageForm() throws Exception {
		mockMvc.perform(get("/runePages/new")).andExpect(status().is2xxSuccessful()).andExpect(model()
				.attributeExists("runePage")).andExpect(model().attributeExists("branches")).andExpect(model().attributeExists("runes"))
		.andExpect(model().attributeExists("secondaryRunes"))
				.andExpect(view().name("runePages/editRunePage"));
	}
	
	@Test
	void testInitCreationRunePageFormNotLogged() throws Exception {
		mockMvc.perform(get("/runePages/new")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
	
//	@WithMockUser(value = "summoner1", authorities = {"summoner"})
//	@Test
//	void testProcessCreationRunePageFormSuccess() throws Exception {
//		mockMvc.perform(post("/runePages/save").with(csrf()).param("name", "name test").param("summoner", "1")
//				.param("mainBranch", "Precision").param("secondaryBranch", "Domination").param("keyRune", "Conqueror")
//				.param("mainRune1", "Overheal").param("mainRune2", "Legend: Alacrity").param("mainRune3", "Cut Down")
//				.param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
//				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/runePages/mine"));
//	}
	
	@Test
	void testProcessCreationRunePageFormSuccessNotLogged() throws Exception {
		mockMvc.perform(post("/runePages/save").with(csrf()).param("name", "name test").param("summoner", "1")
				.param("mainBranch", "Precision").param("secondaryBranch", "Domination").param("keyRune", "Conqueror")
				.param("mainRune1", "Overheal").param("mainRune2", "Legend: Alacrity").param("mainRune3", "Cut Down")
				.param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/login"));
	}
	
//	@WithMockUser(value = "summoner1", authorities = {"summoner"})
//	@Test
//	void testProcessCreationRunePageFormErrors() throws Exception {
//		mockMvc.perform(post("/runePages/save").with(csrf()).param("name", "name test").param("summoner", "1")
//				.param("mainBranch", "Precision").param("secondaryBranch", "Precision").param("keyRune", "Conqueror")
//				.param("mainRune1", "Overheal").param("mainRune2", "Legend: Alacrity").param("mainRune3", "Cut Down")
//				.param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
//				.andExpect(status().is2xxSuccessful()).andExpect(view().name("runePages/editRunePage"))
//				.andExpect(model().attributeHasFieldErrorCode("runePage", "mainBranch",
//						"Main Branch should not be the same as Secondary Branch"));
//	}
	
//	@WithMockUser(value = "summoner1", authorities = {"summoner"})
//	@Test
//	void testDeleteRunePageSuccess() throws Exception {
//		mockMvc.perform(get("/runePages/{runePageId}/remove", 1)).andExpect(status().is3xxRedirection())
//				.andExpect(view().name("redirect:/runePages/mine"));
//	}
	
//	@WithMockUser(value = "summoner2", authorities = {"summoner"})
//	@Test
//	void testDeleteRunePageNotYours() throws Exception {
//		mockMvc.perform(get("/runePages/{runePageId}/remove", 1)).andExpect(status().is3xxRedirection())
//				.andExpect(view().name("redirect:/runePages/mine"));
//	}
	
	@Test
	void testDeleteRunePageNotLogged() throws Exception {
		mockMvc.perform(get("/runePages/{runePageId}/remove", 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}
	
//	@WithMockUser(value = "summoner1", authorities = {"summoner"})
//	@Test
//	void testInitUpdateRunePageFormSuccess() throws Exception {
//		mockMvc.perform(get("/runePages/{runePageId}/edit", 1)).andExpect(status().is2xxSuccessful()).andExpect(model()
//				.attributeExists("runePage"))
//				.andExpect(view().name("runePages/editRunePage"));
//	}
	
//	@WithMockUser(value = "summoner2", authorities = {"summoner"})
//	@Test
//	void testInitUpdateRunePageFormNotYours() throws Exception {
//		mockMvc.perform(get("/runePages/{runePageId}/edit", 1)).andExpect(status().is3xxRedirection())
//				.andExpect(view().name("redirect:/runePages/mine"));
//	}
	@Test
	void testInitUpdateRunePageFormNotLogged() throws Exception {
		mockMvc.perform(get("/runePages/{runePageId}/edit", 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}
	
//	@WithMockUser(value = "summoner1", authorities = {"summoner"})
//	@Test
//	void testProcessUpdateRunePageFormSuccess() throws Exception {
//		mockMvc.perform(post("/runePages/{runePageId}/edit", 1).with(csrf()).param("name", "name test").param("summoner", "1")
//				.param("mainBranch", "Precision").param("secondaryBranch", "Domination").param("keyRune", "Conqueror")
//				.param("mainRune1", "Overheal").param("mainRune2", "Legend: Alacrity").param("mainRune3", "Cut Down")
//				.param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
//				.andExpect(status().is2xxSuccessful()).andExpect(view().name("runePages/mine"));
//	}
	
//	@WithMockUser(value = "summoner2", authorities = {"summoner"})
//	@Test
//	void testProcessUpdateRunePageFormNotYours() throws Exception {
//		mockMvc.perform(post("/runePages/{runePageId}/edit", 1).with(csrf()).param("name", "name test").param("summoner", "1")
//				.param("mainBranch", "Precision").param("secondaryBranch", "Domination").param("keyRune", "Conqueror")
//				.param("mainRune1", "Overheal").param("mainRune2", "Legend: Alacrity").param("mainRune3", "Cut Down")
//				.param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
//				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/runePages/mine"));
//	}
	
	@WithMockUser(value = "summoner1", authorities = {"summoner"})
	@Test
	void testProcessUpdateRunePageFormErrors() throws Exception {
		mockMvc.perform(post("/runePages/{runePageId}/edit", 1).with(csrf()).param("name", "name test").param("summoner", "1")
				.param("mainBranch", "Precision").param("secondaryBranch", "Precision").param("keyRune", "Conqueror")
				.param("mainRune1", "Overheal").param("mainRune2", "Legend: Alacrity").param("mainRune3", "Cut Down")
				.param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/runePages/editRunePage"));
	}
}
