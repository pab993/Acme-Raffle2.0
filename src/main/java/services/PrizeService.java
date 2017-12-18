
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PrizeRepository;
import domain.Code;
import domain.Manager;
import domain.Prize;
import domain.Property;
import domain.Raffle;

@Service
@Transactional
public class PrizeService {

	//Managed Repository 
	// ===============================================================================================

	@Autowired
	private PrizeRepository	prizeRepository;

	//Services
	// ===============================================================================================

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private CodeService		codeService;

	@Autowired
	private PropertyService	propertyService;

	@Autowired
	private ActorService	actorService;


	//SCRUDs Methods
	//===============================================================================================

	public Prize findOne(final int prizeId) {
		Prize result;

		result = this.prizeRepository.findOne(prizeId);

		return result;
	}

	public Collection<Prize> findAll() {

		Collection<Prize> result = new HashSet<Prize>();

		result = this.prizeRepository.findAll();

		return result;
	}

	public Prize create(final Raffle raffle) {
		Assert.notNull(raffle);
		final Manager principal;
		Prize result;

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(principal.getRaffles().contains(raffle));
		Assert.isInstanceOf(Manager.class, principal);

		result = new Prize();
		result.setRaffle(raffle);
		raffle.getPrizes().add(result);

		return result;
	}

	public Prize save(final Prize prize) {
		Assert.notNull(prize);
		Assert.isTrue(prize.getCodesGenerated() > prize.getWinnerCodes());

		Prize saved;
		Manager principal;
		Date now;

		now = new Date(System.currentTimeMillis());
		Assert.isTrue(prize.getRaffle().getPublicationTime().after(now));

		principal = this.managerService.findByPrincipal();
		Assert.isInstanceOf(Manager.class, principal);
		Assert.notNull(principal);
		Assert.isTrue(prize.getRaffle().getManager().equals(principal));
		Assert.isInstanceOf(Manager.class, principal);

		//		principal.getPrizes().add(prize);

		saved = this.prizeRepository.save(prize);

		final Collection<Code> codesWinnerGenerated = new HashSet<Code>();
		final Collection<Code> codesNotWinnerGenerated = new HashSet<Code>();
		final int numCodesGenerated = saved.getCodesGenerated();
		final int numWinnerCodes = saved.getWinnerCodes();
		Collection<Code> codes = new HashSet<Code>();

		codes = prize.getCodes();

		if (codes != null)
			for (final Code code : codes)
				this.codeService.delete(code);

		for (int i = 0; i < numCodesGenerated; i++)
			if (codesNotWinnerGenerated.size() == numCodesGenerated - numWinnerCodes) {
				final Code newCode = this.codeService.createWinner(saved);
				codesWinnerGenerated.add(newCode);
			} else if (codesWinnerGenerated.size() == numWinnerCodes) {
				final Code newCode1 = this.codeService.createNotWinner(saved);
				codesNotWinnerGenerated.add(newCode1);
			} else if (!(codesNotWinnerGenerated.size() == numCodesGenerated - numWinnerCodes && !(codesWinnerGenerated.size() == numWinnerCodes))) {
				final Code newCode2 = this.codeService.create(saved);
				if (newCode2.getWinner() == true)
					codesWinnerGenerated.add(newCode2);
				if (newCode2.getWinner() == false)
					codesNotWinnerGenerated.add(newCode2);
			}

		return saved;
	}

	public Prize save2(final Prize prize) {
		Assert.notNull(prize);
		Assert.isTrue(prize.getCodesGenerated() > prize.getWinnerCodes());

		Prize saved;
		Manager principal;
		Date now;

		now = new Date(System.currentTimeMillis());
		Assert.isTrue(prize.getRaffle().getPublicationTime().after(now));

		principal = this.managerService.findByPrincipal();
		Assert.isInstanceOf(Manager.class, principal);
		Assert.notNull(principal);
		Assert.isTrue(prize.getRaffle().getManager().equals(principal));
		Assert.isInstanceOf(Manager.class, principal);

		//		principal.getPrizes().add(prize);

		saved = this.prizeRepository.save(prize);

		return saved;
	}

	public void delete(final Prize prize) {
		Assert.notNull(prize);
		final Manager principal;
		Collection<Code> codes;
		Collection<Property> properties;
		Date now;

		now = new Date(System.currentTimeMillis() - 10);
		Assert.isTrue(prize.getRaffle().getPublicationTime().after(now));

		principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(prize.getRaffle().getManager().equals(principal));
		Assert.isInstanceOf(Manager.class, principal);

		codes = prize.getCodes();
		properties = prize.getProperties();
		//		Collection<Property> ps = new ArrayList<Property>();
		//		prize.setProperties(ps);
		//		prizeRepository.save(prize);

		if (codes != null)
			for (final Code code : codes)
				this.codeService.delete(code);

		if (properties != null)
			for (Property property : properties) {
				property.getPrizes().remove(prize);
				propertyService.save(property);
			}

		this.prizeRepository.delete(prize);

	}

	//Other Business Methods =========================================================================

	public Collection<Prize> findAllByRaffle(final int raffleId) {

		Collection<Prize> result;

		result = this.prizeRepository.findAllByRaffle(raffleId);

		return result;
	}

	public Collection<Prize> findAllByUserAndRaffle(final int actorId, final int raffleId) {

		Collection<Prize> result;

		result = this.prizeRepository.findAllByUserAndRaffle(actorId, raffleId);

		return result;
	}

	public void deleteProperty(final Prize prize, final Property property) {
		Assert.isTrue(this.actorService.isManager());
		Assert.isTrue(property.getPrizes().contains(prize));

		prize.getProperties().remove(property);
		property.getPrizes().remove(prize);

		this.save2(prize);
		this.propertyService.save(property);
	}

	public void addProperty(final Prize prize, final Property property) {
		Assert.isTrue(this.actorService.isManager());
		Assert.isTrue(!property.getPrizes().contains(prize));

		prize.getProperties().add(property);
		property.getPrizes().add(prize);

		this.save2(prize);
		this.propertyService.save(property);
	}

	//Dashboard=========================================================================

	public Object[] minMaxAvgStddevOfCodesPerPrize() {

		Object[] result;

		result = this.prizeRepository.minMaxAvgStddevOfCodesPerPrize();

		return result;
	}
}
