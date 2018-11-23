
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String					name;			// notBlank  unique
	private Collection<Category>	descendants;


	@OneToMany
	public Collection<Category> getDescendants() {
		return this.descendants;
	}

	public void setDescendants(final Collection<Category> descendants) {
		this.descendants = descendants;
	}

	@NotBlank
	@Column(name = "name", unique = true)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
