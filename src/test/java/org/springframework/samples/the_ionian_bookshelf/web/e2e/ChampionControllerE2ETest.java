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
public class ChampionControllerE2ETest {
	@Autowired
	private MockMvc mockMvc;
	
	
	@WithMockUser(username="admin",authorities= {"admin"})
	@Test
	void testShowChampionListHtml() throws Exception {
		
		mockMvc.perform(get("/champions")).andExpect(status().isOk()).andExpect(model().attributeExists("champions"))
		.andExpect(model().attributeExists("role"))
				.andExpect(view().name("/champions/listadoCampeones"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testInitCreationChampionFormAdmin() throws Exception {
		mockMvc.perform(get("/champions/new")).andExpect(status().is2xxSuccessful()).andExpect(model()
				.attributeExists("champion"))
				.andExpect(view().name("champions/editCampeon"));
	}

	@WithMockUser(username="summoner1",authorities= {"summoner"})
	@Test
	void testInitCreationChampionFormNoAdmin() throws Exception {
		mockMvc.perform(get("/champions/new")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
	
	
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessCreationChampionFormSuccess() throws Exception {
		mockMvc.perform(post("/champions/save").with(csrf()).param("name", "nombre campeón").param("description", "Una descripción de runa").param("health", "1000.0")
				.param("mana", "500.0").param("energy", "").param("attack", "1.0").param("speed", "1.2").param("role", "Tirador"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/champions/"));
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessCreationChampionFormHasErrors() throws Exception {
		mockMvc.perform(post("/champions/save").with(csrf()).param("name", "").param("description", "Una descripción de runa").param("health", "1000.0")
				.param("mana", "500.0").param("energy", "").param("attack", "1.0").param("speed", "1.2").param("role", "Tirador"))
				.andExpect(status().isOk()).andExpect(view().name("champions/editCampeon"))
				.andExpect(model().attributeHasFieldErrors("champion", "name"));
			
	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testDeleteChampionSuccess() throws Exception {
		mockMvc.perform(get("/champions/{championId}/remove", 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/champions/"));
	}
	
	@WithMockUser(value = "summoner1", authorities = {"summoner"})
	@Test
	void testDeleteChampionWithoutLoginAsAdmin() throws Exception {
		mockMvc.perform(get("/champions/{championId}/remove", 1)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
}
