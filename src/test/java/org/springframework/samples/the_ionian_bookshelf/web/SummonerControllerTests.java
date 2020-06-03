package org.springframework.samples.the_ionian_bookshelf.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.service.LeagueService;
import org.springframework.samples.the_ionian_bookshelf.service.ReviewerService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = SummonerController.class
,excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE
, classes = WebSecurityConfigurer.class)
, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class SummonerControllerTests {

	private static final int TEST_ID = 1;
	
	private Summoner summonerMock = mock(Summoner.class);	
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SummonerService summonerService;

	@MockBean
	private ReviewerService reviewerService;
	
	@MockBean
	private ChampionService championService;
	
	@MockBean
	private LeagueService leagueService;
	
	@WithMockUser(value = "Reviewer1")
	@Test
	void testBanSummoner() throws Exception {
		mockMvc.perform(get("/summoner/{summonerId}/ban", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/summoner/all"));
	}
	
	@WithMockUser(value = "Reviewer1")
	@Test
	void testDesbanSummoner() throws Exception {
		mockMvc.perform(get("/summoner/{summonerId}/desban", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/summoner/all"));
	}
	
	@WithMockUser(value = "unauthorized")
	@Test
	void testInitUpdateFormNotLogged() throws Exception {
		when(this.summonerService.findByPrincipal()).thenThrow(new NullPointerException());
		mockMvc.perform(get("/summoner/edit")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/login"));
	}

	@WithMockUser(value = "summoner1")
	@Test
	void testInitUpdateFormSuccessful() throws Exception {
		when(this.summonerService.findByPrincipal()).thenReturn(summonerMock);
		mockMvc.perform(get("/summoner/edit")).andExpect(status().is2xxSuccessful()).andExpect(view().name("actor/edit"));
	}
	
	@WithMockUser(value = "summoner21")
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/summoner/edit").with(csrf()).param("email", "summoner21@gmail.com").param("user.username", "summoner21")
				.param("user.password", "summoner21").param("save", "Save").param("champsId", "1")
				.param("champsId", "2").param("leagueId", "1").param("banned", "false")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"));
	}
	
	@WithMockUser(value = "summoner21")
	@Test
	void testProcessUpdateFormNoPassword() throws Exception {
		mockMvc.perform(post("/summoner/edit").with(csrf()).param("email", "summoner21@gmail.com").param("user.username", "summoner21")
				.param("user.password", "").param("save", "Save").param("champsId", "1")
				.param("champsId", "2").param("leagueId", "1")).andExpect(status().is2xxSuccessful()).andExpect(view().name("actor/edit"));
	}
}
