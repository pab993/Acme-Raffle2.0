
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Comentable;

public interface ComentableRepository extends JpaRepository<Comentable, Integer> {

	@Query("select c from Comentable c where c.id = ?1")
	Comentable findOneAux(int comentableId);

	@Query("select avg(c.comments.size), stddev(c.comments.size) from Comentable c")
	Object[] avgStddevOfCommentsPerComentable();

	@Query("select avg(c.score) from Comment c where c.comentable.id = ?1")
	Object[] ratioStars(int comentableId);

}
