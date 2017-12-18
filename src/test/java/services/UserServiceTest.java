
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
import domain.User;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private UserService				userService;

	@Autowired
	private ActorRegisterService	actorRegisterService;


	// Tests
	// ====================================================

	/*
	 * Browse the catalogue of academies and navigate to the courses that they offer.
	 * 
	 * En este caso de uso se comprobamos que cualquiera puede listar las academias que existen en nuestra aplicación.
	 */

	public void listOfUsersTest(final String username, final Class<?> expected) {
		Class<?> caught = null;

		try {
			this.authenticate(username);

			this.userService.findAll();

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * To check the validity of a new user in our system, the system must check the username,
	 * the passwords, the name, the surname, the phone, the email and the postal address.
	 * 
	 * En este test, se comprueba el registro de una nueva academia.
	 * Para ello introducimos valores correctos e incorrectos para observar el comportamiento de la aplicación.
	 */

	/*
	 * Register a new user.
	 * 
	 * En este caso de uso simularemos el registro de un candidato.
	 */

	public void userRegisterTest(final String username, final String password, final String passwordRepeat, final String name, final String surname, final String phone, final String email, final String postalAddress, final Class<?> expected) {
		Class<?> caught = null;

		try {

			final User result = this.actorRegisterService.createUser();

			Assert.notNull(username);
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

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	//Drivers
	// ===================================================

	@Test
	public void driverListUserTest() {

		final Object testingData[][] = {
			// Alguien sin registrar/logueado -> true
			{
				null, null
			},
			// Un dancer -> true
			{
				"user1", null
			},
			// Una user -> true
			{
				"user1", null
			},
			// Un administrador -> true
			{
				"admin", null
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.listOfUsersTest((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	@Test
	public void driverUserRegisterTest() {

		final Object testingData[][] = {
			// Creacion correcta-> true
			{
				"userTest", "userTest", "userTest", "userTestName", "userTestSurname", "+ES1234456", "userTest@userTest.com", "12345", null
			},
			// Todo vacio --> false
			{
				null, null, null, null, null, null, null, null, IllegalArgumentException.class
			},
			// Password null -> false
			{
				"userTest", null, "userTest", "userTestName", "userTestSurname", "ES1234456", "userTest@userTest.com", "12345", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.userRegisterTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (Class<?>) testingData[i][8]);
	}

	// Colección de test para probar el requisito 10.1, ban a user.
	// Los tests comprueban que:
	// Un actor autenticado como Admin puede banear a un usuario correctamente.
	// Un actor autenticado como Admin no puede banear a un usuario si ya está baneado.
	// Un actor autenticado como Manager no puede banear a un usuario.
	// Un actor no autenticado no puede banear a un usuario.
	// Un actor autenticado como Auditor no puede banear a un usuario.

	@Test
	public void driverBanUser() {
		final Object testingData[][] = {
			{
				"admin", 23, null
			}, {
				"admin", 25, IllegalArgumentException.class
			}, {
				"manager", 24, IllegalArgumentException.class
			}, {
				null, 24, IllegalArgumentException.class
			}, {
				"auditor", 25, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateBanUser((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateBanUser(final String username, final int userId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			final User user = this.userService.findOne(userId);
			this.userService.ban(user);
			Assert.isTrue(user.getUserAccount().isEnabled() == false);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	// Colección de test para probar el requisito 10.1, unban a user.
	// Los tests comprueban que:
	// Un actor autenticado como Admin puede desbanear a un usuario correctamente.
	// Un actor autenticado como Admin no puede desbanear a un usuario si no está baneado.
	// Un actor autenticado como Manager no puede desbanear a un usuario.
	// Un actor no autenticado no puede desbanear a un usuario.
	// Un actor autenticado como Auditor no puede desbanear a un usuario.
	@Test
	public void driverUnbanUser() {
		final Object testingData[][] = {
			{
				"admin", 25, null
			}, {
				"admin", 26, IllegalArgumentException.class
			}, {
				"manager", 24, IllegalArgumentException.class
			}, {
				null, 24, IllegalArgumentException.class
			}, {
				"auditor", 25, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateUnbanUser((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateUnbanUser(final String username, final int userId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			final User user = this.userService.findOne(userId);
			this.userService.unban(user);
			Assert.isTrue(user.getUserAccount().isEnabled() == true);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}
}
