
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class ProfessionalRecord extends DomainEntity {

	private String	companyName;	// notBlank
	private Date	startDate;
	private Date	finishDate;
	private String	role;			//notBlank


	@NotBlank
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}
	@Temporal(TemporalType.DATE)
	public Date getFinishDate() {
		return this.finishDate;
	}
	public void setFinishDate(final Date finishDate) {
		this.finishDate = finishDate;
	}
	@NotBlank
	public String getRole() {
		return this.role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

}
