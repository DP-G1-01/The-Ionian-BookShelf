package org.springframework.samples.the_ionian_bookshelf.web;

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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.the_ionian_bookshelf.configuration.SecurityConfiguration;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.service.BuildService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = BuildController.class
,excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE
, classes = WebSecurityConfigurer.class)
, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class BuildControllerTests {

	private static final int TEST_ID = 1;
	
	private static final int TEST_ID2 = 3;
	
	private Build buildMock = mock(Build.class);
	
	private Summoner sumMock = mock(Summoner.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SummonerService summonerService;
	
	@MockBean
	private BuildService buildService;
	
	@MockBean
	private ThreadService threadService;
	
	@BeforeEach
	void setup() {
		Role r = new Role("Rol1", "Soy un rol de prueba ten paciencia", "https://www.youtube.com/");
		Champion c = new Champion("Cham1", "La descripción es algo superfluo sin cabida en una mente abierta",
				10., 5., null, 20., 50., r);
		Collection<Champion> mains = new ArrayList<Champion>();
		mains.add(c);
		User user = new User();
		user.setUsername("Pepin");
		user.setPassword("papin");
		Summoner summoner = new Summoner();
		summoner.setUser(user);
		summoner.setEmail("pru@gmail.com");
		summoner.setMains(mains);
		Build build = new Build("Build de testeo", "Soy una build con una descripción muy bonita, sí", false, new ArrayList<>(), c, null, null, summoner);
		Thread thr = new Thread("Thread de teste", "Soy el thread creado para el hoy y el mañana, ala");
		Build buildVisible = new Build("Build de testeo visible", "Soy una build con una descripción muy bonita, sí", true, new ArrayList<>(), c, null, thr, summoner);
		List<Build> list = new ArrayList<>();
		list.add(build);
		list.add(buildVisible);
		Summoner sumMock = mock(Summoner.class);
		when(this.summonerService.findByPrincipal()).thenReturn(sumMock);
		when(this.summonerService.findByPrincipal().getId()).thenReturn(TEST_ID);
		given(this.buildService.findBuildById(TEST_ID)).willReturn(buildMock);
		when(this.buildService.findMineBuilds(this.summonerService.findByPrincipal().getId())).thenReturn(list);
		when(sumMock.getId()).thenReturn(TEST_ID);
		when(buildMock.getSummoner()).thenReturn(sumMock);
	}
	
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
		when(this.summonerService.findByPrincipal()).thenThrow(NoSuchElementException.class);
		mockMvc.perform(get("/mine/builds")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}

	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowBuildVisible() throws Exception {
		when(buildMock.isVisibility()).thenReturn(true);
		
		mockMvc.perform(get("/builds/{buildId}", TEST_ID)).andExpect(status().isOk())
		.andExpect(model().attributeExists("build"))
		.andExpect(model().attributeExists("sitems"))
		.andExpect(view().name("builds/editBuild"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowBuildPrivate() throws Exception {
		
		when(buildMock.isVisibility()).thenReturn(false);
		mockMvc.perform(get("/builds/{buildId}", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/builds"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowBuildMine() throws Exception {
		when(buildMock.isVisibility()).thenReturn(false);
		mockMvc.perform(get("/mine/builds/{buildId}", TEST_ID)).andExpect(status().isOk())
		.andExpect(model().attributeExists("build"))
		.andExpect(model().attributeExists("sitems"))
		.andExpect(view().name("builds/editBuild"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowBuildMineAnon() throws Exception {
		when(this.summonerService.findByPrincipal()).thenThrow(NoSuchElementException.class);
		mockMvc.perform(get("/mine/builds/{buildId}", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowBuildMineNotMineReally() throws Exception {
		when(this.summonerService.findByPrincipal()).thenReturn(sumMock);
		when(this.summonerService.findByPrincipal().getId()).thenReturn(TEST_ID2);
		mockMvc.perform(get("/mine/builds/{buildId}", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/mine/builds"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testInitCreationFormSumm() throws Exception {
		mockMvc.perform(get("/mine/builds/new")).andExpect(status().isOk())
		.andExpect(view().name("builds/editBuild"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testInitCreationFormAnon() throws Exception {
		when(this.summonerService.findByPrincipal()).thenThrow(NoSuchElementException.class);
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
		when(this.summonerService.findByPrincipal()).thenThrow(NoSuchElementException.class);
		mockMvc.perform(get("/mine/builds/{buildId}/edit", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testInitUpdateFormNotMine() throws Exception {
		when(this.summonerService.findByPrincipal()).thenReturn(sumMock);
		when(this.summonerService.findByPrincipal().getId()).thenReturn(TEST_ID2);
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
				.andExpect(view().name("redirect:/mine/builds"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteRuneWithoutLoginAsSumm() throws Exception {
		when(this.summonerService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/mine/builds/{buildId}/remove", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
	}
}