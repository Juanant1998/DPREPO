
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Score extends DomainEntity {

	private Double	numericScore;


	public Double getNumericScore() {
		return this.numericScore;
	}

	public void setNumericScore(final Double numericScore) {
		this.numericScore = numericScore;
	}

	public void calculatePuntuation(final int p, final int n) {

		this.numericScore = (double) (p - n);
	}

}
