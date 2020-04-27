package org.springframework.samples.the_ionian_bookshelf.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.MessageService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.samples.the_ionian_bookshelf.service.VoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VoteController {

	private VoteService voteService;
	
	@Autowired
	public VoteController(final VoteService voteService) {
		this.voteService = voteService;
	}
	
	@GetMapping("/threads/{threadId}/upVote")
	public String upVoteThread(ModelMap map, @PathVariable("threadId") int threadId) {
		
		try {
			voteService.createUpVoteByThreadId(threadId);	
		} catch (AssertionError e) {
			return "/threads/error";
		}
		return "redirect:/threads";
	}
	
	@GetMapping("/threads/{threadId}/downVote")
	public String downThread(ModelMap map, @PathVariable("threadId") int threadId) {
		
		try {
			voteService.createDownVoteByThreadId(threadId);	
		} catch (AssertionError e) {
			return "/threads/error";
		}
		return "redirect:/threads";
	}
}
