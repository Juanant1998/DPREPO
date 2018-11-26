
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

	public MessageBox createNewMessageBox(final String username, final String msgboxname) {
		final MessageBox msgbox = this.mbs.create();
		msgbox.setName(username + " " + msgboxname);
		msgbox.setSystemBox(true);

		final MessageBox result = this.mbs.save(msgbox);

		return result;
	}

	/*
	 * public Actor editData(Actor a1,String username,String password){
	 * 
	 * Assert.notNull(a1);
	 * Assert.notNull(username);
	 * Assert.notNull(password);
	 * 
	 * final Actor a2 = create();
	 * a2.setAddress(a1.getAddress());
	 * a2.setEmail(a1.getEmail());
	 * a2.setMiddleName(a1.getMiddleName());
	 * a2.setName(a1.getName());
	 * a2.setPhone(a1.getPhone());
	 * a2.setPhoto(a1.getPhoto());
	 * a2.setProfiles(new ArrayList<Profile>());
	 * a2.setIsSuspicious(a1.getIsSuspicious());
	 * a2.setIsBanned(a1.getIsBanned());
	 * 
	 * final MessageBox in = this.createNewMessageBox(username, "-in");
	 * final MessageBox out = this.createNewMessageBox(username, "-out");
	 * final MessageBox trash = this.createNewMessageBox(username, "-trash");
	 * final MessageBox spam = this.createNewMessageBox(username, "-spam");
	 * 
	 * final Collection<MessageBox> msboxes = new ArrayList<MessageBox>();
	 * msboxes.add(in);
	 * msboxes.add(out);
	 * msboxes.add(trash);
	 * msboxes.add(spam);
	 * 
	 * a2.setMessageBoxes(msboxes);
	 * final Actor x= this.save(a2);
	 * 
	 * 
	 * return x;
	 * 
	 * 
	 * }
	 */

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

		for (final Actor a : recipients)
			this.storeMessageOnInBox(m, a);

		final Message messageFinal = this.ms.save(m);

		return messageFinal;
	}

	public MessageBox editMessageBox(final MessageBox m, final String newName) {
		//Otra opción es editar el nombre desde el m.getName.

		Assert.isTrue(!m.isSystemBox());

		m.setName(newName);

		final MessageBox result = this.mbs.save(m);

		return result;

	}
	public void deleteMessageBox(final MessageBox m) {
		//final UserAccount actual = LoginService.getPrincipal();
		//COMPROBAR si es el dueño de la message box

		Assert.isTrue(!m.isSystemBox());

		this.mbs.delete(m);

	}

	private void storeMessageOnInBox(final Message m, final Actor a) {
		Assert.notNull(a);
		Assert.notNull(m);
		final Collection<MessageBox> msgboxes = a.getMessageBoxes();

		MessageBox in = null;
		for (final MessageBox mbox : msgboxes)
			if (mbox.isSystemBox() == true && mbox.getName().endsWith("in")) {
				final Collection<Message> messages = mbox.getMessages();
				messages.add(m);
				mbox.setMessages(messages);
				in = mbox;
				break;
			}

		Assert.notNull(in);
		//Guardar de nuevo el actor con la nueva lista de mensajes?
	}

}
