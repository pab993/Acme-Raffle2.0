
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ComentableRepository;
import domain.Comentable;

@Service
@Transactional
public class ComentableService {

	@Autowired
	private ComentableRepository	comentableRepository;


	public Comentable findOneAux(final int comentableId) {
		Assert.isTrue(comentableId != 0);
		Comentable result;

		result = this.comentableRepository.findOneAux(comentableId);
		return result;
	}

	public Comentable findOne(final int comentableId) {
		Assert.isTrue(comentableId != 0);
		Comentable result;

		result = this.comentableRepository.findOne(comentableId);
		return result;
	}

	public Collection<Comentable> findAll() {
		Collection<Comentable> result;

		result = this.comentableRepository.findAll();

		return result;
	}

	public Object[] avgStddevOfCommentsPerComentable() {

		Object[] result;

		result = this.comentableRepository.avgStddevOfCommentsPerComentable();

		return result;
	}

	public Object[] ratioStars(final int comentableId) {

		Object[] result;

		result = this.comentableRepository.ratioStars(comentableId);

		return result;
	}

}
