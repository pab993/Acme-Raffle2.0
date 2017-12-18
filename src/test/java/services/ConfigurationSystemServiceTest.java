
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import utilities.AbstractTest;
import domain.ConfigurationSystem;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class ConfigurationSystemServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private ConfigurationSystemService	configurationSystemService;


	// Tests
	// ====================================================

	/*
	 * To check the validity of a new configuration system, the system must check the banner (url) and the lesson's tax.
	 * 
	 * En este test, se comprueban los valores introducidos en el panel de configuración del sistema.
	 * Para ello introducimos valores correctos e incorrectos para observa el comportamiento de la aplicación.
	 */

	public void configurationSystemEditTest(final String username, final String banner, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			Assert.isTrue(ResourceUtils.isUrl(banner));

			final ConfigurationSystem cs = this.configurationSystemService.getCS();
			this.configurationSystemService.save(cs);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers
	// ===================================================

	@Test
	public void driverConfigurationSystemEditTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> false
			{
				null, "http://www.picture.com", IllegalArgumentException.class
			},
			// Todo vacio --> false
			{
				null, null, IllegalArgumentException.class
			},
			// Un administrador intenta cambiar la configuración -> true
			{
				"admin", "http://www.picture.com", null
			},
			// Introducimos algo que no es una url -> false
			{
				"admin", "test", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.configurationSystemEditTest((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

}
