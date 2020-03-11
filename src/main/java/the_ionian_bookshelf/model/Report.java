package the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="reports")
@AllArgsConstructor
public class Report extends BaseEntity {

//	@Valid
//	@ManyToOne
//	private Summoner summoner;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name="reviewer_id")
	private Reviewer reviewer;

	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name="message_id")
	private Message message;

	@NotBlank
	@Column(name = "reason")
	private String reason; // Esto será un desplegable con varios motivos posibles

	@Column(name = "text")
	@Size(min = 4, max = 40)
	private String text; // Si el motivo fuera "Otro", se da la opción de especificarlo vía texto

	@NotBlank
	@Pattern(regexp = "^(DENIED|PENDING|ACCEPTED)$")
	@Column(name="status")
	private String status;

	@NotBlank
	@Size(min = 5, max = 50)
	@Column(name = "resolution")
	private String resolution; // Esto será la acción que llevó a cabo el revisor, si es que procedía

}
