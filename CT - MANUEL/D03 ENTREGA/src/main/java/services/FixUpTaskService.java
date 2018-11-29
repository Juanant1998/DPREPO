package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import repositories.FixUpTaskRepository;
import domain.FixUpTask;

@Service
@Transactional
public class FixUpTaskService {

	@Autowired
	private FixUpTaskRepository fixUpTaskRepository; 
	
	public FixUpTask create(){
		return new FixUpTask();
	}
	public Collection<FixUpTask> findAll(){
		return fixUpTaskRepository.findAll();
	}
	public FixUpTask findOne(int fixUpTaskId){
		return fixUpTaskRepository.findOne(fixUpTaskId);
	}
	public FixUpTask save(FixUpTask fixUpTask){
		return fixUpTaskRepository.save(fixUpTask);
	}
	public void delete(FixUpTask fixUpTask){
		fixUpTaskRepository.delete(fixUpTask);
	}
}
