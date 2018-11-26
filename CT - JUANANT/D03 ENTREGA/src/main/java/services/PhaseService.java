package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Phase;

import repositories.PhaseRepository;

@Transactional
@Service
public class PhaseService {
	
	@Autowired
	private PhaseRepository phaseRepository;
	
	public Phase create(){
		return new Phase();	
	}
	public Collection<Phase> findAll(){
		return phaseRepository.findAll();
	}
	
	public Phase findOne(int phaseId){
		return phaseRepository.findOne(phaseId);
	}
	
	public Phase save(Phase phase){
		return phaseRepository.save(phase);
	}
	
	public void delete(Phase phase){
		phaseRepository.delete(phase);
	}
	

}
