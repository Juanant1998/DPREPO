
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

	@Query("select min(f.applications.size)from FixUpTask f")
	Integer minApplicationsByFixUpTasks();

	@Query("select max(f.applications.size)from FixUpTask f")
	Integer maxApplicationsByFixUpTasks();

	@Query("select sqrt(sum(f.applications.size * f.applications.size)/ count(f.applications.size) - avg(f.applications.size) * avg(f.applications.size))from FixUpTask f")
	Integer desviationApplicationsByFixUpTasks();

	//C3
	@Query("select avg(f.maximumPrice)from FixUpTask f")
	Double averageMaximunPriceByFixUpTasks();

	@Query("select min(f.maximumPrice)from FixUpTask f")
	Integer minMaximunPriceByFixUpTasks();

	@Query("select max(f.maximumPrice)from FixUpTask f")
	Integer maxAMaximunPriceByFixUpTasks();

	@Query("select sqrt(sum(f.maximumPrice * f.maximumPrice)/ count(f.maximumPrice) - avg(f.maximumPrice) * avg(f.maximumPrice))from FixUpTask f")
	Integer desviationMaximumPriceByFixUpTasks();
	
	
	//B1

	@Query("select avg(f.complaints.size)from FixUpTask f")
	Double averageComplaintsByFixUpTasks();

	@Query("select min(f.complaints.size)from FixUpTask f")
	Integer minComplaintsByFixUpTasks();

	@Query("select max(f.complaints.size)from FixUpTask f")
	Integer maxComplaintsByFixUpTasks();

	@Query("select sqrt(sum(f.complaints.size * f.complaints.size) / count(f.complaints.size) - avg(f.complaints.size) * avg(f.complaints.size)) from FixUpTask f")
	Integer desviationComplaintsByFixUpTasks();
		
	
	//B3
	@Query("select count(f1)*1.0 / (select count(f2) from FixUpTask f2)*1.0 from FixUpTask f1 where f1.complaints.size > 0")
	Double ratioOneComplaintForFixUpTasks();

}
