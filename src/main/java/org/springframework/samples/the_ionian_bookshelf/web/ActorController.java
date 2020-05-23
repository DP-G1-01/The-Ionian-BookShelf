
package org.springframework.samples.the_ionian_bookshelf.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Actor;
import org.springframework.samples.the_ionian_bookshelf.model.Administrator;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.League;
import org.springframework.samples.the_ionian_bookshelf.model.Reviewer;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;
import org.springframework.samples.the_ionian_bookshelf.service.AuthoritiesService;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.service.LeagueService;
import org.springframework.samples.the_ionian_bookshelf.service.ReviewerService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private SummonerService summonerService;

	@Autowired
	private ReviewerService reviewerService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ChampionService champService;
	
	@Autowired
	private LeagueService leagueService;

	@Autowired
	private AuthoritiesService authService;

	// Sign up --------------------------------------------------------

	@GetMapping(value = "/signUp")
	public ModelAndView signUp() {
		ModelAndView result;

		result = new ModelAndView("actor/signUp");
		result.addObject("requestURI", "actor/signUp");
		return result;
	}

	@GetMapping(value = "/createSummoner")
	public ModelAndView createSummoner() {
		ModelAndView result;

		try {
			final Summoner summoner = this.summonerService.create();

			final String role = "summoner";
			Collection<Champion> champs = this.champService.findAll();
			Collection<League> leagues = this.leagueService.findAll();

			result = this.createEditModelAndView(summoner);
			result.addObject("role", role);
			result.addObject("champs", champs);
			result.addObject("leagues", leagues);

		} catch (Exception oups) {
			return new ModelAndView("redirect:/");
		} catch (AssertionError oups) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	@GetMapping(value = "administrator/createReviewer")
	public ModelAndView createReviewer() {
		ModelAndView result;
		try {
			this.authService.checkAuthorities("administrator");
			final Reviewer reviewer = this.reviewerService.create();

			final String role = "reviewer";

			result = this.createEditModelAndView(reviewer);
			result.addObject("role", role);
		} catch (Exception oups) {
			return new ModelAndView("redirect:/");
		} catch (AssertionError oups) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	@GetMapping(value = "/administrator/createAdministrator")
	public ModelAndView createAdmin() {
		ModelAndView result;
		try {
			this.authService.checkAuthorities("administrator");
			final Administrator admin = this.administratorService.create();
			final String role = "administrator";
			result = this.createEditModelAndView(admin);
			result.addObject("role", role);
		} catch (Exception oups) {
			return new ModelAndView("redirect:/");
		} catch (AssertionError oups) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;
		result = this.createEditModelAndView(actor, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String messageCode) {
		ModelAndView result;
		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);
		result.addObject("message", messageCode);
		// the message code references an error message or null
		return result;
	}

}
