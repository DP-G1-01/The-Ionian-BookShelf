package org.springframework.samples.the_ionian_bookshelf.web;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.the_ionian_bookshelf.configuration.SecurityConfiguration;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.model.RunePage;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.service.BranchService;
import org.springframework.samples.the_ionian_bookshelf.service.RunePageService;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RunePageController.class,
includeFilters = { @ComponentScan.Filter(value = BranchFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = RuneFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = SummonerFormatter.class, type = FilterType.ASSIGNABLE_TYPE)}
,excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE
, classes = WebSecurityConfigurer.class)
, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class RunePageControllerTests {
	
	private RunePage runePageMock = mock(RunePage.class);
		
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RunePageService runePageService;

	@MockBean
	private SummonerService summonerService;
	
	@MockBean
	private BranchService branchService;
	
	@MockBean
	private RuneService runeService;

	@BeforeEach
	void setup() {
		Branch mainBranch = new Branch("Precision", "descripcion", "http://www.image.com");
		Branch secondaryBranch = new Branch("Domination","Descripcion del branch2","http://www.miimagendebranch2.com");
		mainBranch.setId(40);
		secondaryBranch.setId(41);
		Rune keyRune = new Rune("Conqueror", "Description", mainBranch, "Key");
		keyRune.setId(50);
		Rune mainRune1 = new Rune("Overheal", "Description", mainBranch, "1");
		mainRune1.setId(51);
		Rune mainRune2 = new Rune("Legend: Alacrity", "Description", mainBranch, "2");
		mainRune2.setId(52);
		Rune mainRune3 = new Rune("Cut Down", "Description", mainBranch, "3");
		mainRune3.setId(53);
		Rune secRune1 = new Rune("Cheap Shot", "Description", secondaryBranch, "1");
		secRune1.setId(54);
		Rune secRune2 = new Rune("Ghost Poro", "Description", secondaryBranch, "2");
		secRune2.setId(55);
		Rune secRune3 = new Rune("Ravenous Hunter", "Description", secondaryBranch, "3");
		secRune3.setId(56);
		Role r = new Role("Rol1", "Soy un rol de prueba ten paciencia", "https://www.youtube.com/");
		Champion c = new Champion("Cham1", "La descripción es algo superfluo sin cabida en una mente abierta",
				10., 5., null, 20., 50., r);
		Collection<Champion> mains = new ArrayList<Champion>();
		mains.add(c);
		User user = new User();
		user.setUsername("summoner1");
		user.setPassword("papin");
		Summoner summoner = new Summoner();
		summoner.setUser(user);
		summoner.setId(1);
		summoner.setEmail("pru@gmail.com");
		summoner.setMains(mains);
		RunePage runePage = new RunePage("Nueva página de runas", summoner, mainBranch, secondaryBranch, keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune2);
		RunePage runePage1 = new RunePage("Nueva página de runas", summoner, mainBranch, secondaryBranch, keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune3);
		List<RunePage> list = new ArrayList<>();
		runePage.setId(1);
		runePage1.setId(2);
		list.add(runePage);
		list.add(runePage1);
		
		//Entiendo que esto es necesario para los formatter
		given(this.branchService.findAll()).willReturn(Lists.newArrayList(mainBranch, secondaryBranch));
		given(this.runeService.findAll()).willReturn(Lists.newArrayList(keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune2, secRune3));
		given(this.summonerService.findByPrincipal()).willReturn(summoner);
		when(this.summonerService.findByPrincipal()).thenReturn(summoner);
		when(this.runePageService.findAllMine()).thenReturn(list);
		given(this.runePageService.findOne(1)).willReturn(runePage);
		given(runePageMock.getSummoner()).willReturn(summoner);
		given(this.summonerService.findAll()).willReturn(Lists.newArrayList(summoner));
	}

	@WithMockUser(value = "summoner1")
	@Test
	void testShowRunePagesListHtml() throws Exception {
		mockMvc.perform(get("/runePages/mine")).andExpect(status().isOk()).andExpect(model().attributeExists("runePages"))
				.andExpect(view().name("runePages/listadoPaginasRunas"));
	}

	@WithMockUser(value = "summoner1")
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/runePages/new")).andExpect(status().is2xxSuccessful()).andExpect(view().name("runePages/editRunePage"));
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
		mockMvc.perform(post("/runePages/new").with(csrf()).param("name", "name test").param("summoner", "1")
				.param("mainBranch", "Precision").param("secondaryBranch", "Domination").param("keyRune", "Conqueror").param("mainRune1", "Overheal")
				.param("mainRune2", "Legend: Alacrity").param("mainRune3", "Cut Down").param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
				.andExpect(status().isOk());
	}

	@WithMockUser(value="summoner1")
	@Test
	void testProcessCreationMainRuneDifferentNode() throws Exception {
		mockMvc.perform(post("/runePages/save").with(csrf()).param("name", "name test").param("summoner", "1")
				.param("mainBranch", "Precision").param("secondaryBranch", "Domination").param("keyRune", "Conqueror").param("mainRune1", "Overheal")
				.param("mainRune2", "Legend: Alacrity").param("mainRune3", "Cheap Shot").param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("runePage"))
				.andExpect(model().attributeHasFieldErrorCode("runePage", "keyRune", "Main runes' nodes must coincide with their prefix"))
				.andExpect(view().name("runePages/editRunePage"));
	}
	
	@WithMockUser(value="summoner1")
	@Test
	void testProcessCreationMainBranchEqualsSecBranch() throws Exception {
		mockMvc.perform(post("/runePages/save").with(csrf()).param("name", "name test").param("summoner", "1")
				.param("mainBranch", "Precision").param("secondaryBranch", "Precision").param("keyRune", "Conqueror").param("mainRune1", "Overheal")
				.param("mainRune2", "Legend: Alacrity").param("mainRune3", "Cut Down").param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("runePage"))
				.andExpect(model().attributeHasFieldErrorCode("runePage", "mainBranch", "Main Branch should not be the same as Secondary Branch"))
				.andExpect(view().name("runePages/editRunePage"));
	}
	
	@WithMockUser(value="summoner1")
	@ParameterizedTest 
	@ValueSource(strings = {"Overheal", "Legend: Alacrity", "Cut Down", "Ghost Poro", "Ravenous Hunter"  }) 
	void testProcessCreationKeyRuneEqualsOthers(String keyRune) throws Exception {
		mockMvc.perform(post("/runePages/save").with(csrf()).param("name", "name test").param("summoner", "1")
				.param("mainBranch", "Precision").param("secondaryBranch", "Domination").param("keyRune", keyRune).param("mainRune1", "Overheal")
				.param("mainRune2", "Legend: Alacrity").param("mainRune3", "Cut Down").param("secRune1", "Ghost Poro").param("secRune2", "Ravenous Hunter"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("runePage"))
				.andExpect(model().attributeHasFieldErrorCode("runePage", "keyRune", "All runes must be different each other"))
				.andExpect(view().name("runePages/editRunePage"));
		
	}

	//Delete de una página de runas siendo su propietario
	@WithMockUser(value = "summoner1")
	@Test
	void testDeleteRunePageSuccess() throws Exception {
		mockMvc.perform(get("/runePages/{runePageId}/remove", 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/runePages/mine"));
	}

	//Intento de delete de pagina de runas sin ser su propietario
	@WithMockUser(value = "admin")
	@Test
	void testDeleteRuneWithoutLoginAsOwner() throws Exception {
		Summoner notOwner = new Summoner();
		when(this.summonerService.findByPrincipal()).thenReturn(notOwner);
		mockMvc.perform(get("/runePages/{runePageId}/remove", 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/runePages/mine"));
	}
}
