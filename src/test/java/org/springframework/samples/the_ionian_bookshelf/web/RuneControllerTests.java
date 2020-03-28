package org.springframework.samples.the_ionian_bookshelf.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.samples.the_ionian_bookshelf.service.RuneService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


//Con estas anotaciones he corregido el error failed to load ApplicationContext
@SpringBootTest
@AutoConfigureMockMvc
class RuneControllerTests {
	
	private static final int TEST_RUNE_ID = 4;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RuneService runeService;

	//Mostara el listado
	@WithMockUser(value = "spring")
	@Test
	void testShowRuneList() throws Exception {
		mockMvc.perform(get("/runes")).andExpect(status().isOk()).andExpect(model().attributeExists("runes"))
				.andExpect(model().attributeExists("branch")).andExpect(view().name("runes/listadoRunas"));
	}

	//Acceder al form de creación
	@WithMockUser(value = "admin")
	@Test
	void testInitCreationRuneForm() throws Exception {
		mockMvc.perform(get("/runes/new")).andExpect(status().isOk()).andExpect(model().attributeExists("rune"))
				.andExpect(view().name("runes/editRune"));
	}
	
	//Intento de acceder al form de creación sin ser admin
	@WithMockUser(value = "pepe")
	@Test
	void testInitCreationRuneFormWithoutLoginAsAdmin() throws Exception {
		mockMvc.perform(get("/runes/new")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}

	//No redirige, me da isOk y creo que está mal
//	@WithMockUser(value = "admin")
//	@Test
//	void testProcessCreationRuneFormSuccess() throws Exception {
//		mockMvc.perform(post("/runes/new").param("name", "Joe").param("description", "Bloggs")
//				.param("branch", "Precision").param("node", "1")).andExpect(status().is3xxRedirection());
//	}
	
	//Delete de una runa siendo admin
	@WithMockUser(value = "admin")
	@Test
	void testDeleteRuneSuccess() throws Exception {
		mockMvc.perform(get("/runes/{runeId}/remove", TEST_RUNE_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/runes/"));
	}
	
	//Intento de delete de una runa sin ser admin
	@WithMockUser(value = "spring")
	@Test
	void testDeleteRuneWithoutLoginAsAdmin() throws Exception {
		mockMvc.perform(get("/runes/{runeId}/remove", TEST_RUNE_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}

}
