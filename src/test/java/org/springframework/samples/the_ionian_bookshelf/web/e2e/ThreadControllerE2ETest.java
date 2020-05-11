package org.springframework.samples.the_ionian_bookshelf.web.e2e;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-mysql.properties")
public class ThreadControllerE2ETest {
	
	private static final int THREAD_ID = 1;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ThreadService threadService;
	
	@WithMockUser(username="admin",authorities= {"admin"})
	@Test
	void testShowThreadListSuccess() throws Exception {
		mockMvc.perform(get("/threads")).andExpect(status().isOk()).andExpect(model().attributeExists("threads"))
				.andExpect(view().name("threads/listadoThreads"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testShowThreadMessagesListSuccess() throws Exception {
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

	
	@WithMockUser(username="admin",authorities= {"admin"})
	@Test
	void testDeleteThreadSuccess() throws Exception {
		Thread thread = new Thread("Titulo", "Descripcion de thread", null);
		this.threadService.save(thread);
		mockMvc.perform(get("/threads/{threadId}/remove", thread.getId())).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/threads"));
	}

	@WithMockUser(username="admin",authorities= {"admin"})
	@Test
	void testDeleteThreadLeagueError() throws Exception {
		mockMvc.perform(get("/threads/{threadId}/remove", THREAD_ID)).andExpect(status().is2xxSuccessful())
				.andExpect(view().name("/threads/error"));
	}
	
	@WithMockUser(value = "RAIMUNDOKARATE98")
	@Test
	void testDeleteThreadWithoutLoginAsAdmin() throws Exception {
		mockMvc.perform(get("/threads/{threadId}/remove", THREAD_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteThreadWithNotLoggedUser() throws Exception {
		mockMvc.perform(get("/threads/{threadId}/remove", THREAD_ID)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/login"));
	}

}
