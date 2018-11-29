package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Application;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.MessageBox;
import domain.Phase;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	@Autowired
	private HandyWorkerService handyWorkerService;
	
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private PhaseService phaseService;
	@Autowired
	private ActorService actorService;
	

	@Test 
	public void testSaveActors(){
		//Actor
		Collection<HandyWorker> actors = new ArrayList<>();
		HandyWorker guardando=handyWorkerService.findOne(super.getEntityId("handyWorker3"));
		handyWorkerService.save(guardando);
		actors.add(guardando);
		Assert.isTrue(actors.contains(guardando));
		
	}
	@Test
	public void  testDeleteHandy(){
		HandyWorker borrando= handyWorkerService.findOne(super.getEntityId("handyWorker3"));
			handyWorkerService.delete(borrando);
			Assert.isNull(handyWorkerService.findOne(borrando.getId()));
	}
	@Test
	public void  findOneOk(){
		HandyWorker  find = handyWorkerService.findOne(super.getEntityId("handyWorker3"));
		 int  findId= find.getId();
		 Assert.notNull(handyWorkerService.findOne(findId));
	}
	@Test
	public void FindAll(){
		Collection<HandyWorker> actors;
		actors = this.handyWorkerService.findAll();
		Assert.isTrue(!actors.isEmpty());
		
	}
	@Test
	public void createTest(){
		HandyWorker  create= handyWorkerService.create();
		Assert.notNull(create);
		
	}
	@Test
	public void createNewMessageBox(){
		super.authenticate("handyWorker1");
		String username = "paquito";
		String msgboxname= "dejajajas";
		MessageBox box = this.actorService.createNewMessageBox(username, msgboxname);
		Assert.notNull(box);
		super.authenticate(null);
		
	}
	@Test
	public void registerTest(){
		super.authenticate("handyWorker1");
		HandyWorker h;
		h = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		UserAccount  n  = new UserAccount();
		Authority  s  = new Authority();
		n.setUsername("paquito");
		n.setPassword("21232f297a57a5a743894a0e4a801fc2");
		s.setAuthority(Authority.HANDYWORKER);
		Collection<Authority> authorities = new ArrayList<>();
		authorities.add(s);
		n.setAuthorities(authorities);
		h.setUserAccount(n);
		
		HandyWorker  registrado = this.handyWorkerService.register(h);
		registrado.setUserAccount(n);
		Assert.isTrue(registrado.getIsBanned().equals(h.getIsBanned()));
		super.authenticate(null);
	}
	@Test
	public void getAllFixUpTasksTest(){
		super.authenticate("handyWorker1");
		Collection<FixUpTask> fs =this.handyWorkerService.getAllFixUpTasks();
		Assert.isTrue(!fs.isEmpty());
		super.authenticate(null);
		
	}
	@Test
	public void getFixUpTaskCustomer(){
		super.authenticate("handyWorker1");
		FixUpTask f=this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		
		Customer c = this.handyWorkerService.getFixUpTaskCustomer(f);
		Assert.notNull(c);
		Assert.isTrue(c.getFixUpTasks().contains(f));
		super.authenticate(null);
	}
	@Test
	public void filterFixUpTasksByKeywordTest(){
		super.authenticate("handyWorker1");
		String keyword = "description";
		Collection<FixUpTask> fs = this.handyWorkerService.filterFixUpTasksByKeyword(keyword);
		Assert.isTrue(!fs.isEmpty());
		super.authenticate("handyworker1");
	}
	/*@Test
	public void filterFixUpTasksByDateRangeTest(){
		super.authenticate("handyworker1");
		Date date = Date.UTC(year, month, date, hrs, min, sec)
		Collection<FixUpTask> fs = this.handyWorkerService.filterFixUpTasksByKeyword(keyword);
		Assert.isTrue(!fs.isEmpty());
		super.authenticate(null);
	}*/
	@Test
	public void listApplicationsTest(){
		super.authenticate("handyWorker1");
		Collection<Application> apps = this.handyWorkerService.listApplications();
		Assert.isTrue(!apps.isEmpty());
		super.authenticate("handyworker1");
	}
	@Test
	public void showApplicationTest(){
		super.authenticate("handyWorker1");
		Application app=this.applicationService.findOne(super.getEntityId("application1"));
		Assert.notNull(this.handyWorkerService.showApplication(app.getId()));
		super.authenticate(null);
	}
	@Test
	public void createApplicationTest(){
		super.authenticate("handyWorker1");
		Application app=this.applicationService.findOne(super.getEntityId("application1"));
		Application app2 = this.handyWorkerService.createApplication(app);
		Assert.notNull(app2);
		super.authenticate(null);
	}

	@Test
	public void addPhaseTest(){
		super.authenticate("handyWorker5");
		Application app=this.applicationService.findOne(super.getEntityId("application7"));
		FixUpTask fut = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		Phase ph = this.phaseService.findOne(super.getEntityId("phase1"));
		Phase res = this.handyWorkerService.addPhase(app, ph, fut);
		ph.setDescription("jiji");
		Assert.isTrue(!ph.getDescription().equals(res.getDescription()));
		super.authenticate(null);
	}
	@Test
	public void getFixUpTaskPhasesTest(){
		super.authenticate("handyWorker1");
		FixUpTask fut = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		Collection<Phase> futs = this.handyWorkerService.getFixUpTaskPhases(fut);
		Assert.isTrue(!futs.isEmpty());
		super.authenticate(null);
	}
	@Test
	public void updatePhase(){
		super.authenticate("handyWorker1");
		Phase ph = this.phaseService.findOne(super.getEntityId("phase1"));
		Phase phneutro= this.handyWorkerService.updatePhase(ph);
		Assert.notNull(phneutro);
		super.authenticate(null);
	}
	@Test
	public void deletePhase(){
		super.authenticate("handyWorker1");
		Phase ph = this.phaseService.findOne(super.getEntityId("phase1"));
		this.handyWorkerService.deletePhase(ph);
		Assert.isNull(this.phaseService.findOne(ph.getId()));
		super.authenticate(null);
	}
	@Test
	public void getFinderFixUpTasksTest(){
		super.authenticate("handyWorker1");
		Collection<FixUpTask> fs = this.handyWorkerService.getFinderFixUpTasks();
		Assert.isTrue(!fs.isEmpty());
	}




}