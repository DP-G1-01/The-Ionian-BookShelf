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
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.security.LoginService;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.MessageService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.samples.the_ionian_bookshelf.utilities.AbstractTest;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest(controllers = MessageController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
@TestInstance(Lifecycle.PER_CLASS)
public class MessageControllerTest extends AbstractTest {

	private static final int THREAD_ID = 100;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MessageService messageService;

	private Thread threadMock = mock(Thread.class);
	private Message messageMock = mock(Message.class);
	private Summoner summonerMock = mock(Summoner.class);

	@MockBean
	private ThreadService threadService;

	@MockBean
	private SummonerService summonerService;

	@MockBean
	private LoginService loginService;

	@MockBean
	private AdministratorService adminService;

//	@BeforeEach
//	void setup() {
//
//		Thread thread = new Thread("Titulo", "Descripcion de thread");
//		User user = new User();
//		user.setUsername("testMessage");
//		user.setPassword("test");
//		Summoner summoner = new Summoner();
//		summoner.setUser(user);
//		summoner.setEmail("test@gmail.com");
//		Date moment = new Date(System.currentTimeMillis() - 1);
//		Message message = new Message("NUEVO MENSAJE DE THREAD PARA TEST", moment, summoner, thread);
//		Message badSaveTest = new Message("badText", moment, summoner, thread);
//		Message positiveSaveTest = new Message("PositiveText", moment, summoner, thread);
//		List<Message> messages = new ArrayList<Message>();
//		messages.add(message);
//		messages.add(badSaveTest);
//		messages.add(positiveSaveTest);
//	}

	@DisplayName("Create Message Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the username of the Summoner authenticated")
	@CsvSource({ "nullUser,null", "nullUser,100", "RAIMUNDOKARATE98,null", "RAIMUNDOKARATE98,100" })
	@WithMockUser(value = "RAIMUNDOKARATE98")
	void createMessageTest(String username, String threadId) throws Exception {

		if (username.equals("nullUser")) {

			if (threadId.equals("null")) {
				when(this.messageService.create(0)).thenThrow(AssertionError.class);
				mockMvc.perform(get("/threads/{threadId}/messages/new", "0")).andExpect(status().is2xxSuccessful())
						.andExpect(view().name("exception"));
			} else {
				when(this.messageService.create(100)).thenThrow(AssertionError.class);
				mockMvc.perform(get("/threads/{threadId}/messages/new", threadId))
						.andExpect(status().is2xxSuccessful());
			}
		} else {
			if (threadId.equals("null")) {
				when(this.messageService.create(0)).thenThrow(AssertionError.class);
				mockMvc.perform(get("/threads/{threadId}/messages/new", "0")).andExpect(status().is2xxSuccessful());
			} else {
				when(this.messageService.create(100)).thenReturn(mock(Message.class));
				mockMvc.perform(get("/threads/{threadId}/messages/new", Integer.parseInt(threadId)))
						.andExpect(status().is2xxSuccessful()).andExpect(view().name("exception"));
			}
		}
	}

	@DisplayName("Save Message Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the text of the message")
	@CsvSource({ "badText,nullUser", "positiveText,nullUser", "badText,RAIMUNDOKARATE98",
			"positiveText,RAIMUNDOKARATE98" })
	@WithMockUser(value = "RAIMUNDOKARATE98")
	void saveMessageTest(String text, String username) throws Exception {

		if (username.equals("nullUser")) {
			if (text.equals("badText")) {
				when(this.messageService.saveMessage(messageMock)).thenThrow(AssertionError.class);
				mockMvc.perform(post("/threads/{threadId}/messages/save", 100).with(csrf()).param("text", text)
						.param("moment", "2000/10/10 10:10").param("summoner", "5"))
						.andExpect(status().is2xxSuccessful());
			} else {
				when(this.messageService.saveMessage(messageMock)).thenThrow(AssertionError.class);
				mockMvc.perform(post("/threads/{threadId}/messages/save", 100).with(csrf()).param("text", text)
						.param("moment", "2000/10/10 10:10").param("summoner", "5"))
						.andExpect(status().is2xxSuccessful());
			}
		} else {
			if (text.equals("badText")) {
				when(this.messageService.saveMessage(messageMock)).thenThrow(AssertionError.class);
				mockMvc.perform(post("/threads/{threadId}/messages/save", 100).with(csrf()).param("text", text)
						.param("moment", "2000/10/10 10:10").param("summoner", "5"))
						.andExpect(status().is2xxSuccessful());
			} else {
				when(this.messageService.saveMessage(messageMock)).thenCallRealMethod();
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
			when(this.messageService.findOneMesageById(0)).thenThrow(AssertionError.class);
			mockMvc.perform(get("/threads/{threadId}/messages/{messageId}/remove", 100, 0))
					.andExpect(view().name("redirect:/"));
		} else {
			if (id.equals("1")) {
				when(this.messageService.findOneMesageById(Integer.parseInt(id))).thenThrow(AssertionError.class);
				mockMvc.perform(get("/threads/{threadId}/messages/{messageId}/remove", 100, Integer.parseInt(id)))
						.andExpect(view().name("redirect:/"));
			} else {
				when(this.messageService.findOneMesageById(Integer.parseInt(id))).thenCallRealMethod();
				mockMvc.perform(get("/threads/{threadId}/messages/{messageId}/remove", 100, Integer.parseInt(id)))
						.andExpect(status().is3xxRedirection());
			}
		}
	}
}