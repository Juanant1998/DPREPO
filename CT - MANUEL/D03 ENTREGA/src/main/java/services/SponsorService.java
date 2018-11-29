package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.HandyWorker;
import domain.MessageBox;
import domain.Profile;
import domain.Sponsor;
import domain.Tutorial;


@Service
@Transactional
public class SponsorService {
	
	@Autowired
	private SponsorRepository sponsorRepository;
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private TutorialService tutorialService;
	
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	public Sponsor create(){
		final Sponsor result = new Sponsor();
		final UserAccount user = new UserAccount();
		result.setProfiles(new ArrayList<Profile>());
		final Authority a = new Authority();
		a.setAuthority(Authority.SPONSOR);
		final Collection<Authority> r = new ArrayList<Authority>();
		r.add(a);
		user.setAuthorities(r);
		result.setUserAccount(user);

		return result;	
	}
	public Collection<Sponsor> findAll(){
		return sponsorRepository.findAll();
	}
	
	public Sponsor findOne(int sponsorId){
		return sponsorRepository.findOne(sponsorId);
	}
	
	public Sponsor save(Sponsor sponsor){
		return sponsorRepository.save(sponsor);
	}
	
	public void delete(Sponsor sponsor){
		sponsorRepository.delete(sponsor);
	}
	
	public void checkAuthority() {
		UserAccount ua;
		ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Collection<Authority> auth = ua.getAuthorities();
		final Authority a = new Authority();
		a.setAuthority(Authority.SPONSOR);
		Assert.isTrue(auth.contains(a));
	}
	
	public Sponsor register(final Sponsor sp, final String username, final String password) {
		this.checkAuthority();

		Assert.notNull(username);
		Assert.notNull(password);
		Assert.notNull(sp);
		Assert.notEmpty(sp.getProfiles());
		

		final Sponsor result = this.create();
		result.setAddress(sp.getAddress());
		result.setMiddleName(sp.getMiddleName());
		result.setName(sp.getName());
		result.setPhone(sp.getPhone());
		result.setPhoto(sp.getPhoto());
		result.setProfiles(sp.getProfiles());
		result.getUserAccount().setPassword(password);
		result.getUserAccount().setUsername(username);

		final MessageBox in = actorService.createNewMessageBox(username, "-in");
		final MessageBox out = actorService.createNewMessageBox(username, "-out");
		final MessageBox trash = actorService.createNewMessageBox(username, "-trash");
		final MessageBox spam = actorService.createNewMessageBox(username, "-spam");

		final Collection<MessageBox> msboxes = new ArrayList<MessageBox>();
		msboxes.add(in);
		msboxes.add(out);
		msboxes.add(trash);
		msboxes.add(spam);

		result.setMessageBoxes(msboxes);
		final Sponsor x = this.save(result);

		return x;
	
	}
	
	public Collection<Tutorial> findAllTutorials(){ //inutilidad
		this.checkAuthority();
		Collection<Tutorial> res = tutorialService.findAll();
		return res;
	}
	
	public Map<HandyWorker, Collection<Tutorial>> findTutorialsByHandyWorker(){
		this.checkAuthority();
		Map<HandyWorker, Collection<Tutorial>> res = new HashMap<HandyWorker, Collection<Tutorial>>();
		Collection<HandyWorker> hws = handyWorkerService.findAll();
		
		for(HandyWorker h: hws){
		      res.put(h, putTutorialInHandyWorker(h));
	    }
		
		return res;
	}
	
    private Collection<Tutorial> putTutorialInHandyWorker(HandyWorker h) {
		Collection<Tutorial> res = new ArrayList<Tutorial>();
		for(Tutorial t: this.tutorialService.findAll()){
			if(t.getHandyworker()==h){
				res.add(t);
			}
		}
		return res;
	}
	
    
    
		
	

}
