package org.springframework.samples.theionianbookshelf.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Check;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Check(constraints = "champion IS NOT NULL AND item IS NULL"
		+ "OR champion IS NULL AND item IS NOT NULL")
public class ChangeRequest extends BaseEntity {
	
	@Valid
	@ManyToOne(optional = true)
	private Champion champion;
	
	@Valid
	@ManyToOne(optional = true)
	private Item item;
	
	@Column(name = "title")
	@Size(min = 10, max = 40)
	private String title;
	 
	@Column(name = "description")
	@Size(min = 20, max = 500)
	private String description;
	
	//un array con valores positivos o negativos de los cambios que van a hacerse
	//a los atributos de Champion [Health, Mana, Energy, Attack, Speed] 
	//ej: [0,-2,+50,0,0] -> -2 en Mana y +50 en Energy
	@Size(min = 5, max = 5)
	private List<Double> changeChamp;  
	
	@ElementCollection
	@Size(min = 1, max = 3)
	private Collection<String> changeItem; //cambios en los items
	
	@NotBlank
	@Valid
	private Status status;

}
