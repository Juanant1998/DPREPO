package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Category;

import repositories.CategoryRepository;


@Service
@Transactional
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository; 
	
	public Category create(){
		return new Category();
	}
	public Collection<Category> findAll(){
		return categoryRepository.findAll();
	}
	public Category findOne(int categoryId){
		return categoryRepository.findOne(categoryId);
	}
	public Category save(Category category){
		return categoryRepository.save(category);
	}
	public void delete(Category category){
		categoryRepository.delete(category);
	}

}

