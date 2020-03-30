package org.springframework.samples.the_ionian_bookshelf.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.the_ionian_bookshelf.model.Item;
import org.springframework.samples.the_ionian_bookshelf.model.Role;
import org.springframework.samples.the_ionian_bookshelf.repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class ItemServiceMockedTests {

	 	@Mock
	    private ItemRepository itemRepository;

	    protected ItemService itemService;

	    @BeforeEach
	    void setup() {
	        itemService = new ItemService(itemRepository);
	    }

	    @Test
	    void shouldFindItems() {
	    	List <String> attributes = new ArrayList<>();
			attributes.add("1");
			List <Role> roles = new ArrayList<>();
			Role r = new Role("rolTest", "testeoooooooooo", "https://www.google.es");
			roles.add(r);
			Item i = new Item("test", "testeandoooooooooooooooooo", attributes, roles);
			List<Item> sampleItems = new ArrayList<Item>();
	        sampleItems.add(i);
	        when(itemRepository.findAll()).thenReturn(sampleItems);

	        Collection<Item> items = this.itemService.findAll();

	        assertThat(items).hasSize(1);
	        Item item = items.iterator().next();
	        assertEquals(i, item);
	    }
	    
}
