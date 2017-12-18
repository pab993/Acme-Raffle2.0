
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Prize;

@Repository
public interface PrizeRepository extends JpaRepository<Prize, Integer> {

	@Query("select p from Prize p where p.raffle.id = ?1")
	Collection<Prize> findAllByRaffle(int raffleId);

	@Query("select min(p.codes.size), max(p.codes.size), avg(p.codes.size), stddev(p.codes.size) from Prize p")
	Object[] minMaxAvgStddevOfCodesPerPrize();

	//select c.prize from User u join u.codes c where u.id = ?1 and c.prize.raffle.id = ?2
	@Query("select p from Prize p join p.codes c where c.user.id = ?1 and p.raffle.id = ?2 group by p")
	Collection<Prize> findAllByUserAndRaffle(int actorId, int raffleId);
}
