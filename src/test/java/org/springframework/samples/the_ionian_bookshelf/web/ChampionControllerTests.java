package org.springframework.samples.the_ionian_bookshelf.web;


import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.service.ReviewerService;
import org.springframework.samples.the_ionian_bookshelf.service.RoleService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ChampionController.class,
includeFilters = @ComponentScan.Filter(value = RoleFormatter.class, type = FilterType.ASSIGNABLE_TYPE)
,excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE
, classes = WebSecurityConfigurer.class)
, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class ChampionControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ChampionService championService;

	@MockBean
	private AdministratorService administratorService;

	@MockBean
	private RoleService roleService;
	
	@MockBean
	private ReviewerService reviewerService;
	

	@BeforeEach
	void setup() {
		Role role = new Role(); 
		role.setId(3);
		role.setName("Tirador");
		role.setDescription("DescBranch");
		role.setImage("http://www.role.com");	
		
		Champion champion = new Champion();
		champion.setId(11);
		champion.setName("Nombre");
		champion.setDescription("Descripcion");
		champion.setHealth(1000.0);
		champion.setMana(400.0);
		champion.setEnergy(0.0);
		champion.setAttack(1.2);
		champion.setSpeed(1.1);
		champion.setRole(role);
		
		User user = new User();
		user.setUsername("admin");
		user.setPassword("admin");
		Administrator admin = new Administrator();
		admin.setUser(user);
		admin.setId(1);
		admin.setEmail("admin@gmail.com");

		given(this.championService.findChampionById(11)).willReturn(champion);
		given(this.roleService.findAll()).willReturn(Lists.newArrayList(role));
		given(this.administratorService.findByPrincipal()).willReturn(admin);

	}

	//listado de campeones
	@WithMockUser(value = "spring")
	@Test
	void testShowChampionsListHtml() throws Exception {
		when(this.administratorService.findByPrincipal()).thenCallRealMethod();
		mockMvc.perform(get("/champions")).andExpect(status().isOk()).andExpect(model().attributeExists("champions"))
		.andExpect(model().attributeExists("champions"))
				.andExpect(view().name("/champions/listadoCampeones"));
	}



	 /*
	  * TEST DE CREAR DE CAMPEON
	  */

	
	@WithMockUser(value = "admin", roles="administrator")
	@Test
	void testInitCreationFormAdmin() throws Exception {
		mockMvc.perform(get("/champions/new")).andExpect(status().is2xxSuccessful()).andExpect(model()
				.attributeExists("champion"))
				.andExpect(view().name("champions/editCampeon"));
	}


	@WithMockUser(value = "NoAdmin")
	@Test
	void testInitCreationFormSumm() throws Exception {
		when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/champions/new")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}


    @WithMockUser(value = "admin")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/champions/save").with(csrf()).param("name", "name test").param("description", "descripcion")
				.param("health", "1000.0").param("mana", "400.0").param("energy", "").param("attack", "1.1").param("speed", "1.2")
				.param("role", "Tirador"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/champions/"));
	}
	
	
	
	
	@WithMockUser(value="admin")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/champions/save").with(csrf()).param("name", "").param("description", "")
				.param("health", "1000.0").param("mana", "400.0").param("attack", "1.1").param("speed", "1.2")
				.param("role", "Tirador"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("champion"))
				.andExpect(model().attributeHasFieldErrors("champion", "description"))
				.andExpect(model().attributeHasFieldErrors("champion", "name"))
				.andExpect(view().name("champions/editCampeon"));
	}
	
	
	@WithMockUser(value="NoAdmin")
	@Test
	void testProcessCreationFormWithoutLogin() throws Exception {
		mockMvc.perform(post("/champions/save").with(csrf()).param("name", "").param("description", "")
				.param("health", "1000.0").param("mana", "400.0").param("attack", "1.1").param("speed", "1.2")
				.param("role", "Tirador"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("champion"))
				.andExpect(model().attributeHasFieldErrors("champion", "description"))
				.andExpect(model().attributeHasFieldErrors("champion", "name"))
				.andExpect(view().name("champions/editCampeon"));
	}
	
	 /*
	  * TEST DE UPDATE DE CAMPEON
	  */
	
	//Update as not admin
		@WithMockUser(value = "persona")
		@Test
		void testInitUpdateChampionFormWithoutLogin() throws Exception {
			when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
			mockMvc.perform(get("/champions/{championId}/edit", 11)).andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/login"));
		}
		
		
		
		//Update as admin
		@WithMockUser(value = "admin")
		@Test
		void testInitUpdateChampionForm() throws Exception {
			mockMvc.perform(get("/champions/{championId}/edit", 11)).andExpect(status().isOk())
					.andExpect(model().attributeExists("champion"))
					.andExpect(model().attribute("champion", hasProperty("name", is("Nombre"))))
					.andExpect(model().attribute("champion", hasProperty("description", is("Descripcion"))))
					.andExpect(model().attribute("champion", hasProperty("health", is(1000.0))))
					.andExpect(model().attribute("champion", hasProperty("mana", is(400.0))))
					.andExpect(model().attribute("champion", hasProperty("energy", is(0.0))))
					.andExpect(model().attribute("champion", hasProperty("attack", is(1.2))))
					.andExpect(model().attribute("champion", hasProperty("speed", is(1.1))))
					.andExpect(view().name("champions/editCampeon"));

			verify(championService).findChampionById(11);
		}

	    @WithMockUser(value = "admin")
		@Test
		void testProcessUpdateChampionFormSuccess() throws Exception {
	    	when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
			mockMvc.perform(post("/champions/{championId}/edit", 11)
								.with(csrf())
								.param("name", "My personal champion")
								.param("description", "New description")
								.param("health", "1200.0")
								.param("mana", "1200.0")
								.param("energy", "")
								.param("attack", "1.0")
								.param("speed", "2")
								.param("role", "Tirador"))
					.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/champions/"));
		}
	    
	    @WithMockUser(value = "admin")
		@Test
		void testProcessUpdateChampionFormSuccessError() throws Exception {
	    	when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
			mockMvc.perform(post("/champions/{championId}/edit", 11)
								.with(csrf())
								.param("name", "")
								.param("description", "New description")
								.param("health", "1200.0")
								.param("mana", "1200.0")
								.param("energy", "")
								.param("attack", "1.0")
								.param("speed", "2")
								.param("role", "Tirador"))
					.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/champions/editCampeon"));
		}


	
	 /*
	  * TEST DE BORRADO DE CAMPEON
	  */
	    
	  //Delete de una runa siendo admin
		@WithMockUser(value = "admin")
		@Test
		void testDeleteChampionsSuccess() throws Exception {
			mockMvc.perform(get("/champions/{championId}/remove", 11)).andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/champions/"));
		}

		//Intento de delete de una runa sin ser admin
		@WithMockUser(value = "spring")
		@Test
		void testDeleteChampionsWithoutLoginAsAdmin() throws Exception {
			when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
			mockMvc.perform(get("/champions/{championId}/remove", 11)).andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/login"));
		}
		
		
	
	
}
