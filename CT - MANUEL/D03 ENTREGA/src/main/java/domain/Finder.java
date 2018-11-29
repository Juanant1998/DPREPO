
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String					query;
	private Double					minPrice;
	private Double					maxPrice;
	private Date					startDate;
	private Date					endDate;

	private Collection<FixUpTask>	fixUpTasks;
	private Collection<Category>	categories;
	private Collection<Warranty>	warranties;

	@ManyToMany
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}

	@NotBlank
	public String getQuery() {
		return this.query;
	}

	public void setQuery(final String query) {
		this.query = query;
	}

	@AttributeOverrides({
		@AttributeOverride(name = "amount", column = @Column(name = "minimumAmount")), @AttributeOverride(name = "currency", column = @Column(name = "minimumCurrency"))
	})
	public Double getMinPrice() {
		return this.minPrice;
	}

	public void setMinPrice(final Double minPrice) {
		this.minPrice = minPrice;
	}
	@AttributeOverrides({
		@AttributeOverride(name = "amount", column = @Column(name = "maximumAmount")), @AttributeOverride(name = "currency", column = @Column(name = "maximumCurrency"))
	})
	public Double getMaxPrice() {
		return this.maxPrice;
	}

	public void setMaxPrice(final Double maxPrice) {
		this.maxPrice = maxPrice;
	}
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}
	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@ManyToMany
	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}
    @ManyToMany
	public Collection<Warranty> getWarranties() {
		return warranties;
	}

	public void setWarranties(Collection<Warranty> warranties) {
		this.warranties = warranties;
	}

}
