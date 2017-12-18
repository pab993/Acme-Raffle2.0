
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.TabooWord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class TabooWordServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private TabooWordService	tabooWordService;


	// Tests
	// ====================================================

	/*
	 * Manage his or her tabooWords, which includes listing, creating, editing, and deleting them.
	 * 
	 * En este caso de uso se listan, crean, editan y eliminan los tabooWords
	 * Para provocar el error, se intenta guardar mediante un usuario no autentificado e incluyendo valores erróneos para observar el comportamiento de la aplicación.
	 */

	public void tabooWordCreateAndSaveTest(final String username, final String name, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			final TabooWord result = this.tabooWordService.create();

			result.setName(name);

			this.tabooWordService.save(result);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	public void tabooWordEditTest(final String username, final int tabooWordId, final String name, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			final TabooWord result = this.tabooWordService.findOne(tabooWordId);

			result.setName(name);

			this.tabooWordService.save(result);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	public void tabooWordDeleteTest(final String username, final int tabooWordId, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			final TabooWord result = this.tabooWordService.findOne(tabooWordId);

			this.tabooWordService.delete(result);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	// Drivers
	// ====================================================

	@Test
	public void driverTabooWordCreateAndSaveTest() {

		final Object testingData[][] = {
			// Crear una factura estando logueado como manager -> true
			{
				"admin", "tabooWordTest", null
			},
			// Crear una factura sin estar logueado --> false
			{
				null, "tabooWordTest", IllegalArgumentException.class
			},
			// Crear una factura logueado como manager -> false
			{
				"manager1", "tabooWordTest", IllegalArgumentException.class
			},
			// Crear una factura logueado como admin -> false
			{
				"user1", "tabooWordTest", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.tabooWordCreateAndSaveTest((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	@Test
	public void driverTabooWordEditTest() {

		final Object testingData[][] = {
			// Crear una factura estando logueado como manager -> true
			{
				"admin", 100, "tabooWordTest", null
			},
			// Crear una factura sin estar logueado --> false
			{
				null, 100, "tabooWordTest", IllegalArgumentException.class
			},
			// Crear una factura logueado como manager -> false
			{
				"manager1", 100, "tabooWordTest", IllegalArgumentException.class
			},
			// Crear una factura logueado como admin -> false
			{
				"user1", 100, "tabooWordTest", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.tabooWordEditTest((String) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);

	}

	@Test
	public void driverTabooWordDeleteTest() {

		final Object testingData[][] = {
			// Crear una factura estando logueado como manager -> true
			{
				"admin", 100, null
			},
			// Crear una factura sin estar logueado --> false
			{
				null, 99, IllegalArgumentException.class
			},
			// Crear una factura logueado como manager -> false
			{
				"manager1", 99, IllegalArgumentException.class
			},
			// Crear una factura logueado como admin -> false
			{
				"user1", 99, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.tabooWordDeleteTest((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}

}
