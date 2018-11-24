
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import domain.Actor;
import domain.Message;
import domain.MessageBox;

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

	public Message sendMessage(final Message msg) {
		Assert.notNull(msg);

		final Message m = this.ms.create();
		m.setBody(msg.getBody());
		m.setMoment(msg.getMoment());
		m.setPriority(msg.getPriority());
		m.setSubject(msg.getSubject());
		m.setTags(msg.getTags());

		final Collection<Actor> recipients = msg.getRecipients();

		m.setRecipients(recipients);

		m.setSender(msg.getSender());
		/*
		 * for(Actor a: recipients){
		 * storeMessageOnInBox(m, a);
		 * }
		 */
		final Message messageFinal = this.ms.save(m);

		return messageFinal;
	}

	public void deleteMessage(final Message msg) {
		this.ms.delete(msg);
	}

	public MessageBox createNewMessageBox(final String name) {
		final MessageBox messagebox = this.mbs.create();
		Assert.notNull(name);

		messagebox.setName(name);
		messagebox.setSystemBox(false);

		final MessageBox result = this.mbs.save(messagebox);
		return result;
	}

	/*
	 * public void deleteMessageBox(final MessageBox m) {
	 * Collection<Authority> auth = LoginService.getPrincipal().getAuthorities();
	 * //INSERTAR QUERY: select a1 from actor
	 * if(auth.contains(o))
	 * Assert.isTrue(!m.isSystemBox());
	 * Assert.isTrue(LoginService.getPrincipal() == m.g)
	 * this.mbs.delete(m);
	 * }
	 */
	public MessageBox editMessageBox(final MessageBox m, final String newName) {
		//Otra opción es editar el nombre desde el m.getName.

		Assert.isTrue(!m.isSystemBox());

		m.setName(newName);

		final MessageBox result = this.mbs.save(m);

		return result;

	}

	/*
	 * private void storeMessageOnInBox(Message m, Actor a) {
	 * Collection<MessageBox> msgboxes = a.getMessageBoxes();
	 * 
	 * MessageBox in = null;
	 * for(MessageBox mbox : msgboxes){
	 * if(mbox.isSystemBox() == true && mbox.getName().endsWith("in")){
	 * in = mbox;
	 * break;
	 * }
	 * }
	 * 
	 * Assert.notNull(in);
	 * 
	 * 
	 * }
	 */
}
