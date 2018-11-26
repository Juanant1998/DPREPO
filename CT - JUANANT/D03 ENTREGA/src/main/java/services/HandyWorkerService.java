
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.Customer;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.MessageBox;
import domain.Phase;
import domain.Profile;

@Service
@Transactional
public class HandyWorkerService {

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	@Autowired
	private MessageBoxService		mbs;

	@Autowired
	private FixUpTaskService		futs;

	@Autowired
	private CustomerRepository		cr;

	@Autowired
	private FinderService			fs;

	@Autowired
	private UserAccountService		uas;

	@Autowired
	private ApplicationService		as;

	@Autowired
	private PhaseService			ps;


	public HandyWorker create() {//revisar
		final HandyWorker result = new HandyWorker();
		final UserAccount user = new UserAccount();
		result.setProfiles(new ArrayList<Profile>());
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		final Collection<Authority> r = new ArrayList<Authority>();
		r.add(a);
		user.setAuthorities(r);
		result.setUserAccount(user);

		return result;
	}

	public void checkAuthority() {
		UserAccount ua;
		ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Collection<Authority> auth = ua.getAuthorities();
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(auth.contains(a));
	}

	public MessageBox createNewMessageBox(final String username, final String msgboxname) {
		final MessageBox msgbox = this.mbs.create();
		msgbox.setName(username + " " + msgboxname);
		msgbox.setSystemBox(true);

		final MessageBox result = this.mbs.save(msgbox);

		return result;
	}

	public HandyWorker register(final HandyWorker hw, final String username, final String password) {
		this.checkAuthority();

		Assert.notNull(username);
		Assert.notNull(password);
		Assert.notNull(hw);
		Assert.notEmpty(hw.getProfiles());
		Assert.notEmpty(hw.getApplications());
		Assert.notNull(hw.getFinder());
		Assert.notNull(hw.getCurriculum());

		final HandyWorker result = this.create();
		result.setAddress(hw.getAddress());
		result.setApplications(new ArrayList<Application>());
		result.setCurriculum(hw.getCurriculum());
		result.setEmail(hw.getEmail());
		result.setFinder(hw.getFinder());
		result.setMake(hw.getMake());
		result.setMiddleName(hw.getMiddleName());
		result.setName(hw.getName());
		result.setPhone(hw.getPhone());
		result.setPhoto(hw.getPhoto());
		result.setProfiles(hw.getProfiles());
		result.getUserAccount().setPassword(password);
		result.getUserAccount().setUsername(username);

		final MessageBox in = this.createNewMessageBox(username, "-in");
		final MessageBox out = this.createNewMessageBox(username, "-out");
		final MessageBox trash = this.createNewMessageBox(username, "-trash");
		final MessageBox spam = this.createNewMessageBox(username, "-spam");

		final Collection<MessageBox> msboxes = new ArrayList<MessageBox>();
		msboxes.add(in);
		msboxes.add(out);
		msboxes.add(trash);
		msboxes.add(spam);

		result.setMessageBoxes(msboxes);
		final HandyWorker x = this.save(result);

		return x;

	}

	public Collection<FixUpTask> getAllFixUpTasks() {
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);

		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));

		final Collection<FixUpTask> result = this.futs.findAll();

		return result;

	}

	public Customer navigateToCustomer(final FixUpTask fut) {

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);

		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(a));

		final Customer c = this.cr.findCustomerByFixUpTask(fut);

		return c;

	}
	public Finder filterFixUpTasksByKeyword(final String keyword) {
		final Finder finder = this.fs.createFinderByKeyword(keyword);

		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		actual.setFinder(finder);
		final Finder result = this.fs.save(finder);
		final HandyWorker h = this.handyWorkerRepository.save(actual);

		return result;
	}

	public Finder filterFixUpTasksByDateRange(final Date start, final Date end) {
		final Finder finder = this.fs.createFinderByDateRange(start, end);

		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		actual.setFinder(finder);
		final Finder result = this.fs.save(finder);
		final HandyWorker h = this.handyWorkerRepository.save(actual);

		return result;
	}
	public Finder filterFixUpTasksByPriceRange(final Double min, final Double max) {
		final Finder finder = this.fs.createFinderByPriceRange(min, max);

		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		actual.setFinder(finder);
		final Finder result = this.fs.save(finder);
		final HandyWorker h = this.handyWorkerRepository.save(actual);

		return result;
	}

	public Collection<Application> listApplications() {
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		return actual.getApplications();
	}

	public Application showApplication(final int applicationId) {
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		final Application application = this.as.findOne(applicationId);

		Assert.isTrue(actual.getApplications().contains(application));

		return application;
	}

	public Application createApplication(final Application app) {
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));

		final Application res = new Application();
		res.setComments(app.getComments());
		res.setCreditCard(app.getCreditCard());
		res.setMoment((LocalDate.now().toDate()));
		res.setOfferedPrice(app.getOfferedPrice());
		res.setStatus("PENDING");

		final Application result = this.as.save(res);

		return result;
	}

	public Phase addPhase(final Application a, final Phase phase) {
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(authority));

		Assert.isTrue(actual.getApplications().contains(a));
		Assert.isTrue(a.getStatus() == "ACCEPTED");
		Assert.notNull(phase);
		final Phase p = new Phase();
		p.setTitle(phase.getTitle());
		p.setDescription(phase.getDescription());
		p.setStartMoment(phase.getStartMoment());
		p.setEndMoment(phase.getEndMoment());

		final Phase result = this.ps.save(p);

		return result;

	}

	public Collection<Phase> getFixUpTaskPhases(final FixUpTask f) {
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));
		//preguntar si hay que comprobar que el handy worker es el propietario de la application aceptada.
		return f.getPhases();

	}

	public Phase updatePhase(final Phase p) {
		final HandyWorker actual = this.uas.getHandyByUserAccount(LoginService.getPrincipal());

		final Authority a = new Authority();
		a.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(actual.getUserAccount().getAuthorities().contains(a));
		Assert.notNull(p);

		final Phase result = this.ps.save(p);

		return result;

	}

	public void deletePhase(final Phase p) {
		this.ps.delete(p);
	}

	public Collection<HandyWorker> findAll() {
		return this.handyWorkerRepository.findAll();
	}
	public HandyWorker findOne(final int handyWorkerId) {
		return this.handyWorkerRepository.findOne(handyWorkerId);
	}
	public HandyWorker save(final HandyWorker handyWorker) {
		return this.handyWorkerRepository.save(handyWorker);
	}
	public void delete(final HandyWorker handyWorker) {
		this.handyWorkerRepository.delete(handyWorker);
	}
}
