package org.springframework.samples.the_ionian_bookshelf.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.the_ionian_bookshelf.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	Item findById(int id) throws DataAccessException;
	Item findItemById(int id);

	void removeItemById(int id);
}
