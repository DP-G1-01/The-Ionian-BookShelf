package the_ionian_bookshelf.web;

import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import the_ionian_bookshelf.model.Authority;
import the_ionian_bookshelf.model.Branch;
import the_ionian_bookshelf.model.Rune;
import the_ionian_bookshelf.model.UserAccount;
import the_ionian_bookshelf.service.RuneService;

@Controller
public class RuneController {
	
	@Autowired
	private final RuneService runeService;
	
	@Autowired
	public RuneController(RuneService runeService) {
		this.runeService = runeService;
	}
	/*
	//Para poner las branches en un select o algo así en el jsp
	@ModelAttribute("branches")
	public Collection<Branch> populateBranches() {
		return this.runeService.findBranches();
	}
	*/
//	@GetMapping(value = "/runes/new")
//	public String initCreationForm(UserAccount user, ModelMap model) throws AccessException {
//		assert user != null;
//		assert model != null;
//		String result = "/panic";
//		Authority auth = new Authority();
//		auth.setAuthority(Authority.ADMINISTRATOR);
//		if(!user.getAuthorities().contains(auth)) {
//			throw new AccessException("You must have administrator privileges");
//		}else {
//			Rune rune = new Rune();
//			model.put("rune", rune);
//			result = "/runes/CreateOrUpdateRuneForm";
//		}
//		return result;
//	}
	//Esto inicializaría el validador, hay que crearlo
//	@InitBinder("rune")
//	public void initPetBinder(WebDataBinder dataBinder) {
//		dataBinder.setValidator(new RuneValidator());
//	}
	
//	@PostMapping(value = "/runes/new")
//	public String proccessCreationForm(UserAccount user, @Valid Rune rune, BindingResult result,
//			ModelMap model) throws AccessException {
//		assert user != null;
//		assert model != null;
//		assert rune != null;
//		assert result != null;
//		
//		Authority auth = new Authority();
//		auth.setAuthority(Authority.ADMINISTRATOR);
//		if(!user.getAuthorities().contains(auth)) {
//			throw new AccessException("You must have administrator privileges");
//		}else {
//			if(result.hasErrors()) {
//				model.put("rune", rune);
//				return "/runes/CreateOrUpdateRuneForm";
//			}else {
//				this.runeService.saveRune(rune);
//				return "redirect:/runes/list";
//			}
//		}
//	}
//	
	
	
	//lista de runas
	@GetMapping(value = "/runes")
	public String listadoRunas(ModelMap modelMap) {
		String vista = "runes/listadoRunas";
		Iterable<Rune> runes = runeService.findAll();
		modelMap.addAttribute("runes", runes );
		return vista;
	}
	
	//Intento de hacer lo mismo que Pet con PetType
		@ModelAttribute("branch")
		public Collection<Branch> populateBranches() {
			return this.runeService.findBranchess();
		}
	
	//Creacion de una runa
	@GetMapping(value="/runes/new")
	public String crearRuna(ModelMap modelMap) {
		String view="runes/editRune";
		modelMap.addAttribute("rune", new Rune());
		
		return view;
	}
	
	@PostMapping(value="runes/save")
	public String salvarRuna(@Valid Rune rune, BindingResult result, ModelMap model) {
		String view = "runes/listadoRunas";
		if(rune.getBranch()==null) {
			model.addAttribute("rune", rune);
			return "runes/editRune";
		}
		else if(result.hasErrors()) {
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
		String view ="runes/listadoRunas";
		Rune runa = runeService.findRuneById(runeId);
		if(runa!=null) {
			runeService.deleteRune(runa);
			modelMap.addAttribute("message","Rune delete successfully");
		}else {
			modelMap.addAttribute("message","Rune not found");
		}
		
		return "redirect:/runes/";
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
