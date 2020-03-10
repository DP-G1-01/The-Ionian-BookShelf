package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import the_ionian_bookshelf.model.Administrator;

public interface AdministratorRepository extends CrudRepository<Administrator, Integer> {

	@Query("select admin from Administrator admin where admin.userAccount.id = ?1")
	Administrator findByUserAccountId(int id);

}