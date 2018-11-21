package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.CreditCard;

import repositories.CreditCardRepository;


@Service
@Transactional
public class CreditCardService {

	@Autowired
	private CreditCardRepository creditCardRepository; 
	
	public CreditCard create(){
		return new CreditCard();
	}
	public Collection<CreditCard> findAll(){
		return creditCardRepository.findAll();
	}
	public CreditCard findOne(int creditCardId){
		return creditCardRepository.findOne(creditCardId);
	}
	public CreditCard save(CreditCard creditCard){
		return creditCardRepository.save(creditCard);
	}
	public void delete(CreditCard creditCard){
		creditCardRepository.delete(creditCard);
	}

}




