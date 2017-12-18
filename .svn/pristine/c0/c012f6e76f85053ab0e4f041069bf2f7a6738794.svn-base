
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;
import domain.User;

@Service
@Transactional
public class UserService {

	//Managed Repository =============================================================================

	@Autowired
	private UserRepository	userRepository;

	//Services
	// ===============================================================================================

	@Autowired
	private ActorService	actorService;


	//SCRUDs Methods
	//===============================================================================================

	public User findOne(final int userId) {
		User result;

		result = this.userRepository.findOne(userId);

		return result;
	}

	public Collection<User> findAll() {

		Collection<User> result = new HashSet<User>();
		result = this.userRepository.findAll();

		return result;
	}

	public Collection<User> userBan(final Collection<User> users) {
		final Collection<User> res;

		res = new ArrayList<User>();

		for (final User us : users)
			if (us.getUserAccount().isEnabled() == false)
				res.add(us);

		return res;
	}

	public void ban(final User user) {
		Assert.isTrue(this.actorService.isAdmin());
		Assert.isTrue(user.getUserAccount().isEnabled() == true);
		Assert.notNull(user);

		user.getUserAccount().setEnabled(false);

	}

	public void unban(final User user) {
		Assert.isTrue(this.actorService.isAdmin());
		Assert.isTrue(user.getUserAccount().isEnabled() == false);

		user.getUserAccount().setEnabled(true);
	}

	//Other Business Methods =========================================================================

	public User findByPrincipal() {
		User result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public User findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		User result;

		result = this.userRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	// Dashboard =========================================================================

	public Object[] ratioBanUnBanUsers() {

		Object[] result;

		result = this.userRepository.ratioBanUnBanUsers();

		return result;
	}

	public Collection<User> findUsersOrderByValidCodes() {

		Collection<User> result;

		result = this.userRepository.findUsersOrderByValidCodes();

		return result;
	}

	public Object[] minMaxAvgStddevOfValidCodesPerUser() {

		Object[] result;

		result = this.userRepository.minMaxAvgStddevOfValidCodesPerUser();

		return result;
	}

}
