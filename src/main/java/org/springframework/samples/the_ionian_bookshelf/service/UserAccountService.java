/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.the_ionian_bookshelf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.UserAccount;
import org.springframework.samples.the_ionian_bookshelf.repository.UserAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
@Transactional
public class UserAccountService {

	@Autowired
	private UserAccountRepository uaRepository;

	public UserAccount create() {

		final UserAccount res = new UserAccount();

		return res;
	}

}
