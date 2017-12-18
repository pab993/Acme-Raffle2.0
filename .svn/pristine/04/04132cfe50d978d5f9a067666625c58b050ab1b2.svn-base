
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

import services.CodeService;
import services.ManagerService;
import services.PrizeService;
import services.RaffleService;
import services.UserService;
import domain.Code;
import domain.Manager;
import domain.Prize;
import domain.Raffle;
import domain.User;
import forms.CodeForm;

@Controller
@RequestMapping("/code")
public class CodeController extends AbstractController {

	// Services
	// ============================================================================

	@Autowired
	private UserService		userService;

	@Autowired
	private RaffleService	raffleService;

	@Autowired
	private PrizeService	prizeService;

	@Autowired
	private CodeService		codeService;

	@Autowired
	private ManagerService	managerService;


	// Constructors
	// ============================================================================

	public CodeController() {
		super();
	}

	//Listing
	// ============================================================================

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView listUser(@RequestParam(required = false) final Integer prizeId) {
		ModelAndView result;

		try {
			final Collection<Code> codes;
			User principal;
			principal = this.userService.findByPrincipal();

			final Prize prize = this.prizeService.findOne(prizeId);
			Assert.notNull(prize);

			codes = this.codeService.findAllByUserAndPrize(principal.getId(), prize.getId());

			result = new ModelAndView("code/myList");
			result.addObject("codes", codes);
			result.addObject("requestURI", "code/myList.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;
	}

	//Listing
	// ============================================================================

	@RequestMapping(value = "/listByPrize", method = RequestMethod.GET)
	public ModelAndView listByPrize(@RequestParam(required = false) final Integer prizeId) {
		ModelAndView result;

		try {
			final Collection<Code> codes;

			final Prize prize = this.prizeService.findOne(prizeId);
			Assert.notNull(prize);
			Date now;
			now = new Date(System.currentTimeMillis() - 10);

			final Manager manager = this.managerService.findByPrincipal();

			Assert.isTrue(prize.getRaffle().getManager().equals(manager));

			codes = this.codeService.findCodesByPrize(prizeId);

			result = new ModelAndView("code/myList");
			result.addObject("codes", codes);
			result.addObject("now", now);
			result.addObject("raffle", prize.getRaffle());
			result.addObject("requestURI", "code/listByPrize.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;
	}

	@RequestMapping(value = "/enter", method = RequestMethod.GET)
	public ModelAndView enter(@RequestParam final int prizeId) {
		ModelAndView result;

		try {
			Date now = new Date(System.currentTimeMillis() - 10);
			Prize prize = prizeService.findOne(prizeId);
			Assert.notNull(prize);
			Assert.isTrue(prize.getRaffle().getPublicationTime().before(now));
			Assert.isTrue(prize.getRaffle().getDeadline().after(now));

			final CodeForm codeForm = new CodeForm();
			codeForm.setIdPrize(prizeId);

			result = new ModelAndView("code/enter");
			result.addObject("codeForm", codeForm);
			result.addObject("raffleId", prize.getRaffle().getId());

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;

	}

	@RequestMapping(value = "/enter", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CodeForm codeForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(codeForm);
		else
			try {

				final Code code = this.codeService.findCodesByPrizeAndName(codeForm.getIdPrize(), this.codeService.transformCode(codeForm.getName()));
				final String message = this.codeService.codeMessage(code);

				result = this.createEditModelAndView(codeForm, message);

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(codeForm, "code.commit.error");
			}

		return result;
	}

	// Edition
	// =============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int codeId) {
		ModelAndView result;
		try {
			final Manager principal = this.managerService.findByPrincipal();
			Date now = new Date(System.currentTimeMillis() - 10);
			Code code = codeService.findOne(codeId);
			Assert.notNull(code);
			Assert.isTrue(code.getPrize().getRaffle().getPublicationTime().after(now));
			Assert.isTrue(principal.getId() == code.getPrize().getRaffle().getManager().getId());

			result = this.createEditModelAndView2(code);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;

	}

	// Save
	// =============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Code code, final BindingResult binding) {
		ModelAndView result;
		Raffle raffle;

		raffle = code.getPrize().getRaffle();

		try {
			codeService.recontruct(code, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView2(code, "code.commit.error");
			else {
				result = new ModelAndView("redirect:/code/listByPrize.do?prizeId=" + code.getPrize().getId());
				Code saved = codeService.save(code);

			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView2(code, "code.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Code code, final BindingResult binding) {
		ModelAndView result;

		try {
			codeService.delete(code);
			result = new ModelAndView("redirect:/code/listByPrize.do?prizeId=" + code.getPrize().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView2(code, "code.commit.error");
		}
		return result;
	}

	// Ancilliary methods
	// =================================================================================================

	protected ModelAndView createEditModelAndView(final CodeForm codeForm) {
		ModelAndView result;

		result = this.createEditModelAndView(codeForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CodeForm codeForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("code/enter");
		Prize prize = prizeService.findOne(codeForm.getIdPrize());
		result.addObject("codeForm", codeForm);
		result.addObject("raffleId", prize.getRaffle().getId());
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndView2(final Code code) {
		ModelAndView result;

		result = this.createEditModelAndView2(code, null);

		return result;
	}

	protected ModelAndView createEditModelAndView2(final Code code, final String message) {
		ModelAndView result;

		result = new ModelAndView("code/edit");
		Prize prize = prizeService.findOne(code.getPrize().getId());
		result.addObject("code", code);
		result.addObject("raffleId", prize.getRaffle().getId());
		result.addObject("message", message);

		return result;
	}

}
