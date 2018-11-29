package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;


@Service
@Transactional
public class PersonalRecordService {
	
	@Autowired
	private PersonalRecordRepository personalRecordRepository;
	
	public PersonalRecord create(){
		return new PersonalRecord();	
	}
	public Collection<PersonalRecord> findAll(){
		return personalRecordRepository.findAll();
	}
	
	public PersonalRecord findOne(int personalRecordId){
		return personalRecordRepository.findOne(personalRecordId);
	}
	
	public PersonalRecord save(PersonalRecord personalRecord){
		return personalRecordRepository.save(personalRecord);
	}
	
	public void delete(PersonalRecord personalRecord){
		personalRecordRepository.delete(personalRecord);
	}

}
