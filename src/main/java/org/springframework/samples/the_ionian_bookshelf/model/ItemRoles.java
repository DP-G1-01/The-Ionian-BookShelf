package org.springframework.samples.the_ionian_bookshelf.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_roles")
public class ItemRoles {
	
	@EmbeddedId
    ItemRolesKey id;
 
    @ManyToOne
    @MapsId("item_id")
    @JoinColumn(name = "item_id")
    Item item;
 
    @ManyToOne
    @MapsId("role_id")
    @JoinColumn(name = "role_id")
    Role role;
 
}
