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
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.service.ItemService;
import org.springframework.samples.the_ionian_bookshelf.service.ReviewerService;
import org.springframework.samples.the_ionian_bookshelf.service.RoleService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers = ChampionController.class
,excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE
, classes = WebSecurityConfigurer.class)
, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
class ChampionControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ChampionService championService;
	
	@MockBean
	private RoleService roleService;
	
	@MockBean
	private AdministratorService administratorService;
	
	@MockBean
	private ReviewerService reviewerService;
	
	@WithMockUser(value = "spring")
	@Test
	void testShowChampionListHtml() throws Exception {
		mockMvc.perform(get("/champions")).andExpect(status().isOk()).andExpect(model().attributeExists("champions"))
		.andExpect(model().attributeExists("role"))
				.andExpect(view().name("/champions/listadoCampeones"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitCreationFormAnon() throws Exception {
		mockMvc.perform(get("/champions/new")).andExpect(status().is3xxRedirection());
	}
//	@WithMockUser(value = "spring")
//	@Test
//	void testProcessCreationFormSuccess() throws Exception {
//		mockMvc.perform(post("/champions/new").param("name", "Lulu").param("description", "Campeona de League of Legends")
//				.param("health", "900").param("mana", "500").param("energy", "0").param("attack", "1.20").param("speed", "0.90").param("role_id", "1"))
//				.andExpect(status().is2xxSuccessful());
//	}
	
	
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
