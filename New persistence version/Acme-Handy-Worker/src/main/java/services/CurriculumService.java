package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Curriculum;

import repositories.CurriculumRepository;


@Service
@Transactional
public class CurriculumService {

	@Autowired
	private CurriculumRepository curriculumRepository; 
	
	public Curriculum create(){
		return new Curriculum();
	}
	public Collection<Curriculum> findAll(){
		return curriculumRepository.findAll();
	}
	public Curriculum findOne(int curriculumId){
		return curriculumRepository.findOne(curriculumId);
	}
	public Curriculum save(Curriculum curriculum){
		return curriculumRepository.save(curriculum);
	}
	public void delete(Curriculum curriculum){
		curriculumRepository.delete(curriculum);
	}
}
