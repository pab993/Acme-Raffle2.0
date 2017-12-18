
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.LoginService;
import security.UserAccount;
import domain.Manager;

@Service
@Transactional
public class ManagerService {

	//Managed Repository =============================================================================

	@Autowired
	private ManagerRepository	managerRepository;

	//Supporting services =============================================================================

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BillService			billService;


	//SCRUDs Methods
	//===============================================================================================

	public Manager findOne(final int managerId) {
		Manager result;

		result = this.managerRepository.findOne(managerId);

		return result;
	}

	public Collection<Manager> findAll() {

		Collection<Manager> result;

		result = this.managerRepository.findAll();

		return result;

	}

	//Other Business Methods =========================================================================

	public Manager findByPrincipal() {
		Manager result;
		UserAccount userAccount;
		try {
			userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount);

			result = this.findByUserAccount(userAccount);
			Assert.notNull(result);
		} catch (final Throwable oops) {
			result = null;
		}
		return result;
	}

	public Manager findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Manager result;

		result = this.managerRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Collection<Manager> findDebtors() {
		Assert.isTrue(this.actorService.isAdmin());

		final Collection<Manager> debtors = new ArrayList<Manager>();
		final Collection<Manager> managers = this.findAll();

		for (final Manager m : managers) {
			final int unpaidBills = this.billService.calculateBillsUnpaidByManagerIdInt(m.getId());

			if (unpaidBills >= 3)
				debtors.add(m);
		}
		return debtors;
	}

	public void ban(final Manager manager) {
		Assert.isTrue(this.actorService.isAdmin());
		Assert.isTrue(manager.getUserAccount().isEnabled() == true);
		Assert.notNull(manager);

		manager.getUserAccount().setEnabled(false);
	}

	public void unban(final Manager manager) {
		Assert.isTrue(this.actorService.isAdmin());
		Assert.isTrue(manager.getUserAccount().isEnabled() == false);
		Assert.notNull(manager);

		manager.getUserAccount().setEnabled(true);
	}

	public Collection<Manager> findManagersOrderByUnpaidBills() {

		Collection<Manager> result;

		result = this.managerRepository.findManagersOrderByUnpaidBills();

		return result;

	}

	public Collection<Object[]> ratioDebtorManagers() {

		Collection<Object[]> result;

		result = this.managerRepository.ratioDebtorManagers();

		return result;

	}

}
