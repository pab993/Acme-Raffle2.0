
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PropertyRepository;
import domain.Prize;
import domain.Property;

@Service
@Transactional
public class PropertyService {

	//Managed Repository =============================================================================

	@Autowired
	private PropertyRepository		propertyRepository;

	//Services
	// ===============================================================================================

	@Autowired
	private AdministratorService	administratorService;


	//SCRUDs Methods

	//===============================================================================================

	public Property create() {

		Assert.notNull(this.administratorService.findByPrincipal());

		Property res;

		res = new Property();

		return res;
	}

	public Property save(final Property property) {

		Assert.notNull(property);

		return this.propertyRepository.save(property);
	}

	public void delete(final Property property) {
		Assert.notNull(property);
		Assert.notNull(this.administratorService.findByPrincipal());

		this.propertyRepository.delete(property);

	}

	public Property findOne(final int propertyId) {
		return this.propertyRepository.findOne(propertyId);
	}

	public Collection<Property> findAll() {
		return this.propertyRepository.findAll();
	}

	public Collection<Property> findAllPropertyPrize(final Prize prize) {
		return prize.getProperties();
	}

	//Other Business Methods =========================================================================

	public Collection<Property> findAllByPrize(final int prizeId) {

		Collection<Property> result;

		result = this.propertyRepository.findAllByPrizeId(prizeId);

		return result;
	}

}
