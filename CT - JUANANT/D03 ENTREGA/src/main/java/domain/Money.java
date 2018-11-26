
package domain;

import javax.persistence.Access;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(javax.persistence.AccessType.PROPERTY)
public class Money {

	private Double	amount;	// min  0 Digits(9,2)
	private String	currency;	// not blank


	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(final String amount) {
		final int index = amount.indexOf(",");
		this.amount = Double.parseDouble(amount.substring(0, index).trim());
	}
	@NotBlank
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(final String currency) {
		final int index = currency.indexOf(",");

		this.currency = currency.substring(index + 1);
	}

}
