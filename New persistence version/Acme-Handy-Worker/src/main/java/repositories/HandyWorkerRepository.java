
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {
	
	//C10
	@Query("select h1.id from HandyWorker h1 inner join h1.applications a1 where (a1.status = 'ACCEPTED') and (select count(a2) from Application a2 where a2.id = a1.id and a2.status='ACCEPTED') > (select count(a1)*1.0 / (select count (a2) from Application a2)*1.0 from Application a1 where a1.status='ACCEPTED')*1.1 group by 'h' order by h1.applications.size")
	Collection<HandyWorker> tenPercentMoreAcceptedApplications();

}
