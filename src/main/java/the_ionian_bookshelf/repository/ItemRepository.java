package the_ionian_bookshelf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import the_ionian_bookshelf.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	
	Item findItemById(int id);

	void removeItemById(int id);
}
