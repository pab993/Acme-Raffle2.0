
package controllers;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditService;
import services.AuditorService;
import services.RaffleService;
import domain.Audit;
import domain.Auditor;
import domain.Raffle;

@Controller
@RequestMapping("/audit")
public class AuditController extends AbstractController {

	// Services
	// ============================================================================

	@Autowired
	private AuditService	auditService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private RaffleService	raffleService;


	// Constructors
	// ============================================================================

	public AuditController() {
		super();
	}

	//Listing
	// ============================================================================

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Audit> audits;
		Auditor principal;

		principal = this.auditorService.findByPrincipal();
		audits = this.auditService.findAllByAuditor(principal.getId());

		result = new ModelAndView("audit/list");
		result.addObject("audits", audits);
		result.addObject("principal", principal);
		result.addObject("requestURI", "audit/myList.do");

		return result;
	}

	// Create
	// =================================================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int raffleId) {
		ModelAndView result;

		try {
			Auditor principal;
			Raffle raffle;
			raffle = this.raffleService.findOne(raffleId);
			Assert.notNull(raffle);
			Date now = new Date(System.currentTimeMillis() - 10);
			Assert.isTrue(raffle.getPublicationTime().before(now));

			principal = this.auditorService.findByPrincipal();
			final Audit audit = this.auditService.create(raffle);

			result = this.createEditModelAndView(audit);
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}
		return result;
	}

	// Edition
	// =============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditId) {
		ModelAndView result;

		try {
			final Auditor principal = this.auditorService.findByPrincipal();
			final Audit audit = this.auditService.findOne(auditId);
			Assert.notNull(audit);
			Date now = new Date(System.currentTimeMillis() - 10);
			Assert.isTrue(audit.getRaffle().getPublicationTime().before(now));
			Assert.isTrue(audit.getAuditor().getId() == principal.getId());
			Assert.isTrue(audit.getIsDraft() == true);

			result = this.createEditModelAndView(audit);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;

	}

	// Save
	// =============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Audit audit, final BindingResult binding) {
		ModelAndView result;

		try {

			if (binding.hasErrors())
				result = this.createEditModelAndView(audit, "audit.save.error");
			else {
				result = new ModelAndView("redirect:/audit/myList.do");
				this.auditService.save(audit);
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(audit, "audit.save.error");
		}

		return result;
	}

	// Deleting
	// -------------------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audit audit, final BindingResult binding) {
		ModelAndView result;

		try {
			this.auditService.deleteAuditor(audit);
			result = new ModelAndView("redirect:/audit/myList.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(audit, "audit.delete.error");
		}
		return result;
	}

	@RequestMapping(value = "/notDraft", method = RequestMethod.GET)
	public ModelAndView unregister(@RequestParam final int auditId) {
		ModelAndView result;

		try {

			final Auditor principal = this.auditorService.findByPrincipal();
			Audit audit;

			audit = this.auditService.findOne(auditId);
			Assert.notNull(audit);
			Assert.isTrue(audit.getAuditor().getId() == principal.getId());

			try {
				this.auditService.changeDraft(audit);
				result = this.list();
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(audit, "audit.changeDraft.error");
			}
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}
		return result;
	}

	// Ancilliary methods
	// =================================================================================================

	private ModelAndView createEditModelAndView(final Audit audit) {

		return this.createEditModelAndView(audit, null);
	}

	private ModelAndView createEditModelAndView(final Audit audit, final String message) {

		final ModelAndView resul = new ModelAndView("audit/edit");

		resul.addObject("audit", audit);
		resul.addObject("message", message);

		return resul;
	}

}
