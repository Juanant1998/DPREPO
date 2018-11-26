
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Tutorial extends DomainEntity {

	private String				title;			//notBlank
	private Date				lastUpdate;
	private String				summary;		//notBlank
	private String				picture;		//url

	private Collection<Section>	sections;
	private HandyWorker			handyworker;


	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Section> getSections() {
		return this.sections;
	}

	public void setSections(final Collection<Section> sections) {
		this.sections = sections;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public HandyWorker getHandyworker() {
		return this.handyworker;
	}

	public void setHandyworker(final HandyWorker h) {
		this.handyworker = h;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}
	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastUpdate() {
		return this.lastUpdate;
	}
	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	@NotBlank
	public String getSummary() {
		return this.summary;
	}
	public void setSummary(final String summary) {
		this.summary = summary;
	}

}
