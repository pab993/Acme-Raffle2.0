
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userAccount.id = ?1")
	User findByUserAccountId(int userAccountId);

	@Query("select count(u)*100.0 / (select count(u1)*1.0 from User u1) from User u where u.userAccount.enabled = false")
	Object[] ratioBanUnBanUsers();

	@Query("select min(u.codes.size), max(u.codes.size), avg(u.codes.size), stddev(u.codes.size) from User u")
	Object[] minMaxAvgStddevOfValidCodesPerUser();

	@Query("select u from User u order by u.codes.size DESC")
	Collection<User> findUsersOrderByValidCodes();

}
