package the_ionian_bookshelf.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import the_ionian_bookshelf.model.Item;
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
	public String processFindForm(Item item, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (item.getTitle() == null) {
			item.setTitle(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Item> results = this.itemService.findAll();
//		if (results.isEmpty()) {
//			// no owners found
//			result.rejectValue("lastName", "notFound", "not found");
//			return "owners/findOwners";
//		}
//		else if (results.size() == 1) {
//			// 1 owner found
//			owner = results.iterator().next();
//			return "redirect:/owners/" + owner.getId();
//		}
//		else {
			// multiple owners found
			model.put("items", results);
			return "items/itemsList";
		}
	
	@GetMapping("/items/{itemId}")
	public ModelAndView showOwner(@PathVariable("itemId") int ownerId) {
		ModelAndView mav = new ModelAndView("items/itemsDetails");
		mav.addObject(this.itemService.findItemById(ownerId));
		return mav;
	}
	
	@GetMapping(value = "/items/{itemId}/remove")
	public String removeOwner(@PathVariable("itemId") int itemId, Model model) {
		itemService.removeItemById(itemId);
		return "redirect:/item/itemsList";
	}
	}
 
