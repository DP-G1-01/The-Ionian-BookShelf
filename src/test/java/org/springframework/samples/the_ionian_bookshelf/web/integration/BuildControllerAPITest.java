package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.League;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.service.LeagueService;
import org.springframework.samples.the_ionian_bookshelf.service.RoleService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.samples.the_ionian_bookshelf.service.UserService;
import org.springframework.samples.the_ionian_bookshelf.web.BuildController;
import org.springframework.samples.the_ionian_bookshelf.web.ItemController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuildControllerAPITest {
	
	@Autowired
	private BuildController buildController;
	
	@Autowired
	private ItemController itemController;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ChampionService championService;
	
	@Autowired
	private ThreadService threadService;
	
	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private SummonerService summonerService;
	
	@Test
	void testShowBuildListHtml() throws Exception {
		ModelMap model = new ModelMap();
		String view=buildController.listPublicBuilds(model);
		assertEquals(view,"builds/buildsList");
		assertNotNull(model.get("builds"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testShowBuildMineListHtml() throws Exception {
		ModelMap model = new ModelMap();
		String view=buildController.listMineBuilds(model);
		assertEquals(view,"builds/buildsList");
		assertNotNull(model.get("builds"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testShowBuildForm() throws Exception {
		ModelMap model = new ModelMap();
		String view=buildController.crearBuild(model);
		assertEquals(view,"builds/editBuild");
		assertNotNull(model.get("build"));	
	}
//	@WithMockUser(username = "summoner1", authorities = "summoner")
//	@Test
//	void testShowBuildPublic() throws Exception {
//		ModelMap model = new ModelMap();
//		String view=buildController.showPublicBuild(2, model);
//		assertEquals(view,"builds/editBuild");
//		assertNotNull(model.get("build"));	
//	}
//	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testNewBuildForm() throws Exception {
	ModelMap model = new ModelMap();
	Champion c = championService.findChampionById(1);
	Collection<Champion> mains = new ArrayList<Champion>();
	mains.add(c);
	Summoner summoner = summonerService.findOne(1);
	Build build = new Build("Build de testeo", "Soy una build con una descripción muy bonita, sí", false, new ArrayList<>(), c, null, null, summoner, 1);
	BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
	String view=buildController.salvarBuild(build, bindingResult, model);
	assertEquals(view,"redirect:/mine/builds");
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testUpdateBuild() throws Exception {
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		Champion c = championService.findChampionById(1);
		Collection<Champion> mains = new ArrayList<Champion>();
		mains.add(c);
		Summoner summoner = summonerService.findOne(1);
		Build build = new Build("Build de testeo", "Soy una build con una descripción muy bonita, sí", false, new ArrayList<>()
				, c, null, null, summoner, 1);
		String view=buildController.processUpdateBuildForm(build, bindingResult, 1);
		assertEquals(view,"redirect:/mine/builds");
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testRemoveBuild() throws Exception {
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		ModelMap model = new ModelMap();
		Champion c = championService.findChampionById(1);
		Collection<Champion> mains = new ArrayList<Champion>();
		mains.add(c);
		Summoner summoner = summonerService.findOne(1);
		Build build = new Build("Build de testeo", "Soy una build con una descripción muy bonita, sí", false, new ArrayList<>(), c, null, null, summoner, 1);
		String view=buildController.removeBuild(1, model);
		assertEquals(view,"redirect:/mine/builds");
	}
}
