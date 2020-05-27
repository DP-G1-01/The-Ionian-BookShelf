package org.springframework.samples.the_ionian_bookshelf.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.the_ionian_bookshelf.configuration.SecurityConfiguration;
import org.springframework.samples.the_ionian_bookshelf.model.Administrator;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.model.User;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.MessageService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.samples.the_ionian_bookshelf.service.VoteService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ThreadController.class
,excludeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE
, classes = WebSecurityConfigurer.class)
, excludeAutoConfiguration = SecurityConfiguration.class)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class ThreadControllerTests {

	private static final int THREAD_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private Thread threadMock;

	@MockBean
	private ThreadService threadService;

	@MockBean
	private MessageService messageService;
	
	@MockBean
	private VoteService voteService;

	@MockBean
	private AdministratorService administratorService;
	
	@MockBean
	private SummonerService summonerService;

	@BeforeEach
	void setup() {
		Thread thread = new Thread("Titulo", "Descripcion de thread", null);
		User user = new User();
		user.setUsername("testThread");
		user.setPassword("test");
		Summoner summoner = new Summoner();
		summoner.setUser(user);
		summoner.setEmail("test@gmail.com");
		Date moment = new Date(System.currentTimeMillis() - 1);
		Message message = new Message("NUEVO MENSAJE DE THREAD PARA TEST", moment, summoner, thread, null);
		Message message2 = new Message("NUEVO MENSAJE 2 DE THREAD PARA TEST", moment, summoner, thread, null);
		Message message3 = new Message("NUEVO MENSAJE 3 DE THREAD PARA TEST", moment, summoner, thread, null);
		List<Message> messages = new ArrayList<Message>();
		messages.add(message);
		messages.add(message2);
		messages.add(message3);

		Summoner summMock = mock(Summoner.class);
		when(this.summonerService.findByPrincipal()).thenReturn(summMock);
		when(this.threadService.findOne(THREAD_ID)).thenReturn(thread);
		when(this.messageService.findByThread(threadMock)).thenReturn(messages);
	}

	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowThreadListSuccess() throws Exception {
		mockMvc.perform(get("/threads")).andExpect(status().isOk()).andExpect(model().attributeExists("threads"))
				.andExpect(view().name("threads/listadoThreads"));
	}

	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowThreadMessagesListSuccess() throws Exception {
		when(this.administratorService.findByPrincipal()).thenCallRealMethod();
		mockMvc.perform(get("/threads/{threadId}", THREAD_ID).param("title", "Titulo del hilo"))
				.andExpect(status().isOk()).andExpect(model().attributeExists("messages"))
				.andExpect(view().name("messages/listadoMessages"));
	}

	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testCreateThreadSuccess() throws Exception {
		mockMvc.perform(get("/threads/new")).andExpect(status().isOk()).andExpect(view().name("threads/createThread"));
	}

	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testSaveThreadSuccess() throws Exception {
		mockMvc.perform(post("/threads/save").with(csrf()).param("title", "HiloTest").param("description",
				"HiloDescriptionTestSuccess")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/threads"));
	}

	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testSaveThreadError() throws Exception {
		mockMvc.perform(post("/threads/save").with(csrf()).param("title", "").param("description", ""))
				.andExpect(status().isOk()).andExpect(model().attributeExists("thread"))
				.andExpect(view().name("threads/createThread"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testDeleteThreadSuccess() throws Exception {
		mockMvc.perform(get("/threads/{threadId}/remove", THREAD_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/threads"));
	}

	@WithMockUser(value = "admin")
	@Test
	void testDeleteThreadThatDoesntExistError() throws Exception {
		when(this.threadService.findOne(THREAD_ID)).thenReturn(null);
		mockMvc.perform(get("/threads/{threadId}/remove", THREAD_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/threads"));
	}

	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testDeleteThreadWithoutLoginAsAdmin() throws Exception {
		when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/threads/{threadId}/remove", THREAD_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteThreadWithNotLoggedUser() throws Exception {
		when(this.administratorService.findByPrincipal()).thenThrow(AssertionError.class);
		mockMvc.perform(get("/threads/{threadId}/remove", THREAD_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}
}
