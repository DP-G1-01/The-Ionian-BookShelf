package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.service.ItemService;
import org.springframework.samples.the_ionian_bookshelf.service.RoleService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.web.ChangeRequestController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChangeRequestControllerAPITest {

	@Autowired
	private ChangeRequestController requestController;

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private SummonerService summonerService;
	
	@WithMockUser(username = "reviewer1", authorities = "reviewer")
	@Test
	void testShowChangeRequestListHtml() throws Exception {
		ModelMap model = new ModelMap();
		String view=requestController.listadoAllRequests(model);
		assertEquals(view,"requests/listadoRequests");
		assertNotNull(model.get("requests"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testShowChangeRequestMineListHtml() throws Exception {
		ModelMap model = new ModelMap();
		String view=requestController.listadoMineRequests(model);
		assertEquals(view,"requests/listadoRequests");
		assertNotNull(model.get("requests"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testNewChangeRequestForm() throws Exception {
		ModelMap model = new ModelMap();
		String view=requestController.crearChangeRequestItem(1, model);
		assertEquals(view,"requests/createRequest");
		assertNotNull(model.get("request"));	
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testCreationForm() throws Exception {
		ModelMap model = new ModelMap();
		Item item= itemService.findOne(1);
		
		ChangeRequest request= new ChangeRequest();
		request.setItem(item);
		request.setTitle("titulo de prueba");
		request.setDescription("descripcion de la prueba que estoy haciendo justo ahora");
		request.setSummoner(summonerService.findOne(1));
		request.setStatus("PENDING");
		request.setChangeItem(Stream.of(1,2,3).map(x-> x.toString()).collect(Collectors.toList()));
		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view=requestController.guardarChangeRequest(request, bindingResult, model);
		assertEquals(view,"redirect:/mine/requests");
		assertNotNull(model.getAttribute("message"));
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testCreationFormErrors() throws Exception {
		ModelMap model = new ModelMap();
		Item item= itemService.findOne(1);
		
		ChangeRequest request= new ChangeRequest();
		request.setItem(item);
		request.setTitle("");
		request.setDescription("");
		request.setSummoner(summonerService.findOne(1));
		request.setStatus("PENDING");
		request.setChangeItem(Stream.of().map(x-> x.toString()).collect(Collectors.toList()));
		
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		bindingResult.reject("title", "The title can't be empty.");
		bindingResult.reject("description", "The description can't be empty.");
		bindingResult.reject("changeItem[0]", "An attribute can't be empty.");
		bindingResult.reject("changeItem[1]", "An attribute can't be empty.");
		bindingResult.reject("changeItem[2]", "An attribute can't be empty.");
		String view=requestController.guardarChangeRequest(request, bindingResult, model);
		assertEquals(view,"requests/createRequest");
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testRemoveChangeRequestNotReviewer() throws Exception {
		ModelMap model = new ModelMap();
		String view=requestController.removeChangeRequest(1, model);
		assertEquals(view,"redirect:/");
		assertNotNull(model.get("message"));	
	}
}
