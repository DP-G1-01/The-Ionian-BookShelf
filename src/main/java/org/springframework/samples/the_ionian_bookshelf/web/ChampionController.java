package org.springframework.samples.the_ionian_bookshelf.web;

import java.util.Collection;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.validators.ChampionValidator;
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
public class ChampionController {

	@Autowired
	private final ChampionService championService;

	@Autowired
	private final AdministratorService administratorService;

	@InitBinder("champion")
	public void initChampionBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new ChampionValidator());
	}

	@Autowired
	public ChampionController(ChampionService championService, AdministratorService administratorService) {
		this.championService = championService;
		this.administratorService = administratorService;
		
	}

	// lista de campeones
	@GetMapping(value = "/champions")
	public String listadoCampeones(ModelMap modelMap) {
		String vista = "/champions/listadoCampeones";
		Collection<Champion> champions = championService.findAll();
		modelMap.addAttribute("champions", champions);
		return vista;
	}

	// Intento de hacer lo mismo que Pet con PetType
	@ModelAttribute("role")
	public Collection<Role> populateRole() {
		return this.championService.findRoles();
	}

	// Creacion de una runa
	@GetMapping(value = "/champions/new")
	public String crearCampeon(ModelMap modelMap) {
		// TRY-CATCH PARA ADMINISTRADOR (En el doc no se habla nada de reviewer)
		try {
			this.administratorService.findByPrincipal();
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		} catch (NoSuchElementException e) {
			modelMap.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		}
		String view = "champions/editCampeon";
		modelMap.addAttribute("champion", new Champion());
		return view;
	}

	@PostMapping(value = "champions/save")
	public String salvarCampeon(@Valid Champion champion, BindingResult result, ModelMap model) {
		// TRY-CATCH PARA ADMINISTRADOR (En el doc no se habla nada de reviewer)
		try {
			this.administratorService.findByPrincipal();
		} catch (AssertionError e) {
			model.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		} catch (NoSuchElementException e) {
			model.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		}
		if (result.hasErrors()) {
			model.addAttribute("champion", champion);
			return "champions/editCampeon";
		} else {
			championService.save(champion);
			model.addAttribute("message", "Champion save successfully");
		}

		return "redirect:/champions/";
	}

	// Remove
	@GetMapping(value = "/champions/{championId}/remove")
	public String borrarChampion(@PathVariable("championId") int championId, ModelMap modelMap) {
		// TRY-CATCH PARA ADMINISTRADOR (En el doc no se habla nada de reviewer)
		try {
			this.administratorService.findByPrincipal();
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		} catch (NoSuchElementException e) {
			modelMap.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		}
		Champion champion = championService.findChampionById(championId);
		if (champion != null) {
			championService.deleteChampion(champion);
			modelMap.addAttribute("message", "Champion delete successfully");
		} else {
			modelMap.addAttribute("message", "Champion not found");
		}

		return "redirect:/champions/";

	}

	// Update
	@GetMapping(value = "/champions/{championId}/edit")
	public String initUpdateChampionForm(@PathVariable("championId") int championId, ModelMap model) {
		try {
			this.administratorService.findByPrincipal();
		} catch (AssertionError e) {
			model.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		} catch (NoSuchElementException e) {
			model.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		}
		Champion champion = this.championService.findChampionById(championId);
		model.addAttribute(champion);
		return "champions/editCampeon";
	}

	@PostMapping(value = "/champions/{championId}/edit")
	public String processUpdateChampionForm(@Valid Champion champion, BindingResult result,
			@PathVariable("championId") int championId) {
		if (result.hasErrors()) {
			return "redirect:/champions/editCampeon";
		} else {
			champion.setId(championId);
			this.championService.save(champion);
			return "redirect:/champions/";
		}
	}

}
