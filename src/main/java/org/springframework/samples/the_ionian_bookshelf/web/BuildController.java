package org.springframework.samples.the_ionian_bookshelf.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.RunePage;
import org.springframework.samples.the_ionian_bookshelf.service.BuildService;
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

	BuildService buildService;
	
	@Autowired
	public BuildController(BuildService buildService) {
		this.buildService = buildService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/builds")
	public String processFindForm(Item Build, BindingResult result, Model model) {
		
		Collection<Build> results = this.buildService.findAll();
			model.addAttribute("builds",results);
			return "builds/buildsList";
		}
	
	@GetMapping("/builds/{buildId}")
	public String showBuild(@PathVariable("buildId") int buildId, ModelMap modelmap) {
		Build build = this.buildService.findBuildById(buildId);
		String items = build.getItems().toString().replace("[", "").replace("]", "");
		System.out.println(items);
		modelmap.addAttribute("build", build);
		modelmap.addAttribute("sitems", items);
		return "builds/editBuild";
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
		build.setItems(var);
		modelMap.addAttribute("build", build);
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
		
		return "redirect:/builds";
	}
	
	
	@GetMapping(value = "/builds/{buildId}/remove")
	public String removeBuild(@PathVariable("buildId") int buildId, ModelMap modelmap) {
		buildService.removeBuildById(buildId);
		return "redirect:/builds";
	}
}
