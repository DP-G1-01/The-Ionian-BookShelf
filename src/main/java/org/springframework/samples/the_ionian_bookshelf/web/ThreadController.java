package org.springframework.samples.the_ionian_bookshelf.web;

import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.MessageService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.samples.the_ionian_bookshelf.service.VoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ThreadController {

	private final ThreadService threadService;

	private final MessageService messageService;

	private final VoteService voteService;

	private final AdministratorService administratorService;

	private final SummonerService summonerService;

	@Autowired
	public ThreadController(final ThreadService threadService, final MessageService messageService,
			final VoteService voteService, final AdministratorService administratorService,
			SummonerService summonerService) {
		this.threadService = threadService;
		this.messageService = messageService;
		this.voteService = voteService;
		this.administratorService = administratorService;
		this.summonerService = summonerService;
	}

	// Listado de threads
	@GetMapping(value = "/threads")
	public String showThreadList(ModelMap modelMap) {

		try {
			Summoner summ = this.summonerService.findByPrincipal();
			if (summ.getBanned() == true) {
				return "redirect:/banned";
			}
		} catch (AssertionError e) {
		} catch (NoSuchElementException e) {
		}
		
		String vista = "threads/listadoThreads";
		Iterable<Thread> threads = this.threadService.findAll();
		for (Thread thread : threads) {
			thread.setPunctuation(voteService.getPuntuationThread(thread));
		}
		modelMap.addAttribute("threads", threads);
		return vista;
	}

	// Listado de Mensajes
	@GetMapping(value = "threads/{threadId}")
	public String showMessageListFromThread(ModelMap map, @PathVariable("threadId") int threadId) {
		try {
			Summoner summ = this.summonerService.findByPrincipal();
			if (summ.getBanned() == true) {
				return "redirect:/banned";
			}
		} catch (AssertionError e) {
		} catch (NoSuchElementException e) {
		}
		
		String vista = "messages/listadoMessages";
		Thread thread = this.threadService.findOne(threadId);
		Iterable<Message> messages = this.messageService.findByThread(thread);
		for (Message message : messages) {
			message.setPunctuation(voteService.getPuntuationMessage(message));
		}
		map.addAttribute("messages", messages);
		map.addAttribute("threadId", threadId);
		map.addAttribute("title", thread.getTitle());
		return vista;
	}

	// Creacion de thread
	@GetMapping(value = "/threads/new")
	public String createThread(ModelMap modelMap) {
		try {
			Summoner summ = this.summonerService.findByPrincipal();
			if (summ.getBanned() == true) {
				return "redirect:/banned";
			}
		} catch (AssertionError e) {
		} catch (NoSuchElementException e) {
		}
		String vista = "threads/createThread";
		Thread thread = this.threadService.create();
		modelMap.addAttribute("thread", thread);
		return vista;
	}

	@PostMapping(value = "/threads/save")
	public String saveThread(@Valid Thread thread, BindingResult result, ModelMap modelMap) {
		try {
			Summoner summ = this.summonerService.findByPrincipal();
			if (summ.getBanned() == true) {
				return "redirect:/banned";
			}
		} catch (AssertionError e) {
		} catch (NoSuchElementException e) {
		}
		
		if (result.hasErrors()) {
			modelMap.addAttribute("thread", thread);
			return "threads/createThread";
		} else {
			this.threadService.save(thread);
			modelMap.addAttribute("message", "Thread saved successfully");
		}
		return "redirect:/threads";
	}

	// Delete de Thread
	@GetMapping(value = "/threads/{threadId}/remove")
	public String deleteThread(@PathVariable("threadId") int threadId, ModelMap modelMap) {
		try {
			this.administratorService.findByPrincipal();
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		} catch (NoSuchElementException e) {
			modelMap.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		}
		Thread thread = threadService.findOne(threadId);
		if (thread != null) {
			try {
				this.threadService.deleteFromMessages(thread);
				this.threadService.delete(thread);
			} catch (AssertionError e) {
				modelMap.addAttribute("message", e.getMessage());
				return "/threads/error";
			}
			modelMap.addAttribute("message", "Thread deleted successfully");
		} else {
			modelMap.addAttribute("message", "Thread not found");
		}
		return "redirect:/threads";
	}

}
