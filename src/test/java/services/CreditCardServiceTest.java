
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;

@Transactional
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class CreditCardServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private CreditCardService	creditCardService;


	// Tests
	// ====================================================

	/*
	 * To check the validity of a credit card, the system must check its brand (which must be either VISA, MASTERCARD,
	 * DISCOVER, DINNERS, or AMEX), its number (which must pass Luhn's check), and its expiration date
	 * (which is expected To be at least one day ahead).
	 * 
	 * En este test, se comprueba si el n�mero introducido de la tarjeta pasa el test de verificaci�n de Luhn.
	 * Para ello introducimos un n�mero correcto que no devolver� ninguna excepci�n y a continuaci�n introduciremos un n�mero incorrecto
	 * que no pasar� el test y por tanto saltar� un error esperado.
	 */

	public void verificacionLuhnTest(final String username, final CreditCard creditCard, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.authenticate(username);

			final int[] digits = this.creditCardService.stringToArray(creditCard.getNumber());
			Assert.isTrue(this.creditCardService.verificacionLuhn(digits));

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);

	}

	/*
	 * En este test, se comprueba si el brand name de la tarjeta es v�lido o no.
	 * Para ello introducimos un brand name correcto y a continuaci�n introduciremos brand name incorrecto
	 * que no pasar� el test y por tanto saltar� un error esperado.
	 */

	public void checkBrandNameTest(final String username, final CreditCard creditCard, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.authenticate(username);

			final boolean res = this.creditCardService.checkBrandName(creditCard);
			Assert.isTrue(res);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);

	}

	/*
	 * En este test, se comprueba si la fecha de la tarjeta es v�lida o no.
	 * Para ello introducimos una fecha que caduca en el futuro y a continuaci�n introduciremos otra fecha
	 * que no pasar� el test por estar caducada y por tanto saltar� un error esperado.
	 */

	public void checkExpirationDateTest(final String username, final CreditCard creditCard, final Class<?> expected) {

		Class<?> caught = null;

		try {

			this.authenticate(username);

			final boolean res = this.creditCardService.checkExpirationDate(creditCard);
			Assert.isTrue(res);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);

	}

	//Drivers
	// ===================================================

	@Test
	public void driverVerificacionLuhnTest() {
		final CreditCard creditCard = this.creditCardService.findOne(109);
		final CreditCard creditCard2 = this.creditCardService.findOne(110);
		creditCard2.setNumber("123456789");

		final Object testingData[][] = {
			// Una creditCard con un n�mero v�lido -> true
			{
				"manager1", creditCard, null
			},
			// Una creditCart con un n�mero no v�lido -> false
			{
				"manager2", creditCard2, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.verificacionLuhnTest((String) testingData[i][0], (CreditCard) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverCheckBrandNameTest() {
		final CreditCard creditCard = this.creditCardService.findOne(109);
		final CreditCard creditCard2 = this.creditCardService.findOne(110);
		creditCard2.setBrandName("No v�lido");

		final Object testingData[][] = {
			// Un brandName v�lido -> true
			{
				"manager1", creditCard, null
			},
			// Una creditCart con un n�mero no v�lido -> false
			{
				"manager2", creditCard2, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.checkBrandNameTest((String) testingData[i][0], (CreditCard) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverCheckExpirationDateTest() {
		final CreditCard creditCard = this.creditCardService.findOne(109);
		final CreditCard creditCard2 = this.creditCardService.findOne(110);
		creditCard2.setExpirationYear(2010);

		final Object testingData[][] = {
			// Una creditCard con una fecha v�lida -> true
			{
				"manager1", creditCard, null
			},
			// Una creditCard con una fecha inv�lida -> false
			{
				"manager2", creditCard2, IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.checkExpirationDateTest((String) testingData[i][0], (CreditCard) testingData[i][1], (Class<?>) testingData[i][2]);
	}

}
