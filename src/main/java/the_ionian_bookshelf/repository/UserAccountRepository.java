package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import the_ionian_bookshelf.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, String> {

	@Query("select ua from userAccounts ua where ua.username = ?1")
	UserAccount findByUsername(String username);

}
