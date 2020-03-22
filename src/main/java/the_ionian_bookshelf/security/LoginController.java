/*
 * LoginController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the TDG
 * Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package the_ionian_bookshelf.security;

import static org.junit.Assert.assertNotNull;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import the_ionian_bookshelf.service.ConfigurationParametersService;

@Controller
public class LoginController {

	// Supporting services ----------------------------------------------------

	@Autowired
	LoginService service;

	@Autowired
	private ConfigurationParametersService configurationParametersService;

	// Constructors -----------------------------------------------------------

	public LoginController() {
		super();
	}

	// Login ------------------------------------------------------------------

	@GetMapping("/security/login")
	public ModelAndView login(@Valid final Credentials credentials, final BindingResult bindingResult,
			@RequestParam(required = false) final boolean showError) {

		assertNotNull(credentials);
		assertNotNull(bindingResult);

		ModelAndView result;

		result = new ModelAndView("security/login");
		result.addObject("credentials", credentials);
		result.addObject("showError", showError);

//		final String banner = this.configurationParametersService.getBanner();
//		result.addObject("banner", banner);

		return result;
	}

	// LoginFailure -----------------------------------------------------------

	@RequestMapping("/loginFailure")
	public ModelAndView failure() {
		ModelAndView result;

		result = new ModelAndView("redirect:login.do?showError=true");

		final String banner = this.configurationParametersService.getBanner();
		result.addObject("banner", banner);

		return result;
	}

}
