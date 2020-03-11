package the_ionian_bookshelf.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="items")
public class Item extends BaseEntity {

	@Column(unique = true, name="title")
	@NotBlank
	@Size(min = 1, max = 20)
	private String title;

	@NotBlank
	@Size(min = 10, max = 500)
	@Column(name="description")
	private String description;

	@ElementCollection
	@NotEmpty
	@Size(min = 1, max = 3)
	@Column(name="attributes")
	private Collection<String> attributes;

	@ElementCollection
	@NotEmpty
	@Valid
	@Size(min = 1, max = 3)
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="item_roles", joinColumns = @JoinColumn(name="item_id"),
			inverseJoinColumns = @JoinColumn(name="role_id"))
	private Collection<Role> roles;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
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
		changeRequest.setItem(this);
	}

}
