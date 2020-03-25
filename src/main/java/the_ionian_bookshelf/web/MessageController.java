package the_ionian_bookshelf.web;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import the_ionian_bookshelf.model.Message;
import the_ionian_bookshelf.service.MessageService;
import the_ionian_bookshelf.service.SummonerService;
import the_ionian_bookshelf.service.ThreadService;

@Controller
public class MessageController {

	@Autowired
	private final MessageService messageService;

	@Autowired
	private MessageController(MessageService messageService, ThreadService threadService,
			SummonerService summonerService) {
		this.messageService = messageService;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")));
			}

			public String getAsText() throws IllegalArgumentException {
				return DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm").format((LocalDateTime) getValue());
			}
		});
	}

	// Creacion de mensajes
	@GetMapping(value = "threads/{threadId}/messages/new")
	public String createMessage(ModelMap modelMap, @PathVariable("threadId") int threadId) {
		String vista = "messages/createMessage";
		Message message = this.messageService.create(threadId);
		modelMap.addAttribute("message", message);
		return vista;
	}

	@PostMapping(value = "threads/{threadId}/messages/save")
	public String saveMessage(@Valid Message message, BindingResult result, ModelMap modelMap) {
		if (result.hasErrors()) {
			System.out.println(result);
			modelMap.addAttribute("message", message);
			return "messages/createMessage";
		} else {
			this.messageService.saveMessage(message);
			modelMap.addAttribute("msg", "Message saved successfully");
		}
		return "redirect:/threads/{threadId}";
	}

	// Delete de Mensajes
	@GetMapping(value = "threads/{threadId}/messages/{messageId}/remove")
	public String deleteThread(@PathVariable("messageId") int messageId, ModelMap modelMap) {
		Message mess = messageService.findOneMesageById(messageId);
		if (mess != null) {
			this.messageService.delete(mess);
			modelMap.addAttribute("msg", "Thread deleted successfully");
		} else {
			modelMap.addAttribute("msg", "Thread not found");
		}
		return "redirect:/threads/{threadId}";
	}
}
