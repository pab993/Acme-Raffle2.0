
package controllers;

import java.util.ArrayList;
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

import services.ActorRegisterService;
import services.ActorService;
import services.BillService;
import services.ComentableService;
import services.CommentService;
import services.TabooWordService;
import domain.Actor;
import domain.Auditor;
import domain.Comment;
import domain.Manager;
import domain.User;
import forms.ActorForm;
import forms.ActorRegisterForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	//Services
	// =============================================================================

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ActorRegisterService	actorRegisterService;

	@Autowired
	private CommentService			commentService;

	@Autowired
	private ComentableService		comentableService;

	@Autowired
	private TabooWordService		tabooWordService;

	@Autowired
	private BillService				billService;


	// Constructors
	// ==============================================================================

	public ActorController() {
		super();
	}

	//Profile
	// ==============================================================================

	@RequestMapping(value = "/seeProfile", method = RequestMethod.GET)
	public ModelAndView verProfile(@RequestParam(required = false) final Integer actorId) {

		ModelAndView result;
		Actor principal;

		try {
			if (actorId != null) {

				final Actor actor = this.actorService.findOne(actorId);
				Assert.notNull(actor);

				principal = this.actorService.findByPrincipal();
				final Collection<Comment> comments = this.commentService.findAllByComentable(actor);
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

				result = new ModelAndView("actor/seeProfile");
				result.addObject("principal", principal);
				result.addObject("actor", actor);
				result.addObject("calculateBillsUnpaidByManager", this.billService.calculateBillsUnpaidByManagerId(actorId));
				result.addObject("calculateTotalTaxByManager", this.billService.calculateTotalTaxByManagerId(actorId));
				result.addObject("ratioStars", this.comentableService.ratioStars(actorId));
				result.addObject("comments", commentMask);
				result.addObject("requestURI", "actor/seeProfile.do");

			} else {

				principal = this.actorService.findByPrincipal();
				result = new ModelAndView("redirect:/actor/seeProfile.do?actorId=" + principal.getId());

			}
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;

	}

	@RequestMapping(value = "/seeProfileUnregistered", method = RequestMethod.GET)
	public ModelAndView verProfileUnregistred(@RequestParam(required = false) final Integer actorId) {

		ModelAndView result;
		Actor principal;

		try {
			if (actorId != null) {

				final Actor actor = this.actorService.findOne(actorId);
				Assert.notNull(actor);

				result = new ModelAndView("actor/seeProfile");
				result.addObject("actor", actor);

			} else {

				principal = this.actorService.findByPrincipal();
				result = new ModelAndView("redirect:/actor/seeProfile.do?actorId=" + principal.getId());

			}
		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/panic/misc.do");

		}

		return result;

	}

	//Edition
	// ================================================================================

	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public ModelAndView editProfile() {

		ModelAndView result;
		Actor principal;

		principal = this.actorService.findByPrincipal();
		final ActorForm actorForm = this.actorService.reconstructToForm(principal);

		result = new ModelAndView("actor/editProfile");
		result.addObject("actorForm", actorForm);

		return result;

	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;
		Actor actor;

		try {

			actor = this.actorService.reconstruct1(actorForm, binding);

			if (binding.hasErrors())

				result = this.createEditModelAndView(actorForm, "actor.save.error");

			else {

				actor = this.actorService.reconstruct2(actorForm, binding);

				result = new ModelAndView("redirect:/actor/seeProfile.do");

				actor = this.actorService.save(actor);

			}

		} catch (final Throwable oops) {

			result = this.createEditModelAndView(actorForm, "actor.save.error");

		}

		return result;

	}

	//Edition
	//=============================================================================

	@RequestMapping(value = "/registerManager", method = RequestMethod.GET)
	public ModelAndView edit() {

		ModelAndView result;
		result = new ModelAndView("actor/registerManager");

		result.addObject("actorRegisterForm", new ActorRegisterForm());

		return result;
	}

	// Save
	// ====================================================================

	@RequestMapping(value = "/registerManager", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ActorRegisterForm actorRegisterForm, final BindingResult binding) {
		ModelAndView result;
		Manager actorRegister;

		try {
			actorRegister = this.actorRegisterService.reconstructManager(actorRegisterForm, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndViewRegister(actorRegisterForm, "actorRegister.save.error");
			else {
				result = new ModelAndView("redirect:/welcome/index.do");
				actorRegister = (Manager) this.actorRegisterService.save(actorRegister);

			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewRegister(actorRegisterForm, "actorRegister.save.error");
		}

		return result;
	}

	//Edition
	//=============================================================================

	@RequestMapping(value = "/registerAuditor", method = RequestMethod.GET)
	public ModelAndView editAuditor() {

		ModelAndView result;
		result = new ModelAndView("actor/registerAuditor");

		result.addObject("actorRegisterForm", new ActorRegisterForm());

		return result;
	}

	// Save
	// ====================================================================

	@RequestMapping(value = "/registerAuditor", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAuditor(@Valid final ActorRegisterForm actorRegisterForm, final BindingResult binding) {
		ModelAndView result;
		Auditor actorRegister;

		try {
			actorRegister = this.actorRegisterService.reconstructAuditor(actorRegisterForm, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndViewRegisterAuditor(actorRegisterForm, "actorRegister.save.error");
			else {
				result = new ModelAndView("redirect:/welcome/index.do");
				actorRegister = (Auditor) this.actorRegisterService.save(actorRegister);

			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewRegisterAuditor(actorRegisterForm, "actorRegister.save.error");
		}

		return result;
	}

	//Edition
	//=============================================================================

	@RequestMapping(value = "/registerUser", method = RequestMethod.GET)
	public ModelAndView editUser() {

		ModelAndView result;
		result = new ModelAndView("actor/registerUser");

		result.addObject("actorRegisterForm", new ActorRegisterForm());

		return result;
	}

	// Save
	// ====================================================================

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST, params = "save")
	public ModelAndView saveUser(@Valid final ActorRegisterForm actorRegisterForm, final BindingResult binding) {
		ModelAndView result;
		User actorRegister;

		try {
			actorRegister = this.actorRegisterService.reconstructUser(actorRegisterForm, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndViewRegisterUser(actorRegisterForm, "actorRegister.save.error");
			else {
				result = new ModelAndView("redirect:/welcome/index.do");
				actorRegister = (User) this.actorRegisterService.save(actorRegister);

			}
		} catch (final Throwable oops) {
			result = this.createEditModelAndViewRegisterUser(actorRegisterForm, "actorRegister.save.error");
		}

		return result;
	}

	// Ancilliary methods
	// =================================================================================================

	//	private ModelAndView createEditModelAndViewRegister(final ActorRegisterForm actorRegisterForm) {
	//
	//		return this.createEditModelAndViewRegister(actorRegisterForm, null);
	//	}

	private ModelAndView createEditModelAndViewRegister(final ActorRegisterForm actorRegisterForm, final String message) {

		final ModelAndView resul = new ModelAndView("actor/registerManager");

		resul.addObject("actorRegisterForm", actorRegisterForm);
		resul.addObject("message", message);
		return resul;
	}

	private ModelAndView createEditModelAndViewRegisterAuditor(final ActorRegisterForm actorRegisterForm, final String message) {

		final ModelAndView resul = new ModelAndView("actor/registerAuditor");

		resul.addObject("actorRegisterForm", actorRegisterForm);
		resul.addObject("message", message);
		return resul;
	}

	private ModelAndView createEditModelAndViewRegisterUser(final ActorRegisterForm actorRegisterForm, final String message) {

		final ModelAndView resul = new ModelAndView("actor/registerUser");

		resul.addObject("actorRegisterForm", actorRegisterForm);
		resul.addObject("message", message);
		return resul;
	}

	// Ancillary Methods
	// ===============================================================================

	protected ModelAndView createEditModelAndView(final ActorForm actorForm) {
		ModelAndView result;

		result = this.createEditModelAndView(actorForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm actorForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/editProfile");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);

		return result;

	}

}
