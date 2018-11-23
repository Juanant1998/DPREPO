
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Message extends DomainEntity {

	private String				subject;	//notBlank
	private String				body;
	private String				priority;	//notBlank
	private String				tags;		//notBlank
	private Date				moment;	//past

	private Actor				sender;
	private Collection<Actor>	recipients;


	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}
	@NotBlank
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(final String priority) {
		this.priority = priority;
	}
	@NotBlank
	public String getTags() {
		return this.tags;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@ManyToMany
	public Collection<Actor> getRecipients() {
		return this.recipients;
	}

	public void setRecipients(final Collection<Actor> recipients) {
		this.recipients = recipients;
	}

}
