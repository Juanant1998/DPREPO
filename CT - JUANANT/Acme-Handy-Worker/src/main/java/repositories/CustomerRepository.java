
package repositories;

import java.util.Collection;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.FixUpTask;

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

	//B4
	@Query("select c.id , count(c3) from Customer c join c.fixUpTasks f, Complaint c3 where c3 member of f.complaints group by c.id order by count(c3) desc")
	Map<Integer, Integer> topThreeCustomers();

	//Devuelve el customer asociado a una fix up task:
	@Query("select c1 from Customer c1 where ?1 member of c1.fixUpTasks")
	Customer findCustomerByFixUpTask(FixUpTask futtosearch);

}
