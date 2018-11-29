
package repositories;

import java.util.Collection;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.FixUpTask;
import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	//C10
	@Query("select h1.id from HandyWorker h1 inner join h1.applications a1 where (a1.status = 'ACCEPTED') and (select count(a2) from Application a2 where a2.id = a1.id and a2.status='ACCEPTED') > (select count(a1)*1.0 / (select count (a2) from Application a2)*1.0 from Application a1 where a1.status='ACCEPTED')*1.1 group by 'h' order by h1.applications.size")
	Collection<HandyWorker> tenPercentMoreAcceptedApplications();

	//B5
	@Query("select h.id, count(c3) from HandyWorker h join h.applications a2,FixUpTask f join f.applications a, Complaint c3 where c3 member of f.complaints and (a.id = a2.id  and a2.status = 'ACCEPTED') group by h.id order by count(c3) desc")
	Map<Integer, Integer> topThreeHandyWorkers();
	
	
	@Query("select f1 from FixUpTask f1 join f1.applications a1, HandyWorker h1 where a1 member of h1.applications and a1.status = 'ACCEPTED' and h1.userAccount = ?1")
	Collection<FixUpTask> getFixUpTaskByHandyWorker(UserAccount u);

}
