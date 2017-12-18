
package controllers;

import java.util.ArrayList;
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

import services.ActorService;
import services.ComentableService;
import services.CommentService;
import services.ManagerService;
import services.PrizeService;
import services.RaffleService;
import services.TabooWordService;
import domain.Comment;
import domain.Manager;
import domain.Prize;
import domain.Raffle;
import domain.User;

@Controller
@RequestMapping("/prize")
public class PrizeController extends AbstractController {

	// Services
	// ============================================================================

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PrizeService		prizeService;

	@Autowired
	private CommentService		commentService;

	@Autowired
	private ComentableService	comentableService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private RaffleService		raffleService;

	@Autowired
	private TabooWordService	tabooWordService;


	// Constructors
	// ============================================================================

	public PrizeController() {
		super();
	}

	//Display
	// ==============================================================================

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView verProfile(@RequestParam(required = false) final Integer prizeId) {

		ModelAndView result;

		try {
			final Date now = new Date(System.currentTimeMillis() - 10);
			final Prize prize = this.prizeService.findOne(prizeId);
			Assert.notNull(prize);

			final Manager manager = this.managerService.findByPrincipal();
			if (manager != null) {
				if (prize.getRaffle().getManager().equals(manager)) {

				} else
					Assert.isTrue(prize.getRaffle().getPublicationTime().before(now));
			} else
				Assert.isTrue(prize.getRaffle().getPublicationTime().before(now));

			final Collection<Comment> comments = this.commentService.findAllByComentable(prize);
			final Collection<Comment> commentMask = new ArrayList<Comment>();

			for (final Comment c : comments) {

				String text = c.getText();
				text = this.tabooWordService.maskCharacters(text);
				final Comment newComment = new Comment();
				newComment.setId(c.getId());
				newComment.setText(text);
				newComment.setActor(c.getActor());
				newComment.setComentable(c.getComentable());
				newComment.setScore(c.getScore());
				newComment.setCreateMoment(c.getCreateMoment());

				commentMask.add(newComment);
			}

			result = new ModelAndView("prize/display");
			result.addObject("prize", prize);
			result.addObject("ratioStars", this.comentableService.ratioStars(prizeId));
			result.addObject("comments", commentMask);
			result.addObject("requestURI", "prize/display.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;

	}

	//Listing
	// ============================================================================

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView listUser(@RequestParam(required = false) final Integer raffleId) {
		ModelAndView result;

		try {
			final Collection<Prize> prizes;
			User principal;
			principal = (User) this.actorService.findByPrincipal();

			final Raffle raffle = this.raffleService.findOne(raffleId);
			Assert.notNull(raffle);

			prizes = this.prizeService.findAllByUserAndRaffle(principal.getId(), raffle.getId());

			result = new ModelAndView("prize/myList");
			result.addObject("prizes", prizes);
			result.addObject("principal", principal);
			result.addObject("requestURI", "prize/myList.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;
	}
	//Listing
	// ============================================================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int raffleId) {
		ModelAndView result;

		try {
			Collection<Prize> prizes;
			Date now;
			now = new Date(System.currentTimeMillis() - 10);

			final Raffle raffle = this.raffleService.findOne(raffleId);
			Assert.notNull(raffle);
			final Manager manager = this.managerService.findByPrincipal();
			if (manager != null) {
				if (raffle.getManager().equals(manager)) {

				} else
					Assert.isTrue(raffle.getPublicationTime().before(now));
			} else
				Assert.isTrue(raffle.getPublicationTime().before(now));

			prizes = this.prizeService.findAllByRaffle(raffleId);

			result = new ModelAndView("prize/list");
			result.addObject("prizes", prizes);
			result.addObject("now", now);
			result.addObject("raffle", raffle);
			result.addObject("principal", this.actorService.findByPrincipal());
			result.addObject("requestURI", "prize/list.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;
	}

	// Create
	// =================================================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int raffleId) {
		ModelAndView result;

		try {
			Raffle raffle;
			final Date now = new Date(System.currentTimeMillis() - 10);

			raffle = this.raffleService.findOne(raffleId);
			Assert.notNull(raffle);
			Assert.isTrue(raffle.getPublicationTime().after(now));
			final Prize prize = this.prizeService.create(raffle);

			result = this.createEditModelAndView(prize);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;
	}

	// Edition
	// =============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int prizeId) {
		ModelAndView result;
		try {
			final Manager principal = this.managerService.findByPrincipal();
			final Date now = new Date(System.currentTimeMillis() - 10);
			final Prize prize = this.prizeService.findOne(prizeId);
			Assert.notNull(prize);
			Assert.isTrue(prize.getRaffle().getPublicationTime().after(now));
			Assert.isTrue(principal.getId() == prize.getRaffle().getManager().getId());

			result = this.createEditModelAndView(prize);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;

	}

	// Save
	// =============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Prize prize, final BindingResult binding) {
		ModelAndView result;
		Raffle raffle;

		raffle = prize.getRaffle();

		try {

			if (binding.hasErrors())
				result = this.createEditModelAndView(prize, "prize.save.error");
			else {
				result = new ModelAndView("redirect:/prize/list.do?raffleId=" + raffle.getId());
				this.prizeService.save(prize);

			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(prize, "prize.save.error");
		}

		return result;
	}

	// Edition Prize
	// =============================================================================

	@RequestMapping(value = "/editPrize", method = RequestMethod.GET)
	public ModelAndView editPrize(@RequestParam final int prizeId) {
		ModelAndView result;

		try {

			final Manager principal = this.managerService.findByPrincipal();
			final Date now = new Date(System.currentTimeMillis() - 10);
			final Prize prize = this.prizeService.findOne(prizeId);
			Assert.notNull(prize);
			Assert.isTrue(prize.getRaffle().getPublicationTime().after(now));
			Assert.isTrue(principal.getId() == prize.getRaffle().getManager().getId());

			result = this.createEditModelAndView2(prize);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;

	}

	// Save Prize
	// =============================================================================

	@RequestMapping(value = "/editPrize", method = RequestMethod.POST, params = "save")
	public ModelAndView savePrize(@Valid final Prize prize, final BindingResult binding) {
		ModelAndView result;
		Raffle raffle;

		raffle = prize.getRaffle();

		try {

			if (binding.hasErrors())
				result = this.createEditModelAndView2(prize, "prize.save.error");
			else {
				result = new ModelAndView("redirect:/prize/list.do?raffleId=" + raffle.getId());
				this.prizeService.save2(prize);
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView2(prize, "prize.save.error");
		}

		return result;
	}

	// Edit Regenerate Prize-Code
	// =============================================================================

	@RequestMapping(value = "/regenerateCode", method = RequestMethod.GET)
	public ModelAndView editRegeneratePrize(@RequestParam final int prizeId) {
		ModelAndView result;

		try {
			final Manager principal = this.managerService.findByPrincipal();
			final Date now = new Date(System.currentTimeMillis() - 10);
			final Prize prize = this.prizeService.findOne(prizeId);
			Assert.notNull(prize);
			Assert.isTrue(prize.getRaffle().getPublicationTime().after(now));
			Assert.isTrue(principal.getId() == prize.getRaffle().getManager().getId());

			result = this.createEditModelAndView3(prize);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;

	}

	// Save Regenerate Prize-Code
	// =============================================================================

	@RequestMapping(value = "/regenerateCode", method = RequestMethod.POST, params = "save")
	public ModelAndView saveRegeneratePrize(@Valid final Prize prize, final BindingResult binding) {
		ModelAndView result;
		Raffle raffle;

		raffle = prize.getRaffle();

		try {

			if (binding.hasErrors())
				result = this.createEditModelAndView3(prize, "prize.save.error");
			else {
				result = new ModelAndView("redirect:/prize/list.do?raffleId=" + raffle.getId());
				this.prizeService.save(prize);

				//				final Collection<Code> codesWinnerGenerated = new HashSet<Code>();
				//				final Collection<Code> codesNotWinnerGenerated = new HashSet<Code>();
				//				final int numCodesGenerated = saved.getCodesGenerated();
				//				final int numWinnerCodes = saved.getWinnerCodes();
				//				Collection<Code> codes = new HashSet<Code>();
				//
				//				codes = prize.getCodes();
				//
				//				if (codes != null)
				//					for (final Code code : codes)
				//						this.codeService.delete(code);
				//
				//				for (int i = 0; i < numCodesGenerated; i++)
				//					if (codesNotWinnerGenerated.size() == numCodesGenerated - numWinnerCodes) {
				//						final Code newCode = this.codeService.createWinner(saved);
				//						codesWinnerGenerated.add(newCode);
				//					} else if (codesWinnerGenerated.size() == numWinnerCodes) {
				//						final Code newCode1 = this.codeService.createNotWinner(saved);
				//						codesNotWinnerGenerated.add(newCode1);
				//					} else if (!(codesNotWinnerGenerated.size() == numCodesGenerated - numWinnerCodes && !(codesWinnerGenerated.size() == numWinnerCodes))) {
				//						final Code newCode2 = this.codeService.create(saved);
				//						if (newCode2.getWinner() == true)
				//							codesWinnerGenerated.add(newCode2);
				//						if (newCode2.getWinner() == false)
				//							codesNotWinnerGenerated.add(newCode2);
				//					}

			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView3(prize, "prize.save.error");
		}

		return result;
	}

	// Deleting
	// -------------------------------------------------------------------------------------

	@RequestMapping(value = "/editPrize", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Prize prize, final BindingResult binding) {
		ModelAndView result;
		Raffle raffle;

		raffle = prize.getRaffle();
		try {
			this.prizeService.delete(prize);
			result = new ModelAndView("redirect:/prize/list.do?raffleId=" + raffle.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView2(prize, "prize.delete.error");
		}
		return result;
	}

	// Ancilliary methods
	// =================================================================================================

	private ModelAndView createEditModelAndView(final Prize prize) {

		return this.createEditModelAndView(prize, null);
	}

	private ModelAndView createEditModelAndView(final Prize prize, final String message) {

		final ModelAndView resul = new ModelAndView("prize/edit");

		resul.addObject("prize", prize);
		resul.addObject("message", message);

		return resul;
	}

	//	private ModelAndView createEditModelAndViewCode(final Prize prize) {
	//
	//		return this.createEditModelAndViewCode(prize, null);
	//	}
	//
	//	private ModelAndView createEditModelAndViewCode(final Prize prize, final String message) {
	//
	//		final ModelAndView resul = new ModelAndView("code/edit");
	//
	//		resul.addObject("prize", prize);
	//		resul.addObject("message", message);
	//
	//		return resul;
	//	}

	private ModelAndView createEditModelAndView2(final Prize prize) {

		return this.createEditModelAndView2(prize, null);
	}

	private ModelAndView createEditModelAndView2(final Prize prize, final String message) {

		final ModelAndView resul = new ModelAndView("prize/editPrize");

		resul.addObject("prize", prize);
		resul.addObject("message", message);

		return resul;
	}

	private ModelAndView createEditModelAndView3(final Prize prize) {

		return this.createEditModelAndView3(prize, null);
	}

	private ModelAndView createEditModelAndView3(final Prize prize, final String message) {

		final ModelAndView resul = new ModelAndView("prize/regenerateCode");

		resul.addObject("prize", prize);
		resul.addObject("message", message);

		return resul;
	}
}
