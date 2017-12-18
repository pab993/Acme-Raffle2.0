
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PropertyServiceTest extends AbstractTest {

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

	// Drivers
	// ====================================================

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
}
