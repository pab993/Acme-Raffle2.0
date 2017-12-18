
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Bill;
import domain.Raffle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class BillServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private RaffleService	raffleService;

	@Autowired
	private BillService		billService;


	// Tests
	// ====================================================

	/*
	 * Manage his or her bills, which includes listing, creating, editing, and deleting them. Note that a raffle can be edited and deleted as long as its publication time has not passed; once the publication time passes, no raffle can be edited or
	 * deleted.
	 * 
	 * En este caso de uso se listan, crean, editan y eliminan los bills que pertenecen a una 'manager' logueado, por lo tanto es accesible tan sólo para los 'managers'.
	 * Para provocar el error, se intenta guardar mediante un usuario no autentificado e incluyendo valores erróneos para observar el comportamiento de la aplicación.
	 */

	public void billCreateAndSaveTest(final String username, final int raffleId, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			final Raffle raffle = this.raffleService.findOne(raffleId);

			final Bill result = this.billService.create(raffle);

			this.billService.save(result);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	// Drivers
	// ====================================================

	@Test
	public void driverBillCreateAndSaveTest() {

		final Object testingData[][] = {
			// Crear una factura estando logueado como manager -> true
			{
				"manager1", 35, null
			},
			// Crear una factura sin estar logueado --> false
			{
				null, 35, IllegalArgumentException.class
			},
			// Crear una factura logueado como auditor -> false
			{
				"auditor1", 35, IllegalArgumentException.class
			},
			// Crear una factura logueado como admin -> false
			{
				"admin", 35, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.billCreateAndSaveTest((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);

	}

}
