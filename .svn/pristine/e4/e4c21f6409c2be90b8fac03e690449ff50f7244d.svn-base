
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditRepository;
import domain.Audit;
import domain.Auditor;
import domain.Manager;
import domain.Raffle;

@Service
@Transactional
public class AuditService {

	//Managed Repository 
	// ===============================================================================================

	@Autowired
	private AuditRepository	auditRepository;

	//Services
	// ===============================================================================================

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private AuditorService	auditorService;


	//SCRUDs Methods
	//===============================================================================================

	public Audit findOne(final int auditId) {
		Audit result;

		result = this.auditRepository.findOne(auditId);

		return result;
	}

	public Audit create(final Raffle raffle) {
		Assert.notNull(raffle);
		final Auditor principal;
		Audit result;
		Date moment;

		principal = this.auditorService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isInstanceOf(Auditor.class, principal);
		moment = new Date(System.currentTimeMillis());

		result = new Audit();
		result.setAuditor(principal);
		result.setMoment(moment);
		result.setIsDraft(true);
		result.setRaffle(raffle);
		principal.getAudits().add(result);
		raffle.getAudits().add(result);

		return result;
	}

	public Audit save(final Audit audit) {
		Assert.notNull(audit);
		Assert.notNull(audit.getIsDraft().equals(true));

		Audit result;

		final Auditor principal = this.auditorService.findByPrincipal();

		Assert.isInstanceOf(Auditor.class, principal);
		Assert.notNull(principal);
		Assert.isTrue(principal.equals(audit.getAuditor()));

		//		principal.getAudits().add(raffle);

		result = this.auditRepository.save(audit);

		return result;
	}

	public void deleteAuditor(final Audit audit) {
		Assert.notNull(audit);
		Assert.isTrue(audit.getIsDraft().equals(true));

		final Auditor principal = this.auditorService.findByPrincipal();
		Assert.notNull(principal);

		this.auditRepository.delete(audit);

	}

	public void delete(final Audit audit) {

		Assert.notNull(audit);

		final Manager principal = this.managerService.findByPrincipal();
		Assert.notNull(principal);

		this.auditRepository.delete(audit);

	}

	//Other Business Methods =========================================================================

	public Collection<Audit> findAllByAuditor(final int auditorId) {

		Collection<Audit> result;

		result = this.auditRepository.findAllByAuditor(auditorId);

		return result;
	}

	public void changeDraft(final Audit audit) {
		Assert.notNull(audit);

		final Auditor principal = this.auditorService.findByPrincipal();
		Assert.notNull(principal);

		audit.setIsDraft(false);
	}

}
