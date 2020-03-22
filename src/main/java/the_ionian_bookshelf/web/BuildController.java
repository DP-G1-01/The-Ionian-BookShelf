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

import the_ionian_bookshelf.model.Build;
import the_ionian_bookshelf.model.Item;
import the_ionian_bookshelf.service.BuildService;

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
	public String showBuild(@PathVariable("itemId") int ownerId, ModelMap modelmap) {
		modelmap.addAttribute(this.buildService.findBuildById(ownerId));
		return "builds/editBuild";
	}
	
	@ModelAttribute("items")
	public Collection<Item> populateItems() {
		return this.buildService.findItems();
	}
	//Creacion de una runa
	@GetMapping(value="/builds/new")
	public String crearBuild(ModelMap modelMap) {
		String view="builds/editBuild";
		modelMap.addAttribute("build", new Build());
		return view;
	}
	
	@PostMapping(value="builds/save")
	public String salvarRuna(@Valid Build build, BindingResult result, ModelMap model) {
	
		if(result.hasErrors()) {
			model.addAttribute("build", build);
			return "builds/editBuild";
		}else {
			buildService.saveBuild(build);
			model.addAttribute("message","Build save successfully");
		}
		
		return "redirect:/builds/";
	}
	
	
	@GetMapping(value = "/builds/{buildId}/remove")
	public String removeBuild(@PathVariable("buildId") int buildId, ModelMap modelmap) {
		buildService.removeBuildById(buildId);
		return "redirect:/builds";
	}
}
