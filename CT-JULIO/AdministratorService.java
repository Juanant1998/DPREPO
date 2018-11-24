
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Category;
import domain.Message;
import domain.MessageBox;
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
		//Assert.isTrue(war2.getDraftMode);
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

	//public Collection<>
}
