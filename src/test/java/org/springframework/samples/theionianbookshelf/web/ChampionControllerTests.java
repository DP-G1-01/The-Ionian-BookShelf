package org.springframework.samples.theionianbookshelf.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import the_ionian_bookshelf.TheIonianBookshelfApplication;
import the_ionian_bookshelf.service.ChampionService;
import the_ionian_bookshelf.service.ItemService;

@AutoConfigureMockMvc
@SpringBootTest (classes = TheIonianBookshelfApplication.class)
class ChampionControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ChampionService championService;
	
	//@WithMockUser(value = "spring")
	@Test
	void testShowChampionListHtml() throws Exception {
		mockMvc.perform(get("/champions")).andExpect(status().isOk()).andExpect(model().attributeExists("champions"))
		.andExpect(model().attributeExists("role"))
				.andExpect(view().name("/champions/listadoCampeones"));
	}
	

	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/champions/new")).andExpect(status().isOk()).andExpect(model().attributeExists("champion"))
				.andExpect(view().name("champions/editCampeon"));
	}
	
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/champions/new").param("name", "Lulu").param("description", "Campeona de League of Legends")
				.param("health", "900").param("mana", "500").param("energy", "0").param("attack", "1.20").param("speed", "0.90").param("role_id", "1"))
				.andExpect(status().is2xxSuccessful());
	}
	
	
//	@Test
//    void testProcessCreationFormHasErrors() throws Exception {
//        mockMvc.perform(post("/champions/new")
//            .param("name", "Lulu").param("description", "Campeona de League of Legends")
//			.param("health", "900").param("mana", "500").param("energy", "0").param("attack", "1.0").param("speed", "0.90").param("role_id", "numero"))
//            .andExpect(model().attributeHasErrors("champion"))
//            .andExpect(model().attributeHasFieldErrors("champion", "role"))
//            .andExpect(status().isOk())
//            .andExpect(view().name("champion/editCampeon"));
//    }

}
