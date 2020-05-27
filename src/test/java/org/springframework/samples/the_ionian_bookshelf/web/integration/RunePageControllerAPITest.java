package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.model.RunePage;
import org.springframework.samples.the_ionian_bookshelf.service.BranchService;
import org.springframework.samples.the_ionian_bookshelf.service.RunePageService;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.utilities.AbstractTest;
import org.springframework.samples.the_ionian_bookshelf.web.RunePageController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class RunePageControllerAPITest {

	@Autowired
	private RunePageController runePageController;
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private RuneService runeService;
	
	@Autowired
	private SummonerService summonerService;
	
	@Autowired
	private RunePageService runePageService;
	
	
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
	
	@Test
	void testNewRunePageNotLogged() throws Exception {
		ModelMap model = new ModelMap();
		String view=runePageController.crearPaginaRuna(model);
		assertEquals(view,"redirect:/login");
		assertNotNull(model.get("message"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testCreationRunePageForm() throws Exception {
		ModelMap model = new ModelMap();
		RunePage runePage= new RunePage();
		Branch mainBranch = branchService.findBranchById(1);
		Branch secBranch = branchService.findBranchById(2);
		Rune keyRune = runeService.findRuneById(1);
		Rune mainRune1 = runeService.findRuneById(5);
		Rune mainRune2 = runeService.findRuneById(8);
		Rune mainRune3 = runeService.findRuneById(12);
		Rune secRune1 = runeService.findRuneById(18);
		Rune secRune2 = runeService.findRuneById(21);
		runePage.setName("RunePage name");
		runePage.setMainBranch(mainBranch);
		runePage.setSecondaryBranch(secBranch);
		runePage.setKeyRune(keyRune);
		runePage.setMainRune1(mainRune1);
		runePage.setMainRune2(mainRune2);
		runePage.setMainRune3(mainRune3);
		runePage.setSecRune1(secRune1);
		runePage.setSecRune2(secRune2);
		runePage.setSummoner(summonerService.findOne(1));
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view=runePageController.salvarPaginaRuna(runePage, bindingResult, model);
		assertEquals(view,"redirect:/runePages/mine");
		assertNotNull(model.getAttribute("message"));
	}
	
	@Test
	void testCreationRunePageFormNotLogged() throws Exception {
		ModelMap model = new ModelMap();
		Branch mainBranch = branchService.findBranchById(1);
		Branch secBranch = branchService.findBranchById(2);
		Rune keyRune = runeService.findRuneById(1);
		Rune mainRune1 = runeService.findRuneById(5);
		Rune mainRune2 = runeService.findRuneById(8);
		Rune mainRune3 = runeService.findRuneById(12);
		Rune secRune1 = runeService.findRuneById(18);
		Rune secRune2 = runeService.findRuneById(21);
		RunePage runePage = new RunePage("RunePage name", summonerService.findOne(1), mainBranch, secBranch, keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune2);
		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view= runePageController.salvarPaginaRuna(runePage, bindingResult, model);
		assertEquals(view,"redirect:/login");
		assertNotNull(model.getAttribute("message"));	
	}
	
	
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testCreationRunePageFormErrors() throws Exception {
		ModelMap model = new ModelMap();
		Branch mainBranch = branchService.findBranchById(2);
		Branch secBranch = branchService.findBranchById(2);
		Rune keyRune = runeService.findRuneById(1);
		Rune mainRune1 = runeService.findRuneById(5);
		Rune mainRune2 = runeService.findRuneById(8);
		Rune mainRune3 = runeService.findRuneById(12);
		Rune secRune1 = runeService.findRuneById(18);
		Rune secRune2 = runeService.findRuneById(21);
		RunePage runePage = new RunePage("RunePage name", summonerService.findOne(1), mainBranch, secBranch, keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune2);

		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		bindingResult.rejectValue("mainBranch", "Main Branch should not be the same as Secondary Branch", "Main Branch should not be the same as Secondary Branch");
		String view= runePageController.salvarPaginaRuna(runePage, bindingResult, model);
		assertEquals(view,"runePages/editRunePage");
		assertNotNull(model.getAttribute("runePage"));	
	}
	
	@Test
	void testRemoveRunePageNotLogged() throws Exception {
		ModelMap model = new ModelMap();
		String view=runePageController.borrarPaginaRunas(1, model);
		assertEquals(view,"redirect:/login");
		assertNotNull(model.get("message"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testUpdateInitRunePageForm() throws Exception {
		ModelMap model = new ModelMap();
		
		Branch mainBranch = branchService.findBranchById(1);
		Branch secBranch = branchService.findBranchById(2);
		Rune keyRune = runeService.findRuneById(1);
		Rune mainRune1 = runeService.findRuneById(5);
		Rune mainRune2 = runeService.findRuneById(8);
		Rune mainRune3 = runeService.findRuneById(12);
		Rune secRune1 = runeService.findRuneById(18);
		Rune secRune2 = runeService.findRuneById(21);
		RunePage runePageAux = new RunePage("RunePage name", summonerService.findOne(1), mainBranch, secBranch, keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune2);
		this.runePageService.save(runePageAux);
		String view=runePageController.initUpdateRunePageForm(runePageAux.getId() , model);
		assertEquals(view,"runePages/editRunePage");
		assertNotNull(model.get("runePage"));	
	}
	
//	@WithMockUser(username = "summoner1", authorities = "summoner")
//	@Test
//	void testUpdateInitRunePageNotYoursForm() throws Exception {
//		
//		ModelMap model = new ModelMap();
//		Branch mainBranch = branchService.findBranchById(1);
//		Branch secBranch = branchService.findBranchById(2);
//		Rune keyRune = runeService.findRuneById(1);
//		Rune mainRune1 = runeService.findRuneById(5);
//		Rune mainRune2 = runeService.findRuneById(8);
//		Rune mainRune3 = runeService.findRuneById(12);
//		Rune secRune1 = runeService.findRuneById(18);
//		Rune secRune2 = runeService.findRuneById(21);
//		RunePage runePageAux = new RunePage("RunePage name", summonerService.findOne(1), mainBranch, secBranch, keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune2);
//		this.runePageService.save(runePageAux);
//		String view=runePageController.initUpdateRunePageForm(runePageAux.getId() , model);
//		assertEquals(view,"redirect:/runePages/mine");
//		assertNotNull(model.get("message"));
//	}
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testProcessUpdateRunePage() throws Exception {
		
		Branch mainBranch = branchService.findBranchById(1);
		Branch secBranch = branchService.findBranchById(2);
		Rune keyRune = runeService.findRuneById(1);
		Rune mainRune1 = runeService.findRuneById(5);
		Rune mainRune2 = runeService.findRuneById(8);
		Rune mainRune3 = runeService.findRuneById(12);
		Rune secRune1 = runeService.findRuneById(18);
		Rune secRune2 = runeService.findRuneById(21);
		RunePage updateRunePage = new RunePage("RunePage name", summonerService.findOne(1), mainBranch, secBranch, keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune2);
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view=runePageController.processUpdateRunePageForm(updateRunePage, bindingResult, 1);
		assertEquals(view,"runePages/mine"); 
	}
//	@WithMockUser(username = "summoner1", authorities = "summoner")
//	@Test
//	void testProcessUpdateRunePageNotYours() throws Exception {
//		
//		Branch mainBranch = branchService.findBranchById(1);
//		Branch secBranch = branchService.findBranchById(2);
//		Rune keyRune = runeService.findRuneById(1);
//		Rune mainRune1 = runeService.findRuneById(5);
//		Rune mainRune2 = runeService.findRuneById(8);
//		Rune mainRune3 = runeService.findRuneById(12);
//		Rune secRune1 = runeService.findRuneById(18);
//		Rune secRune2 = runeService.findRuneById(21);
//		RunePage updateRunePage = new RunePage("RunePage name", summonerService.findOne(1), mainBranch, secBranch, keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune2);
//		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
//		String view=runePageController.processUpdateRunePageForm(updateRunePage, bindingResult, 1);
//		assertEquals(view,"redirect:/runePages/mine"); 
//	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testProcessUpdateRunePageFormError() throws Exception {
		
		Branch mainBranch = branchService.findBranchById(2);
		Branch secBranch = branchService.findBranchById(2);
		Rune keyRune = runeService.findRuneById(1);
		Rune mainRune1 = runeService.findRuneById(5);
		Rune mainRune2 = runeService.findRuneById(8);
		Rune mainRune3 = runeService.findRuneById(12);
		Rune secRune1 = runeService.findRuneById(18);
		Rune secRune2 = runeService.findRuneById(21);
		RunePage updateRunePage = new RunePage("RunePage name", summonerService.findOne(1), mainBranch, secBranch, keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune2);
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		bindingResult.rejectValue("mainBranch", "Main Branch should not be the same as Secondary Branch", "Main Branch should not be the same as Secondary Branch");
		String view=runePageController.processUpdateRunePageForm(updateRunePage, bindingResult, 1);
		assertEquals(view,"redirect:/runePages/editRunePage"); 
	}
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testRemoveRunePage() throws Exception {
	
		ModelMap model = new ModelMap();
		String view=runePageController.borrarPaginaRunas(1, model);
		assertEquals(view,"redirect:/runePages/mine");
	}
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testRemoveRunePageNotYours() throws Exception {
		
		ModelMap model = new ModelMap();
		Branch mainBranch = branchService.findBranchById(1);
		Branch secBranch = branchService.findBranchById(2);
		Rune keyRune = runeService.findRuneById(1);
		Rune mainRune1 = runeService.findRuneById(5);
		Rune mainRune2 = runeService.findRuneById(8);
		Rune mainRune3 = runeService.findRuneById(12);
		Rune secRune1 = runeService.findRuneById(18);
		Rune secRune2 = runeService.findRuneById(21);
		RunePage runePageAux = new RunePage("RunePage name", summonerService.findOne(1), mainBranch, secBranch, keyRune, mainRune1, mainRune2, mainRune3, secRune1, secRune2);
		this.runePageService.save(runePageAux);
		
		String view=runePageController.borrarPaginaRunas(runePageAux.getId(), model);
		assertEquals(view,"redirect:/runePages/mine");
	}
}
