package org.springframework.samples.the_ionian_bookshelf.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.the_ionian_bookshelf.configuration.SecurityConfiguration;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.League;
import org.springframework.samples.the_ionian_bookshelf.model.Reviewer;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.service.ChangeRequestService;
import org.springframework.samples.the_ionian_bookshelf.service.ReviewerService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ChangeRequestController.class
,excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE
, classes = WebSecurityConfigurer.class)
, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class ChangeRequestControllerTests {

	private static final int TEST_ID = 1;
	
	private static final int TEST_ID2 = 3;
	
	private ChangeRequest changeRequestMock = mock(ChangeRequest.class);
	
	private Summoner sumMock = mock(Summoner.class);
	
	private Reviewer reviewerMock = mock(Reviewer.class);
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SummonerService summonerService;
	
	@MockBean
	private ReviewerService reviewerService;
	
	@MockBean
	private ChangeRequestService changeRequestService;
	
	@BeforeEach
	void setup() {
		List <String> attributes = new ArrayList<>();
		attributes.add("4");
		attributes.add("2");
		attributes.add("1");
		List <Role> roles = new ArrayList<>();
		Role r = new Role("rolTest", "testeoooooooooo", "https://www.google.es");
		roles.add(r);
		List <String> changeItem = new ArrayList<>();
		changeItem.add("1");
		changeItem.add("1");
		changeItem.add("3");
		Item item = new Item("test", "testeandoooooooooooooooooo", attributes, roles);
		Collection<Champion> mains = new ArrayList<Champion>();
		Champion c = new Champion("Cham1", "La descripción es algo superfluo sin cabida en una mente abierta",
				10., 5., null, 20., 50., r);
		mains.add(c);
		ChangeRequest request = new ChangeRequest(null, item, "Soy el titulo", "Soy la descripcion y debo tener al menos 20 años de edad.", null, changeItem, "PENDING", sumMock, null);
		List<ChangeRequest> list = new ArrayList<>();
		list.add(request);
		Summoner sumMock = mock(Summoner.class);
		when(this.summonerService.findByPrincipal()).thenReturn(sumMock);
		when(this.summonerService.findByPrincipal().getId()).thenReturn(TEST_ID);
		when(this.changeRequestService.findMine(TEST_ID)).thenReturn(list);
		when(this.changeRequestService.findMine(this.summonerService.findByPrincipal().getId())).thenReturn(list);
		
		when(this.changeRequestService.findOne(TEST_ID)).thenReturn(changeRequestMock);
		when(this.changeRequestMock.getSummoner()).thenReturn(sumMock);
		when(this.changeRequestMock.getSummoner().getId()).thenReturn(TEST_ID);
		//when(sumMock.getId()).thenReturn(TEST_ID);
//		when(buildMock.getSummoner()).thenReturn(sumMock);
	}
	
	@WithMockUser(value = "Reviewer1")
	@Test
	void testShowRequestList() throws Exception {
		mockMvc.perform(get("/requests")).andExpect(status().isOk())
		.andExpect(model().attributeExists("requests"))
		.andExpect(view().name("requests/listadoRequests"));
	}
	
	@WithMockUser(value = "Summoner1")
	@Test
	void testShowRequestListSumm() throws Exception {
		when(this.reviewerService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/requests")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testMineRequestSumm() throws Exception {
		mockMvc.perform(get("/mine/requests")).andExpect(status().isOk()).andExpect(model()
				.attributeExists("requests"))
		.andExpect(view().name("requests/listadoRequests"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testMineRequestAnon() throws Exception {
		when(this.summonerService.findByPrincipal()).thenThrow(NoSuchElementException.class);
		mockMvc.perform(get("/mine/requests")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}

	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowRequest() throws Exception {
		
		mockMvc.perform(get("/requests/{requestId}", TEST_ID)).andExpect(status().isOk())
		.andExpect(view().name("requests/createRequest"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowRequestAnon() throws Exception {
		when(this.summonerService.findByPrincipal()).thenThrow(NoSuchElementException.class);
		mockMvc.perform(get("/requests/{requestId}", TEST_ID)).andExpect(status().isOk())
		.andExpect(view().name("requests/createRequest"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowMineRequest() throws Exception {
		
		mockMvc.perform(get("/mine/requests/{requestId}", TEST_ID)).andExpect(status().isOk())
		.andExpect(view().name("requests/createRequest"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowMineRequestAnon() throws Exception {
		when(this.summonerService.findByPrincipal()).thenThrow(NoSuchElementException.class);
		mockMvc.perform(get("/mine/requests/{requestId}", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testChampionRequestNew() throws Exception {
	
		mockMvc.perform(get("/champions/{championId}/newChangeRequest", TEST_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("request"))
		.andExpect(model().attributeExists("championId"))
		.andExpect(model().attributeExists("summonerId"))
		.andExpect(view().name("requests/createRequest"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testItemRequestNew() throws Exception {
	
		mockMvc.perform(get("/items/{itemId}/newChangeRequest", TEST_ID))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("request"))
		.andExpect(model().attributeExists("itemId"))
		.andExpect(model().attributeExists("summonerId"))
		.andExpect(view().name("requests/createRequest"));
	}

	@WithMockUser(value = "rev")
	@Test
	void testDeleteRequestSuccessRev() throws Exception {
		mockMvc.perform(get("/requests/{requestId}/remove", TEST_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/requests"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDeleteRequestWithoutLoginAsRev() throws Exception {
		when(this.reviewerService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/requests/{requestId}/remove", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
	}
	
	@WithMockUser(value = "rev")
	@Test
	void testAcceptRequestRev() throws Exception {
		mockMvc.perform(get("/requests/{requestId}/accept", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/requests"));
	}
	
	@WithMockUser(value = "rev")
	@Test
	void testRejectRequestRev() throws Exception {
		mockMvc.perform(get("/requests/{requestId}/reject", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/requests"));
	}
}
