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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.the_ionian_bookshelf.configuration.SecurityConfiguration;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


//Con estas anotaciones he corregido el error failed to load ApplicationContext
@SpringBootTest
@AutoConfigureMockMvc
class ChampionControllerTests {
	
	private static final int TEST_CHAMPION_ID = 2;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ChampionService championService;
	
	//Mostara el listado
		@WithMockUser(value = "spring")
		@Test
		void testShowChampionsList() throws Exception {
			mockMvc.perform(get("/champions")).andExpect(status().isOk()).andExpect(model().attributeExists("champions"))
					.andExpect(model().attributeExists("role")).andExpect(view().name("/champions/listadoCampeones"));
		}

		//Acceder al form de creación
		@WithMockUser(value = "admin")
		@Test
		void testInitCreationChampionForm() throws Exception {
			mockMvc.perform(get("/champions/new")).andExpect(status().isOk()).andExpect(model().attributeExists("champion"))
					.andExpect(view().name("champions/editCampeon"));
		}
		
		//Intento de acceder al form de creación sin ser admin
		@WithMockUser(value = "pepe")
		@Test
		void testInitCreationChampionFormWithoutLoginAsAdmin() throws Exception {
			mockMvc.perform(get("/champions/new")).andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/login"));
		}

		
		//Delete de una runa siendo admin
		@WithMockUser(value = "admin")
		@Test
		void testDeleteSuccess() throws Exception {
			mockMvc.perform(get("/champions/{championId}/remove", TEST_CHAMPION_ID)).andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/champions/"));
		}
		
		//Intento de delete de una runa sin ser admin
		@WithMockUser(value = "spring")
		@Test
		void testDeleteWithoutLoginAsAdmin() throws Exception {
			mockMvc.perform(get("/champions/{championId}/remove", TEST_CHAMPION_ID)).andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/login"));
		}

}
