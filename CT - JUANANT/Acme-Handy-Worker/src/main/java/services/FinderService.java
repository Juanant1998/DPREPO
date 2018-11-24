
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FinderRepository;
import domain.Finder;
import domain.FixUpTask;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	finderRepository;


	public Finder create() {
		final Collection<FixUpTask> f = new ArrayList<FixUpTask>();
		final Finder result = new Finder();
		result.setFixUpTasks(f);

		return result;
	}
	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();

	}

	public Finder createFinderByKeyword(final String keyword) {
		final Finder result = this.create();
		result.setQuery(keyword);

		final Collection<FixUpTask> fut = this.finderRepository.filterTasksWithKeyword(keyword);

		result.setFixUpTasks(fut);
		return result;

	}

	public Finder createFinderByDateRange(final Date start, final Date end) {
		final Finder result = this.create();
		result.setStartDate(start);
		result.setEndDate(end);

		final Collection<FixUpTask> fut = this.finderRepository.filterTasksWithDateRange(start, end);

		result.setFixUpTasks(fut);
		return result;
	}

	public Finder createFinderByPriceRange(final Double min, final Double max) {
		final Finder result = this.create();
		result.setMinPrice(min);
		result.setMaxPrice(max);

		final Collection<FixUpTask> fut = this.finderRepository.filterTasksWithPriceRange(min, max);

		result.setFixUpTasks(fut);
		return result;
	}
	//Añadir atributos para categoría y garantía.

	/*
	 * public Finder createFinderByCategory(Category c){
	 * final Finder result = this.create();
	 * result.set;
	 * 
	 * Collection<FixUpTask> fut = finderRepository.filterTasksWithKeyword(keyword);
	 * 
	 * result.setFixUpTasks(fut);
	 * return result;
	 * }
	 */

	public Finder findOne(final int finderId) {
		return this.finderRepository.findOne(finderId);
	}
	public Finder save(final Finder finder) {
		return this.finderRepository.save(finder);
	}
	public void delete(final Finder finder) {
		this.finderRepository.delete(finder);
	}
}
