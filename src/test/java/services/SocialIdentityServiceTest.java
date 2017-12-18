
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.SocialIdentity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SocialIdentityServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private SocialIdentityService	socialIdentityService;


	// Tests
	// ====================================================

	/*
	 * Create socialIdentitys over a comentable object.
	 * 
	 * En este caso de uso se crean y se guardan los socialIdentitys que queramos realizar sobre un objeto comentable siempre y cuando
	 * nos encontramos autentificados, por lo tanto es accesible para cualquier 'actor'.
	 * Para provocar el error, se intenta guardar medianteun usuario no autentificado e incluyendo un objeto comentable no válido.
	 */

	protected void deleteTest(final String username, final int socialIdentityId, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);

			final SocialIdentity socialIdentity = this.socialIdentityService.findOne(socialIdentityId);
			this.socialIdentityService.delete(socialIdentity);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

	protected void createSaveTest(final String username, final String nick, final String profile, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final SocialIdentity socialIdentity = this.socialIdentityService.create();
			socialIdentity.setNick(nick);
			socialIdentity.setProfile(profile);

			this.socialIdentityService.save(socialIdentity);
			Assert.isTrue(!socialIdentity.getNick().isEmpty());
			Assert.isTrue(!socialIdentity.getProfile().isEmpty());

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Drivers
	// ====================================================

	@Test
	public void driverDeleteSocialIdentity() {

		final Object testingData1[][] = {
			// Borrar un socialIdentity si estoy autentificado y es mio -> true
			{
				"admin", 27, null
			},
			// Borrar un socialIdentity si no estoy autentificado -> false
			{
				null, 27, IllegalArgumentException.class
			},
			// Borrar un socialIdentity si estoy autentificado y no es mio -> true
			{
				"user1", 27, IllegalArgumentException.class
			},
			// Borrar un socialIdentity si estoy autentificado como user y no exisste-> true
			{
				"user1", 290, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.deleteTest((String) testingData1[i][0], (int) testingData1[i][1], (Class<?>) testingData1[i][2]);
	}

	@Test
	public void driverCreateSaveSocialIdentity() {

		final Object testingData[][] = {
			// Creacion con un user y datos validos -> true
			{
				"user1", "nickTest", "http://www.urlprofiletest.com", null
			},
			// Si introducimos nick erroneo-> false
			{
				"user1", "", "http://www.urlprofiletest.com", IllegalArgumentException.class
			},
			// Si introducimos profile erroneo-> false
			{
				"user1", "nickTest", "", IllegalArgumentException.class
			},
			// Si no estamos logueados -> false
			{
				null, "nickTest", "http://www.urlprofiletest.com", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.createSaveTest((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

}
