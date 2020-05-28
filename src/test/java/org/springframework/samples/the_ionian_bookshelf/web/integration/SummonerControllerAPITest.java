package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.web.SummonerController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SummonerControllerAPITest {

	@Autowired
	private SummonerController summonerController;

	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testBanSummonerNotReviewer() throws Exception {
		ModelMap model = new ModelMap();
		String view=summonerController.banearSummoner(1, model);
		assertEquals(view,"redirect:/");
		assertNotNull(model.get("message"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testDesbanSummonerNotReviewer() throws Exception {
		ModelMap model = new ModelMap();
		String view=summonerController.desbanearSummoner(1, model);
		assertEquals(view,"redirect:/");
		assertNotNull(model.get("message"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testUpdateInitForm() throws Exception {
		ModelAndView modelAndView=this.summonerController.edit();
		assertEquals(modelAndView.getViewName(),"actor/edit");
		assertNotNull(modelAndView.getModel().get("actor"));	
	}
	
	@Test
	void testUpdateInitFormNotLogged() throws Exception {
		ModelAndView modelAndView=this.summonerController.edit();
		assertEquals(modelAndView.getViewName(),"redirect:/login");
	}
}
