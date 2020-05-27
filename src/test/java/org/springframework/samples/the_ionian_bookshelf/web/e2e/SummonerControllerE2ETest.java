package org.springframework.samples.the_ionian_bookshelf.web.e2e;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
