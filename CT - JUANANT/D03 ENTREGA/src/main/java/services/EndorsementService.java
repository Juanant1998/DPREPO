package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Endorsement;

import repositories.EndorsementRepository;



@Service
@Transactional
public class EndorsementService {

	@Autowired
	private EndorsementRepository endorsementRepository; 
	
	public Endorsement create(){
		return new Endorsement();
	}
	public Collection<Endorsement> findAll(){
		return endorsementRepository.findAll();
	}
	public Endorsement findOne(int endorsementId){
		return endorsementRepository.findOne(endorsementId);
	}
	public Endorsement save(Endorsement endorsement){
		return endorsementRepository.save(endorsement);
	}
	public void delete(Endorsement endorsement){
		endorsementRepository.delete(endorsement);
	}
}
