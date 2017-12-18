
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PrizeService;
import services.PropertyService;
import domain.Prize;
import domain.Property;

@Controller
@RequestMapping("/admin/property")
public class AdminPropertyController extends AbstractController {

	// Services
	// ============================================================================

	@Autowired
	private PrizeService	prizeService;

	@Autowired
	private PropertyService	propertyService;


	@RequestMapping(value = "/listPrize", method = RequestMethod.GET)
	public ModelAndView listPrize(@RequestParam final int prizeId) {
		ModelAndView result;

		try {
			Collection<Property> properties;
			Collection<Property> propertiesPrize;

			final Prize prize = this.prizeService.findOne(prizeId);
			Assert.notNull(prize);

			properties = this.propertyService.findAll();
			propertiesPrize = this.propertyService.findAllPropertyPrize(prize);

			result = new ModelAndView("property/list");
			result.addObject("properties", properties);
			result.addObject("propertiesPrize", propertiesPrize);
			result.addObject("prizeId", prizeId);
			result.addObject("requestURI", "property/list.do");

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;
	}

	@RequestMapping(value = "/addProperty", method = RequestMethod.GET)
	public ModelAndView addProperty(final int propertyId, final int prizeId, @RequestParam(defaultValue = "0", required = false) final int errorMessage) {
		ModelAndView res;

		try {
			final Property property = this.propertyService.findOne(propertyId);
			Assert.notNull(property);

			final Prize prize = this.prizeService.findOne(prizeId);
			Assert.notNull(prize);

			res = new ModelAndView();

			try {
				this.prizeService.addProperty(prize, property);
				res.setViewName("redirect:/admin/property/listPrize.do?prizeId=" + prizeId);
			} catch (final Throwable oops) {
				res = new ModelAndView("redirect:/seminary/listPrize.do?prizeId=" + prizeId + "&errorMessage=2");
			}
			this.error(res, errorMessage);

		} catch (final Throwable oops) {

			res = new ModelAndView("redirect:/panic/misc.do");

		}
		return res;
	}

	@RequestMapping(value = "/deleteProperty", method = RequestMethod.GET)
	public ModelAndView deleteProperty(final int propertyId, final int prizeId, @RequestParam(defaultValue = "0", required = false) final int errorMessage) {
		ModelAndView res;

		final Property property = this.propertyService.findOne(propertyId);
		final Prize prize = this.prizeService.findOne(prizeId);

		res = new ModelAndView();

		try {
			this.prizeService.deleteProperty(prize, property);
			res.setViewName("redirect:/admin/property/listPrize.do?prizeId=" + prizeId);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/seminary/listPrize.do?prizeId=" + prizeId + "&errorMessage=3");
		}
		this.error(res, errorMessage);
		return res;
	}

	private void error(final ModelAndView result, final int errorMessage) {
		if (errorMessage != 0)
			switch (errorMessage) {
			case 2:
				result.addObject("errorMessage", "prize.error.addProperty");
				break;
			case 3:
				result.addObject("errorMessage", "prize.error.deleteProperty");
				break;
			default:
				result.addObject("errorMessage", "prize.errorDefault");
				break;
			}
	}

}
