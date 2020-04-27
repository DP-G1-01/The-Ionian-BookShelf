
package org.springframework.samples.the_ionian_bookshelf.web;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Champion;
import org.springframework.samples.the_ionian_bookshelf.model.Summoner;
import org.springframework.samples.the_ionian_bookshelf.service.ChampionService;
import org.springframework.samples.the_ionian_bookshelf.service.SummonerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/summoner")
public class SummonerController extends AbstractController {

	@Autowired
	private SummonerService summonerService;

	@Autowired
	private ChampionService champService;

	// Edition --------------------------------------------------------

	@GetMapping(value = "/edit")
	public ModelAndView edit() {

		ModelAndView res;
		try {
			final Summoner principal = this.summonerService.findByPrincipal();

			res = this.createEditModelAndView(principal);
		} catch (Throwable oups) {
			return new ModelAndView("redirect:/");
		}
		return res;
	}

	// Save -----------------------------------------------------------

	@PostMapping(value = "/edit", params = "save")
	public ModelAndView save(@ModelAttribute("actor") @Valid final Summoner summoner, final BindingResult binding,
			HttpServletRequest request) {

		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(summoner);
		else
			try {
				Collection<Champion> champs = new ArrayList<Champion>();
				String[] champsId = request.getParameterValues("champsId");
				for (String id : champsId) {
					champs.add(this.champService.findChampionById(Integer.parseInt(id)));
				}
				summoner.setMains(champs);
				this.summonerService.save(summoner);

				res = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(summoner, "actor.commit.error");
			}
		return res;

	}

	// Display --------------------------------------------------------

	@GetMapping(value = "/display")
	public ModelAndView display(@RequestParam final int summonerId) {

		ModelAndView res;
		try {
			Summoner summoner;

			summoner = this.summonerService.findOne(summonerId);
			res = new ModelAndView("summoner/display");
			res.addObject("summoner", summoner);
		} catch (Throwable oups) {
			return new ModelAndView("redirect:/");
		}
		return res;
	}

	@GetMapping(value = "/show")
	public ModelAndView display() {

		ModelAndView res;
		try {
			Summoner summoner;

			summoner = this.summonerService.findByPrincipal();
			res = new ModelAndView("summoner/show");
			res.addObject("summoner", summoner);
		} catch (Throwable oups) {
			return new ModelAndView("redirect:/");
		}
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
//		res.addObject("requestURI", "summoner/edit.do");
		res.addObject("message", messageCode);
		// the message code references an error message or null
		return res;
	}

}
