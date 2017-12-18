
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	//Average number of comments per actor, offer, or request.
	@Query("select avg(c.comments.size) from Comentable c")
	Double avgPerActorOfferRequest();

	//Average number of comments posted by administrators and customers.
	@Query("select avg(a.comments.size) from Actor a")
	Double avgPerAdminsAndCustomers();

	//The actors who have posted ±10% the average number of comments per actor.
	@Query("select a from Actor a where a.comments.size between (select 0.9*avg(a1.comments.size) from Actor a1) and (select 1.1*avg(a2.comments.size) from Actor a2)")
	Collection<Actor> actorsBetweenAvgTenPerCent();

	@Query("select c from Comment c where c.comentable.id=?1 and c.actor.id = ?2")
	Collection<Comment> findNotBaned(int id, int principalId);

	@Query("select c from Comment c where c.comentable.id=?1")
	Collection<Comment> findAllByComentable(int comentableId);

	@Query("select avg(c.score), stddev(c.score) from Comment c")
	Object[] avgStddevOfStarsPerComment();

	@Query("select c from Comment c where c.text like %?1%")
	Collection<Comment> findAllByTabooWord(String tabooWord);

}
