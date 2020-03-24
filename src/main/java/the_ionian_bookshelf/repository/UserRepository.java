package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.User;

public interface UserRepository extends JpaRepository<User, String> {

}
