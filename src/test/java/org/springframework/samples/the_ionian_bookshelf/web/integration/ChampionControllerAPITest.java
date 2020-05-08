package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.BranchService;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.service.RoleService;
import org.springframework.samples.the_ionian_bookshelf.service.RuneService;
import org.springframework.samples.the_ionian_bookshelf.web.ChampionController;
import org.springframework.samples.the_ionian_bookshelf.web.RuneController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChampionControllerAPITest {

	@Autowired
	private ChampionController championController;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ChampionService championService;
	
	@Autowired
	private AdministratorService administratorService;
	

	@Test
	void testShowChampionListHtml() throws Exception {
		ModelMap model = new ModelMap();
		String view=championController.listadoCampeones(model);
		assertEquals(view,"/champions/listadoCampeones");
		assertNotNull(model.get("champions"));	
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testNewChampionForm() throws Exception {
		ModelMap model = new ModelMap();
		String view=championController.crearCampeon(model);
		assertEquals(view,"champions/editCampeon");
		assertNotNull(model.get("champion"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner1")
	@Test
	void testNewChampionFormNoAdmin() throws Exception {
		ModelMap model = new ModelMap();
		String view=championController.crearCampeon(model);
		assertEquals(view,"redirect:/login");
		assertNotNull(model.get("message"));	
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testCreationChampionForm() throws Exception {
		ModelMap model = new ModelMap();
		Champion champion= new Champion();
		Role role = roleService.findOneById(1);
		
		champion.setName("Champion name");
		champion.setDescription("desc");
		champion.setHealth(1000.0);
		champion.setMana(500.0);
		champion.setEnergy(null);
		champion.setAttack(1.0);
		champion.setSpeed(1.2);
		champion.setRole(role);
		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view=championController.salvarCampeon(champion, bindingResult, model);
		assertEquals(view,"redirect:/champions/");
		assertNotNull(model.getAttribute("message"));
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner1")
	@Test
	void testCreationChampionFormNotAdmin() throws Exception {
		ModelMap model = new ModelMap();
		Champion champion= new Champion();
		Role role = roleService.findOneById(1);
		
		champion.setName("Champion name");
		champion.setDescription("desc");
		champion.setHealth(1000.0);
		champion.setMana(500.0);
		champion.setEnergy(null);
		champion.setAttack(1.0);
		champion.setSpeed(1.2);
		champion.setRole(role);
		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view=championController.salvarCampeon(champion, bindingResult, model);
		assertEquals(view,"redirect:/login");
		assertNotNull(model.get("message"));	
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testCreationChampionFormErrors() throws Exception {
		ModelMap model = new ModelMap();
		Champion champion= new Champion();
		Role role = roleService.findOneById(1);
		
		champion.setName("");
		champion.setDescription("desc");
		champion.setHealth(1000.0);
		champion.setMana(500.0);
		champion.setEnergy(null);
		champion.setAttack(1.0);
		champion.setSpeed(1.2);
		champion.setRole(role);
		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		bindingResult.reject("name", "no puede estar vacío");
		bindingResult.reject("name", "el tamaño tiene que estar entre 10 y 500");
		String view=championController.salvarCampeon(champion, bindingResult, model);
		assertEquals(view,"champions/editCampeon");
	}
	

	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testRemoveChampion() throws Exception {
		ModelMap model = new ModelMap();
		String view=championController.borrarChampion(1, model);
		assertEquals(view,"redirect:/champions/");
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testRemoveChampionNotAdmin() throws Exception {
		ModelMap model = new ModelMap();
		String view=championController.borrarChampion(1, model);
		assertEquals(view,"redirect:/login");
		assertNotNull(model.get("message"));	
	}

	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testUpdateInitChampionForm() throws Exception {
		ModelMap model = new ModelMap();
		int championId = championService.findChampionById(2).getId(); //Es la unica forma que me ha funcionado, si ponia el id del tiron petaba
		String view=championController.initUpdateChampionForm(championId, model);
		assertEquals(view,"champions/editCampeon");
		assertNotNull(model.get("champion"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testUpdateInitChampionFormNoAdmin() throws Exception {
		ModelMap model = new ModelMap();
		int championId = championService.findChampionById(2).getId(); //Es la unica forma que me ha funcionado, si ponia el id del tiron petaba
		String view=championController.initUpdateChampionForm(championId, model);
		assertEquals(view,"redirect:/login");
	}
	
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testProcessUpdateChampion() throws Exception {
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		Role role = roleService.findOneById(1);
		Champion newChampion = new Champion("newname", "newdesc", 1000.0, 500.0, null, 1.0, 1.0, role);
		String view=championController.processUpdateChampionForm(newChampion, bindingResult, 1);
		assertEquals(view,"redirect:/champions/"); 
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testProcessUpdateChampionsError() throws Exception {
		ModelMap model = new ModelMap();
		Champion champion= new Champion();
		Role role = roleService.findOneById(1);
		
		champion.setName("");
		champion.setDescription("desc");
		champion.setHealth(1000.0);
		champion.setMana(500.0);
		champion.setEnergy(null);
		champion.setAttack(1.0);
		champion.setSpeed(1.2);
		champion.setRole(role);
		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		bindingResult.reject("name", "no puede estar vacío");
		bindingResult.reject("name", "el tamaño tiene que estar entre 10 y 500");
		String view=championController.processUpdateChampionForm(champion, bindingResult, 1);
		assertEquals(view,"redirect:/champions/editCampeon");
	}
	
}
