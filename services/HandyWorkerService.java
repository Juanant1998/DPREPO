package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.HandyWorker;

import repositories.HandyWorkerRepository;


@Service
@Transactional
public class HandyWorkerService {

	@Autowired
	private HandyWorkerRepository handyWorkerRepository; 
	
	public HandyWorker create(){
		return new HandyWorker();
	}
	public Collection<HandyWorker> findAll(){
		return handyWorkerRepository.findAll();
	}
	public HandyWorker findOne(int handyWorkerId){
		return handyWorkerRepository.findOne(handyWorkerId);
	}
	public HandyWorker save(HandyWorker handyWorker){
		return handyWorkerRepository.save(handyWorker);
	}
	public void delete(HandyWorker handyWorker){
		handyWorkerRepository.delete(handyWorker);
	}
}
