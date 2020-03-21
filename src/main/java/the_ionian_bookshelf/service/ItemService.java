package the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import the_ionian_bookshelf.model.Item;
import the_ionian_bookshelf.model.Role;
import the_ionian_bookshelf.repository.ItemRepository;
import the_ionian_bookshelf.repository.RoleRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	public Collection<Item> findAll() {

		final Collection<Item> res = this.itemRepository.findAll();

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
		Item item = itemRepository.findItemById(itemId);
		itemRepository.delete(item);
	}
	
	public void saveItem(Item i) {
		itemRepository.save(i);
	}

	public Collection<Role> findRoles() {
		return roleRepository.findAll();
	}
}
