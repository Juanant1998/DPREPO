package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.Complaint;

import repositories.ComplaintRepository;


@Service
@Transactional
public class ComplaintService {

	@Autowired
	private ComplaintRepository complaintRepository; 
	
	public Complaint create(){
		return new Complaint();
	}
	public Collection<Complaint> findAll(){
		return complaintRepository.findAll();
	}
	public Complaint findOne(int complaintId){
		return complaintRepository.findOne(complaintId);
	}
	public Complaint save(Complaint complaint){
		return complaintRepository.save(complaint);
	}
	public void delete(Complaint complaint){
		complaintRepository.delete(complaint);
	}

}


