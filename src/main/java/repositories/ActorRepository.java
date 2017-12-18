
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int userAccountId);

	@Query("select a.country , avg(c.score) from Actor a join a.comments c where a.country is not null group by a.country")
	Collection<Object[]> avgStarsPerActorGroupByCountry();

	@Query("select a.city, avg(c.score) from Actor a join a.comments c where a.city is not null group by a.city")
	Collection<Object[]> avgStarsPerActorGroupByCity();

}
