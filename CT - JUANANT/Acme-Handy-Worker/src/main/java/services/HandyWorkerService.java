
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.HandyWorker;
import domain.MessageBox;
import domain.Profile;

@Service
@Transactional
public class HandyWorkerService {

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	@Autowired
	private MessageBoxService		mbs;


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
		Assert.notEmpty(hw.getFinders());
		Assert.notNull(hw.getCurriculum());

		final HandyWorker result = this.create();
		result.setAddress(hw.getAddress());
		result.setApplications(new ArrayList<Application>());
		result.setCurriculum(hw.getCurriculum());
		result.setEmail(hw.getEmail());
		result.setFinders(hw.getFinders());
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
