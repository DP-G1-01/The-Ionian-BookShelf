
package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import the_ionian_bookshelf.model.Actor;
import the_ionian_bookshelf.model.Summoner;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

    @Query(value = "select a from Actor a where a.userAccount.id = ?1", nativeQuery = true)
    Actor findByUserAccountId(int id);

}
