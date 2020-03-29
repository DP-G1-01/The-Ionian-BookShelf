package org.springframework.samples.the_ionian_bookshelf.web;


import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.the_ionian_bookshelf.configuration.SecurityConfiguration;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RuneController.class
,excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE
, classes = WebSecurityConfigurer.class)
, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class RuneControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RuneService runeService;

	@MockBean
	private AdministratorService administratorService;

	@BeforeAll
	void setupInitCreationFormSumm() {

	}


	@WithMockUser(value = "spring")
	@Test
	void testShowItemListHtml() throws Exception {
		when(this.administratorService.findByPrincipal()).thenCallRealMethod();
		mockMvc.perform(get("/runes")).andExpect(status().isOk()).andExpect(model().attributeExists("runes"))
		.andExpect(model().attributeExists("branch"))
				.andExpect(view().name("runes/listadoRunas"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormAdmin() throws Exception {
		mockMvc.perform(get("/runes/new")).andExpect(status().is2xxSuccessful()).andExpect(model()
				.attributeExists("rune"))
				.andExpect(view().name("runes/editRune"));
	}




	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testInitCreationFormSumm() throws Exception {
		when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/runes/new")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}



	@WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(post("/runes/new").with(csrf()).param("name", "name test").param("description", "descripcion")
				.param("branch", "Precision").param("node", "1"))
				.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value="admin")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/runes/save").with(csrf()).param("name", "").param("description", "")
				.param("branch", "Precision").param("node", "1"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("rune"))
				.andExpect(model().attributeHasFieldErrors("rune", "description"))
				.andExpect(model().attributeHasFieldErrors("rune", "name"))
				.andExpect(view().name("runes/editRune"));
	}

	//Delete de una runa siendo admin
	@WithMockUser(value = "admin")
	@Test
	void testDeleteRuneSuccess() throws Exception {
		mockMvc.perform(get("/runes/{runeId}/remove", 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/runes/"));
	}

	//Intento de delete de una runa sin ser admin
	@WithMockUser(value = "spring")
	@Test
	void testDeleteRuneWithoutLoginAsAdmin() throws Exception {
		when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/runes/{runeId}/remove", 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}
}
