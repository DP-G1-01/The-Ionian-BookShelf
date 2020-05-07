package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.League;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.repository.BuildRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.LeagueRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.MessageRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RunePageRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.SummonerRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ThreadRepository;
import org.springframework.security.test.context.support.WithMockUser;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ThreadServiceTests {
	
	@Autowired
	private ThreadService threadService;
	@Autowired
	private ThreadRepository threadRepository;
	@Autowired
	private LeagueRepository leagueRepository;
	@Autowired
	private SummonerRepository summonerRepository;
	@Autowired
	private RunePageRepository runePageRepository;
	@Autowired
	private BuildRepository buildRepository;
	@Autowired
	private MessageRepository messageRepository;
	
	@Test
	@BeforeAll
	void testFindAll() {
		Collection<Thread> threads = threadService.findAll();
		assertEquals(threadRepository.count(), threads.size());
	}
	
	@Test
	@Transactional
	@AfterAll
	void testFindAllEmpty() {
		messageRepository.deleteAll();
		buildRepository.deleteAll();
		runePageRepository.deleteAll();
		summonerRepository.deleteAll();
		leagueRepository.deleteAll();
		threadRepository.deleteAll();
		assertEquals(0, threadRepository.count());
	}
	
//	@Test
//	@Transactional
//	void testFindOne() {
//		Thread i = threadService.findOne(1);
//		Thread ii = threadRepository.findById(1).get();
//		assertEquals(i, ii);
//	} 
	
	@Test
	@Transactional
	void testFindOneError() {
		NoSuchElementException exception = assertThrows(NoSuchElementException.class,()->threadService.findOne(3472));
		assertEquals(NoSuchElementException.class, exception.getClass());
	}
	
	@Test
	@Transactional
	@WithMockUser(value = "admin")
	void testIsAThreadFromLeague() {
		//Thread Creado nuevo no es un thread con league vinculado
		Thread nuevo = new Thread();
		nuevo.setTitle("Titulo Thread Testing");
		nuevo.setDescription("Description Thread Testing");
		threadRepository.save(nuevo);
		boolean before = threadService.isAThreadFromLeague(nuevo); //false, no es un hilo de una liga
		League league = new League();
		league.setName("League");
		league.setThread(nuevo);
		leagueRepository.save(league);
		boolean after = threadService.isAThreadFromLeague(nuevo); 
		assertNotEquals(before, after);
	}
	
	@Test
	@Transactional
	@WithMockUser(value = "admin")
	void testDeleteThread() {
		Thread nuevo = new Thread();
		nuevo.setTitle("Titulo Thread Testing");
		nuevo.setDescription("Description Thread Testing");
		threadService.save(nuevo);
		long l = threadRepository.count();
		Thread thread = threadService.findOne(nuevo.getId());
		threadService.delete(thread);
		long l2 = threadRepository.count();
		System.out.println(l + " y " + l2);
		assertEquals((l-1), l2);
	}
	
//	@Test
//	@Transactional
//	@WithMockUser(value = "admin")
//	void testDeleteThreadWithLeagueError() {
//	Thread thread = new Thread();
//	thread.setTitle("Titulo Thread Testing");
//	thread.setDescription("Description Thread Testing");
//	threadService.save(thread);
//	League league = new League();
//	league.setName("League");
//	league.setThread(thread);
//	leagueRepository.save(league);
//	AssertionError error = assertThrows(AssertionError.class, ()->threadService.delete(thread));
//	assertEquals(AssertionError.class, error.getClass());
//	}
	
//	@Test
//	@Transactional
//	@WithMockUser(value = "RAIMUNDOKARATE98")
//	void testDeleteThreadLoggedAsSummonerError() {
//		Thread thread = new Thread();
//		thread.setTitle("Titulo Thread Testing");
//		thread.setDescription("Description Thread Testing");
//		threadService.save(thread);
//		AssertionError error = assertThrows(AssertionError.class, ()->threadService.delete(thread));
//		assertEquals(AssertionError.class, error.getClass());
//	}
	
	@Test
	@Transactional
	@WithMockUser(value = "admin")
	void testSaveThread() {
		Thread thread = new Thread();
		thread.setTitle("Titulo Thread Testing");
		thread.setDescription("Description Thread Testing");
		threadService.save(thread);
		Thread thread2 = threadService.findOne(thread.getId());
		assertEquals(thread, thread2);
	}
	
	@Test
	@Transactional
	@WithMockUser(value = "admin")
	void testDeleteFromMessages() {
		messageRepository.deleteAll();
		Thread thread = new Thread();
		thread = threadService.findOne(3);
		Date moment = new Date(System.currentTimeMillis() - 1);
		Summoner summoner = new Summoner();
		summoner = summonerRepository.getOne(5); //RAIMUNDOKARATE98 LIGA PLATA
		Message message = new Message("NUEVO MENSAJE DE THREAD PARA TEST", moment, summoner, thread);
		Message message2 = new Message("NUEVO MENSAJE 2 DE THREAD PARA TEST", moment, summoner, thread);
		Message message3 = new Message("NUEVO MENSAJE 3 DE THREAD PARA TEST", moment, summoner, thread);
		messageRepository.save(message);
		messageRepository.save(message2);
		messageRepository.save(message3);
		long before = messageRepository.count();
		threadService.deleteFromMessages(thread);
		long after = messageRepository.count();
		assertEquals(before, after+3);
	}
	
}
