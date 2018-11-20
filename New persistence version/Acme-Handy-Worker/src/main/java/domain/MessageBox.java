
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class MessageBox extends DomainEntity {

	private String	name;		// notBlank
	private boolean	systemBox;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isSystemBox() {
		return this.systemBox;
	}

	public void setSystemBox(final boolean systemBox) {
		this.systemBox = systemBox;
	}

}
