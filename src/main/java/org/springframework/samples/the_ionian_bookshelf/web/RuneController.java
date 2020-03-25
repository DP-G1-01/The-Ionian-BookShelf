package org.springframework.samples.the_ionian_bookshelf.web;

import java.util.Collection;
import java.util.NoSuchElementException;

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

import the_ionian_bookshelf.model.Branch;
import the_ionian_bookshelf.model.Rune;
import the_ionian_bookshelf.service.AdministratorService;
import the_ionian_bookshelf.service.RuneService;
import the_ionian_bookshelf.validators.RuneValidator;

@Controller
public class RuneController {
	
	@Autowired
	private final RuneService runeService;
	
	@Autowired
	private final AdministratorService administratorService;
	
	@InitBinder("rune")
	public void initRuneBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new RuneValidator());
	}
	
	@Autowired
	public RuneController(RuneService runeService, AdministratorService administratorService) {
		this.runeService = runeService;
		this.administratorService = administratorService;
	}

	
	
	//lista de runas
	@GetMapping(value = "/runes")
	public String listadoRunas(ModelMap modelMap) {
		String vista = "runes/listadoRunas";
		Collection<Rune> runes = runeService.findAll();
		modelMap.addAttribute("runes", runes );
		return vista;
	}
	
	//Intento de hacer lo mismo que Pet con PetType
		@ModelAttribute("branch")
		public Collection<Branch> populateBranches() {
			return this.runeService.findBranches();
		}
	
	//Creacion de una runa
	@GetMapping(value="/runes/new")	
	public String crearRuna(ModelMap modelMap) {
		try {
			this.administratorService.findByPrincipal();
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		} catch (NoSuchElementException u) {
			modelMap.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		}
		String view="runes/editRune";
		modelMap.addAttribute("rune", new Rune());
		
		return view;
	}
	
	@PostMapping(value="runes/save")
	public String salvarRuna(@Valid Rune rune, BindingResult result, ModelMap model) {
		try {
			this.administratorService.findByPrincipal();
		} catch (AssertionError e) {
			model.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		} catch (NoSuchElementException u) {
			model.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		}
		if(result.hasErrors()) {
			model.addAttribute("rune", rune);
			return "runes/editRune";
		}else {
			runeService.saveRune(rune);
			model.addAttribute("message","Rune save successfully");
		}
		
		return "redirect:/runes/";
	}
	
	//Remove
	@GetMapping(value="/runes/{runeId}/remove")
	public String borrarRuna(@PathVariable("runeId") int runeId, ModelMap modelMap) {
		try {
			this.administratorService.findByPrincipal();
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		} catch (NoSuchElementException u) {
			modelMap.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		}
		Rune runa = runeService.findRuneById(runeId);
		if(runa!=null) {
			runeService.deleteRune(runa);
			modelMap.addAttribute("message","Rune delete successfully");
		}else {
			modelMap.addAttribute("message","Rune not found");
		}
		
		return "redirect:/runes/";
		
	}
	
	//Update
	@GetMapping(value = "/runes/{runeId}/edit")
	public String initUpdateRuneForm(@PathVariable("runeId") int runeId, Model model) {
		try {
			this.administratorService.findByPrincipal();
		} catch (AssertionError e) {
			model.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		} catch (NoSuchElementException u) {
			model.addAttribute("message", "You must be logged in as an admin");
			return "redirect:/login";
		}
		Rune rune = this.runeService.findRuneById(runeId);
		model.addAttribute(rune);
		return "runes/editRune";
	}

	@PostMapping(value = "/runes/{runeId}/edit")
	public String processUpdateRuneForm(@Valid Rune rune, BindingResult result, @PathVariable("runeId") int runeId) {
		
		if (result.hasErrors()) {
			return "runes/editRune";
		}
		else {
			rune.setId(runeId);
			this.runeService.saveRune(rune);
			return "redirect:/runes/";
		}
	}
	
}
