
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Raffle;

@Repository
public interface RaffleRepository extends JpaRepository<Raffle, Integer> {

	@Query("select r from Raffle r where r.manager.id = ?1")
	Collection<Raffle> findAllByManager(int actorId);

	@Query("select min(r.prizes.size), max(r.prizes.size), avg(r.prizes.size), stddev(r.prizes.size) from Raffle r")
	Object[] minMaxAvgStddevOfPrizesPerRaffle();

	@Query("select r from Raffle r join r.prizes p join p.codes c where c.user.id = ?1 group by r")
	Collection<Raffle> findAllByUser(int actorId);

	@Query("select r from Raffle r where (r.title like %?1% or r.description like %?1%) and r.publicationTime <= current_timestamp()")
	Collection<Raffle> searchRaffles(String keyword);

	@Query("select r from Raffle r where r.publicationTime <= current_timestamp()")
	Collection<Raffle> findAllPublic();
}
