
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	//C1
	@Query("select avg(c.fixUpTasks.size) from Customer c")
	Double averageFixUpTaskByCustomer();
	
	@Query("select min(c.fixUpTasks.size)from Customer c")
	Integer minFixUpTaskByCustomer();
	
	@Query("select max(c.fixUpTasks.size)from Customer c")
	Integer maxFixUpTaskByCustomer();
	
	@Query("select sqrt(sum(c.fixUpTasks.size * c.fixUpTasks.size)/ count(c.fixUpTasks.size) - avg(c.fixUpTasks.size) * avg(c.fixUpTasks.size))from Customer c")
	Integer deviationFixUpTaskByCustomer();
	
	
	//C9
	@Query("select c1 from Customer c1 where c1.fixUpTasks.size > (select avg(c2.fixUpTasks.size) from Customer c2)*1.1 order by c1.fixUpTasks.size")
	Collection<Customer> tenPersentMoreFixUpTasks(); 

}
