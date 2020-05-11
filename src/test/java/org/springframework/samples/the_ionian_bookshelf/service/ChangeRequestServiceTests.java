package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.League;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.repository.ChangeRequestRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ItemRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.LeagueRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RoleRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.SummonerRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ThreadRepository;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ChangeRequestServiceTests {
	
	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected ChangeRequestRepository changeRequestRepository;

	@Autowired
	private ChangeRequestService changeRequestService;

	@Autowired
	protected ItemRepository itemRepository;

	@Autowired
	protected ThreadRepository threadRepository;

	@Autowired
	protected LeagueRepository leagueRepository;
	
	@Autowired
	protected SummonerRepository summonerRepository;
	
	@Test
	@BeforeAll
	void testFindAll() {
		Collection<ChangeRequest> requests = changeRequestService.findAll();
		assertEquals(changeRequestRepository.count(), requests.size());
	}
	
	@Test
	@Transactional
	@AfterAll
	void testFindAllEmpty() {
		changeRequestRepository.deleteAll();
		assertEquals(0, changeRequestRepository.count());
	}
	
//	@Test
//	@Transactional
//	void testFindOne() {
//		ChangeRequest requests = changeRequestService.findChangeRequestById(1);
//		ChangeRequest requests2 = changeRequestRepository.findChangeRequestById(1);
//		assertEquals(requests, requests2);
//	}
	
	@Test
	@Transactional
	void testFindOneError() {
		AssertionError exception = assertThrows(AssertionError.class,()->changeRequestService.findChangeRequestById(3472));
		assertEquals(AssertionError.class, exception.getClass());
	}
	
	@Test
	@Transactional
	void testRemoveChangeRequest() {
		long l = changeRequestRepository.count();
		ChangeRequest request = changeRequestService.findChangeRequestById(1);
		changeRequestService.delete(request);
		long l2 = changeRequestRepository.count();
		assertEquals((l-1), l2);
	}
	
	@Test
	@Transactional
	void testSaveChangeRequest() {
		List <String> attributes = new ArrayList<>();
		attributes.add("4");
		attributes.add("2");
		attributes.add("1");
		List <Role> roles = new ArrayList<>();
		Role r = new Role("rolTest", "testeoooooooooo", "https://www.google.es");
		roles.add(r);
		List <String> changeItem = new ArrayList<>();
		changeItem.add("1");
		changeItem.add("1");
		changeItem.add("3");
		Item item = new Item("test", "testeandoooooooooooooooooo", attributes, roles);
		itemRepository.save(item);
		Collection<Champion> mains = new ArrayList<Champion>();
		Champion c = new Champion("Cham1", "La descripción es algo superfluo sin cabida en una mente abierta",
				10., 5., null, 20., 50., r);
		mains.add(c);
		User user = new User();
		user.setUsername("Pepin");
		user.setPassword("papin");
		Summoner summoner = new Summoner();
		summoner.setUser(user);
		summoner.setEmail("pru@gmail.com");
		summoner.setMains(mains);
		Thread t1 = new Thread("Estoy aquí", "Intentado acabar los tests ya que es tardecito hombre no es plan", null);
		threadRepository.save(t1);
		League leg = new League("L1", t1);
		leagueRepository.save(leg);
		summoner.setLeague(leg);
		summonerRepository.save(summoner);
		ChangeRequest request = new ChangeRequest(null, item, "Soy el titulo", "Soy la descripcion y debo tener al menos 20 años de edad.", null, changeItem, "PENDING", summoner, null);
		changeRequestService.save(request);
		ChangeRequest request2 = changeRequestService.findChangeRequestById(request.getId());
		assertEquals(request, request2);
	} 
	
	@Test
	@Transactional
	void testRemoveChangeRequestError() {
		AssertionError exception = assertThrows(AssertionError.class,()->changeRequestService.delete(changeRequestService.findChangeRequestById(75)));
		assertEquals(AssertionError.class, exception.getClass());
	}
}
