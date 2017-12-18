
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

	@Query("select b from Bill b where b.raffle.id = ?1")
	Bill findOneByRaffleId(int raffleId);

	@Query("select sum(b.price) from Bill b where b.raffle.manager.id = ?1 and b.raffle.publicationTime < current_timestamp()  and b.paid = false and payMoment != null")
	Object[] calculateTotalTaxByManagerId(int managerId);

	@Query("select count(b) from Bill b where b.raffle.manager.id = ?1 and b.raffle.publicationTime <= current_timestamp()  and b.paid = false and payMoment != null")
	Object[] calculateBillsUnpaidByManagerId(int managerId);

	@Query("select count(b) from Bill b where b.raffle.manager.id = ?1 and b.raffle.publicationTime <= current_timestamp()  and b.paid = false and payMoment != null")
	int calculateBillsUnpaidByManagerIdInt(int managerId);

	@Query("select b from Bill b where b.raffle.publicationTime <= current_timestamp() and b.paid = false and b.payMoment != null")
	Collection<Bill> findAllUnpaidBill();

	@Query("select b from Bill b where b.raffle.manager.id = ?1 and payMoment != null")
	Collection<Bill> findAllByManagerId(int managerId);

	@Query("select b from Bill b where b.payMoment = null and b.paid = false and b.raffle.publicationTime < ?1")
	Collection<Bill> findAllByComputeDate(Date computeDate);

	@Query("select b from Bill b where b.payMoment != null")
	Collection<Bill> findAllComputed();

	@Query("select min(m.raffles.size), max(m.raffles.size), avg(m.raffles.size) from Manager m join m.raffles r where r.publicationTime <= current_timestamp()")
	Object[] minMaxAvgOfBillsPerManager();

	@Query("select min(m.raffles.size), max(m.raffles.size), avg(m.raffles.size) from Manager m  join m.raffles r where r.publicationTime <= current_timestamp() and r.bill.payMoment != null and r.bill.paid = false")
	Object[] minMaxAvgOfUnpaidBillsPerManager();

	@Query("select min(m.raffles.size), max(m.raffles.size), avg(m.raffles.size) from Manager m  join m.raffles r where r.publicationTime <= current_timestamp() and r.bill.payMoment != null and r.bill.paid = true")
	Object[] minMaxAvgOfPaidBillsPerManager();
}
