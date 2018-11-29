package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MiscellaneousRecordRepository;
import domain.MiscellaneousRecord;


@Service
@Transactional
public class MiscellaneousRecordService {
	
	@Autowired
	private MiscellaneousRecordRepository miscellaneousRecordRepository;
	
	public MiscellaneousRecord create(){
		return new MiscellaneousRecord();	
	}
	public Collection<MiscellaneousRecord> findAll(){
		return miscellaneousRecordRepository.findAll();
	}
	
	public MiscellaneousRecord findOne(int miscellaneousRecordId){
		return miscellaneousRecordRepository.findOne(miscellaneousRecordId);
	}
	
	public MiscellaneousRecord save(MiscellaneousRecord miscellaneousRecord){
		return miscellaneousRecordRepository.save(miscellaneousRecord);
	}
	
	public void delete(MiscellaneousRecord miscellaneousRecord){
		miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

}
