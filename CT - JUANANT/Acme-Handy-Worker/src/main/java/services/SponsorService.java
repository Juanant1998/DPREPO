package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.SponsorRepository;
import domain.Sponsor;


@Service
@Transactional
public class SponsorService {
	
	@Autowired
	private SponsorRepository sponsorRepository;
	
	public Sponsor create(){
		return new Sponsor();	
	}
	public Collection<Sponsor> findAll(){
		return sponsorRepository.findAll();
	}
	
	public Sponsor findOne(int sponsorId){
		return sponsorRepository.findOne(sponsorId);
	}
	
	public Sponsor save(Sponsor sponsor){
		return sponsorRepository.save(sponsor);
	}
	
	public void delete(Sponsor sponsor){
		sponsorRepository.delete(sponsor);
	}

}
