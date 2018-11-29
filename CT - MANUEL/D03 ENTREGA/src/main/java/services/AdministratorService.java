
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import repositories.ApplicationRepository;
import repositories.CustomerRepository;
import repositories.FixUpTaskRepository;
import repositories.HandyWorkerRepository;
import repositories.ReportRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Category;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;
import domain.MessageBox;
import domain.Referee;
import domain.Warranty;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	administratorRepository;

	@Autowired
	private WarrantyService			warrantyService;
	@Autowired
	private CategoryService			categoryService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private FixUpTaskRepository futs;
	@Autowired
	private ApplicationRepository as;
	@Autowired
	private HandyWorkerRepository hs;
	@Autowired
	private CustomerRepository cs;
	@Autowired
	private RefereeService rs;
	@Autowired
	private ReportRepository rp;


	public Administrator create() {
		return new Administrator();
	}
	public Collection<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}
	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}
	public Administrator save(final Administrator administrator) {
		return this.administratorRepository.save(administrator);
	}
	public void delete(final Administrator administrator) {
		this.administratorRepository.delete(administrator);
	}

	public void checkAuthority() {

		UserAccount user;
		user = LoginService.getPrincipal();
		Assert.notNull(user);
		final Collection<Authority> authority = user.getAuthorities();
		Assert.notNull(authority);
		final Authority a1 = new Authority();
		a1.setAuthority(Authority.ADMIN);
		Assert.isTrue(authority.contains(a1));

	}

	public Administrator createAdmin() {
		final Administrator result;
		this.checkAuthority();

		final UserAccount userAccount = new UserAccount();
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		userAccount.addAuthority(a);
		result = this.create();
		result.setUserAccount(userAccount);

		return result;
	}

	public List<Warranty> warrantyList() {
		final List<Warranty> res = (List<Warranty>) this.warrantyService.findAll();
		return res;
	}

	public Warranty showWarranty(final int warId) {
		return this.warrantyService.findOne(warId);
	}

	public Warranty createWarranty() {
		return this.warrantyService.create();
	}
	public Warranty updateWarranty(final Warranty war, final int id) {
		final Warranty war2 = this.showWarranty(id);
		Assert.isTrue(war.isDraftMode());
		war2.setApplicableLaws(war.getApplicableLaws());
		war2.setTerms(war.getTerms());
		war2.setTitle(war.getTitle());

		return war2;
	}

	public void deleteWarranty(final Warranty war) {
		//Assert.isTrue(war.getDraftMode);
		this.warrantyService.delete(war);
	}

	public List<Category> categoryList() {
		final List<Category> res = (List<Category>) this.categoryService.findAll();
		return res;
	}

	public Category showCategory(final int catId) {
		return this.categoryService.findOne(catId);
	}

	public Category createCategory() {
		return this.categoryService.create();
	}

	public Category updateCategory(final Category cat, final int id) {
		final Category cat2 = this.showCategory(id);
		cat2.setDescendants(cat.getDescendants());
		cat2.setName(cat.getName());
		cat2.setVersion(cat.getVersion());

		return cat2;
	}

	public void deleteCategory(final Category category) {
		this.categoryService.delete(category);
	}

	public void broadcastMessage(final Message message) {
		final List<Actor> listaActores = (List<Actor>) this.actorService.findAll();
		for (final Actor a : listaActores)
			for (final MessageBox msb : a.getMessageBoxes())
				if (msb.isSystemBox() == true && msb.getName().endsWith("in")) {
					final List<Message> messages = (List<Message>) msb.getMessages();
					messages.add(message);
					msb.setMessages(messages);
				}
	}

	public Collection<Object> displayClevelDashboard(){
		Integer min1 = futs.minApplicationsByFixUpTasks();
		Integer max1 = futs.minApplicationsByFixUpTasks();
		Double avg1 = futs.averageApplicationsByFixUpTasks();
		Integer d1 = futs.desviationApplicationsByFixUpTasks();
		
		Integer min2 = cs.minFixUpTaskByCustomer();
		Integer max2= cs.maxFixUpTaskByCustomer();
		Double avg2 = cs.averageFixUpTaskByCustomer();
		Integer d2 = cs.deviationFixUpTaskByCustomer();
		
		Integer min3 = futs.minMaximunPriceByFixUpTasks();
		Integer max3 = futs.minMaximunPriceByFixUpTasks();
		Double avg3 = futs.averageMaximunPriceByFixUpTasks();
		Integer d3 = futs.desviationMaximumPriceByFixUpTasks();
		
		Integer min4 = as.minOfferedPriceByFixUpTasks();
		Integer max4 = as.maxOfferedPriceByFixUpTasks();
		Double avg4 = as.averageOfferedPriceByFixUpTasks();
		Integer d4 = as.desviationOfferedPriceByFixUpTasks();
		
		Double rpending = as.ratioPendingAplications();
		
		Double raccepted = as.ratioAcceptedAplications();
		
		Double rrejected = as.ratioRejectedAplications();
		
		Double relapsed = as.ratioPendingApplicationsSpiret();
		
		Collection<Customer> c9 = cs.tenPersentMoreFixUpTasks();
		
		Collection<HandyWorker> c10 = hs.tenPercentMoreAcceptedApplications();
		
		Collection<Object> res = new ArrayList<Object>();
		
		res.add(min1);res.add(min2);res.add(min3);res.add(min4);
		res.add(max1);res.add(max2);res.add(max3);res.add(max4);
		res.add(avg1);res.add(avg2);res.add(avg3);res.add(avg4);
		res.add(d1);res.add(d2);res.add(d3);res.add(d4);
		res.add(rpending);res.add(raccepted);res.add(rrejected);res.add(relapsed);
		res.add(c9);res.add(c10);
		
		return res;
		
	}
	
	public Referee createRefereeAccount(Referee r){
		this.checkAuthority();
		
		Referee result = rs.create();
		Assert.notNull(r);
		result.setAddress(r.getAddress());
		result.setEmail(r.getEmail());
		result.setIsBanned(r.getIsBanned());
		result.setIsSuspicious(r.getIsSuspicious());
		result.setMessageBoxes(r.getMessageBoxes());
		result.setMiddleName(r.getMiddleName());
		result.setName(r.getName());
		result.setPhone(r.getPhone());
		result.setPhoto(r.getPhoto());
		result.setProfiles(r.getProfiles());
		result.setUserAccount(r.getUserAccount());

		Referee resultado = rs.save(result);
		
		return resultado;
	}
	
	public Collection<Actor> getListOfSuspiciousActors(){
		checkAuthority();
		Collection<Actor> iterable = actorService.findAll();
		Collection<Actor> result = new ArrayList<Actor>();
		for(Actor a: iterable){
			if(a.getIsSuspicious()){
				result.add(a);
			}
		}
		return result;
	}
	
	public Actor banActor(Actor a){
		checkAuthority();
		if(!a.getIsBanned() && a.getIsSuspicious()){
			a.setIsBanned(true);
		}
		Actor result = actorService.save(a);

		return result;
	}
	
	public Actor unbanActor(Actor a){
		checkAuthority();
		if(a.getIsBanned()){
			a.setIsBanned(false);
		}
		Actor result = actorService.save(a);

		return result;
	}
	
	public Collection<Object> displayBlevelDashboard(){
		Collection<Object> res = new ArrayList<Object>();
		
		Collection<Customer> topCustomers = new ArrayList<Customer>();
		Collection<HandyWorker> topHandyWorkers = new ArrayList<HandyWorker>();
		
		Integer min1 = futs.minComplaintsByFixUpTasks();
		Integer max1 = futs.maxComplaintsByFixUpTasks();
		Double avg1 = futs.averageComplaintsByFixUpTasks();
		Integer desv1 = futs.desviationComplaintsByFixUpTasks();
		Integer min2 = rp.minNotesByReports();
		Integer max2 = rp.maxNotesByReports();
		Double avg2 = rp.averageNotesByReports();
		Integer desv2 = rp.desviationNotesByReports();
		Double rat3 = futs.ratioOneComplaintForFixUpTasks();
		
		topCustomers.addAll((Collection<? extends Customer>) cs.topThreeCustomers());
		topHandyWorkers.addAll((Collection<? extends HandyWorker>) hs.topThreeHandyWorkers());
		
		res.add(min1);
		res.add(max1);
		res.add(avg1);
		res.add(desv1);
		res.add(min2);
		res.add(max2);
		res.add(avg2);
		res.add(desv2);
		res.add(rat3);
		res.addAll(topCustomers);
		res.addAll(topHandyWorkers);
		
		return res;
	}
}
