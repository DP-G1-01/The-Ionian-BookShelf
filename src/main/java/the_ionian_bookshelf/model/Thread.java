package the_ionian_bookshelf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "threads")
public class Thread extends BaseEntity {

    @NotBlank
    @Column(name = "title")
    @Size(min = 5, max = 50)
    public String title;

    @NotBlank
    @Column(name = "description")
    @Size(min = 20, max = 500)
    public String description;
}
