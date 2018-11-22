package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProfessionalRecordRepository;
import domain.ProfessionalRecord;


@Service
@Transactional
public class ProfessionalRecordService {
	
	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;
	
	public ProfessionalRecord create(){
		return new ProfessionalRecord();	
	}
	public Collection<ProfessionalRecord> findAll(){
		return professionalRecordRepository.findAll();
	}
	
	public ProfessionalRecord findOne(int professionalRecord){
		return professionalRecordRepository.findOne(professionalRecord);
	}
	
	public ProfessionalRecord save(ProfessionalRecord professionalRecord){
		return professionalRecordRepository.save(professionalRecord);
	}
	
	public void delete(ProfessionalRecord professionalRecord){
		professionalRecordRepository.delete(professionalRecord);
	}

}
