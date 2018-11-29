package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;


import domain.Complaint;
import domain.MessageBox;
import domain.Note;
import domain.Profile;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class RefereeService {   //comprobar en cada metodo que no está baneado.
	
	@Autowired
	private RefereeRepository refereeRepository;
	
	@Autowired
	private ComplaintService complaintService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private NoteService noteService;
		
	@Autowired
	private ActorService actorService;

	
	public Referee create(){
		Assert.isTrue(actorService.isActualActorBanned());
		final Referee result = new Referee();
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
		Assert.isTrue(actorService.isActualActorBanned());
		UserAccount ua;
		ua = LoginService.getPrincipal();
		Assert.notNull(ua);
		final Collection<Authority> auth = ua.getAuthorities();
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(auth.contains(a));
	}
		
	
	
	public Referee register(final Referee re, final String username, final String password) {
		Assert.isTrue(actorService.isActualActorBanned());
		this.checkAuthority();

		Assert.notNull(username);
		Assert.notNull(password);
		Assert.notNull(re);
		Assert.notEmpty(re.getProfiles());
		

		final Referee result = this.create();
		result.setAddress(re.getAddress());
		result.setMiddleName(re.getMiddleName());
		result.setName(re.getName());
		result.setPhone(re.getPhone());
		result.setPhoto(re.getPhoto());
		result.setProfiles(re.getProfiles());
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
		final Referee x = this.save(result);

		return x;

	}
	public Collection<Referee> findAll(){
		return refereeRepository.findAll();
	}
	
	public Referee findOne(int refereeId){
		return refereeRepository.findOne(refereeId);
	}
	
	public Referee save(Referee referee){
		return refereeRepository.save(referee);
	}
	
	public void delete(Referee referee){
		refereeRepository.delete(referee);
	}
	
	public Collection<Complaint> findFreeComplaints(){
		Assert.isTrue(actorService.isActualActorBanned());
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		
		
		Collection<Complaint> res = complaintService.findAll();
		for(Complaint c : res){
			if(c.getReport()!=null){
				res.remove(c);
			}
		}
		
		return res;
	}
	
	public Collection<Complaint> findMyComplaints(){
		Assert.isTrue(actorService.isActualActorBanned());
		Collection<Complaint> res = complaintService.findAll();
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		
		for(Complaint c : res){
			if(!(c.getReport().getReferee().getUserAccount().equals(userAccount))){
				res.remove(c);
			}
		}
		
		return res;
	}
	
	public Report editReport(Report r){
		Assert.isTrue(actorService.isActualActorBanned());
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		Assert.isTrue(!r.isDraftMode());
		
		Report res = reportService.findOne(r.getId());
		res = reportService.save(r);
		
		return res;
	}
	
	public void deleteReport(Report r){
		Assert.isTrue(actorService.isActualActorBanned());
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		Assert.isTrue(!r.isDraftMode());
		
		Report res = reportService.findOne(r.getId());
		reportService.delete(res);
	}
	
	public Collection<Report> findMyReports(){
		Assert.isTrue(actorService.isActualActorBanned());
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		
		Collection<Report> res = reportService.findAll();
		
		for(Report r : res) {
			if(!(r.getReferee().getUserAccount().equals(userAccount))){
				res.remove(r);
			}
		}
		
		return res;
	}
	
	public Note createNote(Note n, Report r){
		Assert.isTrue(actorService.isActualActorBanned());
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		
	    Assert.isTrue(r.isDraftMode());
		r.getNotes().add(n);
	    
		noteService.save(n);
		
		return n;
	}
	
	public Note writeComment(String s, Note n){
		Assert.isTrue(actorService.isActualActorBanned());
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		
		Assert.isTrue(this.findMyNotes().contains(n));
		
		Note note;
		note = noteService.findOne(n.getId());
		note.setComment(s);
		note = noteService.save(note);
		
		return note;
	}
	
	public Collection<Note> findMyNotes(){
		Assert.isTrue(actorService.isActualActorBanned());
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		Assert.isTrue(userAccount.getAuthorities().contains(a));
		
		Collection<Note> res = noteService.findAll();
		
		for(Report r : this.findMyReports()){
		  for(Note n: res){
			if(!(r.getNotes().contains(n))){
				res.remove(n);
			}
		  }
		}
		
		return res;
	}

}
