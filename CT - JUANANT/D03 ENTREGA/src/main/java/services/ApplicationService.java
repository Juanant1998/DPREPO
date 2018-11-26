package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Application;

import repositories.ApplicationRepository;


@Service
@Transactional
public class ApplicationService {
	@Autowired
	private ApplicationRepository applicationRepository; 
	
	public Application create(){
		return new Application();
	}
	public Collection<Application> findAll(){
		return applicationRepository.findAll();
	}
	public Application findOne(int applicationId){
		return applicationRepository.findOne(applicationId);
	}
	public Application save(Application application){
		return applicationRepository.save(application);
	}
	public void delete(Application application){
		applicationRepository.delete(application);
	}

}
