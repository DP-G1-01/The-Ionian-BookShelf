package org.springframework.samples.the_ionian_bookshelf.web;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.samples.the_ionian_bookshelf.service.ChangeRequestService;
import org.springframework.samples.the_ionian_bookshelf.service.ReviewerService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.validators.ChangeRequestValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChangeRequestController {
	
	private final ChangeRequestService changeRequestService;
	
	private final SummonerService summonerService;
	
	private final ReviewerService reviewerService;

	@InitBinder("request")
	public void initChangeRequestBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ChangeRequestValidator());
	}
	
//	@Autowired
//    ChangeRequestValidator validator;
	
	@Autowired
	public ChangeRequestController(ChangeRequestService changeRequestService, SummonerService summonerService, ReviewerService reviewerService) {
		this.changeRequestService = changeRequestService;
		this.summonerService = summonerService;
		this.reviewerService = reviewerService;
	}
	
	@GetMapping(value = "/requests")
	public String listadoAllRequests(ModelMap modelMap) {
		try {
			this.reviewerService.findByPrincipal();
		} catch (NoSuchElementException u) {
			modelMap.addAttribute("message", "You must be logged in as a reviewer");
			return "redirect:/login";
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as a reviewer");
			return "redirect:/";
		}
		
		String vista = "requests/listadoRequests";
		Iterable<ChangeRequest> requests = changeRequestService.findAll();
		modelMap.addAttribute("requests", requests);
		return vista;
	}
	
	@GetMapping(value = "/mine/requests")
	public String listadoMineRequests(ModelMap modelMap) {
		try {
			this.summonerService.findByPrincipal();
		} catch (NoSuchElementException u) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/login";
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/";
		}
		
		String vista = "requests/listadoRequests";
		Iterable<ChangeRequest> requests = changeRequestService.findMine(this.summonerService.findByPrincipal().getId());
		modelMap.addAttribute("requests", requests);
		return vista;
	}
	
	@GetMapping("/requests/{requestId}")
	public String showRequest(@PathVariable("requestId") int requestId, ModelMap modelMap) {
	
		try {
			this.reviewerService.findByPrincipal();
		} catch (NoSuchElementException u) {
			modelMap.addAttribute("message", "You must be logged in as a reviewer");
			return "redirect:/login";
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as a reviewer");
			return "redirect:/";
		}
		modelMap.addAttribute("request", this.changeRequestService.findOne(requestId));
		return "requests/createRequest";
	}
	
	@GetMapping("/mine/requests/{requestId}")
	public String showMineRequest(@PathVariable("requestId") int requestId, ModelMap modelMap) {
		try {
			this.summonerService.findByPrincipal();
		} catch (NoSuchElementException u) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/login";
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/";
		}
		ChangeRequest request = this.changeRequestService.findOne(requestId);
		String view = "";
		if(summonerService.findByPrincipal().getId() == request.getSummoner().getId()) {
			view = "requests/createRequest";
			modelMap.addAttribute("request", request);
		} else {
			view = "redirect:/";
			modelMap.addAttribute("message", "You must be logged in as the summoner who create the request");
		}
		return view;
	}
	
	@GetMapping(value="/champions/{championId}/newChangeRequest")
	public String crearChangeRequestChampion(@PathVariable("championId") int championId, ModelMap modelMap) {
		try {
			this.summonerService.findByPrincipal();
		} catch (NoSuchElementException u) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/login";
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/";
		}
		String view="requests/createRequest";
		ChangeRequest request = new ChangeRequest();
		request.setStatus("PENDING");
		List<String> var = new ArrayList<>();
		request.setChangeChamp(var);
		Integer summonerId = this.summonerService.findByPrincipal().getId();
		modelMap.addAttribute("request", request);
		modelMap.addAttribute("championId", championId);
		modelMap.addAttribute("summonerId", summonerId);
		return view;
	}
	
	@GetMapping(value="/items/{itemId}/newChangeRequest")
	public String crearChangeRequestItem(@PathVariable("itemId") int itemId, ModelMap modelMap) {
		try {
			this.summonerService.findByPrincipal();
		} catch (NoSuchElementException u) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/login";
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/";
		}
		String view="requests/createRequest";
		ChangeRequest request = new ChangeRequest();
		request.setStatus("PENDING");
		List<String> var = new ArrayList<>();
		request.setChangeChamp(var);
		Integer summonerId = this.summonerService.findByPrincipal().getId();
		modelMap.addAttribute("request", request);
		modelMap.addAttribute("itemId", itemId);
		modelMap.addAttribute("summonerId", summonerId);
		return view;
	}
	
	@PostMapping(value="requests/saveChangeRequest")
	public String guardarChangeRequest(@ModelAttribute("request") @Valid ChangeRequest request, BindingResult result, ModelMap model) {
		
		try {
			this.summonerService.findByPrincipal();
		} catch (NoSuchElementException u) {
			model.addAttribute("message", "You must be logged in as summoner");
			return "redirect:/login";
		} catch (AssertionError e) {
			model.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/";
		}
		
		if(result.hasErrors()) {
			result.getAllErrors().stream().forEach(x->System.out.println(x.toString()));
			if(request.getChampion() != null) {
				model.addAttribute("championId", request.getChampion().getId());
			} else {
				model.addAttribute("itemId", request.getItem().getId());
			}
			model.addAttribute("request", request);
			Integer summonerId = this.summonerService.findByPrincipal().getId();
//			model.addAttribute("itemId", itemId);
			model.addAttribute("summonerId", summonerId);
			return "requests/createRequest";
		}else {

//			if (request.getItem() != null) {
//				for (int i = 0; i < request.getChangeItem().size(); i++) {
//					Integer valorNuevo = Integer.parseInt(request.getChangeItem().get(i));
//					Integer valorViejo = Integer.parseInt(request.getItem().getAttributes().get(i));
//
//					if (valorNuevo.compareTo(valorViejo) >= 10) {
//
//					}
//				}
//			} else {
//				Champion campeon = request.getChampion();
//				List<Double> nuevosValores = request.getChangeChamp().stream().map(x -> Double.parseDouble(x))
//						.collect(Collectors.toList());
//
//				if (campeon.getHealth().compareTo(nuevosValores.get(0)) >= 10) {
//
//				} else if (campeon.getMana() != null && campeon.getMana().compareTo(nuevosValores.get(1)) >= 10) {
//
//				} else if (campeon.getEnergy() != null && campeon.getEnergy().compareTo(nuevosValores.get(2)) >= 10) {
//
//				} else if (campeon.getAttack().compareTo(nuevosValores.get(3)) >= 10) {
//
//				} else if (campeon.getSpeed().compareTo(nuevosValores.get(4)) >= 10) {
//
//				} else if (request.getChangeChamp().get(1) != null && request.getChangeChamp().get(2) != null) {
//					
//				}
//			}
			
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
	public String removeChangeRequest(@PathVariable("requestId") int requestId, ModelMap modelMap) {
		
		try {
			this.reviewerService.findByPrincipal();
		} catch (NoSuchElementException u) {
			modelMap.addAttribute("message", "You must be logged in as a reviewer");
			return "redirect:/login";
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as a reviewer");
			return "redirect:/";
		}
		ChangeRequest request = changeRequestService.findOne(requestId);
		changeRequestService.delete(request);
		
		return "redirect:/requests";
	}
	
}
