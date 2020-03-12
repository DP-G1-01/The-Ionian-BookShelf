package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

	@Query("select ua from UserAccount ua where ua.username = ?1")
	UserAccount findByUsername(String username);

}
