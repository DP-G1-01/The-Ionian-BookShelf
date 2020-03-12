package the_ionian_bookshelf.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.Branch;
import the_ionian_bookshelf.model.ConfigurationParameters;
import the_ionian_bookshelf.model.Role;

public interface ConfigurationParametersRepository extends JpaRepository<ConfigurationParameters, Integer> {

	@Query("select confParams from ConfigurationParameters confParams")
	ConfigurationParameters getConfigurationParameters();

	@Query("select sysName from ConfigurationParameters confParams")
	String getSysName();

	@Query("select banner from ConfigurationParameters confParams")
	String getBanner();

	@Query("select message from ConfigurationParameters confParams")
	String getMessage();

	@Query("select roles from ConfigurationParameters confParams join confParams.roles roles")
	Collection<Role> getRoles();

	@Query("select branches from ConfigurationParameters confParams join confParams.branches branches")
	Collection<Branch> getBranches();

}
