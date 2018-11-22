package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.FinderRepository;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository finderRepository; 
	
	public Finder create(){
		return new Finder();
	}
	public Collection<Finder> findAll(){
		return finderRepository.findAll();
	}
	public Finder findOne(int finderId){
		return finderRepository.findOne(finderId);
	}
	public Finder save(Finder finder){
		return finderRepository.save(finder);
	}
	public void delete(Finder finder){
		finderRepository.delete(finder);
	}
}
