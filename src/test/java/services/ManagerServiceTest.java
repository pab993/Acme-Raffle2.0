
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Manager;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class ManagerServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private ActorRegisterService	actorRegisterService;


	// Tests
	// ====================================================

	/*
	 * Browse the list of managers and navigate to the subjects that they teach.
	 * 
	 * En este caso de uso se comprobamos que cualquiera puede listar los profesores que existen en nuestra aplicación y su correspondientes asignaturas.
	 */

	/*
	 * To check the validity of a new manager in our system, the system must check the username,
	 * the passwords, the name, the surname, the phone, the email and the postal address.
	 * 
	 * En este test, se comprueba el registro de una nueva academia.
	 * Para ello introducimos valores correctos e incorrectos para observar el comportamiento de la aplicación.
	 */

	/*
	 * Register a new manager.
	 * 
	 * En este caso de uso simularemos el registro de un candidato.
	 */

	public void managerRegisterTest(final String username, final String password, final String passwordRepeat, final String name, final String surname, final String phone, final String email, final String postalAddress, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			final Manager result = this.actorRegisterService.createManager();

			Assert.notNull("ManagerTest");
			Assert.notNull(password);
			Assert.notNull(passwordRepeat);
			Assert.isTrue(password.equals(passwordRepeat));
			Assert.notNull(phone);
			Assert.isTrue(phone.matches("^[+][a-zA-Z]{2}([(][0-9]{1,3}[)])?[0-9]{4,25}$"));
			Assert.notNull(email);
			Assert.notNull(name);
			Assert.notNull(surname);

			result.getUserAccount().setUsername(username);
			result.setName(name);
			result.setSurname(surname);
			result.setPhone(phone);
			result.setEmail(email);
			result.setPostalAddress(postalAddress);
			result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(password, null));

			this.actorRegisterService.save(result);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers
	// ===================================================

	@Test
	public void driverManagerRegisterTest() {

		final Object testingData[][] = {
			// Creacion correcta-> true
			{
				"admin", "managerTest", "managerTest", "managerTestName", "managerTestSurname", "+ES1234456", "managerTest@managerTest.com", "12345", null
			},
			// Todo vacio --> false
			{
				null, null, null, null, null, null, null, null, IllegalArgumentException.class
			},
			// Creacion correcta pero sin estar logueado como admin -> false
			{
				null, "managerTest", "managerTest", "managerTestName", "managerTestSurname", "ES1234456", "managerTest@managerTest.com", "12345", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.managerRegisterTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}
}
