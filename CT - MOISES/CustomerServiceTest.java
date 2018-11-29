package services;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Note;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:spring/dataSource.xml",
		"classpath:spring/config/packages.xml"
	})


@Transactional
public class CustomerServiceTest extends AbstractTest {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private FixUpTaskService fixUpTaskService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private ComplaintService complaintService;
	

	
	@Test
	public void testSaveCustomers(){
		Customer saved = this.customerService.findOne(super.getEntityId("customer1"));
		Collection<Customer> customers = new ArrayList<>();
		
		this.customerService.save(saved);
		customers.add(saved);
		
		Assert.isTrue(customers.contains(saved));
		
	}
	@Test
	public void deleteTest(){
		Customer saved = this.customerService.findOne(super.getEntityId("customer1"));
		
		this.customerService.delete(saved);
		Assert.isNull(this.customerService.findOne(saved.getId()));
	}

	@Test
	public void testFindAll() {
		Collection<Customer> customers;
		customers = this.customerService.findAll();
		Assert.isTrue(!customers.isEmpty());
	}
	
	@Test
	public void testFindOne(){
		Customer c;
		c = this.customerService.findOne(super.getEntityId("customer2"));
		int findId = c.getId();
		Assert.notNull(this.customerService.findOne(findId));
		
	}

	@Test
	public void testCreate(){
		Customer c = this.customerService.create();
		Assert.notNull(c);
	}
@Test 
public void testRegister(){
super.authenticate("customer2");
	Customer c;
	c = this.customerService.findOne(super.getEntityId("customer2"));
	UserAccount  n  = new UserAccount();
	Authority  s  = new Authority();
	n.setUsername("antonioqwwq");
	n.setPassword("21232f297a57a5a743894a0e4a801fc2");
	s.setAuthority(Authority.CUSTOMER);
	Collection<Authority> authorities = new ArrayList<>();
	authorities.add(s);
	n.setAuthorities(authorities);
	c.setUserAccount(n);
	
	Customer  registrado = this.customerService.register(c);
	registrado.setUserAccount(n);
	Assert.isTrue(registrado.getIsBanned().equals(c.getIsBanned()));
	super.authenticate(null);
	 

	
	
		
}

@Test
public void testGetMyFixUpTasks(){
	super.authenticate("customer1");
	
	
	Collection<FixUpTask>  fixes =this.customerService.getMyFixUpTasks();
	Assert.notEmpty(fixes);
	super.authenticate(null);
}
@Test
public void testGetApplicationsForFut(){
	super.authenticate("customer1");
	FixUpTask  fut = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
	 Collection<Application>  aplicacion =   this.customerService.getApplicationsForFUT(fut);
	 Assert.notEmpty(aplicacion);
	 super.authenticate(null);
	
}
@Test
public void testUpdateApllication(){
	super.authenticate("customer1");
	Application app = this.applicationService.findOne(super.getEntityId("application1"));
	String  status  ="ACCEPTED";
	String  comments ="uhcorc";
	Application nueva= this.customerService.updateApplication(app, status, comments);
	Assert.notNull(nueva);
	super.authenticate(null);
}
@Test
public void testWriteNoteForReport(){
	super.authenticate("customer1");
	Note n = this.noteService.findOne(super.getEntityId("note1"));
	Report r =this.reportService.findOne(super.getEntityId("report1"));
	this.customerService.writeNoteForReport(n, r);
	Collection<Note> notes =  r.getNotes();
	Assert.isTrue(notes.contains(n));
	super.authenticate(null);
	
}@Test
public void testCreateComplaintByCustomer(){
	super.authenticate("customer1");
	
	Complaint  t = this.complaintService.findOne(super.getEntityId("complaint1"));
	//comprobar que corresponden
	FixUpTask fut = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
	this.customerService.createComplaintByCustomer(t, fut);
	Assert.isTrue(fut.getComplaints().contains(t));
	super.authenticate(null);
	
}
@Test
public  void  testWriteCommentOnNote(){
	super.authenticate("customer1");
	
	Note  n = this.noteService.findOne(super.getEntityId("note1"));
	String  antiguo = n.getComment();
	
	String comments =" me encanta dp";
	this.customerService.writeCommentOnNote(n, comments);
	Assert.isTrue(!n.equals(antiguo));
	super.authenticate(null);
	
}
@Test
public  void testGetMyComplaints(){
	
	super.authenticate("customer1");
	Collection<Complaint> complaints =this.customerService.getMyComplaints();
	Assert.notEmpty(complaints);
	super.authenticate(null);
}



}

