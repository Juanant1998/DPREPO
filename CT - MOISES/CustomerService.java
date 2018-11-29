
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.MessageBox;
import domain.Note;
import domain.Report;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository	customerRepository;
	
	@Autowired
	private NoteService ns;
	
	@Autowired
	private UserAccountService	uas;

	@Autowired
	private ApplicationService	as;

	@Autowired
	private ActorService actorservice;
	
	@Autowired
	private ReportService rs;

	public Customer create() {
		return new Customer();
	}
	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}
	public Customer findOne(final int customerId) {
		return this.customerRepository.findOne(customerId);
	}
	public Customer save(final Customer customer) {
		return this.customerRepository.save(customer);
	}
	public void delete(final Customer customer) {
		this.customerRepository.delete(customer);
	}
	public void checkAuthority() {

		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.notNull(user);
		final Collection<Authority> authority = user.getAuthorities();
		Assert.notNull(authority);
		final Authority a1 = new Authority();
		a1.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(authority.contains(a1));

	}


	@Autowired
	private MessageBoxService	mbs;




	public Customer register(final Customer c1) {
		Assert.notNull(c1);
		
		Collection<UserAccount> uss = uas.findAll();

		//Assert.isTrue(!(uss.contains(c1.getUserAccount())));
		final Customer c2 = this.create();
		c2.setAddress(c1.getAddress());
		c2.setEmail(c1.getEmail());
		c2.setFixUpTasks(new ArrayList<FixUpTask>());
		c2.setMiddleName(c1.getMiddleName());
		c2.setName(c1.getName());
		c2.setPhone(c1.getPhone());
		c2.setPhoto(c1.getPhoto());
		c2.setIsSuspicious(c1.getIsSuspicious());
		c2.setIsBanned(c1.getIsBanned());

		final MessageBox in = this.actorservice.createNewMessageBox(c1.getUserAccount().getUsername(),"-in");
		final MessageBox out = this.actorservice.createNewMessageBox(c1.getUserAccount().getUsername(), "-out");
		final MessageBox trash = this.actorservice.createNewMessageBox(c1.getUserAccount().getUsername(), "-trash");
		final MessageBox spam = this.actorservice.createNewMessageBox(c1.getUserAccount().getUsername(), "-spam");

		final Collection<MessageBox> msboxes = new ArrayList<MessageBox>();
		msboxes.add(in);
		msboxes.add(out);
		msboxes.add(trash);
		msboxes.add(spam);

		c2.setMessageBoxes(msboxes);
		final Customer x = this.save(c2);

		return x;

	}

	public Collection<FixUpTask> getMyFixUpTasks() {
		Assert.isTrue(!actorservice.isActualActorBanned());
		final UserAccount actual = LoginService.getPrincipal();
		Assert.notNull(actual);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.CUSTOMER);
		Assert.isTrue(actual.getAuthorities().contains(auth));

		//is account banned?

		final Customer c = this.uas.getCustomerByUserAccount(actual);

		return c.getFixUpTasks();

	}

	public Collection<Application> getApplicationsForFUT(final FixUpTask fut) {
		Assert.isTrue(!actorservice.isActualActorBanned());
		final Collection<Application> result = fut.getApplications();

		return result;
	}

	public Application updateApplication(final Application app, final String status, final String comments) {
		Assert.isTrue(!actorservice.isActualActorBanned());
		final Application appcopia = app;
		app.setStatus(status);

		if (status == "ACCEPTED" && app.getCreditCard().equals(null))
			app.setComments(appcopia.getComments() + "\n you must provide a valid credit card number in order to perform the task.");
		final Application result = this.as.save(app);

		if (comments != null)
			app.setComments(appcopia.getComments() + "\n Customer comments : " + comments);

		return result;
	}
	
	public Collection<Complaint> getMyComplaints(){
		
		Customer actual = uas.getCustomerByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(!actual.getIsBanned());
		Collection<FixUpTask> fixuptasks =  actual.getFixUpTasks();
		Collection<Complaint> result = new ArrayList<Complaint>();
		for(FixUpTask f : fixuptasks){
			Collection<Complaint> c = f.getComplaints();
			for(Complaint x:c){
				result.add(x);
			}
		}
		return result;
	}
	
	public Note writeNoteForReport(Note n, Report r){
		Customer actual = uas.getCustomerByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(!actual.getIsBanned());
		Collection<Note> notes = r.getNotes();
			notes.add(n);
			r.setNotes(notes);
			Note result = ns.save(n);
			Report rActualizado = rs.save(r);
			
			return result;
		}
	
	public Note writeCommentOnNote(Note n, String comments){
		Customer actual = uas.getCustomerByUserAccount(LoginService.getPrincipal());
		Assert.isTrue(!actual.getIsBanned());
		
		Collection<FixUpTask> fixuptasks = new ArrayList<FixUpTask>();
		Collection<Complaint> comp = new ArrayList<Complaint>();
		Collection<Report> reports = new ArrayList<Report>();
		/*boolean z = false; //comentar y poner a false
		for(FixUpTask f:fixuptasks){
			for(Complaint x : f.getComplaints()){
				comp.add(x);
			}
		}
		
		for(Complaint c: comp){
			for(Note notas: c.getReport().getNotes()){
				if (notas.getId() == n.getId()){
					z = true;
				}
			}
		}
		
		Assert.isTrue(z);*/
		
		
		n.setComment(n.getComment() + "Comentario de " + LoginService.getPrincipal().getUsername() + ": " + comments);
		Note result = ns.save(n);
		
		return result;
	
	
	
	}
}
