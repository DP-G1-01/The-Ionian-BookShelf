package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.the_ionian_bookshelf.model.Branch;
import org.springframework.samples.the_ionian_bookshelf.model.ConfigurationParameters;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.repository.ConfigurationParametersRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ConfigurationParametersService {

	@Autowired
	private ConfigurationParametersRepository configurationParametersRepository;

	@Autowired
	private AdministratorService administratorService;

	public ConfigurationParameters create() {

		ConfigurationParameters confParams;

		confParams = new ConfigurationParameters();

		return confParams;
	}

	public ConfigurationParameters save(final ConfigurationParameters confParams) {

		assertNotNull(confParams);
		assertTrue(confParams.getId() != 0);

		this.administratorService.findByPrincipal();

		final ConfigurationParameters saved = this.configurationParametersRepository.save(confParams);

		return saved;
	}

	public ConfigurationParameters getConfigurationParameters() {

		this.administratorService.findByPrincipal();

		final ConfigurationParameters res = this.configurationParametersRepository.getConfigurationParameters();
		assertNotNull(res);

		return res;
	}

	public String getSysName() {

		final String res = this.configurationParametersRepository.getSysName();
		assertNotNull(res);

		return res;
	}

	public String getBanner() {

		final String res = this.configurationParametersRepository.getBanner();
		assertNotNull(res);

		return res;
	}

	public String getMessage() {

		final String res = this.configurationParametersRepository.getMessage();
		assertNotNull(res);

		return res;
	}

	public Collection<Role> getRoles() {

		Collection<Role> res = this.configurationParametersRepository.getRoles();
		assertNotNull(res);

		return res;
	}

	public Collection<Branch> getBranches() {

		Collection<Branch> res = this.configurationParametersRepository.getBranches();
		assertNotNull(res);

		return res;
	}

}
