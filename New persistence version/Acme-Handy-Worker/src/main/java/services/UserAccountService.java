
package services;

import java.util.Collection;

import security.UserAccount;
import security.UserAccountRepository;

public class UserAccountService {

	private UserAccountRepository	uas;


	public UserAccount create() {//mirar
		return new UserAccount();
	}
	public Collection<UserAccount> findAll() {
		return this.uas.findAll();
	}

	public UserAccount findOne(final int uaId) {
		return this.uas.findOne(uaId);
	}

	public UserAccount save(final UserAccount ua) {
		return this.uas.save(ua);
	}

	public void delete(final UserAccount ua) {
		this.uas.delete(ua);
	}

}
