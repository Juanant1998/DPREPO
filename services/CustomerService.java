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
	private CustomerRepository customerRepository; 
	
	public Customer create(){
		return new Customer();
	}
	public Collection<Customer> findAll(){
		return customerRepository.findAll();
	}
	public Customer findOne(int customerId){
		return customerRepository.findOne(customerId);
	}
	public Customer save(Customer customer){
		return customerRepository.save(customer);
	}
	public void delete(Customer customer){
		customerRepository.delete(customer);
	}
}
