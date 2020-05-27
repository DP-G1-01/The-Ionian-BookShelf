package org.springframework.samples.the_ionian_bookshelf.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.the_ionian_bookshelf.model.Build;
import org.springframework.samples.the_ionian_bookshelf.model.ChangeRequest;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.repository.BuildRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ChangeRequestRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.ItemRepository;
import org.springframework.samples.the_ionian_bookshelf.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private BuildRepository buildRepository;

	@Autowired
	private ChangeRequestRepository changeRequestRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	public ItemService(ItemRepository itemRepository) {
		this.itemRepository= itemRepository;
	}
	public Collection<Item> findAll() {

		final Collection<Item> res = this.itemRepository.findAll();

		assertNotNull(res);

		return res;
	}
	@Transactional
	public Item findOne(final int id) throws DataAccessException {

		assertTrue(id != 0);

		final Item res = this.itemRepository.findById(id);
		assertNotNull(res);

		return res;
	}
	@Transactional
	public Item findItemById(int itemId) throws DataAccessException {
		Item item = itemRepository.findById(itemId);
		assertNotNull(item);
		return item;
		
	}
	
	public void removeItemById(int itemId) {
		Item item = itemRepository.findItemById(itemId);
		assertNotNull(item);
		List<Build> builds = buildRepository.findBuildsByItemId(itemId);
		if(!builds.isEmpty()) {
		for(Build b: builds) {
			List<Item> aux = b.getItems();
			aux.remove(item);
			b.setItems(aux);
			buildRepository.save(b);
		}
		}
		ChangeRequest request = changeRequestRepository.findChangeRequestByItemId(itemId);
		
		if(request != null) {
		changeRequestRepository.delete(request);
		}
		
		itemRepository.delete(item);
	}
	
	public void saveItem(Item i) {
		itemRepository.save(i);
	}

	public Collection<Role> findRoles() {
		return roleRepository.findAll();
	}
}
