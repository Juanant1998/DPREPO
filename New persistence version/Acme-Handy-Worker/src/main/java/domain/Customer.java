
package domain;

import java.util.Collection;

import javax.persistence.OneToMany;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;




@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Endorser {

	private Collection<FixUpTask>	fixUpTasks;


	@OneToMany
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}

}
