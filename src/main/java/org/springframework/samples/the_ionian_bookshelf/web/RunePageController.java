package org.springframework.samples.the_ionian_bookshelf.web;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.Rune;
import org.springframework.samples.the_ionian_bookshelf.model.RunePage;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.service.RunePageService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.samples.the_ionian_bookshelf.validators.RunePageValidator;
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
public class RunePageController {

	@Autowired
	private final RunePageService runePageService;

	@Autowired
	private final SummonerService summonerService;

	@Autowired
	public RunePageController(RunePageService runePageService, SummonerService summonerService) {
		this.runePageService = runePageService;
		this.summonerService = summonerService;
	}

	@InitBinder("runePage")
	public void initRunePageBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new RunePageValidator());
	}
	
	@ModelAttribute("branches")
	public Collection<Branch> populateBranches() {
		return this.runePageService.findBranches();
	}

	@ModelAttribute("runes")
	public List<List<Rune>> populateRunesByBranchNode() {
		return this.runePageService.findRunesByBranchNode();
	}

	@ModelAttribute("secondaryRunes")
	public List<List<Rune>> populateSecondaryRunesByBranchNode() {
		return this.runePageService.findSecondaryRunesByBranch();
	}

	// lista de páginas de runas
	@GetMapping(value = "/runePages/mine")
	public String listadoPaginasRunas(ModelMap modelMap) {
		try {
			this.summonerService.findByPrincipal();
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/login";
		} catch (NoSuchElementException u) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/login";
		}
		String vista = "runePages/listadoPaginasRunas";
		Collection<RunePage> runePages = this.runePageService.findAllMine();
		modelMap.addAttribute("runePages", runePages);
		return vista;
	}

	// Creacion de una pagina de runas
	@GetMapping(value = "/runePages/new")
	public String crearPaginaRuna(ModelMap modelMap) {
		try {
			this.summonerService.findByPrincipal();
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/login";
		} catch (NoSuchElementException u) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/login";
		}
		String view = "runePages/editRunePage";
		RunePage newRunePage = this.runePageService.create();
		modelMap.addAttribute("runePage", newRunePage);

		return view;
	}

	@PostMapping(value = "runePages/save")
	public String salvarPaginaRuna(@Valid RunePage runePage, BindingResult result, ModelMap model) {
		try {
			this.summonerService.findByPrincipal();
		} catch (AssertionError e) {
			model.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/login";
		} catch (NoSuchElementException u) {
			model.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/login";
		}
		if (result.hasErrors()) {
			model.addAttribute("runePage", runePage);
			return "runePages/editRunePage";
		} else {
			this.runePageService.save(runePage);
			model.addAttribute("message", "Rune Page save successfully");
		}

		return "redirect:/runePages/mine";
	}

	// Remove
	@GetMapping(value = "/runePages/{runePageId}/remove")
	public String borrarPaginaRunas(@PathVariable("runePageId") int runePageId, ModelMap modelMap) {
		try {
			this.summonerService.findByPrincipal();
		} catch (AssertionError e) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/login";
		} catch (NoSuchElementException u) {
			modelMap.addAttribute("message", "You must be logged in as a summoner");
			return "redirect:/login";
		}
		
		Summoner principal = this.summonerService.findByPrincipal();
		RunePage runePage = runePageService.findOne(runePageId);
		if (runePage != null && runePage.getSummoner().equals(principal)) {
			this.runePageService.delete(runePage);
			modelMap.addAttribute("message", "Rune Page delete successfully");
		} else if (runePage == null) {
			modelMap.addAttribute("message", "Rune Page not found");
		} else {
			modelMap.addAttribute("message", "This Rune Page doesn't belong to you");
		}
		return "redirect:/runePages/mine";
	}
	
	@GetMapping(value = "/runePages/{runePageId}/edit")
	public String initUpdateRuneForm(@PathVariable("runePageId") int runePageId, Model model) {
		String view = "redirect:/login";
		try {
			this.summonerService.findByPrincipal();
		} catch (AssertionError e) {
			model.addAttribute("message", "You must be logged in as a summoner");
			return view;
		} catch (NoSuchElementException u) {
			model.addAttribute("message", "You must be logged in as a summoner");
			return view;
		}
		RunePage runePage = this.runePageService.findOne(runePageId);
		Summoner summoner = this.summonerService.findByPrincipal();
		
		if(runePage!=null && runePage.getSummoner().equals(summoner)) {
			model.addAttribute(runePage);
			view = "runePages/editRunePage";
		}else if(runePage == null) {
			model.addAttribute("message", "Rune Page not found");
		}else {
			model.addAttribute("message", "This Rune Page doesn't belong to you");
			view = "redirect:/runePages/mine";
		}
		return view;
	}
	@PostMapping(value = "/runePages/{runePageId}/edit")
	public String processUpdateRunePageForm(@Valid RunePage runePage, BindingResult result, @PathVariable("runePageId") int runePageId) {
		
		if (result.hasErrors()) {
			return "runePages/editRunePage";
		}
		else {
			runePage.setId(runePageId);
			this.runePageService.save(runePage);
			return "redirect:/runePages/mine";
		}
	}
}
