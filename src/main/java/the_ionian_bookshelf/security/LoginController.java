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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@Autowired
	AuthenticationManager authManager;

	// Constructors -----------------------------------------------------------

	public LoginController() {
		super();
	}

	// Login ------------------------------------------------------------------

	@GetMapping("/security/login")
	public ModelAndView loginView(@Valid final Credentials credentials, final BindingResult bindingResult,
			@RequestParam(required = false) final boolean showError) {

		assertNotNull(credentials);
		assertNotNull(bindingResult);

		ModelAndView result;

		result = new ModelAndView("security/login");
		result.addObject("credentials", credentials);
		result.addObject("showError", showError);

		return result;
	}

	@PostMapping("/security/login")
	public ModelAndView login(@Valid final Credentials credentials, final BindingResult bindingResult,
			@RequestParam(required = false) final boolean showError, HttpServletRequest request) {

		assertNotNull(credentials);
		assertNotNull(bindingResult);

		ModelAndView result;

		try {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
					credentials.getUsername(), credentials.getPassword());
			Authentication authentication = authManager.authenticate(token);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(authentication);

			HttpSession session = request.getSession(true);
			session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

			result = new ModelAndView("/");
		} catch (Exception oops) {
			SecurityContextHolder.getContext().setAuthentication(null);
			System.out.println("Login not succesful");

			result = new ModelAndView("security/login");
			result.addObject("credentials", credentials);
			result.addObject("showError", showError);
		}
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
