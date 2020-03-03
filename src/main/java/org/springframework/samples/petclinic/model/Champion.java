package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Champion extends BaseEntity{

	@Column(unique = true)
	@NotBlank
	private String name;
	
	@NotBlank
	private Double health;
	
	@NotBlank
	private Double mana;
	
	@NotBlank
	private Double energia;
	
	@NotBlank
	private Double attack;
	
	@NotBlank
	private Double speed;
}
