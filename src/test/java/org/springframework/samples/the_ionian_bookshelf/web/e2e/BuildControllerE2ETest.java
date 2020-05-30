package org.springframework.samples.the_ionian_bookshelf.web.e2e;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.service.BuildService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-mysql.properties")
public class BuildControllerE2ETest {
	
	private static final int TEST_ID = 1;
	
	private static final int TEST_ID2 = 3;
	
	private Build buildMock = mock(Build.class);
	
	
	@Autowired
	private MockMvc mockMvc;
		
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowBuildListHtml() throws Exception {
		mockMvc.perform(get("/builds")).andExpect(status().isOk()).andExpect(model().attributeExists("builds"))
		.andExpect(view().name("builds/buildsList"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testMineBuildsSumm() throws Exception {
		mockMvc.perform(get("/mine/builds")).andExpect(status().isOk()).andExpect(model()
				.attributeExists("builds"))
		.andExpect(view().name("builds/buildsList"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testMineBuildsAnon() throws Exception {
		
		mockMvc.perform(get("/mine/builds")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}

	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowBuildMineAnon() throws Exception {
		
		mockMvc.perform(get("/mine/builds/{buildId}", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/mine/builds"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowBuildMineNotMineReally() throws Exception {
		
		mockMvc.perform(get("/mine/builds/{buildId}", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/mine/builds"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testInitCreationFormSumm() throws Exception {
		mockMvc.perform(get("/mine/builds/new")).andExpect(status().isOk())
		.andExpect(view().name("builds/editBuild"));
	}
	
	@Test
	void testInitCreationFormAnon() throws Exception {
		
		mockMvc.perform(get("/mine/builds/new")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
	
	@WithMockUser(value = "summoner1")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/mine/builds/new").with(csrf()).param("title", "titulo test").param("description", "Testeando builds en vez de jugar al Doom")
				.param("champion", "1").param("runePage", "1").param("items[0]", "1").param("items[1]", "2").param("items[2]", "3"))
				.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value="summoner1")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/mine/builds/save").with(csrf()).param("title", "").param("description", "")
				.param("champion", "1").param("runePage", "1").param("items[0]", "1").param("items[1]", "2")
				.param("items[2]", "3"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("build"))
				.andExpect(model().attributeHasFieldErrors("build", "description"))
				.andExpect(model().attributeHasFieldErrors("build", "title"))
				.andExpect(view().name("builds/editBuild"));
	}
	
	@WithMockUser(value = "summoner1")
	@Test
	void testInitUpdateFormSumm() throws Exception {
		mockMvc.perform(get("/mine/builds/1/edit")).andExpect(status().isOk())
		.andExpect(view().name("builds/editBuild"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testInitUpdateFormAnon() throws Exception {
		mockMvc.perform(get("/mine/builds/{buildId}/edit", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testInitUpdateFormNotMine() throws Exception {
		mockMvc.perform(get("/mine/builds/{buildId}/edit", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/mine/builds"));
	}
	
	@WithMockUser(value = "summoner1")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/mine/builds/{buildId}/edit", TEST_ID).with(csrf()).param("title", "titulo test").param("description", "Testeando builds en vez de jugar al Doom")
				.param("champion", "1").param("runePage", "1").param("items[0]", "1").param("items[1]", "2").param("items[2]", "3"))
				.andExpect(status().is2xxSuccessful());
	}
	
	@WithMockUser(value="summoner1")
	@Test
	void testProcessUpdateFormHasErrors() throws Exception {
		mockMvc.perform(post("/mine/builds/{buildId}/edit", TEST_ID).with(csrf()).param("title", "").param("description", "")
				.param("champion", "1").param("runePage", "1").param("items[0]", "1").param("items[1]", "2")
				.param("items[2]", "3"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("build"))
				.andExpect(model().attributeHasFieldErrors("build", "description"))
				.andExpect(model().attributeHasFieldErrors("build", "title"))
				.andExpect(view().name("builds/editBuild"));
	}
	
	@WithMockUser(value = "summ")
	@Test
	void testDeleteItemSuccessSumm() throws Exception {
		mockMvc.perform(get("/mine/builds/{buildId}/remove", TEST_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteRuneWithoutLoginAsSumm() throws Exception {
		mockMvc.perform(get("/mine/builds/{buildId}/remove", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
}