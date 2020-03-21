package service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import the_ionian_bookshelf.TheIonianBookshelfApplication;
import the_ionian_bookshelf.model.ChangeRequest;
import the_ionian_bookshelf.model.Item;
import the_ionian_bookshelf.repository.ChangeRequestRepository;
import the_ionian_bookshelf.repository.RoleRepository;
import the_ionian_bookshelf.service.ChangeRequestService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TheIonianBookshelfApplication.class})
@SpringBootTest
public class ChangeRequestServiceTests {
	
	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected ChangeRequestRepository changeRequestRepository;

	@Autowired
	private ChangeRequestService changeRequestService;
	
	@Test
	void testFindAll() {
		Collection<ChangeRequest> requests = changeRequestService.findAll();
		assertEquals(changeRequestRepository.count(), requests.size());
	}
	
	@Test
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
	void testRemoveChangeRequestById() {
		long l = changeRequestRepository.count();
		ChangeRequest request = changeRequestService.findChangeRequestById(1);
		changeRequestService.delete(request);
		long l2 = changeRequestRepository.count();
		System.out.println(l + " y " + l2);
		assertEquals((l-1), l2);
	}
}
