
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import repositories.RaffleRepository;
import domain.Audit;
import domain.Bill;
import domain.Manager;
import domain.Prize;
import domain.Raffle;

@Service
@Transactional
public class RaffleService {

	//Managed Repository =============================================================================

	@Autowired
	private RaffleRepository	raffleRepository;

	//Services
	// ===============================================================================================

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private PrizeService		prizeService;

	@Autowired
	private AuditService		auditOfService;

	@Autowired
	private BillService			billService;


	//SCRUDs Methods
	//===============================================================================================

	public Raffle findOne(final int raffleId) {
		Raffle result;

		result = this.raffleRepository.findOne(raffleId);

		return result;
	}

	public Collection<Raffle> findAll() {

		Collection<Raffle> result = new HashSet<Raffle>();

		result = this.raffleRepository.findAll();

		return result;
	}

	public Raffle create(final Manager manager) {
		Assert.notNull(manager);
		final Manager principal;
		Raffle result;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.equals(manager));
		Assert.isInstanceOf(Manager.class, principal);

		result = new Raffle();
		result.setManager(manager);
		manager.getRaffles().add(result);

		return result;
	}

	public Raffle save(final Raffle raffle) {
		Assert.notNull(raffle);
		Assert.isTrue(raffle.getDeadline().after(raffle.getPublicationTime()));

		Date now;
		Raffle result;

		now = new Date(System.currentTimeMillis() - 10);
		Assert.isTrue(raffle.getPublicationTime().after(now));

		final Manager principal = this.managerService.findByPrincipal();

		Assert.isInstanceOf(Manager.class, principal);
		Assert.notNull(principal);
		Assert.isTrue(principal.equals(raffle.getManager()));
		Assert.isInstanceOf(Manager.class, principal);

		//		principal.getRaffles().add(raffle);

		result = this.raffleRepository.save(raffle);

		if (this.billService.findOneByRaffle(result) == null) {
			final Bill bill = this.billService.create(result);
			this.billService.save(bill);
		}

		return result;
	}

	public void delete(final Raffle raffle) {
		Assert.notNull(raffle);
		Date now;
		Manager principal;
		Collection<Prize> prizes;
		Collection<Audit> auditOfs;

		now = new Date(System.currentTimeMillis() - 10);
		Assert.isTrue(raffle.getPublicationTime().after(now));
		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.equals(raffle.getManager()));
		Assert.isInstanceOf(Manager.class, principal);
		//		Assert.isTrue(principal.getRaffles().contains(raffle));

		prizes = raffle.getPrizes();
		auditOfs = raffle.getAudits();

		if (prizes != null)
			for (final Prize prize : prizes)
				this.prizeService.delete(prize);

		if (auditOfs != null)
			for (final Audit auditOf : auditOfs)
				this.auditOfService.delete(auditOf);

		this.raffleRepository.delete(raffle);

	}

	//Other Business Methods =========================================================================

	//	public Collection<Raffle> search(String keyword) {
	//		final Collection<Raffle> allRaffles = findAll();
	//		final Collection<Raffle> raffles = new ArrayList<Raffle>();
	//		for (final Raffle ra : allRaffles)
	//			if (!raffles.contains(ra) && ra.getTitle().toLowerCase().contains(keyword) || ra.getDescription().toLowerCase().contains(keyword))
	//				raffles.add(ra);
	//		return raffles;
	//	}

	public Collection<Raffle> search(final String keyword) {
		Collection<Raffle> raffles = new ArrayList<Raffle>();
		raffles = this.raffleRepository.searchRaffles(keyword);
		return raffles;
	}

	public Collection<Raffle> findAllPublic() {
		Collection<Raffle> raffles = new ArrayList<Raffle>();
		raffles = this.raffleRepository.findAllPublic();
		return raffles;
	}

	public Collection<Raffle> findAllByManager(final int actorId) {

		Collection<Raffle> result;

		result = this.raffleRepository.findAllByManager(actorId);

		return result;
	}

	public Collection<Raffle> findAllByUser(final int actorId) {

		Collection<Raffle> result;

		result = this.raffleRepository.findAllByUser(actorId);

		return result;
	}

	//Dashboard=========================================================================

	public Object[] minMaxAvgStddevOfPrizesPerRaffle() {

		Object[] result;

		result = this.raffleRepository.minMaxAvgStddevOfPrizesPerRaffle();

		return result;
	}

	public void reconstruct(final Raffle raffle, final BindingResult binding) {
		this.checkPublicationDateAndDeadLine(raffle, binding);

	}

	private boolean checkPublicationDateAndDeadLine(final Raffle raffle, final BindingResult binding) {
		FieldError error;
		String[] codigos;
		boolean result = false;
		boolean result2 = false;
		final Date now = new Date(System.currentTimeMillis() - 10);

		if (raffle.getPublicationTime() != null)
			if (!raffle.getPublicationTime().after(now))
				result2 = true;

		if (result2) {
			codigos = new String[1];
			codigos[0] = "raffle.publicationTime.error.2";
			error = new FieldError("raffle", "publicationTime", raffle.getPublicationTime(), false, codigos, null, "");
			binding.addError(error);
		}

		if (raffle.getDeadline() != null && raffle.getPublicationTime() != null) {
			if (raffle.getDeadline().before(raffle.getPublicationTime()))
				result = true;

			if (result) {
				codigos = new String[1];
				codigos[0] = "raffle.deadline.error";
				error = new FieldError("raffle", "deadline", raffle.getDeadline(), false, codigos, null, "");
				binding.addError(error);

				codigos = new String[1];
				codigos[0] = "raffle.publicationTime.error";
				error = new FieldError("raffle", "publicationTime", raffle.getPublicationTime(), false, codigos, null, "");
				binding.addError(error);
			}
		}
		return result;
	}
}
