
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {
	
	//C2
	@Query("select avg(f.applications.size)from FixUpTask f")
	Double averageApplicationsByFixUpTasks();
	
	@Query("select min(f.applications.size)from Customer c")
	Integer minApplicationsByFixUpTasks();
	
	@Query("select max(f.applications.size)from Customer c")
	Integer maxApplicationsByFixUpTasks();
	
	@Query("select sqrt(sum(f.applications.size * f.applications.size)/ count(f.applications.size) - avg(f.applications.size) * avg(f.applications.size))from FixUpTask")
	Integer desviationApplicationsByFixUpTasks();
	
	
	//C3
	@Query("select avg(f.MaximunPrice)from FixUpTask f")
	Double averageMaximunPriceByFixUpTasks();
	
	@Query("select min(f.MaximunPrice)from Customer c")
	Integer minMaximunPriceByFixUpTasks();
	
	@Query("select max(f.MaximunPrice)from Customer c")
	Integer maxAMaximunPriceByFixUpTasks();
	
	@Query("select sqrt(sum(f.maximumPrice * f.maximumPrice)/ count(f.maximumPrice) - avg(f.maximumPrice) * avg(f.maximumPrice))from FixUpTask")
	Integer desviationMaximunPriceByFixUpTasks();


}
