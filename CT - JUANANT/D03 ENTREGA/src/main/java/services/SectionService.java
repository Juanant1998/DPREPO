package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SectionRepository;

import domain.Section;

@Service
@Transactional
public class SectionService {
	
	@Autowired
	private SectionRepository sectionRepository;
	
	public Section create(){
		return new Section();	
	}
	public Collection<Section> findAll(){
		return sectionRepository.findAll();
	}
	
	public Section findOne(int sectionId){
		return sectionRepository.findOne(sectionId);
	}
	
	public Section save(Section section){
		return sectionRepository.save(section);
	}
	
	public void delete(Section section){
		sectionRepository.delete(section);
	}

}
