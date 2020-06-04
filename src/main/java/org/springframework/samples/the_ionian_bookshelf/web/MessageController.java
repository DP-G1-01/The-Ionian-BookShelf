package org.springframework.samples.the_ionian_bookshelf.web;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.service.MessageService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageController {

	private final MessageService messageService;

	private final SummonerService summonerService;

	@Autowired
	private MessageController(MessageService messageService, ThreadService threadService,
			SummonerService summonerService) {
		this.messageService = messageService;
		this.summonerService = summonerService;
	}

	// Creacion de mensajes
	@GetMapping(value = "threads/{threadId}/messages/new")
	public String createMessage(ModelMap modelMap, @PathVariable("threadId") int threadId) {
		try {
			Summoner summ = this.summonerService.findByPrincipal();
			if (summ.getBanned() == true) {
				return "redirect:/banned";
			}
		} catch (AssertionError e) {
		} catch (NoSuchElementException e) {
		}

		String vista = "messages/createMessage";
		try {
			Message message = this.messageService.create(threadId);
			modelMap.addAttribute("message", message);
		} catch (Exception oups) {
			return "redirect:/";
		} catch (AssertionError oups) {
			return "redirect:/";
		}
		return vista;
	}

	@PostMapping(value = "threads/{threadId}/messages/save")
	public String saveMessage(@Valid Message message, BindingResult result, ModelMap modelMap) {
		if (result.hasErrors()) {
			modelMap.addAttribute("message", message);
			return "messages/createMessage";
		} else {
			try {
				this.messageService.saveMessage(message);
				modelMap.addAttribute("msg", "Message saved successfully");
			} catch (Exception oups) {
				return "redirect:/";
			} catch (AssertionError oups) {
				return "redirect:/";
			}
		}
		return "redirect:/threads/{threadId}";
	}

	// Delete de Mensajes
	@GetMapping(value = "threads/{threadId}/messages/{messageId}/remove")
	public String deleteThread(@PathVariable("messageId") int messageId, ModelMap modelMap) {

		try {
			Message mess = messageService.findOneMesageById(messageId);
			if (mess != null) {
				this.messageService.delete(mess);
				modelMap.addAttribute("msg", "Thread deleted successfully");
			} else {
				modelMap.addAttribute("msg", "Thread not found");
			}
		} catch (Throwable oups) {
			return "redirect:/";
		}
		return "redirect:/threads/{threadId}";
	}
}
