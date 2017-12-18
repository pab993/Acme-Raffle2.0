
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import domain.Manager;

@Controller
@RequestMapping("/admin/manager")
public class AdminManagerController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdminManagerController() {
		super();
	}


	@Autowired
	private ManagerService	managerService;


	//Browse
	// ============================================================================

	@RequestMapping(value = "/browse", method = RequestMethod.GET)
	public ModelAndView browse(@RequestParam(defaultValue = "0", required = false) final int errorMessage) {
		ModelAndView result;
		Collection<Manager> managers;

		managers = this.managerService.findDebtors();
		//		final Collection<Manager> managerBan = this.managerService.managerBan(managers);

		result = new ModelAndView("manager/browse");
		result.addObject("managers", managers);
		//		result.addObject("managerBan", managerBan);
		result.addObject("requestURI", "manager/browse.do");

		this.error(result, errorMessage);
		return result;
	}

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(final int managerId, @RequestParam(defaultValue = "0", required = false) final int errorMessage) {
		ModelAndView res;

		try {
			final Manager manager = this.managerService.findOne(managerId);
			Assert.notNull(manager);

			res = new ModelAndView();

			try {
				this.managerService.ban(manager);
				res.setViewName("redirect:/admin/manager/browse.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/admin/manager/browse.do?errorMessage=2");
			}
			this.error(res, errorMessage);
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/panic/misc.do");

		}
		return res;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(final int managerId, @RequestParam(defaultValue = "0", required = false) final int errorMessage) {
		ModelAndView res;

		try {
			final Manager manager = this.managerService.findOne(managerId);
			Assert.notNull(manager);

			res = new ModelAndView();

			try {
				this.managerService.unban(manager);
				res.setViewName("redirect:/admin/manager/browse.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/admin/manager/browse.do?errorMessage=2");
			}
			this.error(res, errorMessage);
		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/panic/misc.do");

		}
		return res;
	}

	private void error(final ModelAndView result, final int errorMessage) {
		if (errorMessage != 0)
			switch (errorMessage) {
			case 2:
				result.addObject("errorMessage", "admin.error.ban");
				break;
			default:
				result.addObject("errorMessage", "admin.errorDefault");
				break;
			}
	}

}
