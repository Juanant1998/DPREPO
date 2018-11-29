package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {
	
	//B2
	@Query("select avg(r.notes.size) from Report r")
	Double averageNotesByReports();

	@Query("select min(r.notes.size) from Report r")
	Integer minNotesByReports();

	@Query("select max(r.notes.size)from Report r")
	Integer maxNotesByReports();

	@Query("select sqrt(sum(r.notes.size * r.notes.size) / count(r.notes.size) - avg(r.notes.size) * avg(r.notes.size)) from Report r")
	Integer desviationNotesByReports();

}
