
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

import services.AdministratorService;
import services.PrizeService;
import services.PropertyService;
import domain.Prize;
import domain.Property;

@Controller
@RequestMapping("/property")
public class PropertyController extends AbstractController {

	//Services =======================================================================

	@Autowired
	private PropertyService			propertyService;

	@Autowired
	private PrizeService			prizeService;

	@Autowired
	private AdministratorService	administratorService;


	//Listing ========================================================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Property> properties;

		properties = this.propertyService.findAll();

		result = new ModelAndView("property/list");
		result.addObject("properties", properties);
		result.addObject("requestURI", "property/list.do");

		return result;
	}

	//Listing ========================================================================

	@RequestMapping(value = "/listByPrize", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int prizeId) {
		ModelAndView result;

		try {

			final Prize prize = this.prizeService.findOne(prizeId);
			Assert.notNull(prize);

			final Collection<Property> properties;

			properties = this.propertyService.findAllByPrize(prizeId);

			result = new ModelAndView("property/list");
			result.addObject("properties", properties);
			result.addObject("requestURI", "property/list.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;
	}

	//Creating
	// ===========================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		try {
			final Property property = this.propertyService.create();
			result = this.createEditModelAndView(property);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	//Edit ==============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int propertyId) {
		ModelAndView result;

		try {
			final Property property = this.propertyService.findOne(propertyId);
			Assert.notNull(property);

			result = this.createEditModelAndView(property);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Property property, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(property);
		else
			try {

				this.propertyService.save(property);
				result = new ModelAndView("redirect:/property/list.do");

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(property, "property.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Property property, final BindingResult binding) {
		ModelAndView result;

		final Property propertyF = this.propertyService.findOne(property.getId());

		try {

			this.propertyService.delete(propertyF);

			result = new ModelAndView("redirect:/property/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(property, "property.commit.error");
		}
		return result;
	}

	//Ancillary methods
	// =======================================================================

	protected ModelAndView createEditModelAndView(final Property property) {
		ModelAndView result;

		result = this.createEditModelAndView(property, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Property property, final String message) {
		ModelAndView result;

		result = new ModelAndView("property/edit");

		result.addObject("message", message);
		result.addObject("property", property);
		result.addObject("requestURI", "property/edit.do");

		return result;
	}

}
