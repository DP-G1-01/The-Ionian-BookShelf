package org.springframework.samples.the_ionian_bookshelf.web;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.samples.the_ionian_bookshelf.model.Reviewer;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
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
	
	private Reviewer revMock = mock(Reviewer.class);
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SummonerService summonerService;

	@MockBean
	private ReviewerService reviewerService;
	
	@MockBean
	private ChampionService championService;
	
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
}
