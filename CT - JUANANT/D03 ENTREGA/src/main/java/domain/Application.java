
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private Date		moment;		//past
	private String		status;		//notBlank pattern 
	private Double		offeredPrice;
	private String		comments;		//notBlank

	private CreditCard	creditCard;


	@OneToOne(optional = false)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}


	public static final String	PENDING		= "PENDING";
	public static final String	ACCEPTED	= "ACCEPTED";
	public static final String	REJECTED	= "REJECTED";


	@Temporal(TemporalType.TIMESTAMP)
	@Past
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}
	@NotBlank
	@Pattern(regexp = "^" + Application.ACCEPTED + "|" + Application.PENDING + "|" + Application.REJECTED + "$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}
	@NotNull
	public Double getOfferedPrice() {
		return this.offeredPrice;
	}
	public void setOfferedPrice(final Double offeredPrice) {
		this.offeredPrice = offeredPrice;
	}
	@NotBlank
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

}
