package org.springframework.samples.the_ionian_bookshelf.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.ItemService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ItemController.class
,excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE
, classes = WebSecurityConfigurer.class)
, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class ItemControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ItemService itemService;
	
	@MockBean
	private AdministratorService administratorService;
	
	private Administrator adminMock = mock(Administrator.class);
	
	
	@BeforeEach
	void setup() {
		Administrator adminMock = mock(Administrator.class);
		when(this.administratorService.findByPrincipal()).thenReturn(adminMock);
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowItemListHtml() throws Exception {
		
		mockMvc.perform(get("/items")).andExpect(status().isOk()).andExpect(model().attributeExists("items"))
		.andExpect(model().attributeExists("role"))
				.andExpect(view().name("items/itemsList"));
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testInitCreationFormAdmin() throws Exception {
		mockMvc.perform(get("/items/new")).andExpect(status().is2xxSuccessful()).andExpect(model()
				.attributeExists("item"))
				.andExpect(view().name("items/editItem"));
	}

	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testInitCreationFormSumm() throws Exception {
		when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/items/new")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
	}
	
	
	

//	@WithMockUser(value = "admin")
//	@Test
//	void testProcessCreationFormSuccess() throws Exception {
//		mockMvc.perform(post("/items/save").with(csrf()).param("title", "titulo test").param("description", "Testeando items en vez de jugar al Doom")
//				.param("attributes[0]", "43").param("attributes[1]", "10").param("attributes[2]", "23").param("roles[0]", "Tirador")
//				.param("roles[1]", "Apoyo").param("roles[2]", "Tanque"))
//				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/items/"));
//	}
	
	@WithMockUser(value = "admin", authorities = {"admin"})
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/items/save").with(csrf()).param("title", "").param("description", "")
				.param("attributes[0]", "43").param("attributes[1]", "10").param("attributes[2]", "67").param("roles[0]", "Apoyo")
				.param("roles[1]", "Tirador").param("roles[2]", "Asesino"))
				.andExpect(status().isOk()).andExpect(view().name("items/editItem"))
				.andExpect(model().attributeHasFieldErrors("item", "description"))
				.andExpect(model().attributeHasFieldErrors("item", "title"));
			
	}
	
	@WithMockUser(value = "admin")
	@Test
	void testDeleteItemSuccess() throws Exception {
		mockMvc.perform(get("/items/{itemId}/remove", 1)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/items"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteItemWithoutLoginAsAdmin() throws Exception {
		when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/items/{itemId}/remove", 1)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/items"));
	}
}
