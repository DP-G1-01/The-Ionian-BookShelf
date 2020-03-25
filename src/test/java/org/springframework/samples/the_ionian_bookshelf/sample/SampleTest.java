/*
 * SampleTest.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package org.springframework.samples.the_ionian_bookshelf.sample;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.the_ionian_bookshelf.security.LoginService;
import org.springframework.samples.the_ionian_bookshelf.service.ActorService;
import org.springframework.samples.the_ionian_bookshelf.service.AdministratorService;

import utilities.AbstractTest;

@SpringBootTest
public class SampleTest extends AbstractTest {

	// System under test ------------------------------------------------------

	// Tests ------------------------------------------------------------------

	// The following are fictitious test cases that are intended to check that
	// JUnit works well in this project. Just righ-click this class and run
	// it using JUnit.

	@Autowired
	private LoginService loginService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private AdministratorService adminService;

	@Test
	public void SamplePositiveTest() {
		assertTrue(true);
	}

	@Test
	public void SampleNegativeTest() {
		assertTrue(false);
	}

	@Test
	public void authenticateAdmin() {

		this.authenticate("admin");
		assertTrue(this.loginService.getPrincipal().getUsername().equals("admin"));
		assertTrue(this.actorService.checkAuthority(this.adminService.findByPrincipal(), "administrator"));
	}

}
