package org.springframework.samples.the_ionian_bookshelf.web.e2e;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ChangeRequestControllerE2ETest {

	private static final int TEST_ID = 1;
	
	private static final int TEST_ID2 = 2;
	
	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(username="reviewer1",authorities= {"reviewer"})
	@Test
	void testShowRequestList() throws Exception {
		mockMvc.perform(get("/requests")).andExpect(status().isOk()).andExpect(model().attributeExists("requests"))
		.andExpect(view().name("requests/listadoRequests"));
	}
	
	@WithMockUser(username="summoner1",authorities= {"summoner"})
	@Test
	void testShowRequestListSumm() throws Exception {
		mockMvc.perform(get("/requests")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
	}
	
	@WithMockUser(username="summoner1",authorities= {"summoner"})
	@Test
	void testMineRequestSumm() throws Exception {
		mockMvc.perform(get("/mine/requests")).andExpect(status().isOk()).andExpect(model().attributeExists("requests"))
		.andExpect(view().name("requests/listadoRequests"));
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testMineRequestAnon() throws Exception {
		mockMvc.perform(get("/mine/requests")).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}

//	@WithMockUser(username="reviewer1",authorities= {"reviewer"})
//	@Test
//	void testShowRequest() throws Exception {
//		mockMvc.perform(get("/requests/{requestId}", 1)).andExpect(status().isOk())
//		.andExpect(view().name("requests/createRequest"));
//	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowRequestAnon() throws Exception {
		mockMvc.perform(get("/requests/{requestId}", 1)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
	
//	@WithMockUser(username="summoner1",authorities= {"summoner"})
//	@Test
//	void testShowMineRequest() throws Exception {	
//		mockMvc.perform(get("/mine/requests/{requestId}",1)).andExpect(status().isOk())
//		.andExpect(view().name("requests/createRequest"));
//	}
	
	@WithMockUser(value = "spring")
	@Test
	void testShowMineRequestAnon() throws Exception {
		mockMvc.perform(get("/mine/requests/{requestId}",1)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/login"));
	}
	
//	@WithMockUser(username="summoner1",authorities= {"summoner"})
//	@Test
//	void testInitCreationChangeRequestFromSummoner() throws Exception {
//		mockMvc.perform(get("/champions/{championId}/newChangeRequest",1)).andExpect(status().is2xxSuccessful()).andExpect(model()
//				.attributeExists("champion"))
//				.andExpect(view().name("requests/createRequest"));
//	}

	@WithMockUser(username="reviewer1",authorities= {"reviewer"})	
	@Test
	void testInitCreationChangeRequestFromNoSummoner() throws Exception {
		mockMvc.perform(get("/champions/{championId}/newChangeRequest", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
	}
	
//	@WithMockUser(username="summoner1",authorities= {"summoner"})
//	@Test
//	void testChangeRequestNew() throws Exception {
//		mockMvc.perform(post("/requests/saveChangeRequest").with(csrf())
//				.param("title", "nombre request")
//				.param("description", "Una descripción de una request que esta aqui y ahora")
//				.param("changeChamp[0]", "900")
//				.param("changeChamp[1]", "500")
//				.param("changeChamp[2]", "1")
//				.param("changeChamp[3]", "1")
//				.param("changeChamp[4]", "1")
//				.param("status", "PENDING")
//				.param("champion", "1")
//				.param("summoner", "1"))
//				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/mine/requests"));
//	}
	
	@WithMockUser(username="reviewer1",authorities= {"reviewer"})	
	@Test
	void testChangeRequestNewNotSummoner() throws Exception {
		mockMvc.perform(post("/requests/saveChangeRequest").with(csrf())
				.param("title", "nombre request")
				.param("description", "Una descripción de una request que esta aqui y ahora")
				.param("changeChamp[0]", "1")
				.param("changeChamp[1]", "1")
				.param("changeChamp[2]", "1")
				.param("changeChamp[3]", "1")
				.param("changeChamp[4]", "1")
				.param("status", "PENDING")
				.param("championId", "1")
				.param("summonerId", "1"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"));
	}

	@WithMockUser(username="summoner1",authorities= {"summoner"})
	@Test
	void testChangeRequestNewFormErrors() throws Exception {
		mockMvc.perform(post("/requests/saveChangeRequest").with(csrf())
				.param("title", "nombre request")
				.param("description", "")
				.param("changeChamp[0]", "1")
				.param("changeChamp[1]", "1")
				.param("changeChamp[2]", "1")
				.param("changeChamp[3]", "1")
				.param("changeChamp[4]", "1")
				.param("status", "PENDING")
				.param("championId", "1")
				.param("summonerId", "1"))
				.andExpect(status().isOk()).andExpect(view().name("requests/createRequest"))
				.andExpect(model().attributeHasFieldErrors("request", "description"));;
	}

//	@WithMockUser(username="reviewer1",authorities= {"reviewer"})
//	@Test
//	void testDeleteRequestSuccessRev() throws Exception {
//		mockMvc.perform(get("/requests/{requestId}/remove", TEST_ID2)).andExpect(status().is3xxRedirection())
//				.andExpect(view().name("redirect:/requests"));
//	}
//	
//	@WithMockUser(value = "spring")
//	@Test
//	void testDeleteRequestWithoutLoginAsRev() throws Exception {
//		mockMvc.perform(get("/requests/{requestId}/remove", TEST_ID)).andExpect(status().is3xxRedirection())
//		.andExpect(view().name("redirect:/login"));
//	}
	
//	@WithMockUser(username="reviewer1",authorities= {"reviewer"})
//	@Test
//	void testAcceptRequestRev() throws Exception {
//		mockMvc.perform(get("/requests/{requestId}/accept", TEST_ID)).andExpect(status().is3xxRedirection())
//		.andExpect(view().name("redirect:/requests"));
//	}
	
//	@WithMockUser(username="reviewer1",authorities= {"reviewer"})
//	@Test
//	void testRejectRequestRev() throws Exception {
//		mockMvc.perform(get("/requests/{requestId}/reject", TEST_ID)).andExpect(status().is3xxRedirection())
//		.andExpect(view().name("redirect:/requests"));
//	}
}

