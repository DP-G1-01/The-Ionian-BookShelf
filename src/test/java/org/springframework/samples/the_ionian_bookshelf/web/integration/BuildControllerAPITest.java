package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.web.BuildController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuildControllerAPITest {
	
	@Autowired
	private BuildController buildController;

	
	@Test
	void testShowBuildListHtml() throws Exception {
		ModelMap model = new ModelMap();
		String view=buildController.listPublicBuilds(model);
		assertEquals(view,"builds/buildsList");
		assertNotNull(model.get("builds"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testShowChangeRequestMineListHtml() throws Exception {
		ModelMap model = new ModelMap();
		String view=buildController.listMineBuilds(model);
		assertEquals(view,"builds/buildsList");
		assertNotNull(model.get("builds"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testNewChangeRequestForm() throws Exception {
		ModelMap model = new ModelMap();
		String view=buildController.crearBuild(model);
		assertEquals(view,"builds/editBuild");
		assertNotNull(model.get("build"));	
	}
}
