package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import the_ionian_bookshelf.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer> {

	@Query(value= "select ua from user_accounts ua where ua.username = ?1", nativeQuery = true)
	UserAccount findByUsername(String username);

}
