
package services;

import java.util.ArrayList;
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
import domain.MessageBox;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository	customerRepository;

	@Autowired
	private UserAccountService	uas;

	@Autowired
	private ApplicationService	as;


	public Customer create() {
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
	public void checkAuthority() {

		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.notNull(user);
		final Collection<Authority> authority = user.getAuthorities();
		Assert.notNull(authority);
		final Authority a1 = new Authority();
		a1.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(authority.contains(a1));

	}


	@Autowired
	private MessageBoxService	mbs;


	public MessageBox createNewMessageBox(final String username, final String msgboxname) {
		final MessageBox msgbox = this.mbs.create();
		msgbox.setName(username + " " + msgboxname);
		msgbox.setSystemBox(true);

		final MessageBox result = this.mbs.save(msgbox);

		return result;
	}

	public Customer register(final Customer c1, final String username, final String password) {
		Assert.notNull(c1);
		Assert.notNull(username);
		Assert.notNull(password);
		this.checkAuthority();

		final Customer c2 = this.create();
		c2.setAddress(c1.getAddress());
		c2.setEmail(c1.getEmail());
		c2.setFixUpTasks(new ArrayList<FixUpTask>());
		c2.setMiddleName(c1.getMiddleName());
		c2.setName(c1.getName());
		c2.setPhone(c1.getPhone());
		c2.setPhoto(c1.getPhoto());
		c2.setIsSuspicious(c1.getIsSuspicious());
		c2.setIsBanned(c1.getIsBanned());

		final MessageBox in = this.createNewMessageBox(username, "-in");
		final MessageBox out = this.createNewMessageBox(username, "-out");
		final MessageBox trash = this.createNewMessageBox(username, "-trash");
		final MessageBox spam = this.createNewMessageBox(username, "-spam");

		final Collection<MessageBox> msboxes = new ArrayList<MessageBox>();
		msboxes.add(in);
		msboxes.add(out);
		msboxes.add(trash);
		msboxes.add(spam);

		c2.setMessageBoxes(msboxes);
		final Customer x = this.save(c2);

		return x;

	}

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
