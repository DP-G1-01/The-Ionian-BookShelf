package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Reviewer;
import org.springframework.samples.the_ionian_bookshelf.service.ReviewerService;
import org.springframework.samples.the_ionian_bookshelf.utilities.AbstractTest;
import org.springframework.samples.the_ionian_bookshelf.web.ActorController;
import org.springframework.samples.the_ionian_bookshelf.web.ReviewerController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.servlet.ModelAndView;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReviewerControllerAPITest extends AbstractTest {

	@Autowired
	private ActorController actorController;

	@Autowired
	private ReviewerController reviewerController;

	@Autowired
	private ReviewerService revService;

	@DisplayName("Create Reviewer Controller API Test")
	@ParameterizedTest(name = "\"{0}\" represents the username of the Admin authenticated")
	@CsvSource({ "nullUser", "admin" })
	void testNewReviewerForm(String adminUsername) {

		if (adminUsername.contentEquals("nullUser")) {
			ModelAndView mv = this.actorController.createReviewer();
			assertEquals("redirect:/", mv.getViewName());
		} else {
			this.authenticate(adminUsername);
			ModelAndView mv = this.actorController.createReviewer();
			assertEquals("actor/edit", mv.getViewName());
		}
	}

	@DisplayName("Save Reviewer Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the status of the operation to be performed")
	@CsvSource({ "negative", "positive" })
	void saveReviewerTest(String status) throws Exception {

		this.authenticate("admin");
		Reviewer rev = this.revService.create();
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		if (status.contentEquals("negative")) {
			rev.setEmail("");
			rev.getUser().setUsername("");
			rev.getUser().setPassword("");
			rev.getUser().setEnabled(true);

			bindingResult.reject("email", "El email debe seguir buen formato y no estar vacio");
			bindingResult.reject("user.username",
					"El username no puede estar vacio y debe estar entre 5 y 32 caracteres");
			bindingResult.reject("user.password",
					"La password no puede estar vacio y debe estar entre 5 y 32 caracteres");
			ModelAndView mv = this.reviewerController.save(rev, bindingResult);
			assertEquals("actor/edit", mv.getViewName());
		} else {
			rev.setEmail("reviewer5@gmail.com");
			rev.getUser().setUsername("reviewer5");
			rev.getUser().setPassword("reviewer5");
			rev.getUser().setEnabled(true);
			ModelAndView mv = this.reviewerController.save(rev, bindingResult);
			assertEquals("redirect:/", mv.getViewName());
		}
	}

}
