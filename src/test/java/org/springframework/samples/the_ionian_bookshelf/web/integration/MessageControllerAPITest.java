package org.springframework.samples.the_ionian_bookshelf.web.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collections;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.service.MessageService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.samples.the_ionian_bookshelf.utilities.AbstractTest;
import org.springframework.samples.the_ionian_bookshelf.web.MessageController;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerAPITest extends AbstractTest {

	@Autowired
	private MessageController messageController;
	@Autowired
	private MessageService messageService;
	@Autowired
	private SummonerService summonerService;
	@Autowired
	private ThreadService threadService;

	@DisplayName("Create Message Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the id of the Thread for which we are creating a message")
	@CsvSource({ "0", "100" })
	@WithMockUser(value = "RAIMUNDOKARATE98", authorities = "summoner")
	void testNewMessageForm(String threadId) {

		ModelMap model = new ModelMap();
		Integer threadIdInteger = Integer.parseInt(threadId);

		if (threadIdInteger == 0) {
			String view = messageController.createMessage(model, threadIdInteger);
			assertEquals(view, "redirect:/");
		} else if (threadIdInteger == 100) {
			String view = messageController.createMessage(model, threadIdInteger);
			assertEquals(view, "messages/createMessage");
			assertNotNull(model.get("message"));
		}
	}

	@DisplayName("Save Message Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the output of the Test")
	@CsvSource({ "No text and no threadId, No text, 0", "No threadId and some text, Some text to put here, 0",
			"No text and a threadId, No text, 100", "Some text and a threadId, Some text to put here, 100" })
	@WithMockUser(value = "RAIMUNDOKARATE98", authorities = "summoner")
	void testSaveMessageForm(String status, String text, String threadId) throws Exception {

		ModelMap model = new ModelMap();
		Integer threadIdInteger = Integer.parseInt(threadId);
		Summoner summ = this.summonerService.findOne(5);
		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);

		if (text.equals("No text")) {
			if (threadIdInteger == 0) {
				Message message = new Message("", moment, summ, null, null);
				BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
				bindingResult.reject("text", "El texto no puede estar vacio");
				bindingResult.reject("text", "El tamaño del texto debe ser de entre 10 y 500 caracteres");
				bindingResult.reject("thread", "El mensaje no está asociado a un Thread");
				String view = this.messageController.saveMessage(message, bindingResult, model);
				assertEquals(view, "messages/createMessage");
			} else if (threadIdInteger == 100) {
				Thread thread = this.threadService.findOne(100);
				Message message = new Message("", moment, summ, thread, null);
				BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
				bindingResult.reject("text", "El texto no puede estar vacio");
				bindingResult.reject("text", "El tamaño del texto debe ser de entre 10 y 500 caracteres");
				String view = this.messageController.saveMessage(message, bindingResult, model);
				assertEquals(view, "messages/createMessage");
			}
		} else {
			if (threadIdInteger == 0) {
				Message message = new Message(text, moment, summ, null, null);
				BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
				bindingResult.reject("thread", "El mensaje no está asociado a un Thread");
				String view = this.messageController.saveMessage(message, bindingResult, model);
				assertEquals(view, "messages/createMessage");
			} else if (threadIdInteger == 100) {
				Thread thread = this.threadService.findOne(100);
				Message message = new Message(text, moment, summ, thread, null);
				BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
				String view = this.messageController.saveMessage(message, bindingResult, model);
				assertEquals(view, "redirect:/threads/{threadId}");
			}
		}
	}

	@DisplayName("Delete Message Controller Test")
	@ParameterizedTest(name = "\"{0}\" represents the Message id to delete")
	@CsvSource({ "null", "1", "100" })
	@WithMockUser(value = "RAIMUNDOKARATE98", authorities = "administrator")
	void testDeleteMessage(String id) throws NumberFormatException, Exception {

		ModelMap model = new ModelMap();

		if (id.equals("null")) {
			String view = this.messageController.deleteThread(0, model);
			assertEquals(view, "redirect:/");
		} else {
			if (id.equals("1")) {
				Integer messageIdInteger = Integer.parseInt(id);
				String view = this.messageController.deleteThread(messageIdInteger, model);
				assertEquals(view, "redirect:/");
			} else {
				this.authenticate("admin");
				Integer messageIdInteger = Integer.parseInt(id);
				String view = this.messageController.deleteThread(messageIdInteger, model);
				assertEquals(view, "redirect:/threads/{threadId}");
			}
		}
	}

}
