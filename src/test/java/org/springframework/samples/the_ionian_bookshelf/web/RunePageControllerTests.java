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
import org.springframework.samples.the_ionian_bookshelf.service.RunePageService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RunePageController.class
,excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE
, classes = WebSecurityConfigurer.class)
, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class RunePageControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RunePageService runePageService;

	@MockBean
	private SummonerService summonerService;

	@BeforeAll
	void setupInitCreationFormSumm() {

	}


	@WithMockUser(value = "summoner1")
	@Test
	void testShowRunePagesListHtml() throws Exception {
		when(this.summonerService.findByPrincipal()).thenCallRealMethod();
		mockMvc.perform(get("/runePages/mine")).andExpect(status().isOk()).andExpect(model().attributeExists("runePages"))
				.andExpect(view().name("runePages/listadoPaginasRunas"));
	}

	@WithMockUser(value = "summoner1")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/runePages/new")).andExpect(status().is2xxSuccessful()).andExpect(model()
				.attributeExists("runePage"))
				.andExpect(view().name("runePages/editRunePage"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormAdminError() throws Exception {
		when(this.summonerService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/runePages/new")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}

	@WithMockUser(value = "summoner1")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		when(this.summonerService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(post("/runePages/new").with(csrf()).param("name", "name test").param("summoner", "summoner1")
				.param("mainBranch", "Precision").param("secondaryBranch", "Domination").param("keyRune", "Conqueror").param("mainRune1", "Overheal")
				.param("mainRune2", "Legend: Alacrity").param("mainRune3", "Cut Down").param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
				.andExpect(status().is2xxSuccessful());
	}

	@WithMockUser(value="summoner1")
	@Test
	void testProcessCreationMainRuneNotInMainBranch() throws Exception {
		mockMvc.perform(post("/runePages/save").with(csrf()).param("name", "name test").param("summoner", "summoner1")
				.param("mainBranch", "Precision").param("secondaryBranch", "Domination").param("keyRune", "Conqueror").param("mainRune1", "Overheal")
				.param("mainRune2", "Legend: Alacrity").param("mainRune3", "Aftershock").param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("runePage"))
				.andExpect(model().attributeHasFieldErrors("runePage", "keyRune"))
				.andExpect(view().name("runePages/editRunePage"));
	}
	
	@WithMockUser(value="summoner1")
	@Test
	void testProcessCreationMainBranchEqualsSecBranch() throws Exception {
		mockMvc.perform(post("/runePages/save").with(csrf()).param("name", "name test").param("summoner", "summoner1")
				.param("mainBranch", "Precision").param("secondaryBranch", "Precision").param("keyRune", "Conqueror").param("mainRune1", "Overheal")
				.param("mainRune2", "Legend: Alacrity").param("mainRune3", "Cut Down").param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("runePage"))
				.andExpect(model().attributeHasFieldErrors("runePage", "mainBranch"))
				.andExpect(view().name("runePages/editRunePage"));
	}
//
//	//Delete de una runa siendo admin
//	@WithMockUser(value = "admin")
//	@Test
//	void testDeleteRuneSuccess() throws Exception {
//		mockMvc.perform(get("/runes/{runeId}/remove", 1)).andExpect(status().is3xxRedirection())
//				.andExpect(view().name("redirect:/runes/"));
//	}
//
//	//Intento de delete de una runa sin ser admin
//	@WithMockUser(value = "spring")
//	@Test
//	void testDeleteRuneWithoutLoginAsAdmin() throws Exception {
//		when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
//		mockMvc.perform(get("/runes/{runeId}/remove", 1)).andExpect(status().is3xxRedirection())
//				.andExpect(view().name("redirect:/login"));
//	}
}
