package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.EndorserRecord;

import repositories.EndorserRecordRepository;


@Service
@Transactional
public class EndorserRecordService {

	@Autowired
	private EndorserRecordRepository endorserRecordRepository; 
	
	public EndorserRecord create(){
		return new EndorserRecord();
	}
	public Collection<EndorserRecord> findAll(){
		return endorserRecordRepository.findAll();
	}
	public EndorserRecord findOne(int endorserRecordId){
		return endorserRecordRepository.findOne(endorserRecordId);
	}
	public EndorserRecord save(EndorserRecord endorserRecord){
		return endorserRecordRepository.save(endorserRecord);
	}
	public void delete(EndorserRecord endorserRecord){
		endorserRecordRepository.delete(endorserRecord);
	}
}
