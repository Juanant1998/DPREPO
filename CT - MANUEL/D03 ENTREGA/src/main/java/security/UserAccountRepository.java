/*
 * UserAccountRepository.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.HandyWorker;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

	@Query("select ua from UserAccount ua where ua.username = ?1")
	UserAccount findByUsername(String username);

	@Query("select a1 from HandyWorker a1 join a1.userAccount user where user.username = ?1")
	HandyWorker getHandyByUserAccount(String username);

	@Query("select a1 from Customer a1 join a1.userAccount user where user.username = ?1")
	Customer getCustomerByUserAccount(String username);
}
