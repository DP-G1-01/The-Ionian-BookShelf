package the_ionian_bookshelf.web;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import the_ionian_bookshelf.model.Branch;
import the_ionian_bookshelf.model.Rune;
import the_ionian_bookshelf.model.RunePage;
import the_ionian_bookshelf.service.RunePageService;

@Controller
public class RunePageController {
	
	@Autowired
	private final RunePageService runePageService;
	
	@Autowired
	public RunePageController(RunePageService runePageService) {
		this.runePageService = runePageService;
	}
	
	@ModelAttribute("branches")
	public Collection<Branch> populateBranches() {
		return this.runePageService.findBranches();
	}
	@ModelAttribute("runes")
	public List<List<Rune>> populateRunesByBranchNode() {
		return this.runePageService.findRunesByBranchNode();
	}
	
	//lista de páginas de runas
	@GetMapping(value = "/runePages/mine")
	public String listadoPaginasRunas(ModelMap modelMap) {
		String vista = "runePages/listadoPaginasRunas";
		Iterable<RunePage> runePages = this.runePageService.findAllMine();
		modelMap.addAttribute("runePages", runePages);
		return vista;
	}
	
	
	//Creacion de una pagina de runas
	@GetMapping(value="/runePages/new")
	public String crearPaginaRuna(ModelMap modelMap) {
		String view="runePages/editRunePage";
		modelMap.addAttribute("runePage", new RunePage());
		
		return view;
	}
	
	@PostMapping(value="runePages/save")
	public String salvarRuna(@Valid RunePage runePage, BindingResult result, ModelMap model) {
		if(runePage.getKeyRune()==null ||runePage.getMainRune1()==null ||runePage.getMainRune2()==null ||
				runePage.getMainRune3()==null ||runePage.getSecRune1()==null ||runePage.getSecRune2()==null) {
			model.addAttribute("runePage", runePage);
			return "runePages/editRunePage";
		}
		else if(result.hasErrors()) {
			model.addAttribute("runePage", runePage);
			return "runePages/editRunePage";
		}else {
			this.runePageService.save(runePage);
			model.addAttribute("message","Rune Page save successfully");
		}
		
		return "redirect:/runePages/";
	}
	
	//Remove
	@GetMapping(value="/runePages/{runePageId}/remove")
	public String borrarPaginaRunas(@PathVariable("runePageId") int runePageId, ModelMap modelMap) {
		RunePage runePage = runePageService.findOne(runePageId);
		if(runePage!=null) {
			this.runePageService.delete(runePage);
			modelMap.addAttribute("message","Rune Page delete successfully");
		}else {
			modelMap.addAttribute("message","Rune Page not found");
		}
		
		return "redirect:/runePages/mine";
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
