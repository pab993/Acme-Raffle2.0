package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SpamRepository;
import domain.SpamKeyword;

@Service
@Transactional
public class SpamService {
	
	@Autowired
	private SpamRepository spamRepository ;
	
	public SpamService(){
		super();
	}
	
	// CRUD methods --------------------------------------------------------------------------------
	public SpamKeyword create() {
		SpamKeyword res = new SpamKeyword();
		
		//TODO
		
		return res;
	}

	public SpamKeyword findOne(int spamId) {
		return spamRepository.findOne(spamId);
	}

	public Collection<SpamKeyword> findAll(){
		return spamRepository.findAll();
	}
		
	public SpamKeyword save(SpamKeyword spam) {
		Assert.notNull(spam);
		return spamRepository.save(spam);
	}

	public void delete(SpamKeyword spam) {
		Assert.notNull(spam);
		Assert.isTrue(spamRepository.exists(spam.getId()));
		
		spamRepository.delete(spam);
		
		Assert.isTrue(!spamRepository.exists(spam.getId()));
	}

}