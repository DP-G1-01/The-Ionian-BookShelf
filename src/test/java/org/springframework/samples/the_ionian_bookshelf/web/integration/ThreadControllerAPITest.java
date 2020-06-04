package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.samples.the_ionian_bookshelf.web.ThreadController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThreadControllerAPITest {

	@Autowired
	private ThreadController threadController;
	@Autowired
	private ThreadService threadService;
	
	//Listado de Threads
//	@Test
//	void testShowThreadList() throws Exception{
//		ModelMap model = new ModelMap();
//		String view=threadController.showThreadList(model);
//		assertEquals(view,"threads/listadoThreads");
//		assertNotNull(model.get("threads"));
//	}
	
	//Listado de Mensajes de un Thread
	@Test
	void testShowMessageListFromThread() throws Exception{
		ModelMap model = new ModelMap();
		String view = threadController.showMessageListFromThread(model,1);
		assertEquals(view,"messages/listadoMessages");
		assertNotNull(model.get("messages"));
	}
	
	//Creacion de Thread
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testNewThreadForm() throws Exception {
		ModelMap model = new ModelMap();
		String view= threadController.createThread(model);
		assertEquals(view,"threads/createThread");
		assertNotNull(model.get("thread"));	
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testSaveThread() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("TituloTest", "Descripcion de thread de prueba", null);
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		String view=threadController.saveThread(thread, bindingResult, model);
		assertEquals(view,"redirect:/threads");
		assertEquals(model.getAttribute("message"),"Thread saved successfully");
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testSaveErrorFormThread() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("", "", null);
		BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
		bindingResult.reject("name", "no puede estar vacío");
		bindingResult.reject("name", "el tamaño tiene que estar entre 10 y 500");
		String view=threadController.saveThread(thread, bindingResult, model);
		assertEquals(view,"threads/createThread");
		assertNotNull(model.get("thread"));
	}
	
	//Eliminacion de Thread
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testDeleteThread() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("TituloTest", "Descripcion de thread de prueba", null);
		threadService.save(thread);
		String view = threadController.deleteThread(thread.getId(), model);
		assertEquals(view, "redirect:/threads");
		assertEquals(model.getAttribute("message"), "Thread deleted successfully");
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testDeleteErrorThread() throws Exception{
		ModelMap model = new ModelMap();
		String view = threadController.deleteThread(1, model);
		assertEquals(view, "/threads/error");
		assertEquals(model.getAttribute("message"), "NO SE PUEDE ELIMINAR UN THREAD VINCULADO A UNA LIGA");
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testDeleteThreadAsSummoner() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("TituloTest", "Descripcion de thread de prueba", null);
		threadService.save(thread);
		String view = threadController.deleteThread(thread.getId(), model);
		assertEquals(view, "redirect:/login");
		assertEquals(model.getAttribute("message"), "You must be logged in as an admin");
	}
	
}
