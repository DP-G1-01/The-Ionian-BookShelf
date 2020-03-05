package org.springframework.samples.theionianbookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report extends BaseEntity {

	@OneToOne
	@NotNull
	@Column(name = "summoner")
	private Summoner summoner;
	
	@ManyToOne
	@NotNull
	@Column(name = "message")
	private Message message;

	@NotBlank
	@Column(name = "reason")
	private String reason; //Esto será un desplegable con varios motivos posibles
		
	@Column(name = "text")
	@Size(min = 10, max = 40)
	private String text; //Si el motivo fuera "Otro", se da la opción de especificarlo vía texto
	
	@Column(name = "revisado")
	private boolean revisado;
	
	@NotBlank
	@Size(min = 5, max = 50)
	@Column(name = "resolution")
	private String resolution; //Esto será la acción que llevó a cabo el revisor, si es que procedía
		
}
