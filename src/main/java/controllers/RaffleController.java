
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
import services.RaffleService;
import services.TabooWordService;
import domain.Actor;
import domain.Comment;
import domain.Manager;
import domain.Raffle;

@Controller
@RequestMapping("/raffle")
public class RaffleController extends AbstractController {

	// Services
	// ============================================================================

	@Autowired
	private RaffleService		raffleService;

	@Autowired
	private CommentService		commentService;

	@Autowired
	private ComentableService	comentableService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private TabooWordService	tabooWordService;


	// Constructors
	// ============================================================================

	public RaffleController() {
		super();
	}

	//Display
	// ==============================================================================

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView verProfile(@RequestParam(required = false) final Integer raffleId) {

		ModelAndView result;

		try {

			final Date now = new Date(System.currentTimeMillis() - 10);
			final Raffle raffle = this.raffleService.findOne(raffleId);
			Assert.notNull(raffle);
			final Manager manager = this.managerService.findByPrincipal();
			if (manager != null) {
				if (raffle.getManager().equals(manager)) {

				} else
					Assert.isTrue(raffle.getPublicationTime().before(now));
			} else
				Assert.isTrue(raffle.getPublicationTime().before(now));

			final Collection<Comment> comments = this.commentService.findAllByComentable(raffle);
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

			result = new ModelAndView("raffle/display");
			result.addObject("raffle", raffle);
			result.addObject("ratioStars", this.comentableService.ratioStars(raffleId));
			result.addObject("comments", commentMask);
			result.addObject("requestURI", "raffle/display.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;

	}

	//Listing
	// ============================================================================

	@RequestMapping(value = "/myListUser", method = RequestMethod.GET)
	public ModelAndView listUser() {
		ModelAndView result;
		final Collection<Raffle> raffles;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		raffles = this.raffleService.findAllByUser(principal.getId());

		result = new ModelAndView("raffle/myList");
		result.addObject("raffles", raffles);
		result.addObject("principal", principal);
		result.addObject("requestURI", "raffle/myListUser.do");

		return result;
	}

	//Listing
	// ============================================================================

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Raffle> raffles;
		Manager principal;
		Date now;

		now = new Date(System.currentTimeMillis() - 10);
		principal = this.managerService.findByPrincipal();
		raffles = this.raffleService.findAllByManager(principal.getId());

		result = new ModelAndView("raffle/list");
		result.addObject("raffles", raffles);
		result.addObject("principal", principal);
		result.addObject("now", now);
		result.addObject("requestURI", "raffle/myList.do");

		return result;
	}

	//Browse
	// ============================================================================

	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public ModelAndView browse() {
		ModelAndView result;
		Collection<Raffle> raffles;
		Date now;

		now = new Date(System.currentTimeMillis() - 10);
		raffles = this.raffleService.findAllPublic();

		result = new ModelAndView("raffle/browse");
		result.addObject("raffles", raffles);
		result.addObject("now", now);
		result.addObject("requestURI", "raffle/browse.do");

		return result;
	}

	// Create
	// =================================================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Manager principal;

		principal = this.managerService.findByPrincipal();
		final Raffle raffle = this.raffleService.create(principal);

		result = this.createEditModelAndView(raffle);

		return result;
	}

	// Edition
	// =============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int raffleId) {
		ModelAndView result;

		try {
			Date now;

			now = new Date(System.currentTimeMillis() - 10);

			final Manager principal = this.managerService.findByPrincipal();

			final Raffle raffle = this.raffleService.findOne(raffleId);
			Assert.notNull(raffle);
			Assert.isTrue(principal.getId() == raffle.getManager().getId());
			Assert.isTrue(raffle.getPublicationTime().after(now));

			result = this.createEditModelAndView(raffle);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;

	}

	// Save
	// =============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Raffle raffle, final BindingResult binding) {
		ModelAndView result;

		try {

			this.raffleService.reconstruct(raffle, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndView(raffle, "raffle.save.error");
			else {
				result = new ModelAndView("redirect:/raffle/myList.do");
				this.raffleService.save(raffle);

				//				final Raffle saved = this.raffleService.save(raffle);
				//
				//				final Collection<Code> codesWinnerGenerated = new HashSet<Code>();
				//				final int numCodesGenerated = saved.getCodesGenerated();
				//				final int numWinnerCodes = saved.getWinnerCodes();
				//
				//				for (int i = 0; 0 < numCodesGenerated; i++) {
				//					final Code newCode = this.codeService.create(saved);
				//					if (newCode.getWinner() == true)
				//						codesWinnerGenerated.add(newCode);
				//					if (codesWinnerGenerated.size() == numWinnerCodes)
				//						this.codeService.createNotWinner(saved);
				//				}
			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(raffle, "raffle.save.error");
		}

		return result;
	}

	// Deleting
	// -------------------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Raffle raffle, final BindingResult binding) {
		ModelAndView result;

		try {
			this.raffleService.delete(raffle);
			result = new ModelAndView("redirect:/raffle/myList.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(raffle, "raffle.delete.error");
		}
		return result;
	}

	// Ancilliary methods
	// =================================================================================================

	private ModelAndView createEditModelAndView(final Raffle raffle) {

		return this.createEditModelAndView(raffle, null);
	}

	private ModelAndView createEditModelAndView(final Raffle raffle, final String message) {

		final ModelAndView resul = new ModelAndView("raffle/edit");

		resul.addObject("raffle", raffle);
		resul.addObject("message", message);

		return resul;
	}

	// Search ---------------------------------------------------------------
	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView filterList(final String keyword) {

		ModelAndView res;
		Collection<Raffle> raffles = new ArrayList<Raffle>();
		try {
			raffles = this.raffleService.search(keyword);
			res = new ModelAndView("raffle/browse");
			res.addObject("raffles", raffles);
			res.addObject("requestURI", "raffle/browse.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/raffle/browse.do");
		}
		return res;
	}

}
