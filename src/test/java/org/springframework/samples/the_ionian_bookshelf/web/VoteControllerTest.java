package org.springframework.samples.the_ionian_bookshelf.web;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.security.LoginService;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.MessageService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.samples.the_ionian_bookshelf.service.VoteService;
import org.springframework.samples.the_ionian_bookshelf.utilities.AbstractTest;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest(controllers = VoteController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@TestInstance(Lifecycle.PER_CLASS)
public class VoteControllerTest extends AbstractTest {

	private static final int THREAD_ID = 100;
	private static final int MESSAGE_ID = 100;
	private static final int BUILD_ID = 100;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VoteService voteService;

	private Thread threadMock = mock(Thread.class);
	private Message messageMock = mock(Message.class);
	private Build buildMock = mock(Build.class);
	private Summoner summonerMock = mock(Summoner.class);

	@MockBean
	private ThreadService threadService;

	@MockBean
	private MessageService messageService;

	@MockBean
	private SummonerService summonerService;

	@MockBean
	private LoginService loginService;

	@MockBean
	private AdministratorService adminService;

	@DisplayName("Upvote a Thread Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the case under test")
	@CsvSource({ "Not existing Thread,0", "Thread ready to upvote, 100" })
	@WithMockUser(value = "admin")
	void upvoteThreadTest(String CUT, String threadId) throws Exception {

		if (threadId.equals("0")) {
			mockMvc.perform(get("threads/{threadId}/upVote", 0)).andExpect(status().is4xxClientError());
		} else {
			mockMvc.perform(get("threads/{threadId}/upVote", 100)).andExpect(status().is4xxClientError());
		}
	}

	@DisplayName("Downvote a Thread Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the case under test")
	@CsvSource({ "Not existing Thread,0", "Thread ready to downvote, 100" })
	@WithMockUser(value = "admin")
	void downvoteThreadTest(String CUT, String threadId) throws Exception {

		if (threadId.equals("0")) {
			mockMvc.perform(get("threads/{threadId}/downVote", 0)).andExpect(status().is4xxClientError());
		} else {
			mockMvc.perform(get("threads/{threadId}/downVote", 100)).andExpect(status().is4xxClientError());
		}
	}

	@DisplayName("Upvote a Message Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the case under test")
	@CsvSource({ "Not existing Message,0", "Message ready to upvote, 100" })
	@WithMockUser(value = "admin")
	void upvoteMessageTest(String CUT, String messageId) throws Exception {

		if (messageId.equals("0")) {
			mockMvc.perform(get("threads/{threadId}/message/messageId}/upVote", 100, 0))
					.andExpect(status().is4xxClientError());
		} else {
			mockMvc.perform(get("threads/{threadId}/message/messageId}/upVote", 100, 100))
					.andExpect(status().is4xxClientError());
		}
	}

	@DisplayName("Downvote a Message Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the case under test")
	@CsvSource({ "Not existing Message,0", "Message ready to downvote, 100" })
	@WithMockUser(value = "admin")
	void downvoteMessageTest(String CUT, String messageId) throws Exception {

		if (messageId.equals("0")) {
			mockMvc.perform(get("threads/{threadId}/message/messageId}/downVote", 100, 0))
					.andExpect(status().is4xxClientError());
		} else {
			mockMvc.perform(get("threads/{threadId}/message/messageId}/downVote", 100, 100))
					.andExpect(status().is4xxClientError());
		}
	}

	@DisplayName("Upvote a Build Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the case under test")
	@CsvSource({ "Not existing Build,0", "Build ready to upvote, 100" })
	@WithMockUser(value = "admin")
	void upvoteBuildTest(String CUT, String buildId) throws Exception {

		if (buildId.equals("0")) {
			mockMvc.perform(get("build/{buildId}/upVote", 0)).andExpect(status().is4xxClientError());
		} else {
			mockMvc.perform(get("build/{buildId}/upVote", 100)).andExpect(status().is4xxClientError());
		}
	}

	@DisplayName("Downvote a Build Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the case under test")
	@CsvSource({ "Not existing Build,0", "Build ready to downvote, 100" })
	@WithMockUser(value = "admin")
	void downvoteBuildTest(String CUT, String buildId) throws Exception {

		if (buildId.equals("0")) {
			mockMvc.perform(get("build/{buildId}/downVote", 0)).andExpect(status().is4xxClientError());
		} else {
			mockMvc.perform(get("build/{buildId}/downVote", 100)).andExpect(status().is4xxClientError());
		}
	}
}
