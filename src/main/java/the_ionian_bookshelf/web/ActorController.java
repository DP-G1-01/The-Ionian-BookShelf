
package the_ionian_bookshelf.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import the_ionian_bookshelf.model.Actor;
import the_ionian_bookshelf.model.Administrator;
import the_ionian_bookshelf.model.Reviewer;
import the_ionian_bookshelf.model.Summoner;
import the_ionian_bookshelf.service.AdministratorService;
import the_ionian_bookshelf.service.ReviewerService;
import the_ionian_bookshelf.service.SummonerService;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private SummonerService summonerService;

	@Autowired
	private ReviewerService reviewerService;

	@Autowired
	private AdministratorService administratorService;

	// Sign up --------------------------------------------------------

	@RequestMapping(value = "/signUp", method = RequestMethod.GET)
	public ModelAndView signUp() {
		ModelAndView result;

		result = new ModelAndView("actor/signUp");
		result.addObject("requestURI", "actor/signUp");
		return result;
	}

	@RequestMapping(value = "/createSummoner", method = RequestMethod.GET)
	public ModelAndView createSummoner() {
		ModelAndView result;
		final Summoner summoner = this.summonerService.create();

		final String role = "summoner";

		result = this.createEditModelAndView(summoner);
		result.addObject("role", role);

		return result;
	}

	@RequestMapping(value = "/createReviewer", method = RequestMethod.GET)
	public ModelAndView createReviewer() {
		ModelAndView result;
		final Reviewer reviewer = this.reviewerService.create();

		final String role = "reviewer";

		result = this.createEditModelAndView(reviewer);
		result.addObject("role", role);

		return result;
	}

	@RequestMapping(value = "/administrator/createAdministrator", method = RequestMethod.GET)
	public ModelAndView createAdmin() {
		ModelAndView result;
		this.administratorService.findByPrincipal();
		final Administrator admin = this.administratorService.create();
		final String role = "administrator";
		result = this.createEditModelAndView(admin);
		result.addObject("role", role);
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
