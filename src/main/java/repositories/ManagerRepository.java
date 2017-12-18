
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findByUserAccountId(int userAccountId);

	@Query("select b.raffle.manager from Bill b where b.raffle.publicationTime <= current_timestamp()  and b.paid = false and payMoment != null group by b.raffle.manager")
	Collection<Manager> findManagersOrderByUnpaidBills();

	@Query("select (count(b.raffle.manager)*100.0 / (select count(m)*1.0 from Manager m)) from Bill b where b.raffle.publicationTime <= current_timestamp()  and b.paid = false and payMoment != null group by b.raffle.manager")
	Collection<Object[]> ratioDebtorManagers();

}
