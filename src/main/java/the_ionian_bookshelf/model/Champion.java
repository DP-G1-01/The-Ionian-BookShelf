package the_ionian_bookshelf.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "champions")
public class Champion extends BaseEntity {

	@NotBlank
	@Column(name = "name", unique = true)
	@Size(min = 1, max = 20)
	private String name;

	@NotBlank
	@Column(name = "description")
	@Size(min = 10, max = 500)
	private String description;

	@NotBlank
	@Column(name = "health")
	private Double health;

	// Sin la anotación @NotBlank para poder seleccionar si tiene mana o energía.
	// Se hará la comprobación en los servicios
	@Min(0)
	@Column(name = "mana")
	private Double mana;

	// Sin la anotación @NotBlank para poder seleccionar si tiene mana o energía.
	// Se hará la comprobación en los servicios
	@Min(0)
	@Column(name = "energy")
	private Double energy;

	@NotBlank
	@Column(name = "attack")
	private Double attack;

	@NotBlank
	@Column(name = "speed")
	private Double speed;

	@NotBlank
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "role_id")
	private Role role;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "champion")
	private Set<ChangeRequest>	changeRequests;
	
	protected Set<ChangeRequest> getChangeRequestsInternal() {
		if (this.changeRequests == null) {
			this.changeRequests = new HashSet<>();
		}
		return this.changeRequests;
	}

	protected void setChangeRequestsInternal(Set<ChangeRequest> changeRequests) {
		this.changeRequests = changeRequests;
	}

	public List<ChangeRequest> getChangeRequests() {
		List<ChangeRequest> sortedChangeRequests = new ArrayList<>(getChangeRequestsInternal());
		PropertyComparator.sort(sortedChangeRequests, new MutableSortDefinition("date", false, false));
		return Collections.unmodifiableList(sortedChangeRequests);
	}

	public void addChangeRequest(ChangeRequest changeRequest) {
		getChangeRequestsInternal().add(changeRequest);
		changeRequest.setChampion(this);
	}
}
