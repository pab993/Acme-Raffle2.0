
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Code;

@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {

	@Query("select c from Code c where c.name = ?1 and c.prize.id = ?2")
	Code findByCode(String name, int prizeId);

	@Query("select c from Code c where c.user.id = ?1 and c.prize.id = ?2")
	Collection<Code> findAllByUserAndPrize(int actorId, int prizeId);

	@Query("select c from Code c where c.prize.id = ?1")
	Collection<Code> findCodesByPrize(int prizeId);

	@Query("select c from Code c where c.prize.id = ?1 and c.name like %?2%")
	Code findCodesByPrizeAndName(int prizeId, String name);

}
