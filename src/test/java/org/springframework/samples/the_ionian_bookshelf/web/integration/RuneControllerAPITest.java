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
import org.springframework.samples.the_ionian_bookshelf.model.Administrator;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.BranchService;
import org.springframework.samples.the_ionian_bookshelf.service.ItemService;
import org.springframework.samples.the_ionian_bookshelf.service.RoleService;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;
import org.springframework.samples.the_ionian_bookshelf.web.ItemController;
import org.springframework.samples.the_ionian_bookshelf.web.RuneController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RuneControllerAPITest {

	@Autowired
	private RuneController runeController;
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private RuneService runeService;
	
	@Autowired
	private AdministratorService administratorService;
	

	@Test
	void testShowRuneListHtml() throws Exception {
		ModelMap model = new ModelMap();
		String view=runeController.listadoRunas(model);
		assertEquals(view,"runes/listadoRunas");
		assertNotNull(model.get("runes"));	
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testNewRuneForm() throws Exception {
		ModelMap model = new ModelMap();
		String view=runeController.crearRuna(model);
		assertEquals(view,"runes/editRune");
		assertNotNull(model.get("rune"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner1")
	@Test
	void testNewRuneFormNoAdmin() throws Exception {
		ModelMap model = new ModelMap();
		String view=runeController.crearRuna(model);
		assertEquals(view,"redirect:/login");
		assertNotNull(model.get("message"));	
	}
	
	
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testCreationRuneForm() throws Exception {
		ModelMap model = new ModelMap();
		Rune rune= new Rune(); 
		Branch branch = branchService.findBranchById(1);
		
		rune.setName("Rune name");
		rune.setDescription("desc");
		rune.setBranch(branch);
		rune.setNode("1");
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view=runeController.salvarRuna(rune, bindingResult, model);
		assertEquals(view,"redirect:/runes/");
		assertNotNull(model.getAttribute("message"));
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner1")
	@Test
	void testCreationRuneFormNotAdmin() throws Exception {
		ModelMap model = new ModelMap();
		Rune rune= new Rune(); 
		Branch branch = branchService.findBranchById(1);
		
		rune.setName("Rune name");
		rune.setDescription("desc");
		rune.setBranch(branch);
		rune.setNode("1");
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view=runeController.salvarRuna(rune, bindingResult, model);
		assertEquals(view,"redirect:/login");
		assertNotNull(model.get("message"));	
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testCreationRuneFormErrors() throws Exception {
		ModelMap model = new ModelMap();
		Rune rune= new Rune();
		Branch branch = branchService.findBranchById(1);
		
		rune.setName("");
		rune.setDescription("desc");
		rune.setBranch(branch);
		rune.setNode("1");
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		bindingResult.reject("name", "no puede estar vacío");
		bindingResult.reject("name", "el tamaño tiene que estar entre 10 y 500");
		String view=runeController.salvarRuna(rune, bindingResult, model);
		assertEquals(view,"runes/editRune");
	}
	

	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testRemoveRune() throws Exception {
		ModelMap model = new ModelMap();
		String view=runeController.borrarRuna(1, model);
		assertEquals(view,"redirect:/runes/");
	}

	
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testRemoveRuneNotAdmin() throws Exception {
		ModelMap model = new ModelMap();
		String view=runeController.borrarRuna(1, model);
		assertEquals(view,"redirect:/login");
		assertNotNull(model.get("message"));	
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testUpdateInitRuneForm() throws Exception {
		ModelMap model = new ModelMap();
		int runeId = runeService.findRuneById(2).getId(); 
		String view=runeController.initUpdateRuneForm(runeId , model); //Es la unica forma que me ha funcionado, si ponia el id del tiron petaba
		assertEquals(view,"runes/editRune");
		assertNotNull(model.get("rune"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testUpdateInitRuneFormNoAdmin() throws Exception {
		ModelMap model = new ModelMap();
		int runeId = runeService.findRuneById(2).getId(); 
		String view=runeController.initUpdateRuneForm(runeId , model); //Es la unica forma que me ha funcionado, si ponia el id del tiron petaba
		assertEquals(view,"redirect:/login");
	}
	
	
	
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testProcessUpdateRune() throws Exception {
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		Branch branch = branchService.findBranchById(1);
		Rune updateRune = new Rune("newname","newdesc",branch,"1");
		String view=runeController.processUpdateRuneForm(updateRune, bindingResult, 1);
		assertEquals(view,"redirect:/runes/"); 
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testProcessUpdateRuneError() throws Exception {
		ModelMap model = new ModelMap();
		Rune rune= new Rune();
		Branch branch = branchService.findBranchById(1);
		
		rune.setName("Rune name");
		rune.setDescription("desc");
		rune.setBranch(branch);
		rune.setNode("1");
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		bindingResult.reject("name", "no puede estar vacío");
		bindingResult.reject("name", "el tamaño tiene que estar entre 10 y 500");
		String view=runeController.processUpdateRuneForm(rune, bindingResult, 1);
		assertEquals(view,"redirect:/runes/editRune");
	}
	
	
	
	
	
}
