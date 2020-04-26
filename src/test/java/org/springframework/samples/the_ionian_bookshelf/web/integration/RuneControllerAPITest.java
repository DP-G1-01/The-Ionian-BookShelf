package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
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
	void testCreationRuneFormErrors() throws Exception {
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
		String view=runeController.salvarRuna(rune, bindingResult, model);
		assertEquals(view,"runes/editRune");
	}
	
}
