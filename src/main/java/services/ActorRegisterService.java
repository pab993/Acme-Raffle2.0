
package services;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import repositories.ActorRegisterRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;
import domain.Auditor;
import domain.Manager;
import domain.User;
import forms.ActorRegisterForm;

@Service
@Transactional
public class ActorRegisterService {

	//Managed Repository =============================================================================

	@Autowired
	private ActorRegisterRepository	actorRegisterRepository;

	@Autowired
	private UserAccountRepository	userAccountRepository;


	//Simple CRUD methods ============================================================================

	public Manager createManager() {
		Manager result;
		UserAccount userAccount;
		Authority authority;

		authority = new Authority();
		userAccount = new UserAccount();

		authority.setAuthority("MANAGER");
		userAccount.addAuthority(authority);
		userAccount.setEnabled(true);

		result = new Manager();

		result.setUserAccount(userAccount);

		return result;
	}

	public Auditor createAuditor() {
		Auditor result;
		UserAccount userAccount;
		Authority authority;

		authority = new Authority();
		userAccount = new UserAccount();

		authority.setAuthority("AUDITOR");
		userAccount.addAuthority(authority);
		userAccount.setEnabled(true);

		result = new Auditor();

		result.setUserAccount(userAccount);

		return result;
	}

	public User createUser() {
		User result;
		UserAccount userAccount;
		Authority authority;

		authority = new Authority();
		userAccount = new UserAccount();

		authority.setAuthority("USER");
		userAccount.addAuthority(authority);
		userAccount.setEnabled(true);

		result = new User();

		result.setUserAccount(userAccount);

		return result;
	}

	public Actor save(final Actor actorRegister) {
		Assert.notNull(actorRegister);
		Assert.notNull(actorRegister.getUserAccount());
		Actor result;

		result = this.actorRegisterRepository.save(actorRegister);

		return result;
	}

	//Other Business Methods =========================================================================

	public Manager reconstructManager(final ActorRegisterForm actorRegisterForm, final BindingResult binding) {

		Manager result;

		result = this.createManager();
		result.getUserAccount().setUsername(actorRegisterForm.getUsername());
		result.setName(actorRegisterForm.getName());
		result.setSurname(actorRegisterForm.getSurname());
		result.setPhone(actorRegisterForm.getPhone());
		result.setEmail(actorRegisterForm.getEmail());
		result.setCity(actorRegisterForm.getCity());
		result.setCountry(actorRegisterForm.getCountry());
		result.setPostalAddress(actorRegisterForm.getPostalAddress());
		result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(actorRegisterForm.getPassword(), null));

		//this.comprobarPhone(actorRegisterForm.getPhone(), binding);
		this.comprobarContrasena(actorRegisterForm.getPassword(), actorRegisterForm.getRepeatPassword(), binding);
		this.comprobarPostalAddress(actorRegisterForm.getPostalAddress(), binding);
		this.comprobarUsuarioUnico(actorRegisterForm.getUsername(), binding);

		return result;
	}

	public Auditor reconstructAuditor(final ActorRegisterForm actorRegisterForm, final BindingResult binding) {

		Auditor result;

		result = this.createAuditor();
		result.getUserAccount().setUsername(actorRegisterForm.getUsername());
		result.setName(actorRegisterForm.getName());
		result.setSurname(actorRegisterForm.getSurname());
		result.setPhone(actorRegisterForm.getPhone());
		result.setEmail(actorRegisterForm.getEmail());
		result.setCity(actorRegisterForm.getCity());
		result.setCountry(actorRegisterForm.getCountry());
		result.setPostalAddress(actorRegisterForm.getPostalAddress());
		result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(actorRegisterForm.getPassword(), null));

		//this.comprobarPhone(actorRegisterForm.getPhone(), binding);
		this.comprobarContrasena(actorRegisterForm.getPassword(), actorRegisterForm.getRepeatPassword(), binding);
		this.comprobarPostalAddress(actorRegisterForm.getPostalAddress(), binding);
		this.comprobarUsuarioUnico(actorRegisterForm.getUsername(), binding);

		return result;
	}

	public User reconstructUser(final ActorRegisterForm actorRegisterForm, final BindingResult binding) {

		User result;

		result = this.createUser();
		result.getUserAccount().setUsername(actorRegisterForm.getUsername());
		result.setName(actorRegisterForm.getName());
		result.setSurname(actorRegisterForm.getSurname());
		result.setPhone(actorRegisterForm.getPhone());
		result.setEmail(actorRegisterForm.getEmail());
		result.setPostalAddress(actorRegisterForm.getPostalAddress());
		result.setCity(actorRegisterForm.getCity());
		result.setCountry(actorRegisterForm.getCountry());
		result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(actorRegisterForm.getPassword(), null));

		//this.comprobarPhone(actorRegisterForm.getPhone(), binding);
		this.comprobarContrasena(actorRegisterForm.getPassword(), actorRegisterForm.getRepeatPassword(), binding);
		this.comprobarPostalAddress(actorRegisterForm.getPostalAddress(), binding);
		this.comprobarUsuarioUnico(actorRegisterForm.getUsername(), binding);

		return result;
	}

	private boolean comprobarUsuarioUnico(final String username, final BindingResult binding) {
		FieldError error;
		String[] codigos;
		boolean result = false;

		final UserAccount userAccount = this.userAccountRepository.findByUsername(username);

		if (userAccount == null)
			result = true;

		if (!result) {
			codigos = new String[1];
			codigos[0] = "actorRegister.username.unique";
			//			error = new FieldError("cadidateForm", "password", "actorRegister.paswword.mismatch");
			error = new FieldError("actorRegisterForm", "username", username, false, codigos, null, "");
			binding.addError(error);
		}
		return result;
	}

	private boolean comprobarContrasena(final String password, final String passwordRepeat, final BindingResult binding) {
		FieldError error;
		String[] codigos;
		boolean result;

		if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(passwordRepeat))
			result = password.equals(passwordRepeat);
		else
			result = false;

		if (!result) {
			codigos = new String[1];
			codigos[0] = "actorRegister.password.mismatch";
			//			error = new FieldError("cadidateForm", "password", "actorRegister.paswword.mismatch");
			error = new FieldError("actorRegisterForm", "password", password, false, codigos, null, "");
			binding.addError(error);
		}

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
				codigos[0] = "actorRegister.postalAddress.error";
				error = new FieldError("actorRegisterForm", "postalAddress", postalAddress, false, codigos, null, "");
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
				codigos[0] = "actorRegister.phone.error";
				error = new FieldError("actorRegisterForm", "phone", phone, false, codigos, null, "");
				binding.addError(error);
			}

		return result;
	}

}
