
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Warranty extends DomainEntity {

	private String	title;			//notBlank
	private String	terms;			//notBlank
	private String	applicableLaws;	//notBlank
     private   boolean  draftMode;

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}
	@NotBlank
	public String getTerms() {
		return this.terms;
	}

	public void setTerms(final String terms) {
		this.terms = terms;
	}
	@NotBlank
	public String getApplicableLaws() {
		return this.applicableLaws;
	}

	public void setApplicableLaws(final String applicableLaws) {
		this.applicableLaws = applicableLaws;
	}

	public boolean isDraftMode() {
		return draftMode;
	}

	public void setDraftMode(boolean draftMode) {
		this.draftMode = draftMode;
	}

}
