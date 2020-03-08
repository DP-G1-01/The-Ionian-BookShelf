package org.springframework.samples.theionianbookshelf.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.theionianbookshelf.model.Administrator;
import org.springframework.samples.theionianbookshelf.model.Authority;
import org.springframework.samples.theionianbookshelf.model.UserAccount;
import org.springframework.samples.theionianbookshelf.repository.AdministratorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository adminRepo;

	@Autowired
	private UserAccountService uaService;

	public Administrator create() {

		Administrator res;
		UserAccount ua;
		Authority auth;

		res = new Administrator();
		ua = this.uaService.create();

		auth = new Authority();
		auth.setAuthority(Authority.ADMINISTRATOR);
		ua.setAuthority(auth);

		res.setUserAccount(ua);

		return res;
	}

	public Collection<Administrator> findAll() {

		final Collection<Administrator> res = this.adminRepo.findAll();
		Assert.notNull(res);

		return res;
	}

	public Administrator findOne(final int id) {
		
		assert

		Assert.isTrue(id != 0);

		final Administrator res = this.adminRepo.findOne(id);
		Assert.notNull(res);

		return res;
	}

	public Administrator save(final Administrator admin) {

		Assert.notNull(admin);

		this.findByPrincipal();

		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();

		admin.getUserAccount().setPassword(encoder.encodePassword(admin.getUserAccount().getPassword(), null));

		if (admin.getPhoneNumber() != null) {

			final String editedPhone = this.configurationParametersService.checkPhoneNumber(admin.getPhoneNumber());
			admin.setPhoneNumber(editedPhone);
		}

		final Administrator saved = this.adminRepo.save(admin);

		return saved;
	}

	/**
	 * This method finds the logged user that is using the application. Apart from
	 * this, it checks that the user is an Administrator
	 * 
	 * @return The logged user, an instance of Administrator
	 */
	public Administrator findByPrincipal() {

		Administrator res;
		final UserAccount ua = LoginService.getPrincipal();
		Assert.notNull(ua);

		res = this.findByUserAccountId(ua.getId());

		final boolean hasAuthority = this.actorService.checkAuthority(res, Authority.ADMINISTRATOR);
		Assert.isTrue(hasAuthority);

		return res;
	}

	public Administrator findByUserAccountId(final int id) {

		Assert.isTrue(id != 0);

		final Administrator res = this.adminRepo.findByUserAccountId(id);
		Assert.notNull(res);

		return res;
	}

}
