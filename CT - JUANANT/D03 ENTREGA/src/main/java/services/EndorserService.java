package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorserRepository;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {

	@Autowired
	private EndorserRepository endorserRepository; 
	
	public Endorser create(){
		return new Endorser();
	}
	public Collection<Endorser> findAll(){
		return endorserRepository.findAll();
	}
	public Endorser findOne(int endorserId){
		return endorserRepository.findOne(endorserId);
	}
	public Endorser save(Endorser endorser){
		return endorserRepository.save(endorser);
	}
	public void delete(Endorser endorser){
		endorserRepository.delete(endorser);
	}
}
