
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.PrizeService;
import domain.Prize;

@Controller
@RequestMapping("/admin/prize")
public class AdminPrizeController extends AbstractController {

	// Services
	// ============================================================================

	@Autowired
	private PrizeService	prizeService;


	//	@Autowired
	//	private PropertyService	propertyService;

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		Collection<Prize> prizes;
		Date now;

		now = new Date(System.currentTimeMillis());
		prizes = this.prizeService.findAll();

		result = new ModelAndView("prize/list");
		result.addObject("prizes", prizes);
		result.addObject("now", now);
		result.addObject("requestURI", "prize/list.do");

		return result;
	}

	//	@RequestMapping(value = "/addProperty", method = RequestMethod.GET)
	//	public ModelAndView addProperty(@RequestParam final int prizeId) {
	//		ModelAndView result;
	//		final Prize prize = this.prizeService.findOne(prizeId);
	//		Collection<Property> properties = propertyService.findAll();
	//		Collection<Property> propertiesPrize = propertyService.findAllPropertyPrize(prize);
	//		Collection<Property> restProperties = new ArrayList<Property>();
	//		for (Property pr : properties) {
	//			if (!propertiesPrize.contains(pr)) {
	//				restProperties.add(pr);
	//			}
	//		}
	//
	//		result = this.createEditModelAndView(prize);
	//		result.addObject("propertiesPrize", propertiesPrize);
	//		result.addObject("restProperties", restProperties);
	//
	//		return result;
	//
	//	}
	//
	//	@RequestMapping(value = "/removeProperty", method = RequestMethod.GET)
	//	public ModelAndView removeProperty() {
	//		ModelAndView result;
	//		Collection<Prize> prizes;
	//		Date now;
	//
	//		now = new Date(System.currentTimeMillis());
	//		prizes = this.prizeService.findAll();
	//
	//		result = new ModelAndView("prize/list");
	//		result.addObject("prizes", prizes);
	//		result.addObject("now", now);
	//		result.addObject("requestURI", "prize/list.do");
	//
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "/deleteProperty", method = RequestMethod.GET)
	//	public ModelAndView deleteProperty(@RequestParam final int propertyId, final int prizeId) {
	//		ModelAndView result;
	//		final Property property = this.propertyService.findOne(propertyId);
	//		final Prize prize = prizeService.findOne(prizeId);
	//		Collection<Property> properties = propertyService.findAll();
	//		Collection<Property> propertiesPrize = propertyService.findAllPropertyPrize(prize);
	//		Collection<Property> restProperties = new ArrayList<Property>();
	//		for (Property pr : properties) {
	//			if (!propertiesPrize.contains(pr)) {
	//				restProperties.add(pr);
	//			}
	//		}
	//
	//		prizeService.deleteProperty(prize, property);
	//
	//		prizeService.save2(prize);
	//
	//		result = new ModelAndView("redirect:/admin/prize/editProperty.do?prizeId=" + prizeId);
	//		result.addObject("propertiesPrize", propertiesPrize);
	//		result.addObject("restProperties", restProperties);
	//
	//		return result;
	//
	//	}

	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid final Prize prize, final BindingResult binding) {
	//		ModelAndView result;
	//		//		Raffle raffle;
	//
	//		//		raffle = prize.getRaffle();
	//
	//		try {
	//
	//			if (binding.hasErrors())
	//				result = this.createEditModelAndView(prize, "prize.save.error");
	//			else {
	//				result = new ModelAndView("redirect:/admin/prize/editProperty.do?prizeId=" + prize.getId());
	//				this.prizeService.save2(prize);
	//
	//			}
	//		} catch (final Throwable oops) {
	//			result = this.createEditModelAndView(prize, "prize.save.error");
	//		}
	//
	//		return result;
	//	}

	//	private ModelAndView createEditModelAndView(final Prize prize) {
	//
	//		return this.createEditModelAndView(prize, null);
	//	}
	//
	//	private ModelAndView createEditModelAndView(final Prize prize, final String message) {
	//
	//		final ModelAndView resul = new ModelAndView("prize/editProperty");
	//
	//		resul.addObject("prize", prize);
	//		resul.addObject("message", message);
	//
	//		return resul;
	//	}

}
