
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Finder;
import domain.FixUpTask;
import domain.Warranty;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	//Finder query for keyword:
	@Query("select f1 from FixUpTask f1 where f1.ticker like ?1 or f1.address like ?1 or f1.description like ?1")
	Collection<FixUpTask> filterTasksWithKeyword(String k);

	//Finder query for prices:
	@Query("select f1 from FixUpTask f1 where f1.maximumPrice between ?1 and ?2")
	Collection<FixUpTask> filterTasksWithPriceRange(Double minPrice, Double maxPrice);

	//Finder query for Dates:
	@Query("select f1 from FixUpTask f1 where f1.endDate between ?1 and ?2")
	Collection<FixUpTask> filterTasksWithDateRange(Date minDate, Date maxDate);

	//Finder query for Warranty:
	@Query("select f1 from FixUpTask f1 where f1.warranty = ?1")
	Collection<FixUpTask> filterTasksWithWarranty(Warranty w);

	//Finder query for Category:
	@Query("select f1 from FixUpTask f1 where f1.category = ?1")
	Collection<FixUpTask> filterTasksWithCategory(Category c);

}
