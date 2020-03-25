package org.springframework.samples.the_ionian_bookshelf.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Message;
import org.springframework.samples.the_ionian_bookshelf.model.Thread;
import org.springframework.samples.the_ionian_bookshelf.service.MessageService;
import org.springframework.samples.the_ionian_bookshelf.service.ThreadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ThreadController {

    @Autowired
    private final ThreadService threadService;
    @Autowired
    private final MessageService messageService;

    @Autowired
    public ThreadController(final ThreadService threadService, final MessageService messageService) {
        this.threadService = threadService;
        this.messageService = messageService;
    }

    // @InitBinder
    // public void setAllowedFields(WebDataBinder dataBinder) {
    // dataBinder.setDisallowedFields("id");
    // }

    // Listado de threads
    @GetMapping(value = "/threads")
    public String showThreadList(ModelMap modelMap) {
        String vista = "threads/listadoThreads";
        Iterable<Thread> threads = this.threadService.findAll();
        modelMap.addAttribute("threads", threads);
        return vista;
    }

    // Listado de Mensajes
    @GetMapping(value = "threads/{threadId}")
    public String showMessageListFromThread(ModelMap map, @PathVariable("threadId") int threadId) {
        String vista = "messages/listadoMessages";
        Thread thread = this.threadService.findOne(threadId);
        Iterable<Message> messages = this.messageService.findByThread(thread);
        map.addAttribute("messages", messages);
        map.addAttribute("threadId", threadId);
        map.addAttribute("title", thread.getTitle());
        return vista;
    }

    // Creacion de thread
    @GetMapping(value = "/threads/new")
    public String createThread(ModelMap modelMap) {
        String vista = "threads/createThread";
        Thread thread = this.threadService.create();
        modelMap.addAttribute("thread", thread);
        return vista;
    }

    @PostMapping(value = "/threads/save")
    public String saveThread(@Valid Thread thread, BindingResult result, ModelMap modelMap) {
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
        Thread thread = threadService.findOne(threadId);
        if (thread != null) {
            this.threadService.deleteFromMessages(thread);
            this.threadService.delete(thread);

            modelMap.addAttribute("message", "Thread deleted successfully");
        } else {
            modelMap.addAttribute("message", "Thread not found");
        }
        return "redirect:/threads";
    }
}
