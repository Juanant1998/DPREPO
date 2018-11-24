
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository	customerRepository;

	@Autowired
	private UserAccountService	uas;

	@Autowired
	private ApplicationService	as;


	public Customer create() {//revisar igual que handyworker
		return new Customer();
	}
	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}
	public Customer findOne(final int customerId) {
		return this.customerRepository.findOne(customerId);
	}
	public Customer save(final Customer customer) {
		return this.customerRepository.save(customer);
	}
	public void delete(final Customer customer) {
		this.customerRepository.delete(customer);
	}
	/*
	 * public Collection<FixUpTask> getMyFUT() {
	 * final UserAccount me = LoginService.getPrincipal(); //cambiarlo por obtener actor actual.
	 * Assert.notNull(me);
	 * //Assert.isTrue(me.getisaccountactivated()); Necesito un atributo para comprobar si la cuenta está baneada.
	 * //Assert.isTrue(me.isthisacustomer()) COMprobar si es un customer.
	 * //Si es un customer no baneado -> c.getFixUpTasks(); return lo anterior.
	 * }
	 */

	public Collection<FixUpTask> getMyFixUpTasks() {
		final UserAccount actual = LoginService.getPrincipal();
		Assert.notNull(actual);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(actual.getAuthorities().contains(actual));

		//is account banned?

		final Customer c = this.uas.getCustomerByUserAccount(actual);

		return c.getFixUpTasks();

	}

	public Collection<Application> getApplicationsForFUT(final FixUpTask fut) {
		final Collection<Application> result = fut.getApplications();

		return result;
	}

	public Application updateApplication(final Application app, final String status, final String comments) {
		final Application appcopia = app;
		app.setStatus(status);

		if (status == "ACCEPTED" && app.getCreditCard().equals(null))
			app.setComments(appcopia.getComments() + "\n you must provide a valid credit card number in order to perform the task.");
		final Application result = this.as.save(app);

		if (comments != null)
			app.setComments(appcopia.getComments() + "\n Customer comments : " + comments);

		return result;
	}
}
