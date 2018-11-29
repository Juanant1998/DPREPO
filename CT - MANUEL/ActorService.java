
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Message;
import domain.MessageBox;
import domain.Profile;
import domain.SystemConfig;

@Service
@Transactional
public class ActorService {

	@Autowired
	private ActorRepository		actorRepository;

	@Autowired
	private MessageBoxService	mbs;

	@Autowired
	private MessageService		ms;


	public Actor create() {
		return new Actor();
	}
	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}
	public Actor findOne(final int actorId) {
		return this.actorRepository.findOne(actorId);
	}
	public Actor save(final Actor actor) {
		return this.actorRepository.save(actor);
	}
	public void delete(final Actor actor) {
		this.actorRepository.delete(actor);
	}
	
	public boolean isActualActorBanned(){
		
		UserAccount actual = LoginService.getPrincipal();
		Actor a = actorRepository.getActor(actual);
		
		return a.getIsBanned();

	}

	public MessageBox createNewMessageBoxNoSystem(final String username, final String msgboxname) {
		final MessageBox msgbox = this.mbs.create();
		msgbox.setName(username + " " + msgboxname);
		msgbox.setSystemBox(false);

		final MessageBox result = this.mbs.save(msgbox);

		return result;
	}

	public MessageBox createNewMessageBox(final String username, final String msgboxname) {
		final MessageBox msgbox = this.mbs.create();
		msgbox.setName(username + " " + msgboxname);
		msgbox.setSystemBox(true);

		final MessageBox result = this.mbs.save(msgbox);

		return result;
	}
	
	 public Actor editMyData(Actor a1){
	  
	  Assert.notNull(a1);
	  
	  final Actor a2 = create();
	  a2.setAddress(a1.getAddress());
	  a2.setEmail(a1.getEmail());
	  a2.setMiddleName(a1.getMiddleName());
	  a2.setName(a1.getName());
	  a2.setPhone(a1.getPhone());
	  a2.setPhoto(a1.getPhoto());
	  a2.setProfiles(new ArrayList<Profile>());
	  a2.setIsSuspicious(a1.getIsSuspicious());
	  a2.setIsBanned(a1.getIsBanned());
	  
	  
	  a2.setMessageBoxes(a1.getMessageBoxes());
	  final Actor x= this.save(a2);
	  
	  
	  return x;
	  
	  
	  }
	 

	 public Message sendMessage(final Message msg) {
			Assert.notNull(msg);

			Assert.isTrue(!msg.getSender().getIsBanned());
			final Message m = this.ms.create();
			m.setBody(msg.getBody());
			m.setMoment(msg.getMoment());
			m.setPriority(msg.getPriority());
			m.setSubject(msg.getSubject());
			m.setTags(msg.getTags());

			final Collection<Actor> recipients = msg.getRecipients();

			m.setRecipients(recipients);

			m.setSender(msg.getSender());

			final Message messageFinal = this.ms.save(m);
			
			for (final Actor a : recipients)
				this.storeMessageOnInBox(messageFinal, a);

			

			return messageFinal;
		}

	public MessageBox editMessageBox(final MessageBox m, final String newName) {
		//Otra opción es editar el nombre desde el m.getName.
		UserAccount actual = LoginService.getPrincipal();
		Actor a = actorRepository.getActor(actual);
		
		Assert.isTrue(a.getMessageBoxes().contains(m));
		Assert.isTrue(!a.getIsBanned());

		Assert.isTrue(!m.isSystemBox());
		Assert.isTrue(!m.isSystemBox());

		m.setName(newName);

		final MessageBox result = this.mbs.save(m);

		return result;

	}
	public void deleteMessageBox(final MessageBox m) {
		final UserAccount actual = LoginService.getPrincipal();
		//COMPROBAR si es el dueño de la message box
		Actor a = actorRepository.getActor(actual);
		
		Assert.isTrue(a.getMessageBoxes().contains(m));
		Assert.isTrue(!a.getIsBanned());

		Assert.isTrue(!m.isSystemBox());

		this.mbs.delete(m);

	}
	
	public boolean checkSpammer(String s){
		List<String> spamwords = SystemConfig.staticgetSpamWords();
		boolean res = false; //devolvemos esta propiedad para hacer más fácil el envío de mensajes.
		for(String spamword:spamwords){
			if(s.contains(spamword)){
				UserAccount actual = LoginService.getPrincipal();
				Actor a = actorRepository.getActor(actual);
				a.setIsSuspicious(true);
				res = true;
			}
		
		}
		
		return res;
	}

	private void storeMessageOnInBox(final Message m, final Actor a) {
		Assert.notNull(a);
		Assert.notNull(m);
		final Collection<MessageBox> msgboxes = a.getMessageBoxes();

		for (final MessageBox mbox : msgboxes)
			if (mbox.isSystemBox() == true && mbox.getName().endsWith("in")) {
				final Collection<Message> messages = mbox.getMessages();
				messages.add(m);
				mbox.setMessages(messages);
				Assert.notNull(mbox);
				MessageBox messagenew = mbs.save(mbox);

			}

		//Guardar de nuevo el actor con la nueva lista de mensajes?
	}

}
