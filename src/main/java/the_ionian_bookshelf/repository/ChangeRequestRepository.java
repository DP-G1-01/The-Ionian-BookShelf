package the_ionian_bookshelf.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;

import the_ionian_bookshelf.model.BaseEntity;
import the_ionian_bookshelf.model.Champion;
import the_ionian_bookshelf.model.ChangeRequest;
import the_ionian_bookshelf.model.Item;

public interface ChangeRequestRepository {
	/**
	 * Save a <code>ChangeRequest</code> to the data store, either inserting or updating it.
	 * 
	 * @param changeRequest
	 *            the <code>ChangeRequest</code> to save
	 * @see BaseEntity#isNew
	 */
	void save(ChangeRequest changeRequest) throws DataAccessException;
	
	@Query("SELECT c FROM Champion c where c.id=:championId")
	Champion findChampionById(int championId);
	
	@Query("SELECT i FROM Item i where i.id=:itemId")
	Item findItemById(int itemId);

}
