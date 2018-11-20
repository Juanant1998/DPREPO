
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.EndorserRecord;

@Repository
public interface EndorsementRecordRepository extends JpaRepository<EndorserRecord, Integer> {

}
