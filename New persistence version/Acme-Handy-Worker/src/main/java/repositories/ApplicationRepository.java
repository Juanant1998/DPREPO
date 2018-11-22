
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	
	//C4
	@Query("select avg(a.offeredPrice)from Application a")
	Double averageOfferedPriceByFixUpTasks();
	
	@Query("select min(a.offeredPrice)from Customer c")
	Integer minOfferedPriceByFixUpTasks();
	
	@Query("select max(a.offeredPrice)from Customer c")
	Integer maxOfferedPriceByFixUpTasks();
	
	@Query("select sqrt(sum(a.offeredPrice * a.offeredPrice)/ count(a.offeredPrice) - avg(a.offeredPrice) * avg(a.offeredPrice)) from Application a")
	Integer desviationOfferedPriceByFixUpTasks();
	
	
	//C5
	@Query("select count(a1)*1.0 / (select count(a2) from Application a2)*1.0 from Application a1 where a1.status='PENDING'")
	Double ratioPendingAplications();
	
	
	//C6
	@Query("select count(a1)*1.0 / (select count(a2) from Application a2)*1.0 from Application a1 where a1.status='ACCEPTED'")
	Double ratioAcceptedAplications();
	
	
	//C7
	@Query("select count(a1)*1.0 / (select count(a2) from Application a2)*1.0 from Application a1 where a1.status='REJECTED'")
	Double ratioRejectedAplications();
	
	//C8
	@Query("select count(a1)*1.0 / (select count(a2)*1.0 from Application a2) from FixUpTask f1 join f1.applications a1 where f1.endDate<CURRENT_DATE and a1.status='PENDING'")
	Double ratioPendingApplicationsSpiret();

}
