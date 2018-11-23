
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.CustomerRepository;
import domain.Customer;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository	customerRepository;


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
}
