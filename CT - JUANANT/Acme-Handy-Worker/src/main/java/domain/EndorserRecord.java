
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class EndorserRecord extends DomainEntity {

	private String	fullendorserName;	//notBlank
	private String	email;				//email
	private String	phoneNumber;		//pattern
	private String	comment;


	@NotBlank
	public String getFullendorserName() {
		return this.fullendorserName;
	}

	public void setFullendorserName(final String fullendorserName) {
		this.fullendorserName = fullendorserName;
	}
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
	//@Pattern(regexp = "+" + "d(2) " + "d+") (a corregir)
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getComment() {
		return this.comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
	}

}
