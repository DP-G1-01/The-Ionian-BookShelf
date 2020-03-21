package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.Champion;
import the_ionian_bookshelf.model.ChangeRequest;
import the_ionian_bookshelf.model.Item;

public interface ChangeRequestRepository extends JpaRepository<ChangeRequest, Integer> {
	
	@Query("SELECT c FROM Champion c where c.id=:championId")
	Champion findChampionById(int championId);
	
	@Query("SELECT i FROM Item i where i.id=:itemId")
	Item findItemById(int itemId);
	
	ChangeRequest findChangeRequestById(int id);

}
