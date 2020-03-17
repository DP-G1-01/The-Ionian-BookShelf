package the_ionian_bookshelf.web;


import java.util.Collection;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import the_ionian_bookshelf.model.Item;
import the_ionian_bookshelf.model.Role;
import the_ionian_bookshelf.service.ItemService;

@Controller
public class ItemController {

	ItemService itemService;
	
	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
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
	//Creacion de una runa
	@GetMapping(value="/items/new")
	public String crearItem(ModelMap modelMap) {
		String view="items/editItem";
		modelMap.addAttribute("item", new Item());
		return view;
	}
	
	@PostMapping(value="items/save")
	public String salvarRuna(@Valid Item item, BindingResult result, ModelMap model) {
	
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
		itemService.removeItemById(itemId);
		return "redirect:/items";
	}
	}
 
