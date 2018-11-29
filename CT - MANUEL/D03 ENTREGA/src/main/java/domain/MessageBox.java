
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class MessageBox extends DomainEntity {

	private String	name;		// notBlank
	
	private boolean	systemBox;
    private Collection<Message> messages;

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
     @ManyToMany
	public Collection<Message> getMessages() {
		return messages;
	}

	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}

}
