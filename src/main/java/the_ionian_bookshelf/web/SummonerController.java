
package the_ionian_bookshelf.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import the_ionian_bookshelf.model.Summoner;
import the_ionian_bookshelf.service.SummonerService;

@Controller
@RequestMapping("/summoner")
public class SummonerController extends AbstractController {

	@Autowired
	private SummonerService summonerService;

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView res;
		final Summoner principal = this.summonerService.findByPrincipal();

		res = this.createEditModelAndView(principal);

		return res;
	}

	// Save -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("actor") @Valid final Summoner summoner, final BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(summoner);
		else
			try {
				this.summonerService.save(summoner);

				res = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(summoner, "actor.commit.error");
			}
		return res;

	}

	// Display --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int summonerId) {

		ModelAndView res;
		Summoner summoner;

		summoner = this.summonerService.findOne(summonerId);
		res = new ModelAndView("summoner/display");
		res.addObject("summoner", summoner);
		return res;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView display() {

		ModelAndView res;
		Summoner summoner;

		summoner = this.summonerService.findByPrincipal();
		res = new ModelAndView("summoner/show");
		res.addObject("summoner", summoner);
		return res;
	}

	// Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Summoner summoner) {

		ModelAndView res;

		res = this.createEditModelAndView(summoner, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Summoner summoner, final String messageCode) {

		ModelAndView res;

		res = new ModelAndView("actor/edit");
		res.addObject("actor", summoner);
		res.addObject("role", "summoner");
		res.addObject("requestURI", "summoner/edit.do");
		res.addObject("message", messageCode);
		// the message code references an error message or null
		return res;
	}

}
