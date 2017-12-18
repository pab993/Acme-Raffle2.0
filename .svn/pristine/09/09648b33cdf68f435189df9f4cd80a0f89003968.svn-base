
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Audit;
import domain.Raffle;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AuditServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private AuditService	auditService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private RaffleService	raffleService;


	// Tests
	// ====================================================

	/*
	 * Manage his or her audit reports, which includes listing, creating, editing, and delet-ing them. An audit report can be edited or deleted as long as the corresponding au-ditor keeps it in draft model; once he or she saves it in final model, it is
	 * published and no more edits are allowed; neither can it be deleted.
	 * 
	 * En este caso de uso se listan, crean, editan y eliminan los audits que pertenecen a una 'auditor' logueado, por lo tanto es accesible tan sólo para los 'auditors'.
	 * Para provocar el error, se intenta guardar mediante un usuario no autentificado e incluyendo valores erróneos para observar el comportamiento de la aplicación.
	 */

	@SuppressWarnings("deprecation")
	public void AuditCreateAndSaveTest(final String username, final String text, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			final Raffle raffle = this.raffleService.findOne(35);
			final Audit result = this.auditService.create(raffle);

			result.setText("text1");

			this.auditService.save(result);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	public void editAuditTest(final String username, final String text, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			Audit result;

			result = this.auditService.findOne(43);

			result.setText(text);

			this.auditService.save(result);
			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	public void deleteAuditTest(final String username, final int auditId, final Class<?> expected) {
		Class<?> caught = null;

		try {

			this.authenticate(username);

			Audit result;

			result = this.auditService.findOne(auditId);

			this.auditService.deleteAuditor(result);

			this.unauthenticate();

		} catch (final Throwable oops) {

			caught = oops.getClass();

		}

		this.checkExceptions(expected, caught);
	}

	// Drivers
	// ====================================================

	@Test
	public void driverAuditCreateAndSaveTest() {

		final Object testingData[][] = {
			// Crear una auditoría estando logueado como auditor -> true
			{
				"auditor1", "text1", null
			},
			// Crear una auditoría sin estar logueado -> false
			{
				null, "text2", IllegalArgumentException.class
			},
			// Crear una auditoría logueado como manager -> false
			{
				"manager1", "text3", IllegalArgumentException.class
			},
			// Crear una auditoría logueado como admin -> false
			{
				"admin", "text4", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.AuditCreateAndSaveTest((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);

	}

	@Test
	public void driverEditAuditTest() {

		final Object testingData[][] = {
			// Editar una auditoría estando logueado como auditor -> true 
			{
				"auditor1", "text1", null
			},
			// Editar una auditoría sin estar logueado -> false
			{
				null, "text2", IllegalArgumentException.class
			},
			// Editar una auditoría logueado como manager -> false
			{
				"manager1", "text3", IllegalArgumentException.class
			},
			// Editar una auditoría logueado como admin -> false
			{
				"admin", "text4", IllegalArgumentException.class
			}

		};
		for (int i = 0; i < testingData.length; i++)
			this.editAuditTest((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	@Test
	public void driverDeleteAuditTest() {

		final Object testingData[][] = {
			// Borrar una auditoría estando logueado como auditor -> true
			{
				"auditor1", 45, null
			},
			// Borrar una auditoría sin estar logueado -> false
			{
				null, 45, IllegalArgumentException.class
			},
			// Borrar una auditoría -> false
			{
				"admin", 45, IllegalArgumentException.class
			},

		};
		for (int i = 0; i < testingData.length; i++)
			this.deleteAuditTest((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

}
