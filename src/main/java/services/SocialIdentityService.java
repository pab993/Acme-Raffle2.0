
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.Actor;
import domain.SocialIdentity;

@Service
@Transactional
public class SocialIdentityService {

	@Autowired
	private SocialIdentityRepository	socialIdentityRepository;

	@Autowired
	private ActorService				actorService;


	public SocialIdentityService() {
		super();
	}

	// CRUD methods --------------------------------------------------------------------------------
	public SocialIdentity create() {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(principal);
		SocialIdentity result;

		result = new SocialIdentity();
		result.setActor(principal);

		return result;
	}

	public SocialIdentity findOne(final int socialIdentityId) {
		return this.socialIdentityRepository.findOne(socialIdentityId);
	}

	public Collection<SocialIdentity> findAll() {
		return this.socialIdentityRepository.findAll();
	}

	public SocialIdentity save(final SocialIdentity socialIdentity) {
		Assert.notNull(socialIdentity);
		Assert.isTrue(this.actorService.isAuthenticated());
		Assert.isTrue(socialIdentity.getActor().getId() == this.actorService.findByPrincipal().getId());
		SocialIdentity res;

		res = this.socialIdentityRepository.save(socialIdentity);

		return res;
	}

	public void delete(final SocialIdentity socialIdentity) {

		Assert.notNull(socialIdentity);
		Assert.isTrue(this.socialIdentityRepository.exists(socialIdentity.getId()));
		Assert.isTrue(this.actorService.findByPrincipal().getId() == socialIdentity.getActor().getId());

		this.socialIdentityRepository.delete(socialIdentity);

		Assert.isTrue(!this.socialIdentityRepository.exists(socialIdentity.getId()));
	}

	// Other business methods --------------------------------------------------------------------------------

	public Collection<SocialIdentity> findByActor(final int idActor) {
		return this.socialIdentityRepository.findByActor(idActor);
	}

}
