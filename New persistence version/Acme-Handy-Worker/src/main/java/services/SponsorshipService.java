package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SponsorshipRepository;

import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {
	
	@Autowired
	private SponsorshipRepository sponsorshipRepository;
	
	public  Sponsorship create(){
		return new Sponsorship();	
	}
	public Collection<Sponsorship> findAll(){
		return sponsorshipRepository.findAll();
	}
	
	public Sponsorship findOne(int sponsorshipId){
		return sponsorshipRepository.findOne(sponsorshipId);
	}
	
	public Sponsorship save(Sponsorship sponsorship){
		return sponsorshipRepository.save(sponsorship);
	}
	
	public void delete(Sponsorship sponsorship){
		sponsorshipRepository.delete(sponsorship);
	}

}
