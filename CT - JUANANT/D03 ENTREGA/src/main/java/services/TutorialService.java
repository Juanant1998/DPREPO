package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import domain.Tutorial;

import repositories.TutorialRepository;

@Service
@Transactional
public class TutorialService {
	
	@Autowired
	private TutorialRepository tutorialRepository;
	
	public Tutorial create(){
		return new Tutorial();	
	}
	public Collection<Tutorial> findAll(){
		return tutorialRepository.findAll();
	}
	
	public Tutorial findOne(int tutorialId){
		return tutorialRepository.findOne(tutorialId);
	}
	
	public Tutorial save(Tutorial tutorial){
		return tutorialRepository.save(tutorial);
	}
	
	public void delete(Tutorial tutorial){
		tutorialRepository.delete(tutorial);
	}

}
