package services;



import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Administrator;

import repositories.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {
	@Autowired
	private AdministratorRepository administratorRepository; 
	
	public Administrator create(){
		return new Administrator();
	}
	public Collection<Administrator> findAll(){
		return administratorRepository.findAll();
	}
	public Administrator findOne(int administratorId){
		return administratorRepository.findOne(administratorId);
	}
	public Administrator save(Administrator administrator){
		return administratorRepository.save(administrator);
	}
	public void delete(Administrator administrator){
		administratorRepository.delete(administrator);
	}

}
