package services;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageRepository;
import domain.Message;


@Service
@Transactional
public class MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	
	public Message create(){
		return new Message();	
	}
	public Collection<Message> findAll(){
		return messageRepository.findAll();
	}
	
	public Message findOne(int messageId){
		return messageRepository.findOne(messageId);
	}
	
	public Message save(Message message){
		return messageRepository.save(message);
	}
	
	public void delete(Message message){
		messageRepository.delete(message);
	}

}
