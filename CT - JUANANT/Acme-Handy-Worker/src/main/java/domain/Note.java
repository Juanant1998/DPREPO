
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	private Date	moment;		//past
	private String	creatorComment;	//notBlank
	private String	comment;		//notBlank


	@Temporal(TemporalType.TIMESTAMP)
	@Past
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}
	@NotBlank
	public String getCreatorComment() {
		return this.creatorComment;
	}

	public void setCreatorComment(final String creatorComment) {
		this.creatorComment = creatorComment;
	}
	@NotBlank
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

}
