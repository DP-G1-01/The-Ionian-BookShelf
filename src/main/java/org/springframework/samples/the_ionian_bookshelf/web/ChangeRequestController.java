package org.springframework.samples.the_ionian_bookshelf.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.service.ChangeRequestService;
import org.springframework.samples.the_ionian_bookshelf.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChangeRequestController {
	
	@Autowired
	private final ChangeRequestService changeRequestService;
	
	@Autowired
	private final ChampionService championService;
	
	@Autowired
	private final ItemService itemService;

	@Autowired
	public ChangeRequestController(ChangeRequestService changeRequestService, ChampionService championService, ItemService itemService) {
		this.changeRequestService = changeRequestService;
		this.championService = championService;
		this.itemService = itemService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/requests")
	public String listadoRunas(ModelMap modelMap) {
		String vista = "requests/listadoRequests";
		Iterable<ChangeRequest> requests = changeRequestService.findAll();
		modelMap.addAttribute("requests", requests);
		return vista;
	}
	
	@GetMapping("/requests/{requestId}")
	public String showRequest(@PathVariable("requestId") int requestId, ModelMap modelmap) {
		modelmap.addAttribute("request", this.changeRequestService.findOne(requestId));
		return "requests/createRequest";
	}
	
	@GetMapping(value="/champions/{championId}/newChangeRequest")
	public String crearChangeRequestChampion(@PathVariable("championId") int championId, ModelMap modelMap) {
		String view="requests/createRequest";
		ChangeRequest request = new ChangeRequest();
		request.setStatus("PENDING");
		List<String> var = new ArrayList<>();
		request.setChangeChamp(var);
		modelMap.addAttribute("request", request);
		modelMap.addAttribute("championId", championId);
		return view;
	}
	
	@GetMapping(value="/items/{itemId}/newChangeRequest")
	public String crearChangeRequestItem(@PathVariable("itemId") int itemId, ModelMap modelMap) {
		String view="requests/createRequest";
		ChangeRequest request = new ChangeRequest();
		request.setStatus("PENDING");
		List<String> var = new ArrayList<>();
		request.setChangeChamp(var);
		modelMap.addAttribute("request", request);
		modelMap.addAttribute("itemId", itemId);
		return view;
	}
	
	@PostMapping(value="requests/saveChangeRequest")
	public String guardarChangeRequest(@Valid ChangeRequest request, BindingResult result, ModelMap model) {
	
		if(result.hasErrors()) {
			if(request.getChampion() != null) {
				model.addAttribute("championId", request.getChampion().getId());
			} else {
				model.addAttribute("itemId", request.getItem().getId());
			}
			model.addAttribute("request", request);
			return "requests/createRequest";
		}else {
			
			if(request.getItem() != null) {
				for(int i = 0; i<request.getChangeItem().size(); i++) {
					Integer valorNuevo = Integer.parseInt(request.getChangeItem().get(i));
					Integer valorViejo = Integer.parseInt(request.getItem().getAttributes().get(i));
					
					if(valorNuevo.compareTo(valorViejo) >= 10) {
						
					}
				}
			} else {
				Champion campeon = request.getChampion();
				List<Double> nuevosValores = request.getChangeChamp().stream().map(x-> Double.parseDouble(x)).collect(Collectors.toList());
				
				if(campeon.getHealth().compareTo(nuevosValores.get(0)) >= 10) {
					
				} else if(campeon.getMana() != null && campeon.getMana().compareTo(nuevosValores.get(1)) >= 10) {
					
				} else if(campeon.getEnergy() != null && campeon.getEnergy().compareTo(nuevosValores.get(2)) >= 10) {
					
				} else if(campeon.getAttack().compareTo(nuevosValores.get(3)) >= 10) {
					
				} else if(campeon.getSpeed().compareTo(nuevosValores.get(4)) >= 10) {
					
				}
			}
			
			changeRequestService.save(request);
			model.addAttribute("message","Change Request save successfully");
		}
		
		return "redirect:/requests";
	}
	
	@GetMapping(value="/requests/{requestId}/accept")
	public String aceptarChangeRequest(@PathVariable("requestId") int requestId, ModelMap modelMap) {
		ChangeRequest request = changeRequestService.findOne(requestId);
		request.setStatus("ACCEPTED");
		changeRequestService.resolve(request);
		modelMap.addAttribute("message","Request accepted successfully");
		
		return "redirect:/requests";
	}
	
	@GetMapping(value="/requests/{requestId}/reject")
	public String rechazarChangeRequest(@PathVariable("requestId") int requestId, ModelMap modelMap) {
		ChangeRequest request = changeRequestService.findOne(requestId);
		request.setStatus("REJECTED");
		changeRequestService.resolve(request);
		modelMap.addAttribute("message","Request rejected successfully");
		
		return "redirect:/requests";
	}
	
	@GetMapping(value = "/requests/{requestId}/remove")
	public String removeChangeRequest(@PathVariable("requestId") int requestId, ModelMap modelmap) {
		ChangeRequest request = changeRequestService.findOne(requestId);
		changeRequestService.delete(request);
		
		return "redirect:/requests";
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
//	@ModelAttribute("changeRequest")
//	public ChangeRequest loadChampionWithChangeRequest(@PathVariable("championId") int championId) {
//		Champion champion = this.changeRequestService.findChampionById(championId);
//		ChangeRequest changeRequest = new ChangeRequest();
//		//champion.addChangeRequest(changeRequest);
//		return changeRequest;
//	}
//
//	// Spring MVC calls method loadChampionWithChangeRequest(...) before
//	// initNewChangeRequestForm is called
//	@GetMapping(value = "/champions/{championId}/changeRequests/new")
//	public String initNewChampionChangeRequestForm(@PathVariable("championId") int championId,
//			Map<String, Object> model) {
//		return "champions/createOrUpdateChangeRequestForm";
//	}
//
//	// Spring MVC calls method loadChampionWithChangeRequest(...) before
//	// processNewChangeRequestForm is called
//	@PostMapping(value = "/champions/{championId}/changeRequests/new")
//	public String processNewChampionChangeRequestForm(@Valid ChangeRequest changeRequest, BindingResult result) {
//		if (result.hasErrors()) {
//			return "champions/createOrUpdateChangeRequestForm";
//		} else {
//			this.changeRequestService.saveChangeRequest(changeRequest);
//			return "redirect:/champions/{championId}";
//		}
//	}
//
//	@GetMapping(value = "/champions/{championId}/changeRequests")
//	public String showChampionChangeRequests(@PathVariable int championId, Map<String, Object> model) {
//		//model.put("changeRequests", this.changeRequestService.findChampionById(championId).getChangeRequests());
//		return "changeRequestList";
//	}
//
//	//**********ITEM**********//
//	@ModelAttribute("changeRequest")
//	public ChangeRequest loadItemWithChangeRequest(@PathVariable("itemId") int itemId) {
//		Item item = this.changeRequestService.findItemById(itemId);
//		ChangeRequest changeRequest = new ChangeRequest();
//		item.addChangeRequest(changeRequest);
//		return changeRequest;
//	}
//
//	// Spring MVC calls method loadItemWithChangeRequest(...) before
//	// initNewChangeRequestForm is called
//	@GetMapping(value = "/items/{itemId}/changeRequests/new")
//	public String initNewItemChangeRequestForm(@PathVariable("itemId") int itemId, Map<String, Object> model) {
//		return "items/createOrUpdateChangeRequestForm";
//	}
//
//	// Spring MVC calls method loadItemWithChangeRequest(...) before
//	// processNewChangeRequestForm is called
//	@PostMapping(value = "/items/{itemId}/changeRequests/new")
//	public String processNewItemChangeRequestForm(@Valid ChangeRequest changeRequest, BindingResult result) {
//		if (result.hasErrors()) {
//			return "items/createOrUpdateChangeRequestForm";
//		} else {
//			this.changeRequestService.saveChangeRequest(changeRequest);
//			return "redirect:/items/{itemId}";
//		}
//	}
//
//	@GetMapping(value = "/items/{itemId}/changeRequests")
//	public String showItemChangeRequests(@PathVariable int itemId, Map<String, Object> model) {
//		model.put("changeRequests", this.changeRequestService.findItemById(itemId).getChangeRequests());
//		return "changeRequestList";
//	}
//
}
