package the_ionian_bookshelf.web;

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

import the_ionian_bookshelf.model.Thread;
import the_ionian_bookshelf.service.ThreadService;


@Controller
public class ThreadController {
	
	@Autowired
	private final ThreadService threadService;
	
	
	@Autowired
	public ThreadController (final ThreadService threadService) {
		this.threadService = threadService;
	}
	
//	@InitBinder
//	public void setAllowedFields(WebDataBinder dataBinder) {
//		dataBinder.setDisallowedFields("id");
//	}
	
	//Listado de threads
	@GetMapping(value = "/threads")
	public String showThreadList(ModelMap modelMap) {
		String vista = "threads/listadoThreads";
		Iterable<Thread> threads = this.threadService.findAll();
		modelMap.addAttribute("threads",threads);
		return vista;
	}
	
	//Creacion de thread
	@GetMapping(value = "/threads/new")
	public String createThread(ModelMap modelMap) {
		String vista = "threads/createThread";
		modelMap.addAttribute("thread", new Thread());
		return vista;
	}
	
	@PostMapping(value = "/threads/save")
	public String saveThread(@Valid Thread thread, BindingResult result, ModelMap modelMap) {
		if(result.hasErrors()) {
			modelMap.addAttribute("thread", thread);
			return "threads/createThread";
		}else {
			this.threadService.save(thread);
			modelMap.addAttribute("message", "Thread saved successfully");
		}
		return "redirect:/threads";
	}
	
	//Delete de Thread SIN MENSAJES
	@GetMapping(value="/threads/{threadId}/remove")
	public String deleteThread(@PathVariable("threadId") int threadId, ModelMap modelMap) {
		Thread thread = threadService.findOne(threadId);
		if(thread != null) {
			threadService.delete(thread);
			modelMap.addAttribute("message", "Thread deleted successfully");
		}else {
			modelMap.addAttribute("message", "Thread not found");
		}
		return "redirect:/threads";
	}
}
