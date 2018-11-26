
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	private Date		moment;	//past
	private String		comments;	//notBlank

	private Endorser	sender;
	private Endorser	endorser;


	@OneToOne(optional = false)
	@ManyToOne(optional = false)
	public Endorser getSender() {
		return this.sender;
	}

	public Score calculateScore() {
		Integer good = 0;
		Integer bad = 0;

		if (this.comments.contains("NOT"))
			bad++;
		if (this.comments.contains("BAD"))
			bad++;
		if (this.comments.contains("HORRIBLE"))
			bad++;
		if (this.comments.contains("AVERAGE"))
			bad++;
		if (this.comments.contains("DISASTER"))
			bad++;

		if (this.comments.contains("GOOD"))
			good++;
		if (this.comments.contains("AMAZING"))
			good++;
		if (this.comments.contains("EXCELENT"))
			good++;
		if (this.comments.contains("BEAUTIFUL"))
			good++;
		if (this.comments.contains("TERRIFIC"))
			good++;
		if (this.comments.contains("GREAT"))
			good++;
		final Score s = new Score();
		s.calculatePuntuation(good, bad);

		return s;
	}

	public void setSender(final Endorser sender) {
		this.sender = sender;
	}

	@ManyToOne(optional = false)
	public Endorser getEndorser() {
		return this.endorser;
	}

	public void setEndorser(final Endorser endorser) {
		this.endorser = endorser;
	}


	public enum badWords {
		NOT, BAD, HORRIBLE, AVERAGE, DISASTER
	};

	public enum goodWords {
		GOOD, FANTASTIC, EXCELLENT, GREAT, AMAZING, TERRIFIC, BEAUTIFUL
	};


	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}
	@NotBlank
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comment) {
		this.comments = comment;
	}

}
