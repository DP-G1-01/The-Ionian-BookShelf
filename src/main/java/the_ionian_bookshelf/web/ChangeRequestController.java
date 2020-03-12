package the_ionian_bookshelf.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import the_ionian_bookshelf.model.Champion;
import the_ionian_bookshelf.model.ChangeRequest;
import the_ionian_bookshelf.model.Item;
import the_ionian_bookshelf.service.ChangeRequestService;

@Controller
public class ChangeRequestController {

	private final ChangeRequestService changeRequestService;

	@Autowired
	public ChangeRequestController(ChangeRequestService changeRequestService) {
		this.changeRequestService = changeRequestService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	//**********CHAMPION**********//
	/**
	 * Called before each and every @GetMapping or @PostMapping annotated method. 2
	 * goals: - Make sure we always have fresh data - Since we do not use the
	 * session scope, make sure that Champion object always has an id (Even though
	 * id is not part of the form fields)
	 * 
	 * @param championId
	 * @return Champion
	 */
	@ModelAttribute("changeRequest")
	public ChangeRequest loadChampionWithChangeRequest(@PathVariable("championId") int championId) {
		Champion champion = this.changeRequestService.findChampionById(championId);
		ChangeRequest changeRequest = new ChangeRequest();
		champion.addChangeRequest(changeRequest);
		return changeRequest;
	}

	// Spring MVC calls method loadChampionWithChangeRequest(...) before
	// initNewChangeRequestForm is called
	@GetMapping(value = "/champions/{championId}/changeRequests/new")
	public String initNewChampionChangeRequestForm(@PathVariable("championId") int championId,
			Map<String, Object> model) {
		return "champions/createOrUpdateChangeRequestForm";
	}

	// Spring MVC calls method loadChampionWithChangeRequest(...) before
	// processNewChangeRequestForm is called
	@PostMapping(value = "/champions/{championId}/changeRequests/new")
	public String processNewChampionChangeRequestForm(@Valid ChangeRequest changeRequest, BindingResult result) {
		if (result.hasErrors()) {
			return "champions/createOrUpdateChangeRequestForm";
		} else {
			this.changeRequestService.saveChangeRequest(changeRequest);
			return "redirect:/champions/{championId}";
		}
	}

	@GetMapping(value = "/champions/{championId}/changeRequests")
	public String showChampionChangeRequests(@PathVariable int championId, Map<String, Object> model) {
		model.put("changeRequests", this.changeRequestService.findChampionById(championId).getChangeRequests());
		return "changeRequestList";
	}

	//**********ITEM**********//
	@ModelAttribute("changeRequest")
	public ChangeRequest loadItemWithChangeRequest(@PathVariable("itemId") int itemId) {
		Item item = this.changeRequestService.findItemById(itemId);
		ChangeRequest changeRequest = new ChangeRequest();
		item.addChangeRequest(changeRequest);
		return changeRequest;
	}

	// Spring MVC calls method loadItemWithChangeRequest(...) before
	// initNewChangeRequestForm is called
	@GetMapping(value = "/items/{itemId}/changeRequests/new")
	public String initNewItemChangeRequestForm(@PathVariable("itemId") int itemId, Map<String, Object> model) {
		return "items/createOrUpdateChangeRequestForm";
	}

	// Spring MVC calls method loadItemWithChangeRequest(...) before
	// processNewChangeRequestForm is called
	@PostMapping(value = "/items/{itemId}/changeRequests/new")
	public String processNewItemChangeRequestForm(@Valid ChangeRequest changeRequest, BindingResult result) {
		if (result.hasErrors()) {
			return "items/createOrUpdateChangeRequestForm";
		} else {
			this.changeRequestService.saveChangeRequest(changeRequest);
			return "redirect:/items/{itemId}";
		}
	}

	@GetMapping(value = "/items/{itemId}/changeRequests")
	public String showItemChangeRequests(@PathVariable int itemId, Map<String, Object> model) {
		model.put("changeRequests", this.changeRequestService.findItemById(itemId).getChangeRequests());
		return "changeRequestList";
	}

}
