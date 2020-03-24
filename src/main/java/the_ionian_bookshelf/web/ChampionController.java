package the_ionian_bookshelf.web;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import the_ionian_bookshelf.model.Champion;
import the_ionian_bookshelf.model.Role;
import the_ionian_bookshelf.service.ChampionService;
import the_ionian_bookshelf.service.RoleService;

@Controller
public class ChampionController {
	
	@Autowired
	private final ChampionService championService;
	
	@Autowired
	private final RoleService roleService;
	
	@Autowired
	public ChampionController(ChampionService championService, RoleService roleService) {
		this.championService = championService;
		this.roleService = roleService;
	}

	
	
	//lista de campeones
	@GetMapping(value = "/champions")
	public String listadoCampeones(ModelMap modelMap) {
		String vista = "/champions/listadoCampeones";
		Collection<Champion> champions = championService.findAll();
		modelMap.addAttribute("champions", champions );
		return vista;
	}
	
	//Intento de hacer lo mismo que Pet con PetType
		@ModelAttribute("role")
		public Collection<Role> populateRole() {
			return this.roleService.findAll();
		}
	
	//Creacion de una runa
	@GetMapping(value="/champions/new")
	public String crearCampeon(ModelMap modelMap) {
		String view="champions/editCampeon";
		modelMap.addAttribute("champion", new Champion());
		return view;
	}
	
	@PostMapping(value="champions/save")
	public String salvarCampeon(@Valid Champion champion, BindingResult result, ModelMap model) {
		if(champion.getRole()==null) {
			model.addAttribute("champion", champion);
			return "champions/editCampeon";
		}
		else if(result.hasErrors()) {
			model.addAttribute("champion", champion);
			return "champions/editCampeon";
		}else {
			championService.save(champion);
			model.addAttribute("message","Champion save successfully");
		}
		
		return "redirect:/champions/";
	}
	
	//Remove
	@GetMapping(value="/champions/{championId}/remove")
	public String borrarChampion(@PathVariable("championId") int championId, ModelMap modelMap) {
		Champion champion = championService.findChampionById(championId);
		if(champion!=null) {
			championService.deleteChampion(champion);
			modelMap.addAttribute("message","Champion delete successfully");
		}else {
			modelMap.addAttribute("message","Champion not found");
		}
		
		return "redirect:/champions/";
		
	}

}
