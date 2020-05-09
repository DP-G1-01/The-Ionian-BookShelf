package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.service.BranchService;
import org.springframework.samples.the_ionian_bookshelf.service.RunePageService;
import org.springframework.samples.the_ionian_bookshelf.web.RunePageController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RunePageControllerAPITest {

	@Autowired
	private RunePageController runePageController;
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private RunePageService runeService;
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testShowRunePageListHtml() throws Exception {
		ModelMap model = new ModelMap();
		String view=runePageController.listadoPaginasRunas(model);
		assertEquals(view,"runePages/listadoPaginasRunas");
		assertNotNull(model.get("runePages"));	
	}
	
	@Test
	void testShowRunePageNotLogged() throws Exception {
		ModelMap model = new ModelMap();
		String view=runePageController.listadoPaginasRunas(model);
		assertEquals(view,"redirect:/login");
		assertNotNull(model.get("message"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testNewRunePageForm() throws Exception {
		ModelMap model = new ModelMap();
		String view=runePageController.crearPaginaRuna(model);
		assertEquals(view,"runePages/editRunePage");
		assertNotNull(model.get("runePage"));	
	}
}
