
package controllers;

import java.util.Collection;

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
import services.SocialIdentityService;
import domain.Actor;
import domain.SocialIdentity;

@Controller
@RequestMapping("/socialIdentity")
public class SocialIdentityController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SocialIdentityService	socialIdentityService;
	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public SocialIdentityController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<SocialIdentity> socialIdentities;
		final Actor actor = this.actorService.findByPrincipal();

		socialIdentities = this.socialIdentityService.findByActor(actor.getId());

		result = new ModelAndView("socialIdentity/list");
		result.addObject("requestURI", "socialIdentity/list.do");
		result.addObject("socialIdentities", socialIdentities);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialIdentity socialIdentity;

		socialIdentity = this.socialIdentityService.create();

		result = this.createEditModelAndView(socialIdentity);
		result.addObject("socialIdentity", socialIdentity);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialIdentityId) {
		ModelAndView result;
		SocialIdentity socialIdentity;

		try {
			socialIdentity = this.socialIdentityService.findOne(socialIdentityId);
			Assert.notNull(socialIdentity);
			Assert.isTrue(socialIdentity.getActor().getId() == this.actorService.findByPrincipal().getId());

			result = this.createEditModelAndView(socialIdentity);

		} catch (final Exception e) {

			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialIdentity socialIdentity, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(socialIdentity, "socialIdentity.commit.error");
		else
			try {
				Assert.isTrue(this.actorService.findByPrincipal().getId() == socialIdentity.getActor().getId());

				this.socialIdentityService.save(socialIdentity);

				result = new ModelAndView("redirect:/socialIdentity/list.do");

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(socialIdentity, "socialIdentity.commit.error");

			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int socialIdentityId) {
		ModelAndView result;

		try {
			final SocialIdentity socialIdentity = this.socialIdentityService.findOne(socialIdentityId);
			Assert.notNull(socialIdentity);
			Assert.isTrue(socialIdentity.getActor().getId() == this.actorService.findByPrincipal().getId());

			this.socialIdentityService.delete(socialIdentity);

			result = new ModelAndView("redirect:/socialIdentity/list.do");

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final SocialIdentity socialIdentity) {
		ModelAndView result;

		result = this.createEditModelAndView(socialIdentity, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SocialIdentity socialIdentity, final String text) {
		ModelAndView result;

		result = new ModelAndView("socialIdentity/edit");
		result.addObject("socialIdentity", socialIdentity);
		result.addObject("message", text);
		result.addObject("requestURI", "socialIdentity/edit.do");

		return result;
	}

}
