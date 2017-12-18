
package controllers;

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

import services.ComentableService;
import services.CommentService;
import services.ManagerService;
import services.PrizeService;
import services.RaffleService;
import domain.Comentable;
import domain.Comment;
import domain.Manager;
import domain.Prize;
import domain.Raffle;
import forms.CommentForm;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService		commentService;

	@Autowired
	private ComentableService	comentableService;

	@Autowired
	private RaffleService		raffleService;

	@Autowired
	private PrizeService		prizeService;

	@Autowired
	private ManagerService		managerService;


	@RequestMapping(value = "/postComment", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int comentableId) {
		ModelAndView result;
		try {
			Assert.notNull(comentableId);
			final Comentable comentable = this.comentableService.findOneAux(comentableId);
			Assert.notNull(comentable);

			Date now = new Date(System.currentTimeMillis() - 10);

			Manager manager = managerService.findByPrincipal();

			if (manager == null) {
				if (Raffle.class.isInstance(comentable)) {
					Raffle raffle = raffleService.findOne(comentableId);
					Assert.isTrue(raffle.getPublicationTime().before(now));
				} else if (Prize.class.isInstance(comentable)) {
					Prize prize = prizeService.findOne(comentableId);
					Assert.isTrue(prize.getRaffle().getPublicationTime().before(now));
				} else {

				}
			} else {
				if (Raffle.class.isInstance(comentable)) {
					Raffle raffle = raffleService.findOne(comentableId);
					if (!raffle.getManager().equals(manager)) {
						Assert.isTrue(raffle.getPublicationTime().before(now));
					}
				} else if (Prize.class.isInstance(comentable)) {
					Prize prize = prizeService.findOne(comentableId);
					if (prize.getRaffle().getManager().equals(manager)) {
						Assert.isTrue(prize.getRaffle().getPublicationTime().before(now));
					}
				} else {

				}
			}
			final CommentForm commentForm = new CommentForm();
			commentForm.setIdComentable(comentableId);

			result = new ModelAndView("comment/postComment");
			result.addObject("commentForm", commentForm);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;

	}
	@RequestMapping(value = "/postComment", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CommentForm commentForm, final BindingResult binding) {
		ModelAndView result;
		System.out.println(binding.getAllErrors());
		final Comment comment = this.commentService.reconstruct(commentForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(commentForm);
		else
			try {

				this.commentService.save(comment);
				if (Raffle.class.isInstance(comment.getComentable()))
					result = new ModelAndView("redirect:/raffle/display.do?raffleId=" + comment.getComentable().getId());
				else if (Prize.class.isInstance(comment.getComentable()))
					result = new ModelAndView("redirect:/prize/display.do?prizeId=" + comment.getComentable().getId());
				else
					result = new ModelAndView("redirect:/actor/seeProfile.do?actorId=" + comment.getComentable().getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(commentForm, "comment.commit.error");
			}

		return result;
	}

	//	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	//	public ModelAndView ban(@RequestParam int commentId) {
	//		commentService.banMethod(commentId);
	//		ModelAndView result = new ModelAndView("redirect:/welcome/index.do");
	//		return result;
	//	}

	protected ModelAndView createEditModelAndView(final CommentForm commentForm) {
		ModelAndView result;

		result = this.createEditModelAndView(commentForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CommentForm commentForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("comment/postComment");

		result.addObject("commentForm", commentForm);
		result.addObject("message", message);

		return result;
	}

}
