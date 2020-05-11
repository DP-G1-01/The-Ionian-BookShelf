package org.springframework.samples.the_ionian_bookshelf.web.e2e;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.service.MessageService;
import org.springframework.samples.the_ionian_bookshelf.service.VoteService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-mysql.properties")
public class VoteControllerE2ETest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private VoteService voteService;

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
