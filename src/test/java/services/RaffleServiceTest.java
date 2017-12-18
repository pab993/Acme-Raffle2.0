
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Manager;
import domain.Raffle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class RaffleServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private RaffleService	raffleService;

	@Autowired
	private ManagerService	managerService;


	// Tests
	// ====================================================

	/*
	 * Manage his or her raffles, which includes listing, creating, editing, and deleting them. Note that a raffle can be edited and deleted as long as its publication time has not passed; once the publication time passes, no raffle can be edited or
	 * deleted.
	 * 
	 * En este caso de uso se listan, crean, editan y eliminan los raffles que pertenecen a una 'manager' logueado, por lo tanto es accesible tan sólo para los 'managers'.
	 * Para provocar el error, se intenta guardar mediante un usuario no autentificado e incluyendo valores erróneos para observar el comportamiento de la aplicación.
	 */

	@SuppressWarnings("deprecation")
	public void RaffleCreateAndSaveTest(final String username, final String title, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			final Manager manager = this.managerService.findOne(15);
			final Raffle result = this.raffleService.create(manager);

			final Date startDate = new Date();
			startDate.setYear(2018);
			final Date finishDate = new Date();
			finishDate.setYear(2019);

			result.setLogo("https://vignette1.wikia.nocookie.net/conworld/images/c/c1/Pepa-Cola.jpg");
			result.setTitle(title);
			result.setDescription("description");
			result.setPublicationTime(startDate);
			result.setDeadline(finishDate);

			this.raffleService.save(result);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	public void editRaffleTest(final String username, final String name, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			Raffle result;

			result = this.raffleService.findOne(35);

			result.setTitle(name);

			this.raffleService.save(result);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	public void deleteRaffleTest(final String username, final int raffleId, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			Raffle result;

			result = this.raffleService.findOne(raffleId);

			this.raffleService.delete(result);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	// Drivers
	// ====================================================

	@Test
	public void driverRaffleCreateAndSaveTest() {

		final Object testingData[][] = {
			// Crear una rifa estando logueado como manager -> true
			{
				"manager1", "title1", null
			},
			// Crear una rifa sin estar logueado --> false
			{
				null, "title2", IllegalArgumentException.class
			},
			// Crear una rifa logueado como auditor -> false
			{
				"auditor1", "title3", IllegalArgumentException.class
			},
			// Crear una rifa logueado como admin -> false
			{
				"admin", "title4", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.RaffleCreateAndSaveTest((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	@Test
	public void driverEditLessonTest() {

		final Object testingData[][] = {
			// Editar una rifa estando logueado como manager que ya ha comenzado -> false 
			{
				"manager1", "title1", IllegalArgumentException.class
			},
			// Editar una rifa sin estar logueado que ya ha comenzado -> false
			{
				null, "title2", IllegalArgumentException.class
			},
			// Editar una rifa logueado como auditor que ya ha comenzado -> false
			{
				"auditor1", "title3", IllegalArgumentException.class
			},
			// Editar una rifa logueado como admin que ya ha comenzado -> false
			{
				"admin", "title4", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.editRaffleTest((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverDeleteRaffleTest() {

		final Object testingData[][] = {
			// Borrar una rifa estando logueado como manager que ya ha comenzado -> false
			{
				"manager1", 35, IllegalArgumentException.class
			},
			// Borrar una rifa sin estar logueado que ya ha comenzado -> false
			{
				null, 39, IllegalArgumentException.class
			},
			// Borrar una rifa que ya ha comenzado -> false
			{
				"admin", 35, IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.deleteRaffleTest((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Test para probar el requisito 6.3 search for raffles using a keyword.
	// Los tests comprueban que:
	// Cualquier actor puede realizar una busqueda correctamente.
	// Al meter una cadena vacía nos devuelve la lista con todos los raffles del sistema
	@Test
	public void driverSearchRaffles() {
		final Object testingData[][] = {
			{
				"admin", "fle2", 1, null
			}, {
				"admin", "", 8, null
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateSearchRaffles((String) testingData[i][0], (String) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void templateSearchRaffles(final String username, final String keyword, final int numRaffles, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			Collection<Raffle> raffles = raffleService.search(keyword);
			Assert.isTrue(raffles.size() == numRaffles);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
