
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import domain.User;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdminUserController() {
		super();
	}


	@Autowired
	private UserService	userService;


	//Browse
	// ============================================================================

	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public ModelAndView browse() {
		ModelAndView result;
		Collection<User> users;

		users = this.userService.findAll();

		final Collection<User> userBan = this.userService.userBan(users);

		result = new ModelAndView("user/browse");
		result.addObject("users", users);
		result.addObject("userBan", userBan);
		result.addObject("requestURI", "user/browse.do");

		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(final int userId) {
		ModelAndView res;

		try {
			final User user = this.userService.findOne(userId);
			Assert.notNull(user);

			res = new ModelAndView();

			try {
				this.userService.ban(user);
				res.setViewName("redirect:/admin/user/browse.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/panic/misc.do");
			}
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/panic/misc.do");

		}
		return res;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(final int userId) {
		ModelAndView res;

		try {
			final User user = this.userService.findOne(userId);
			Assert.notNull(user);
			res = new ModelAndView();

			try {
				this.userService.unban(user);
				res.setViewName("redirect:/admin/user/browse.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("/admin/user/browse.do?error");
			}
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/panic/misc.do");

		}

		return res;
	}

}
