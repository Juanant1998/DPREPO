package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProfileRepository;
import domain.Profile;


@Service
@Transactional
public class ProfileService {
	
	@Autowired
	private ProfileRepository profileRepository;
	
	public Profile create(){
		return new Profile();	
	}
	public Collection<Profile> findAll(){
		return profileRepository.findAll();
	}
	
	public Profile findOne(int profileId){
		return profileRepository.findOne(profileId);
	}
	
	public Profile save(Profile profile){
		return profileRepository.save(profile);
	}
	
	public void delete(Profile profile){
		profileRepository.delete(profile);
	}

}
