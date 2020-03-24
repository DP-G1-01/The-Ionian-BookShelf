
package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import the_ionian_bookshelf.model.Actor;
import the_ionian_bookshelf.model.Summoner;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

}
