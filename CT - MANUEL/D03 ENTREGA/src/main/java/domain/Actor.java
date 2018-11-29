
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public class Actor extends DomainEntity {

	private String	name;			//notBlank
	private String	middleName;
	private String	phone;			//pattern
	private String	address;		//notBlank
	private String	email;			//email
	private String	photo;			//url*
	private Boolean	isSuspicious;
	private Boolean	isBanned;


	public Boolean getIsBanned() {
		return this.isBanned;
	}

	public void setIsBanned(final Boolean isBanned) {
		this.isBanned = isBanned;
	}

	public Boolean getIsSuspicious() {
		return this.isSuspicious;
	}

	public void setIsSuspicious(final Boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}


	//Persistence
	private UserAccount				userAccount;
	private Collection<Profile>		profiles;
	private Collection<MessageBox>	messageBoxes;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}
	public String getMiddleName() {
		return this.middleName;
	}
	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}
	@Pattern(regexp = "^([+-]\\d+\\s+)?(\\([0-9]+\\)\\s+)?([\\d\\w\\s-]+)$")
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}
	@NotBlank
	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}
	@Email
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}
	@URL
	public String getPhoto() {
		return this.photo;
	}
	public void setPhoto(final String photo) {
		this.photo = photo;
	}
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}
	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	@OneToMany
	public Collection<Profile> getProfiles() {
		return this.profiles;
	}

	public void setProfiles(final Collection<Profile> profiles) {
		this.profiles = profiles;
	}

	@OneToMany
	public Collection<MessageBox> getMessageBoxes() {
		return this.messageBoxes;
	}

	public void setMessageBoxes(final Collection<MessageBox> messageBoxes) {
		this.messageBoxes = messageBoxes;
	}

}
