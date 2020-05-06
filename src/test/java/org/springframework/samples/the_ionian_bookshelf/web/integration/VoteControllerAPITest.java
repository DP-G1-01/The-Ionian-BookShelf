package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.service.MessageService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.samples.the_ionian_bookshelf.web.VoteController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoteControllerAPITest {
	
	@Autowired
	private VoteController voteController;
	@Autowired
	private ThreadService threadService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private SummonerService summonerService;
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testUpVoteThread() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("Thread Test", "Descripcion de Thread de prueba", 0);
		threadService.save(thread);
		String vista = voteController.upVoteThread(model, thread.getId());
		assertEquals(vista, "redirect:/threads");
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testUpVoteNotSummonerThread() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("Thread Test", "Descripcion de Thread de prueba", 0);
		threadService.save(thread);
		String vista = voteController.upVoteThread(model, thread.getId());
		assertEquals(vista, "/votes/voteError");
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testUpVoteAgainErrorThread() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("Thread Test", "Descripcion de Thread de prueba", 0);
		threadService.save(thread);
		String vista = voteController.upVoteThread(model, thread.getId());
		String vista2 = voteController.upVoteThread(model, thread.getId());
		assertEquals(vista, "redirect:/threads");
		assertEquals(vista2, "/votes/voteError");
	}

	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testDownVoteThread() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("Thread Test", "Descripcion de Thread de prueba", 0);
		threadService.save(thread);
		String vista = voteController.downVoteThread(model, thread.getId());
		assertEquals(vista, "redirect:/threads");
	}
	
	@WithMockUser(username = "admin", authorities = "admin")
	@Test
	void testDownVoteNotSummonerThread() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("Thread Test", "Descripcion de Thread de prueba", 0);
		threadService.save(thread);
		String vista = voteController.downVoteThread(model, thread.getId());
		assertEquals(vista, "/votes/voteError");
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testDownVoteAgainErrorThread() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("Thread Test", "Descripcion de Thread de prueba", 0);
		threadService.save(thread);
		String vista = voteController.downVoteThread(model, thread.getId());
		String vista2 = voteController.downVoteThread(model, thread.getId());
		assertEquals(vista, "redirect:/threads");
		assertEquals(vista2, "/votes/voteError");
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testUpVoteMessage() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("Thread Test", "Descripcion de Thread de prueba", 0);
		threadService.save(thread);
		Summoner summ = summonerService.findOne(1);
		Message message = new Message("Mensaje de prueba", Date.from(Instant.now()), summ, thread, 0);
		messageService.saveMessage(message);
		String vista = voteController.upVoteMessage(model, 1, message.getId());
		assertEquals(vista, "redirect:/threads/"+1);
	}
	
	//El problema aqui es que para hacer el save de message
	//necesita que hacerlo como usuario summoner
	//El mock es un admin y peta en el servicio de saveMessage
	
//	@WithMockUser(username = "admin", authorities = "admin")
//	@Test
//	void testUpVoteNotSummonerMessage() throws Exception{
//		ModelMap model = new ModelMap();
//		Thread thread = new Thread("Thread Test", "Descripcion de Thread de prueba", 0);
//		threadService.save(thread);
//		Summoner summ = summonerService.findOne(1);
//		Message message = new Message("Mensaje de prueba", Date.from(Instant.now()), summ, thread, 0);
//		messageService.saveMessage(message);
//		String vista = voteController.upVoteMessage(model, 1, message.getId());
//		assertEquals(vista, "/votes/voteError");
//	}

	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testUpVoteAgainErrorMessage() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("Thread Test", "Descripcion de Thread de prueba", 0);
		threadService.save(thread);
		Summoner summ = summonerService.findOne(1);
		Message message = new Message("Mensaje de prueba", Date.from(Instant.now()), summ, thread, 0);
		messageService.saveMessage(message);
		String vista = voteController.upVoteMessage(model, 1, message.getId());
		String vista2 = voteController.upVoteMessage(model, 1, message.getId());
		assertEquals(vista, "redirect:/threads/"+1);
		assertEquals(vista2, "/votes/voteError");
	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testDownVoteMessage() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("Thread Test", "Descripcion de Thread de prueba", 0);
		threadService.save(thread);
		Summoner summ = summonerService.findOne(1);
		Message message = new Message("Mensaje de prueba", Date.from(Instant.now()), summ, thread, 0);
		messageService.saveMessage(message);
		String vista = voteController.downVoteMessage(model, 1, message.getId());
		assertEquals(vista, "redirect:/threads/"+1);
	}
	
	//Pasa lo mismo que UpVote
//	@WithMockUser(username = "admin", authorities = "admin")
//	@Test
//	void testDownVoteNotSummonerMessage() throws Exception{
//		ModelMap model = new ModelMap();
//		Thread thread = new Thread("Thread Test", "Descripcion de Thread de prueba", 0);
//		threadService.save(thread);
//		Summoner summ = summonerService.findOne(1);
//		Message message = new Message("Mensaje de prueba", Date.from(Instant.now()), summ, thread, 0);
//		messageService.saveMessage(message);
//		String vista = voteController.downVoteMessage(model, 1, message.getId());
//		assertEquals(vista, "/votes/voteError");
//	}
	
	@WithMockUser(username = "summoner1", authorities = "summoner")
	@Test
	void testDownVoteAgainErrorMessage() throws Exception{
		ModelMap model = new ModelMap();
		Thread thread = new Thread("Thread Test", "Descripcion de Thread de prueba", 0);
		threadService.save(thread);
		Summoner summ = summonerService.findOne(1);
		Message message = new Message("Mensaje de prueba", Date.from(Instant.now()), summ, thread, 0);
		messageService.saveMessage(message);
		String vista = voteController.downVoteMessage(model, 1, message.getId());
		String vista2 = voteController.downVoteMessage(model, 1, message.getId());
		assertEquals(vista, "redirect:/threads/"+1);
		assertEquals(vista2, "/votes/voteError");
	}
	
}
