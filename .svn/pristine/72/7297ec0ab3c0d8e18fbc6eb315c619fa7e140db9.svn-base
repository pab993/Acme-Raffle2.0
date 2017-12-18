
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BillRepository;
import domain.Bill;
import domain.ConfigurationSystem;
import domain.CreditCard;
import domain.Manager;
import domain.Raffle;
import forms.BillForm;

@Service
@Transactional
public class BillService {

	@Autowired
	private BillRepository				billRepository;

	@Autowired
	private ManagerService				managerService;

	@Autowired
	private AdministratorService		adminService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private CreditCardService			creditCardService;

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	public Bill create(final Raffle raffle) {
		Assert.notNull(raffle);
		Assert.isTrue(this.actorService.isManager());

		Bill result;

		final ConfigurationSystem cs = this.configurationSystemService.findConfigurationSystem();

		result = new Bill();
		result.setName("Bill " + raffle.getTitle());
		result.setPaid(false);
		result.setPrice(cs.getTaxes());
		result.setRaffle(raffle);

		return result;
	}

	public Bill findOne(final int billId) {
		return this.billRepository.findOne(billId);
	}

	public Collection<Bill> findAll() {
		return this.billRepository.findAll();
	}

	public Collection<Bill> findBillManager(final Manager manager) {
		Assert.isTrue(this.actorService.isManager());
		final Collection<Bill> res = new ArrayList<Bill>();
		final Collection<Raffle> raffles = manager.getRaffles();
		final Collection<Bill> bills = this.findAll();
		for (final Bill b : bills)
			if (raffles.contains(b.getRaffle()))
				res.add(b);
		return res;
	}

	public Integer findBillManagerUnpaid(final Manager manager) {
		final Collection<Bill> res = new ArrayList<Bill>();
		final Collection<Raffle> raffles = manager.getRaffles();
		final Collection<Bill> billsUnpaid = new ArrayList<Bill>();
		final Collection<Bill> bills = this.findAll();
		for (final Bill b : bills)
			if (raffles.contains(b.getRaffle()))
				res.add(b);
		for (final Bill bi : res)
			if (bi.getPayMoment().before(Calendar.getInstance().getTime()) && bi.isPaid() == false)
				billsUnpaid.add(bi);

		return billsUnpaid.size();
	}

	public void pay(final Bill bill) {
		final Manager manager = this.managerService.findByPrincipal();
		Assert.isTrue(this.actorService.isManager());
		Assert.isTrue(bill.getPayMoment() != null);
		Assert.isTrue(!bill.isPaid());
		final Collection<Raffle> raffles = manager.getRaffles();
		Assert.isTrue(raffles.contains(bill.getRaffle()));
		final CreditCard creditCard = this.creditCardService.findByManagerId(manager.getId());
		Assert.notNull(creditCard);
		Assert.isTrue(this.creditCardService.checkBrandName(creditCard));
		Assert.isTrue(this.creditCardService.checkExpirationDate(creditCard));
		final int[] digits = this.creditCardService.stringToArray(creditCard.getNumber());
		Assert.isTrue(this.creditCardService.verificacionLuhn(digits));

		bill.setPaid(true);

	}

	public void computeBills(final BillForm billForm) {

		Assert.notNull(billForm);
		Assert.isTrue(this.adminService.findByPrincipal() != null);

		final ConfigurationSystem configurationSystem = this.configurationSystemService.getCS();
		Assert.notNull(configurationSystem);

		final Collection<Bill> computeBills = this.billRepository.findAllByComputeDate(billForm.getComputeDate());

		for (final Bill b : computeBills) {
			b.setPayMoment(b.getRaffle().getPublicationTime());
			this.save(b);
		}

		if (configurationSystem.getPayMoment() == null) {
			configurationSystem.setPayMoment(billForm.getComputeDate());
			this.configurationSystemService.save(configurationSystem);
		} else if (billForm.getComputeDate().after(configurationSystem.getPayMoment())) {
			configurationSystem.setPayMoment(billForm.getComputeDate());
			this.configurationSystemService.save(configurationSystem);
		}

	}
	public Bill save(final Bill bill) {
		Assert.notNull(bill);
		Bill result;

		result = this.billRepository.save(bill);

		return result;
	}

	public Collection<Bill> findBillUnpaid() {
		final Collection<Bill> res = new ArrayList<Bill>();
		final Collection<Bill> bills = this.findAll();
		for (final Bill b : bills)
			if (b.getPayMoment().before(Calendar.getInstance().getTime()) && b.isPaid() == false)
				res.add(b);
		return res;
	}

	public Bill getBillRaffle(final Raffle raffle) {
		Bill res = null;
		final Collection<Bill> bills = this.findAll();

		for (final Bill b : bills)
			if (b.getRaffle().equals(raffle))
				res = b;
		return res;
	}

	public Bill findOneByRaffle(final Raffle raffle) {

		Bill result;

		result = this.billRepository.findOneByRaffleId(raffle.getId());

		return result;
	}

	public Object[] calculateTotalTaxByManagerId(final int managerId) {

		Object[] result;

		result = this.billRepository.calculateTotalTaxByManagerId(managerId);

		return result;
	}

	public Object[] calculateBillsUnpaidByManagerId(final int managerId) {

		Object[] result;

		result = this.billRepository.calculateBillsUnpaidByManagerId(managerId);

		return result;
	}

	public int calculateBillsUnpaidByManagerIdInt(final int managerId) {

		int result;

		result = this.billRepository.calculateBillsUnpaidByManagerIdInt(managerId);

		return result;
	}

	public Collection<Bill> findAllByManager(final Manager manager) {

		Collection<Bill> result;

		result = this.billRepository.findAllByManagerId(manager.getId());

		return result;
	}

	public Collection<Bill> findAllUnpaidBill() {

		Collection<Bill> result;

		result = this.billRepository.findAllUnpaidBill();

		return result;
	}

	public Collection<Bill> findAllComputed() {

		Collection<Bill> result;

		result = this.billRepository.findAllComputed();

		return result;
	}

	public Object[] minMaxAvgOfBillsPerManager() {

		Object[] result;

		result = this.billRepository.minMaxAvgOfBillsPerManager();

		return result;

	}

	public Object[] minMaxAvgOfUnpaidBillsPerManager() {

		Object[] result;

		result = this.billRepository.minMaxAvgOfUnpaidBillsPerManager();

		return result;

	}

	public Object[] minMaxAvgOfPaidBillsPerManager() {

		Object[] result;

		result = this.billRepository.minMaxAvgOfPaidBillsPerManager();

		return result;

	}

}
