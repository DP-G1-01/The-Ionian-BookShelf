package the_ionian_bookshelf.web;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import the_ionian_bookshelf.model.Message;
import the_ionian_bookshelf.model.Summoner;
import the_ionian_bookshelf.model.Thread;
import the_ionian_bookshelf.service.MessageService;
import the_ionian_bookshelf.service.SummonerService;
import the_ionian_bookshelf.service.ThreadService;

@Controller
public class MessageController {
	
	@Autowired
	private final SummonerService summonerService;
	@Autowired
	private final MessageService messageService;
	@Autowired
	private final ThreadService threadService;
	
	@Autowired
	private MessageController (MessageService messageService, ThreadService threadService, SummonerService summonerService) {
		this.messageService = messageService;
		this.threadService = threadService;
		this.summonerService = summonerService;
	}
	
	
	
	//Creacion de mensajes
	@GetMapping(value = "threads/{threadId}/messages/new")
	public String createMessage(ModelMap modelMap,@PathVariable("threadId") int threadId) {
		String vista = "messages/createMessage";
		Message message = new Message();
		LocalDateTime moment = LocalDateTime.now();
		message.setMoment(moment);
		Thread thread = this.threadService.findOne(threadId);
		message.setThread(thread);
		Summoner summoner = this.summonerService.findOneSummonerById(1);
		message.setSummoner(summoner);
		modelMap.addAttribute("message", message);
		modelMap.addAttribute("threadId",threadId);
		return vista;
	}
	
	@PostMapping(value = "threads/{threadId}/messages/save")
	public String saveMessage(@Valid Message message,@PathParam(value="threadId") int threadId, BindingResult result, ModelMap modelMap) {
		System.out.println(message.getMoment());
		if(result.hasErrors()) {
			modelMap.addAttribute("message", message);
			return "messages/createMessage";
		}else {
			this.messageService.saveMessage(message);
			modelMap.addAttribute("msg", "Message saved successfully");
		}
		return "redirect://threads/{threadId}/messages";
	}
	
	//Delete de Mensajes
	@GetMapping(value="threads/{threadId}/messages/{messageId}/remove")
	public String deleteThread(@PathVariable("messageId") int messageId, ModelMap modelMap) {
		Message mess = messageService.findOneMesageById(messageId);
		if(mess != null) {
			this.messageService.delete(mess);
			modelMap.addAttribute("msg", "Thread deleted successfully");
		}else {
			modelMap.addAttribute("msg", "Thread not found");
		}
		return "redirect:/threads/{threadId}/messages";
	}
}
