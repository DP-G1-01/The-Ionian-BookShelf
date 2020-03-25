package org.springframework.samples.the_ionian_bookshelf.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.service.RoleService;
import org.springframework.stereotype.Component;

@Component
public class RoleFormatter implements Formatter<Role> {

	private final RoleService roleService;

	@Autowired
	public RoleFormatter(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public String print(Role role, Locale locale) {
		return role.getName();
	}

	@Override
	public Role parse(String text, Locale locale) throws ParseException {
		Collection<Role> findRoles = this.roleService.findAll();
		for (Role role : findRoles) {
			if (role.getName().equals(text)) {
				return role;
			}
		}
		throw new ParseException("type not found: " + text, 0);
	}

}