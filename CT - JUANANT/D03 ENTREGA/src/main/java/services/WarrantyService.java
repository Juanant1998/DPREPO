package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Warranty;

import repositories.WarrantyRepository;

@Service
@Transactional
public class WarrantyService {
	
	@Autowired
	private WarrantyRepository warrantyRepository;
	
	public Warranty create(){
		return new Warranty();
	}
	
	public Collection<Warranty> findAll(){
		return warrantyRepository.findAll();
	}
	
	public Warranty findOne(int warrantyId){
		return warrantyRepository.findOne(warrantyId);
	}
	
	public Warranty save(Warranty warranty){
		return warrantyRepository.save(warranty);
	}
	
	public void delete(Warranty warranty){
		warrantyRepository.delete(warranty);
	}

}
