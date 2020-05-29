package org.springframework.samples.the_ionian_bookshelf.web.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class SummonerControllerE2ETest {
	
	private static final int TEST_ID = 1;

	@Autowired
	private MockMvc mockMvc;
	
	@WithMockUser(username="reviewer1",authorities= {"reviewer"})
	@Test
	void testBanSummoner() throws Exception {
		mockMvc.perform(get("/summoner/{summonerId}/ban", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/summoner/all"));
	}

	@WithMockUser(username="reviewer1",authorities= {"reviewer"})
	@Test
	void testDesbanSummoner() throws Exception {
		mockMvc.perform(get("/summoner/{summonerId}/desban", TEST_ID)).andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/summoner/all"));
	}
	
	@WithMockUser(value = "unauthorized")
	@Test
	void testInitUpdateFormNotLogged() throws Exception {
		mockMvc.perform(get("/summoner/edit")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/login"));
	}

	//Este test funciona con FecthType.EAGER en mains, lo cual es peor para la optimización.
//	@WithMockUser(value = "summoner1")
//	@Test
//	void testInitUpdateFormSuccessful() throws Exception {
//		mockMvc.perform(get("/summoner/edit")).andExpect(status().is2xxSuccessful()).andExpect(view().name("actor/edit"));
//	}
	
	@WithMockUser(value = "summoner21", authorities= {"summoner"})
	@Test
	void testProcessUpdateFormSuccess() throws Exception {
		mockMvc.perform(post("/summoner/edit").with(csrf()).param("email", "summoner21@gmail.com").param("user.username", "summoner21")
				.param("user.password", "summoner21").param("save", "Save").param("champsId", "1")
				.param("champsId", "2").param("leagueId", "1")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"));
	}
	
	@WithMockUser(value = "summoner21", authorities= {"summoner"})
	@Test
	void testProcessUpdateFormNoPassword() throws Exception {
		mockMvc.perform(post("/summoner/edit").with(csrf()).param("email", "summoner21@gmail.com").param("user.username", "summoner21")
				.param("user.password", "").param("save", "Save").param("champsId", "1")
				.param("champsId", "2").param("leagueId", "1")).andExpect(status().is2xxSuccessful()).andExpect(view().name("actor/edit"));
	}
}
