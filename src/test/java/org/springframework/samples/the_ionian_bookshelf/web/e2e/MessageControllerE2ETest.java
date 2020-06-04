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
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-mysql.properties")
public class MessageControllerE2ETest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private MessageService messageService;

	@DisplayName("Create Message Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the id of the Thread for which we are creating a message")
	@CsvSource({ "0", "100" })
	@WithMockUser(value = "RAIMUNDOKARATE98", authorities = "summoner")
	void createMessageTest(String threadId) throws Exception {

		Integer threadIdInteger = Integer.parseInt(threadId);
		if (threadIdInteger == 0) {
			mockMvc.perform(get("/threads/{threadId}/messages/new", "0")).andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/banned"));
		} else if (threadIdInteger == 100) {
			mockMvc.perform(get("/threads/{threadId}/messages/new", "100")).andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/banned"));
		}
	}

	@DisplayName("Save Message Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the output of the Test")
	@CsvSource({ "No text and no threadId, No text, 0", "No threadId and some text, Some text to put here, 0",
			"No text and a threadId, No text, 100", "Some text and a threadId, Some text to put here, 100" })
	@WithMockUser(value = "RAIMUNDOKARATE98", authorities = "summoner")
	void saveMessageTest(String status, String text, String threadId) throws Exception {

		Integer threadIdInteger = Integer.parseInt(threadId);
		if (text.equals("No text")) {
			if (threadIdInteger == 0) {
				mockMvc.perform(post("/threads/{threadId}/messages/save", 0).with(csrf()).param("text", "")
						.param("moment", "2000/10/10 10:10").param("summoner", "5"))
						.andExpect(status().is2xxSuccessful());
			} else if (threadIdInteger == 100) {
				mockMvc.perform(post("/threads/{threadId}/messages/save", 100).with(csrf()).param("text", "")
						.param("moment", "2000/10/10 10:10").param("summoner", "5"))
						.andExpect(status().is2xxSuccessful());
			}
		} else {
			if (threadIdInteger == 0) {
				mockMvc.perform(post("/threads/{threadId}/messages/save", 0).with(csrf()).param("text", text)
						.param("moment", "2000/10/10 10:10").param("summoner", "5"))
						.andExpect(status().is2xxSuccessful());
			} else if (threadIdInteger == 100) {
				mockMvc.perform(post("/threads/{threadId}/messages/save", 100).with(csrf()).param("text", text)
						.param("moment", "2000/10/10 10:10").param("summoner", "5"))
						.andExpect(status().is2xxSuccessful());
			}
		}
	}

	@DisplayName("Delete Message Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the Message id to delete")
	@CsvSource({ "null", "1", "100" })
	@WithMockUser(value = "RAIMUNDOKARATE98")
	void deleteMessageTest(String id) throws NumberFormatException, Exception {

		if (id.equals("null")) {
			mockMvc.perform(get("/threads/{threadId}/messages/{messageId}/remove", 100, 0))
					.andExpect(view().name("redirect:/"));
		} else {
			if (id.equals("1")) {
				mockMvc.perform(get("/threads/{threadId}/messages/{messageId}/remove", 100, Integer.parseInt(id)))
						.andExpect(view().name("redirect:/"));
			} else {
				mockMvc.perform(get("/threads/{threadId}/messages/{messageId}/remove", 100, Integer.parseInt(id)))
						.andExpect(status().is3xxRedirection());
			}
		}
	}

}
