
package org.springframework.samples.the_ionian_bookshelf.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Reviewer;
import org.springframework.samples.the_ionian_bookshelf.service.ReviewerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/reviewer")
public class ReviewerController extends AbstractController {

	@Autowired
	private ReviewerService reviewerService;

	// Edition --------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView res;
		try {
			final Reviewer principal = this.reviewerService.findByPrincipal();

			res = this.createEditModelAndView(principal);
		} catch (Throwable oups) {
			return new ModelAndView("redirect:/");
		}
		return res;
	}

	// Save -----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("actor") @Valid final Reviewer reviewer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(reviewer);
		else
			try {
				this.reviewerService.save(reviewer);

				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(reviewer, "actor.commit.error");
			}
		return result;

	}

	// Display --------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int reviewerId) {
		ModelAndView result;
		try {
			Reviewer reviewer;

			reviewer = this.reviewerService.findOne(reviewerId);
			result = new ModelAndView("reviewer/display");
			result.addObject("reviewer", reviewer);
		} catch (Throwable oups) {
			return new ModelAndView("redirect:/");
		}
		return result;
	}

	// Ancillary methods --------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Reviewer reviewer) {
		ModelAndView result;

		result = this.createEditModelAndView(reviewer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Reviewer reviewer, final String messageCode) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("actor", reviewer);
		result.addObject("role", "reviewer");
		result.addObject("requestURI", "reviewer/edit.do");
		result.addObject("message", messageCode);
		// the message code references an error message or null
		return result;
	}

}
