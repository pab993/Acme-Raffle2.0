
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Prize;
import domain.Property;
import domain.Raffle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PrizeServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private PrizeService	prizeService;

	@Autowired
	private RaffleService	raffleService;

	@Autowired
	private PropertyService	propertyService;


	// Tests
	// ====================================================

	/*
	 * Manage the prizes that are associated with any of his or her raffles, which includes listing, creating, editing, deleting them, or re-generating its codes. Editing, deleting, or re-generating the codes of a prize is allowed as long as the
	 * corresponding raffle has not started.
	 * 
	 * En este caso de uso se listan, crean, editan y eliminan los prizes que pertenecen a una 'manager' logueado, por lo tanto es accesible tan sólo para los 'managers'.
	 * Para provocar el error, se intenta guardar mediante un usuario no autentificado e incluyendo valores erróneos para observar el comportamiento de la aplicación.
	 */

	@SuppressWarnings("deprecation")
	public void PrizeCreateAndSaveTest(final String username, final String name, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			final Raffle raffle = this.raffleService.findOne(35);
			final Prize result = this.prizeService.create(raffle);

			result.setName(name);
			result.setDescription("description");
			result.setCodesGenerated(8);
			result.setWinnerCodes(5);

			this.prizeService.save(result);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	public void editPrizeTest(final String username, final String name, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			Prize result;

			result = this.prizeService.findOne(48);

			result.setName(name);

			this.prizeService.save(result);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	public void deletePrizeTest(final String username, final int prizeId, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			Prize result;

			result = this.prizeService.findOne(prizeId);

			this.prizeService.delete(result);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	// Drivers
	// ====================================================

	@Test
	public void driverPrizeCreateAndSaveTest() {

		final Object testingData[][] = {
			// Crear una premio estando logueado como manager que ya ha comenzado -> false
			{
				"manager1", "name1", IllegalArgumentException.class
			},
			// Crear una premio sin estar logueado que ya ha comenzado -> false
			{
				null, "name2", IllegalArgumentException.class
			},
			// Crear una premio logueado como auditor que ya ha comenzado -> false
			{
				"auditor1", "name3", IllegalArgumentException.class
			},
			// Crear una premio logueado como admin que ya ha comenzado -> false
			{
				"admin", "name4", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.PrizeCreateAndSaveTest((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	@Test
	public void driverEditPrizeTest() {

		final Object testingData[][] = {
			// Editar una premio estando logueado como manager que ya ha comenzado -> false 
			{
				"manager1", "name1", IllegalArgumentException.class
			},
			// Editar una premio sin estar logueado que ya ha comenzado -> false
			{
				null, "name2", IllegalArgumentException.class
			},
			// Editar una premio logueado como auditor que ya ha comenzado -> false
			{
				"auditor1", "name3", IllegalArgumentException.class
			},
			// Editar una premio logueado como admin que ya ha comenzado -> false
			{
				"admin", "name4", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.editPrizeTest((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverDeletePrizeTest() {

		final Object testingData[][] = {
			// Borrar una premio estando logueado como manager que ya ha comenzado -> false
			{
				"manager1", 48, IllegalArgumentException.class
			},
			// Borrar una premio sin estar logueado que ya ha comenzado -> false
			{
				null, 48, IllegalArgumentException.class
			},
			// Borrar una premio que ya ha comenzado -> false
			{
				"admin", 48, IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.deletePrizeTest((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Colección de test para probar el requisito 10.2 manage the taxonomy of properties.
	// Los tests comprueban que:
	// Un actor autenticado como Admin no puede añadir una propiedad a un prize correctamente.
	// Un actor autenticado como Manager no puede añadir una property a un raffle ya empezado
	// Un actor no autenticado no puede añadir una propiedad a un prize.
	// Un actor autenticado como Auditor no puede añadir una propiedad a un prize.

	@Test
	public void driverAddProperty() {
		final Object testingData[][] = {
			{
				"admin", 54, 49, IllegalArgumentException.class
			}, {
				"manager1", 52, 48, IllegalArgumentException.class
			}, {
				null, 53, 49, IllegalArgumentException.class
			}, {
				"auditor1", 53, 49, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateAddProperty((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void templateAddProperty(final String username, final int propertyId, final int prizeId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			final Property property = this.propertyService.findOne(propertyId);
			final Prize prize = this.prizeService.findOne(prizeId);
			this.prizeService.addProperty(prize, property);
			Assert.isTrue(prize.getProperties().contains(property));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	// Colección de test para probar el requisito 10.2 manage the taxonomy of properties.
	// Los tests comprueban que:
	// Un actor autenticado como Manager que no puede eliminar una property porque el raffle ya ha empezado
	// Un actor autenticado como Admin no puede eliminar una propiedad a un prize.
	// Un actor no autenticado no puede eliminar una propiedad a un prize.
	// Un actor autenticado como Auditor no puede eliminar una propiedad a un prize.
	@Test
	public void driverRemoveProperty() {
		final Object testingData[][] = {
			{
				"manager1", 52, 48, IllegalArgumentException.class
			}, {
				"admin", 54, 50, IllegalArgumentException.class
			}, {
				null, 52, 48, IllegalArgumentException.class
			}, {
				"auditor1", 52, 48, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateRemoveProperty((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void templateRemoveProperty(final String username, final int propertyId, final int prizeId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			final Property property = this.propertyService.findOne(propertyId);
			final Prize prize = this.prizeService.findOne(prizeId);
			Assert.notNull(property);
			Assert.notNull(prize);
			this.prizeService.deleteProperty(prize, property);
			Assert.isTrue(!prize.getProperties().contains(property));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
