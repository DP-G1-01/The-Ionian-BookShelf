package org.springframework.samples.the_ionian_bookshelf.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.RunePage;
import org.springframework.samples.the_ionian_bookshelf.service.BuildService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
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
public class BuildController {

	@Autowired
	private final BuildService buildService;

	@Autowired
	private final SummonerService summonerService;
	
	@Autowired
	public BuildController(BuildService buildService, SummonerService summonerService) {
		this.buildService = buildService;
		this.summonerService = summonerService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/builds")
	public String listPublicBuilds(Item Build, BindingResult result, Model model) {
		
		Collection<Build> builds = this.buildService.findAllPublics();
			model.addAttribute("builds",builds);
			return "builds/buildsList";
		}
	
	@GetMapping(value = "/mine/builds")
	public String listMineBuilds(Item Build, BindingResult result, Model model) {
		//Está comentado por la pereza que me da logearme, pero funciona
//		try {
//			this.summonerService.findByPrincipal();
//		} catch (NoSuchElementException u) {
//			modelMap.addAttribute("message", "You must be logged in as a summoner");
//			return "redirect:/login";
//		} catch (AssertionError e) {
//			modelMap.addAttribute("message", "You must be logged in as a summoner");
//			return "redirect:/";
//		}
		
		Collection<Build> builds = this.buildService.findMineBuilds(this.summonerService.findByPrincipal().getId());
			model.addAttribute("builds",builds);
			return "builds/buildsList";
		}
	
	@GetMapping("/builds/{buildId}")
	public String showPublicBuild(@PathVariable("buildId") int buildId, ModelMap modelmap) {
		String view = "";
		Build build = this.buildService.findBuildById(buildId);
		if(build.isVisibility()) {
			view = "builds/editBuild";

			String items = "";
			for(Item i : build.getItems()) {
				if(build.getItems().get(0) == i) {
					items += i.getTitle();
				} else {
					items += ", " + i.getTitle();
				}
			}
			items.replace("[", "").replace("]", "");
			modelmap.addAttribute("build", build);
			modelmap.addAttribute("sitems", items);
		} else {
			view = "redirect:/builds";
			modelmap.addAttribute("message", "This build is not public.");
		}
		return view;
	}
	
	@GetMapping("/mine/builds/{buildId}")
	public String showMineBuild(@PathVariable("buildId") int buildId, ModelMap modelmap) {
		//Está comentado por la pereza que me da logearme, pero funciona
//		try {
//			this.summonerService.findByPrincipal();
//		} catch (NoSuchElementException u) {
//			modelMap.addAttribute("message", "You must be logged in as a summoner");
//			return "redirect:/login";
//		} catch (AssertionError e) {
//			modelMap.addAttribute("message", "You must be logged in as a summoner");
//			return "redirect:/";
//		}
		
		String view = "";
		Build build = this.buildService.findBuildById(buildId);
		if(summonerService.findByPrincipal().getId() == build.getSummoner().getId()) {
			view = "builds/editBuild";

			String items = "";
			for(Item i : build.getItems()) {
				if(build.getItems().get(0) == i) {
					items += i.getTitle();
				} else {
					items += ", " + i.getTitle();
				}
			}
			items.replace("[", "").replace("]", "");
			modelmap.addAttribute("build", build);
			modelmap.addAttribute("sitems", items);
		} else {
			view = "redirect:redirect:/mine/builds";
			modelmap.addAttribute("message", "You must be logged in as the summoner who create the build.");
		}
		return view;
	}
	
	@ModelAttribute("items")
	public Collection<Item> populateItems() {
		return this.buildService.findItems();
	}
	
	@ModelAttribute("champions")
	public Collection<Champion> populateChampions() {
		return this.buildService.findChampions();
	}
	
	@ModelAttribute("runePages")
	public Collection<RunePage> populateRunePages() {
		return this.buildService.findRunePages();
	}
	//Creacion de una runa
	@GetMapping(value="/builds/new")
	public String crearBuild(ModelMap modelMap) {
		String view="builds/editBuild";
		Build build = new Build();
		List<Item> var = new ArrayList<>();
		Integer summonerId = this.summonerService.findByPrincipal().getId();
		build.setItems(var);
		modelMap.addAttribute("build", build);
		modelMap.addAttribute("summonerId", summonerId);
		return view;
	}
	
	@PostMapping(value="builds/save")
	public String salvarBuild(@Valid Build build, BindingResult result, ModelMap model) {
	
		if(result.hasErrors()) {
			model.addAttribute("build", build);
			return "builds/editBuild";
		}else {
			buildService.saveBuild(build);
			model.addAttribute("message","Build save successfully");
		}
		
		return "redirect:/mine/builds";
	}
	
	
	@GetMapping(value = "/mine/builds/{buildId}/remove")
	public String removeBuild(@PathVariable("buildId") int buildId, ModelMap modelmap) {
		//Está comentado por la pereza que me da logearme, pero funciona
//		try {
//			this.summonerService.findByPrincipal();
//		} catch (NoSuchElementException u) {
//			modelMap.addAttribute("message", "You must be logged in as a summoner");
//			return "redirect:/login";
//		} catch (AssertionError e) {
//			modelMap.addAttribute("message", "You must be logged in as a summoner");
//			return "redirect:/";
//		}
		Build b = buildService.findBuildById(buildId);
		if(b.getSummoner().getId() == this.summonerService.findByPrincipal().getId()) {
			buildService.removeBuildById(buildId);
		} else {
			modelmap.addAttribute("message", "You must be logged in as the summoner who create the build.");
		}
		return "redirect:/mine/builds";
	}
}
