package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Item;
import the_ionian_bookshelf.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	public Item create() {

		final Item res = new Item();

		return res;
	}

	public Collection<Item> findAll() {

		final Collection<Item> res = (Collection<Item>) this.itemRepository.findAll();

		assertNotNull(res);

		return res;
	}

	public Item findOne(final int id) {

		assertTrue(id != 0);

		final Item res = this.itemRepository.findById(id).get();
		assertNotNull(res);

		return res;
	}

	public Item findItemById(int itemId) {
		return itemRepository.findItemById(itemId);
	}
	
	public void removeItemById(int itemId) {
		itemRepository.removeItemById(itemId);
	}
}
