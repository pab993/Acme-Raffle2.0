package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.SpamKeyword;

@Repository
public interface SpamRepository extends JpaRepository<SpamKeyword,Integer>{

	
	
}