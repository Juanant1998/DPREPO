
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Profile extends DomainEntity {

	private String	socialNetwork;	//notBlank
	private String	snLink;		// url
	private String	nick;			//notBlank


	@NotBlank
	public String getSocialNetwork() {
		return this.socialNetwork;
	}

	public void setSocialNetwork(final String socialNetwork) {
		this.socialNetwork = socialNetwork;
	}
	@URL
	public String getSnLink() {
		return this.snLink;
	}

	public void setSnLink(final String snLink) {
		this.snLink = snLink;
	}
	@NotBlank
	public String getNick() {
		return this.nick;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}

}
