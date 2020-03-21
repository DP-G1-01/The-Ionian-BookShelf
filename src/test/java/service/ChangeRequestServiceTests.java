package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import the_ionian_bookshelf.TheIonianBookshelfApplication;
import the_ionian_bookshelf.model.ChangeRequest;
import the_ionian_bookshelf.repository.ChangeRequestRepository;
import the_ionian_bookshelf.repository.RoleRepository;
import the_ionian_bookshelf.service.ChangeRequestService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TheIonianBookshelfApplication.class})
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ChangeRequestServiceTests {
	
	@Autowired
	protected RoleRepository roleRepository;
	
	@Autowired
	protected ChangeRequestRepository changeRequestRepository;

	@Autowired
	private ChangeRequestService changeRequestService;
	
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
	
//	@Test
//	@Transactional
//	void testRemoveChangeRequestError() {
//		ChangeRequest request = changeRequestService.findChangeRequestById(4637);
//		AssertionError exception = assertThrows(AssertionError.class,()->changeRequestService.delete(request));
//		assertEquals(AssertionError.class, exception.getClass());
//	}
}
