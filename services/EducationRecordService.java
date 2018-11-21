package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.EducationRecordRepository;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	@Autowired
	private EducationRecordRepository educationRecordRepository; 
	
	public EducationRecord create(){
		return new EducationRecord();
	}
	public Collection<EducationRecord> findAll(){
		return educationRecordRepository.findAll();
	}
	public EducationRecord findOne(int educationRecordId){
		return educationRecordRepository.findOne(educationRecordId);
	}
	public EducationRecord save(EducationRecord educationRecord){
		return educationRecordRepository.save(educationRecord);
	}
	public void delete(EducationRecord educationRecord){
		educationRecordRepository.delete(educationRecord);
	}
}
