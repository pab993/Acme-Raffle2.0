
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import domain.Comment;

@Controller
@RequestMapping("/admin/comment")
public class AdminCommentController extends AbstractController {

	@Autowired
	private CommentService	commentService;


	//Listing
	// ============================================================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Comment> comments = this.commentService.getCommentInappropiate();

		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("requestURI", "admin/comment/list.do");

		return result;
	}

	// Deleting
	// -------------------------------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int commentId) {
		ModelAndView result;

		try {
			final Comment comment = this.commentService.findOne(commentId);
			Assert.notNull(comment);

			try {
				this.commentService.delete(comment);
				result = new ModelAndView("redirect:/admin/comment/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(comment, "comment.delete.error");
			}

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;
	}

	// Ancilliary methods
	// =================================================================================================

	//	private ModelAndView createEditModelAndView(final Comment comment) {
	//
	//		return this.createEditModelAndView(comment, null);
	//	}

	private ModelAndView createEditModelAndView(final Comment comment, final String message) {

		final ModelAndView resul = new ModelAndView("admin/comment/list");

		resul.addObject("comment", comment);
		resul.addObject("message", message);

		return resul;
	}

}
