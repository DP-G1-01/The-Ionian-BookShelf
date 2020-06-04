package org.springframework.samples.the_ionian_bookshelf.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.the_ionian_bookshelf.configuration.SecurityConfiguration;
import org.springframework.samples.the_ionian_bookshelf.model.Reviewer;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.AuthoritiesService;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.service.LeagueService;
import org.springframework.samples.the_ionian_bookshelf.service.ReviewerService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest(controllers = { ReviewerController.class,
		ActorController.class }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ReviewerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReviewerService reviewerService;

	private Reviewer reviewerMock = mock(Reviewer.class);

	@MockBean
	private SummonerService summonerService;

	@MockBean
	private AdministratorService adminService;

	@MockBean
	private ChampionService champService;

	@MockBean
	private LeagueService leagueService;

	@MockBean
	private AuthoritiesService authService;

	@DisplayName("Create Reviewer Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the username of the Admin authenticated")
	@CsvSource({ "nullUser", "admin" })
	@WithMockUser(value = "admin", authorities = "administrator")
	void createReviewerTest(String username) throws Exception {

		if (username.contentEquals("nullUser")) {

			when(this.reviewerService.create()).thenThrow(AssertionError.class);
			mockMvc.perform(get("/actor/administrator/createReviewer")).andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/"));

		} else {

			when(this.reviewerService.create()).thenReturn(reviewerMock);
			mockMvc.perform(get("/actor/administrator/createReviewer")).andExpect(status().is2xxSuccessful())
					.andExpect(view().name("actor/edit"));

		}
	}

	@DisplayName("Save Reviewer Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the status of the operation to be performed")
	@CsvSource({ "negative", "positive" })
	@WithMockUser(value = "admin", authorities = "administrator")
	void saveReviewerTest(String status) throws Exception {

		if (status.contentEquals("negative")) {

			when(this.reviewerService.save(reviewerMock)).thenThrow(ConstraintViolationException.class);
			mockMvc.perform(post("/reviewer/edit").with(csrf()).param("email", "").param("user.username", "")
					.param("user.password", "").param("save", "save")).andExpect(status().is2xxSuccessful())
					.andExpect(view().name("actor/edit"));

		} else {

			when(this.reviewerService.save(reviewerMock)).thenReturn(reviewerMock);
			mockMvc.perform(post("/reviewer/edit").with(csrf()).param("email", "reviewer5@gmail.com")
					.param("user.username", "reviewer5").param("user.password", "reviewer5").param("save", "save"))
					.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"));

		}
	}

}
