package org.springframework.samples.the_ionian_bookshelf.web;


import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.samples.the_ionian_bookshelf.model.Administrator;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.AuthoritiesService;
import org.springframework.samples.the_ionian_bookshelf.service.BranchService;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

@WebMvcTest(controllers = RuneController.class,
includeFilters = @ComponentScan.Filter(value = BranchFormatter.class, type = FilterType.ASSIGNABLE_TYPE)
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
	private BranchService branchService;

	@MockBean
	private AdministratorService administratorService;
		
	@BeforeEach
	void setup() {
		Branch branch = new Branch();
		branch.setId(3);
		branch.setName("Precision");
		branch.setDescription("DescBranch");
		branch.setImage("http://www.branch.com");	
		Rune rune = new Rune();
		rune.setId(11);
		rune.setName("Nombre");
		rune.setDescription("Descripcion");
		rune.setBranch(branch);
		rune.setNode("1");
		
		User user = new User();
		user.setUsername("admin");
		user.setPassword("admin");
		Administrator admin = new Administrator();
		admin.setUser(user);
		admin.setId(1);
		admin.setEmail("admin@gmail.com");
		
		given(this.runeService.findRuneById(11)).willReturn(rune);
		given(this.branchService.findAll()).willReturn(Lists.newArrayList(branch));
		given(this.administratorService.findByPrincipal()).willReturn(admin);
	}


	@WithMockUser(value = "spring")
	@Test
	void testShowItemListHtml() throws Exception {
		when(this.administratorService.findByPrincipal()).thenCallRealMethod();
		mockMvc.perform(get("/runes")).andExpect(status().isOk()).andExpect(model().attributeExists("runes"))
		.andExpect(model().attributeExists("branch"))
				.andExpect(view().name("runes/listadoRunas"));
	}

	@WithMockUser(value = "admin", roles="administrator")
	@Test
	void testInitCreationFormAdmin() throws Exception {
		mockMvc.perform(get("/runes/new")).andExpect(status().is2xxSuccessful()).andExpect(model()
				.attributeExists("rune"))
				.andExpect(view().name("runes/editRune"));
	}




	@WithMockUser(value = "NoAdmin")
	@Test
	void testInitCreationFormSumm() throws Exception {
		when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/runes/new")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}


    @WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/runes/save").with(csrf()).param("name", "name test").param("description", "descripcion")
				.param("branch", "Precision").param("node", "1"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/runes/"));
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
	
	@WithMockUser(value="NoAdmin")
	@Test
	void testProcessCreationFormWithoutLogin() throws Exception {
		mockMvc.perform(post("/runes/save").with(csrf()).param("name", "").param("description", "")
				.param("branch", "Precision").param("node", "1"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("rune"))
				.andExpect(model().attributeHasFieldErrors("rune", "description"))
				.andExpect(model().attributeHasFieldErrors("rune", "name"))
				.andExpect(view().name("runes/editRune"));
	}
	
	
	//Update as not admin
	@WithMockUser(value = "persona")
	@Test
	void testInitUpdateRuneFormWithoutLogin() throws Exception {
		when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/runes/{runeId}/edit", 11)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
	
	
	
	//Update as admin
	@WithMockUser(value = "admin")
	@Test
	void testInitUpdateRuneForm() throws Exception {
		mockMvc.perform(get("/runes/{runeId}/edit", 11)).andExpect(status().isOk())
				.andExpect(model().attributeExists("rune"))
				.andExpect(model().attribute("rune", hasProperty("name", is("Nombre"))))
				.andExpect(model().attribute("rune", hasProperty("description", is("Descripcion"))))
				.andExpect(model().attribute("rune", hasProperty("node", is("1"))))
				.andExpect(view().name("runes/editRune"));

		verify(runeService).findRuneById(11);
	}

    @WithMockUser(value = "admin")
	@Test
	void testProcessUpdateRuneFormSuccess() throws Exception {
    	when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(post("/runes/{runeId}/edit", 11)
							.with(csrf())
							.param("name", "My personal rune")
							.param("description", "New description")
							.param("branch", "Precision")
							.param("node", "2"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/runes/"));
	}

	//Delete de una runa siendo admin
	@WithMockUser(value = "admin")
	@Test
	void testDeleteRuneSuccess() throws Exception {
		mockMvc.perform(get("/runes/{runeId}/remove", 11)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/runes/"));
	}

	//Intento de delete de una runa sin ser admin
	@WithMockUser(value = "spring")
	@Test
	void testDeleteRuneWithoutLoginAsAdmin() throws Exception {
		when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/runes/{runeId}/remove", 11)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}
	
	
}
