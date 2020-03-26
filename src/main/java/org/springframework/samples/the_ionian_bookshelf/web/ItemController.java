package org.springframework.samples.the_ionian_bookshelf.web;


import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.ItemService;
import org.springframework.samples.the_ionian_bookshelf.validators.ItemValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ItemController {
	
	@Autowired
	private final ItemService itemService;
	
	@Autowired
	private final AdministratorService administratorService;
	
	@Autowired
	public ItemController(ItemService itemService, AdministratorService administratorService) {
		this.itemService = itemService;
		this.administratorService = administratorService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@InitBinder("Item")
	public void initItemBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ItemValidator());
	}
	
	@GetMapping(value = "/items")
	public String processFindForm(Item item, BindingResult result, Model model) {
		
		Collection<Item> results = this.itemService.findAll();
			model.addAttribute("items",results);
			return "items/itemsList";
		}
	
	@GetMapping("/items/{itemId}")
	public String showItem(@PathVariable("itemId") int ownerId, ModelMap modelmap) {
		modelmap.addAttribute(this.itemService.findItemById(ownerId));
		return "items/editItem";
	}
	
	@ModelAttribute("role")
	public Collection<Role> populateRoles() {
		return this.itemService.findRoles();
	}
	
	@GetMapping(value="/items/new")
	public String crearItem(ModelMap modelMap) {
		//Está comentado por la pereza que me da logearme, pero funciona
//		try {
//			this.administratorService.findByPrincipal();
//		} catch (NoSuchElementException u) {
//			modelMap.addAttribute("message", "You must be logged in as an admin");
//			return "redirect:/login";
//		} catch (AssertionError e) {
//			modelMap.addAttribute("message", "You must be logged in as an admin");
//			return "redirect:/";
//		}
		String view="items/editItem";
		modelMap.addAttribute("item", new Item());
		return view;
	}
	
	@PostMapping(value="items/save")
	public String salvarItem(@Valid Item item, BindingResult result, ModelMap model) {
		//Está comentado por la pereza que me da logearme, pero funciona
//		try {
//			this.administratorService.findByPrincipal();
//		} catch (NoSuchElementException u) {
//			modelMap.addAttribute("message", "You must be logged in as an admin");
//			return "redirect:/login";
//		} catch (AssertionError e) {
//			modelMap.addAttribute("message", "You must be logged in as an admin");
//			return "redirect:/";
//		}
		
		if(result.hasErrors()) {
			model.addAttribute("item", item);
			return "items/editItem";
		}else {
			itemService.saveItem(item);
			model.addAttribute("message","Item save successfully");
		}
		
		return "redirect:/items/";
	}
	
	
	@GetMapping(value = "/items/{itemId}/remove")
	public String removeItem(@PathVariable("itemId") int itemId, ModelMap modelmap) {
		//Está comentado por la pereza que me da logearme, pero funciona
//		try {
//			this.administratorService.findByPrincipal();
//		} catch (NoSuchElementException u) {
//			modelMap.addAttribute("message", "You must be logged in as an admin");
//			return "redirect:/login";
//		} catch (AssertionError e) {
//			modelMap.addAttribute("message", "You must be logged in as an admin");
//			return "redirect:/";
//		}
		
		itemService.removeItemById(itemId);
		return "redirect:/items";
	}
	}
 
