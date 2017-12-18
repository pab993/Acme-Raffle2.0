
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import forms.ActorForm;

@Service
@Transactional
public class ActorService {

	//Managed Repository =============================================================================

	@Autowired
	private ActorRepository	actorRepository;


	//Services
	// ===============================================================================================

	//SCRUDs Methods
	//===============================================================================================

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Assert.notNull(actor.getUserAccount());

		Actor result;

		result = this.actorRepository.save(actor);

		return result;
	}

	//Other Business Methods =========================================================================

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		try {
			userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount);

			result = this.findByUserAccount(userAccount);
			Assert.notNull(result);
		} catch (Throwable oops) {
			result = null;
		}

		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Actor result;

		result = this.actorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Actor findOne(final int actorId) {
		Actor result;

		result = this.actorRepository.findOne(actorId);

		return result;
	}

	public Collection<Actor> findAll() {

		Collection<Actor> result = new HashSet<Actor>();

		result = this.actorRepository.findAll();

		return result;
	}

	public ActorForm reconstructToForm(final Actor actor) {
		final ActorForm actorForm = new ActorForm();

		actorForm.setName(actor.getName());
		actorForm.setSurname(actor.getSurname());
		actorForm.setPhone(actor.getPhone());
		actorForm.setEmail(actor.getEmail());
		actorForm.setPostalAddress(actor.getPostalAddress());
		actorForm.setCity(actor.getCity());
		actorForm.setCountry(actor.getCountry());

		return actorForm;

	}

	public Actor reconstruct1(final ActorForm actorForm, final BindingResult binding) {
		Actor result;

		result = this.findByPrincipal();

		//		comprobarPhone(actorForm.getPhone(), binding);
		this.comprobarPostalAddress(actorForm.getPostalAddress(), binding);

		return result;
	}

	//Hay dos reconstructs porque por alguna razón aquí se guardan los cambios en la base de datos en este metodo. Así que de esta manera hago un "rollback".
	public Actor reconstruct2(final ActorForm actorForm, final BindingResult binding) {
		Actor result;

		result = this.findByPrincipal();
		result.setName(actorForm.getName());
		result.setSurname(actorForm.getSurname());
		result.setPhone(actorForm.getPhone());
		result.setEmail(actorForm.getEmail());
		result.setPostalAddress(actorForm.getPostalAddress());
		result.setCity(actorForm.getCity());
		result.setCountry(actorForm.getCountry());

		//		comprobarPhone(actorForm.getPhone(), binding);
		this.comprobarPostalAddress(actorForm.getPostalAddress(), binding);

		return result;
	}

	private boolean comprobarPostalAddress(final String postalAddress, final BindingResult binding) {
		FieldError error;
		String[] codigos;
		boolean result;

		if (postalAddress == null || postalAddress.isEmpty())
			result = true;
		else
			result = false;

		if (!result)
			if (postalAddress.matches("^[0-9]{5}$"))
				result = true;
			else {
				codigos = new String[1];
				codigos[0] = "actor.postalAddress.error";
				error = new FieldError("actorForm", "postalAddress", postalAddress, false, codigos, null, "");
				binding.addError(error);
			}

		return result;
	}

	private boolean comprobarPhone(final String phone, final BindingResult binding) {
		FieldError error;
		String[] codigos;
		boolean result;

		if (phone == null || phone.isEmpty())
			result = true;
		else
			result = false;

		if (!result)
			if (phone.matches("^[+][a-zA-Z]{2}([(][0-9]{1,3}[)])?[0-9]{4,25}$"))
				result = true;
			else {
				codigos = new String[1];
				codigos[0] = "actor.phone.error";
				error = new FieldError("actorForm", "phone", phone, false, codigos, null, "");
				binding.addError(error);
			}

		return result;
	}

	public boolean isAuthenticated() {
		try {
			Assert.notNull(LoginService.getPrincipal());
		} catch (final Exception e) {
			return false;
		}

		return true;
	}

	public void checkActorIsAuthenticated() {
		Assert.notNull(LoginService.getPrincipal());
	}

	public boolean checkRole(final String role) {
		boolean result;
		Collection<Authority> authorities;

		result = false;
		authorities = LoginService.getPrincipal().getAuthorities();
		for (final Authority a : authorities)
			result = result || a.getAuthority().equals(role);

		return result;
	}

	public boolean isAdmin() {
		boolean result;

		result = this.checkRole(Authority.ADMIN);

		return result;
	}

	public boolean isManager() {
		boolean result;

		result = this.checkRole(Authority.MANAGER);

		return result;
	}

	//Dashboard
	// ===============================================================================================

	public Collection<Object[]> avgStarsPerActorGroupByCountry() {

		Collection<Object[]> result;

		result = this.actorRepository.avgStarsPerActorGroupByCountry();

		return result;
	}

	public Collection<Object[]> avgStarsPerActorGroupByCity() {

		Collection<Object[]> result;

		result = this.actorRepository.avgStarsPerActorGroupByCity();

		return result;
	}

}
