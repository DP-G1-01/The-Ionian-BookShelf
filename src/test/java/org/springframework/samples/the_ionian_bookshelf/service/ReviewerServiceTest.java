package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Reviewer;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.security.LoginService;
import org.springframework.samples.the_ionian_bookshelf.utilities.AbstractTest;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewerServiceTest extends AbstractTest {

	@Autowired
	private ReviewerService reviewerService;

	@Autowired
	private LoginService loginService;

	@Test
	void findAllCollectionTest() {

		Collection<Reviewer> res = this.reviewerService.findAll();
		assertTrue(!res.isEmpty());
	}

	@DisplayName("FindOne Test")
	@ParameterizedTest(name = "\"{0}\": Reviewer´s id has to exist")
	@CsvSource({ "0", "50", "4" })
	void findOneTest(Integer reviewerId) {

		if (reviewerId != 0 && this.reviewerService.exists(reviewerId) == false) {
			assertThrows(NoSuchElementException.class, () -> {
				this.reviewerService.findOne(reviewerId);
			});
		} else if (reviewerId == 0) {
			assertThrows(AssertionError.class, () -> {
				this.reviewerService.findOne(reviewerId);
			});
		} else {
			this.reviewerService.findOne(reviewerId);
		}
	}

	@DisplayName("FindByPrincipal Test")
	@ParameterizedTest(name = "\"{0}\": Reviewer´s username has to exist")
	@CsvSource({ "noUsername", "reviewer4" })
	void findByPrincipalTest(String username) {

		if (username.contentEquals("noUsername")) {
			this.authenticate(null);
			assertThrows(AssertionError.class, () -> {
				this.reviewerService.findByPrincipal();
			});
		} else {
			this.authenticate(username);
			this.reviewerService.findByPrincipal();
		}
	}

	@DisplayName("Save Test")
	@ParameterizedTest(name = "\"{0}\": Represents the status of the operation that will be performed")
	@CsvSource({ "Fail", "Success" })
	void saveTest(String status) {

		User user = new User();
		user.setUsername("reviewer5");
		user.setPassword("reviewer5");
		user.setEnabled(true);

		Reviewer toTest = new Reviewer();
		toTest.setUser(user);

		if (status.contentEquals("Fail")) {
			toTest.setEmail("");
			assertThrows(ConstraintViolationException.class, () -> {
				this.reviewerService.save(toTest);
			});
		} else {
			toTest.setEmail("reviewer5@gmail.com");
			this.reviewerService.save(toTest);
		}
	}

}
