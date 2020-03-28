package org.springframework.samples.the_ionian_bookshelf.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.service.RoleService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RoleFormatterTests {

	@Mock
	private RoleService roleService;

	private RoleFormatter roleFormatter;

	@BeforeEach
	void setup() {
		roleFormatter = new RoleFormatter(roleService);
	}

	@Test
	void testPrint() {
		Role role = new Role();
		role.setName("Pirate");
		String roleName = roleFormatter.print(role, Locale.ENGLISH);
		assertEquals("Pirate", roleName);
	}

	@Test
	void shouldParse() throws ParseException {
		Mockito.when(roleService.findAll()).thenReturn(makeRoles());
		Role role = roleFormatter.parse("Pirate", Locale.ENGLISH);
		assertEquals("Pirate", role.getName());
	}

	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(roleService.findAll()).thenReturn(makeRoles());
		Assertions.assertThrows(ParseException.class, () -> {
			roleFormatter.parse("Esto no lo parsea", Locale.ENGLISH);
		});
	}

	/*
	 * Produce Roles de ejemplo
	 */
	private Collection<Role> makeRoles() {
		Collection<Role> role = new ArrayList<>();
		role.add(new Role() {
			{
				setName("Pirate");
			}
		});
		role.add(new Role() {
			{
				setName("Vacio");
			}
		});
		return role;
	}

}
