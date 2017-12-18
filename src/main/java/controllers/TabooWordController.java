
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.TabooWordService;
import domain.TabooWord;

@Controller
@RequestMapping("/tabooWord")
public class TabooWordController extends AbstractController {

	//Services =======================================================================

	@Autowired
	private TabooWordService		tabooWordService;

	@Autowired
	private AdministratorService	administratorService;


	//Listing ========================================================================

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<TabooWord> tabooWords;

		tabooWords = this.tabooWordService.findAll();

		result = new ModelAndView("tabooWord/list");
		result.addObject("tabooWords", tabooWords);
		result.addObject("requestURI", "tabooWord/list.do");

		return result;
	}

	//Creating
	// ===========================================================================

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		try {
			final TabooWord tabooWord = this.tabooWordService.create();
			result = this.createEditModelAndView(tabooWord);

		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	//Edit ==============================================================================

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tabooWordId) {
		ModelAndView result;

		try {
			final TabooWord tabooWord = this.tabooWordService.findOne(tabooWordId);

			if (tabooWord == null)
				result = new ModelAndView("redirect:/panic/misc.do");
			else
				result = this.createEditModelAndView(tabooWord);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/panic/misc.do");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TabooWord tabooWord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tabooWord);
		else
			try {

				this.tabooWordService.save(tabooWord);
				result = new ModelAndView("redirect:/tabooWord/list.do");

			} catch (final Throwable oops) {

				result = this.createEditModelAndView(tabooWord, "tabooWord.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final TabooWord tabooWord, final BindingResult binding) {
		ModelAndView result;

		final TabooWord tabooWordF = this.tabooWordService.findOne(tabooWord.getId());

		try {

			this.tabooWordService.delete(tabooWordF);

			result = new ModelAndView("redirect:/tabooWord/list.do");

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tabooWord, "tabooWord.commit.error");
		}
		return result;
	}

	//Ancillary methods
	// =======================================================================

	protected ModelAndView createEditModelAndView(final TabooWord tabooWord) {
		ModelAndView result;

		result = this.createEditModelAndView(tabooWord, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final TabooWord tabooWord, final String message) {
		ModelAndView result;

		result = new ModelAndView("tabooWord/edit");

		result.addObject("message", message);
		result.addObject("tabooWord", tabooWord);
		result.addObject("requestURI", "tabooWord/edit.do");

		return result;
	}

}
