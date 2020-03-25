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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.TheIonianBookshelfApplication;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.repository.ChangeRequestRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ItemRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RoleRepository;
import org.springframework.samples.the_ionian_bookshelf.service.ChangeRequestService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

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
	private ItemRepository itemRepository;
	
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
	
	@Test
	@Transactional
	void testFindOne() {
		ChangeRequest requests = changeRequestService.findChangeRequestById(1);
		ChangeRequest requests2 = changeRequestRepository.findChangeRequestById(1);
		assertEquals(requests, requests2);
	}
	
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
		System.out.println(l + " y " + l2);
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
		ChangeRequest request = new ChangeRequest(null, item, "Soy el titulo", "Soy la descripcion y debo tener al menos 20 años de edad.", null, changeItem, "PENDING", null, null);
		changeRequestService.save(request);
		ChangeRequest request2 = changeRequestService.findChangeRequestById(request.getId());
		assertEquals(request, request2);
	} 
	
//	@Test
//	@Transactional
//	void testRemoveChangeRequestError() {
//		ChangeRequest request = changeRequestService.findChangeRequestById(4637);
//		AssertionError exception = assertThrows(AssertionError.class,()->changeRequestService.delete(request));
//		assertEquals(AssertionError.class, exception.getClass());
//	}
}
