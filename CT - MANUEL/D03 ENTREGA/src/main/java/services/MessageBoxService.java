package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageBoxRepository;
import domain.MessageBox;

@Service
@Transactional
public class MessageBoxService {
	
	@Autowired
	private MessageBoxRepository messageBoxRepository;
	
	public MessageBox create(){
		return new MessageBox();	
	}
	public Collection<MessageBox> findAll(){
		return messageBoxRepository.findAll();
	}
	
	public MessageBox findOne(int messageBoxId){
		return messageBoxRepository.findOne(messageBoxId);
	}
	
	public MessageBox save(MessageBox messageBox){
		return messageBoxRepository.save(messageBox);
	}
	
	public void delete(MessageBox messageBox){
		messageBoxRepository.delete(messageBox);
	}

}
