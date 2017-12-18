
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import repositories.ConfigurationSystemRepository;
import domain.Administrator;
import domain.ConfigurationSystem;

@Transactional
@Service
public class ConfigurationSystemService {

	@Autowired
	private ConfigurationSystemRepository	configurationSystemRepository;

	@Autowired
	private ActorService					actorService;


	public ConfigurationSystemService() {
		super();
	}

	public ConfigurationSystem findOne(final int configurationSystemId) {
		Assert.notNull(configurationSystemId);

		ConfigurationSystem result;

		result = this.configurationSystemRepository.findOne(configurationSystemId);
		Assert.notNull(result);

		return result;
	}

	public Collection<ConfigurationSystem> findAll() {

		Collection<ConfigurationSystem> configurationSystems;

		configurationSystems = this.configurationSystemRepository.findAll();
		Assert.notNull(configurationSystems);

		return configurationSystems;
	}

	public ConfigurationSystem create() {
		final ConfigurationSystem result = new ConfigurationSystem();
		return result;

	}

	public ConfigurationSystem save(final ConfigurationSystem configurationSystem) {
		Assert.notNull(configurationSystem);
		final Administrator principal = (Administrator) this.actorService.findByPrincipal();
		Assert.notNull(principal);

		final ConfigurationSystem result = this.configurationSystemRepository.save(configurationSystem);
		return result;

	}
	public ConfigurationSystem getCS() {
		Collection<ConfigurationSystem> configurationSystems;

		configurationSystems = this.findAll();
		return configurationSystems.iterator().next();
	}

	public ConfigurationSystem findConfigurationSystem() {
		ConfigurationSystem res = new ConfigurationSystem();
		final Collection<ConfigurationSystem> res1 = this.configurationSystemRepository.findAll();
		for (final ConfigurationSystem f : res1) {
			res = f;
			break;
		}
		return res;
	}

	public ConfigurationSystem reconstructConfigurationSystem(final ConfigurationSystem configurationSystemForm, final BindingResult binding) {

		ConfigurationSystem result;

		result = configurationSystemForm;

		this.checkPayMoment(result.getPayMoment(), binding);

		return result;

	}

	private boolean checkPayMoment(final Date payMoment, final BindingResult binding) {
		FieldError error;
		String[] codigos;
		boolean result;

		if (payMoment == null)
			result = false;
		else
			result = true;

		if (result)
			if (payMoment.after(new Date(System.currentTimeMillis())))
				result = true;
			else {
				codigos = new String[1];
				codigos[0] = "configurationSystem.payMoment.error";
				error = new FieldError("configurationSystem", "payMoment", payMoment, false, codigos, null, "");
				binding.addError(error);
			}

		return result;
	}
}
