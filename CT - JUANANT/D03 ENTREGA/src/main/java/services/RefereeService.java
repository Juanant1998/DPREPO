package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.RefereeRepository;

import domain.Referee;

@Service
@Transactional
public class RefereeService {
	
	@Autowired
	private RefereeRepository refereeRepository;
	
	public Referee create(){
		return new Referee();	
	}
	public Collection<Referee> findAll(){
		return refereeRepository.findAll();
	}
	
	public Referee findOne(int refereeId){
		return refereeRepository.findOne(refereeId);
	}
	
	public Referee save(Referee referee){
		return refereeRepository.save(referee);
	}
	
	public void delete(Referee referee){
		refereeRepository.delete(referee);
	}

}
