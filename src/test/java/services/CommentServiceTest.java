
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Comentable;
import domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CommentServiceTest extends AbstractTest {

	// The SUT
	// ====================================================

	@Autowired
	private CommentService		commentService;

	@Autowired
	private ComentableService	comentableService;


	// Tests
	// ====================================================

	/*
	 * Create comments over a comentable object.
	 * 
	 * En este caso de uso se crean y se guardan los comments que queramos realizar sobre un objeto comentable siempre y cuando
	 * nos encontramos autentificados, por lo tanto es accesible para cualquier 'actor'.
	 * Para provocar el error, se intenta guardar medianteun usuario no autentificado e incluyendo un objeto comentable no válido.
	 */

	protected void createTest(final String username, final int idComentable, final Class<?> expected) {
		// TODO Auto-generated method stub
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final Comentable comentable = this.comentableService.findOneAux(idComentable);
			this.commentService.create(comentable);
			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

	protected void saveTest(final String username, final int idComentable, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {

			this.authenticate(username);
			final Comentable comentable = this.comentableService.findOneAux(idComentable);
			final Comment comment = this.commentService.create(comentable);
			comment.setText("test");
			comment.setScore(2);

			this.commentService.save(comment);

			this.unauthenticate();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	// Drivers
	// ====================================================

	@Test
	public void driverCreateComment() {

		final Object testingData1[][] = {
			// Creación de un comment si estoy autentificado como user -> true
			{
				"user1", 16, null
			},
			// Creación de un comment si no estoy autentificado -> false
			{
				null, 16, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData1.length; i++)
			this.createTest((String) testingData1[i][0], (int) testingData1[i][1], (Class<?>) testingData1[i][2]);
	}

	@Test
	public void driverSaveComment() {

		final Object testingData[][] = {
			// Si existe el idComentable -> true
			{
				"user1", 16, null
			},
			// Si no existe el idComentable -> false
			{
				"user1", 989, NullPointerException.class
			}, {
				// Si no estamos autentificados para guardar un comment -> false
				null, 16, IllegalArgumentException.class
			}, {
				// Si no estamos autentificados para guardar un comment y el idComentable no existe -> false
				null, 989, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.saveTest((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	// Colección de test para probar el requisito 10.2 manage the taxonomy of properties.
	// Los tests comprueban que:
	// Un actor autenticado como Admin puede ver la lista de comentarios inapropiados.
	// Un actor autenticado como Manager no puede ver la lista de comentarios inapropiandos.
	// Un actor no autenticado no puede ver la lista de comentarios inapropiados.
	// Un actor autenticado como Auditor no ver la lista de comentarios inapropiados.
	@Test
	public void driverCommentInapproppiate() {
		final Object testingData[][] = {
			{
				"admin", null
			}, {
				"manager", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}, {
				"auditor", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateCommentInappropiate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void templateCommentInappropiate(final String username, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			final Collection<Comment> comments = this.commentService.getCommentInappropiate();
			Assert.isTrue(comments.size() == 3);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	// Colección de test para probar el requisito 10.2 manage the taxonomy of properties.
	// Los tests comprueban que:
	// Un actor autenticado como Admin puede eliminar un comentario.
	// Un actor autenticado como Manager no puede eliminar un comentario.
	// Un actor no autenticado no puede eliminar un comentario.
	// Un actor autenticado como Auditor no eliminar un comentario.
	@Test
	public void driverDeleteComment() {
		final Object testingData[][] = {
			{
				"admin", 66, null
			}, {
				"manager", 67, IllegalArgumentException.class
			}, {
				null, 67, IllegalArgumentException.class
			}, {
				"auditor", 69, IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteComment((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void templateDeleteComment(final String username, final int commentId, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		try {
			super.authenticate(username);

			final Comment comment = this.commentService.findOne(commentId);
			this.commentService.delete(comment);

			Assert.isNull(this.commentService.findOne(commentId));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

}
