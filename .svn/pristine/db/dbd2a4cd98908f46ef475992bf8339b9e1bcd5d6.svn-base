
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TabooWordRepository;
import domain.TabooWord;

@Service
@Transactional
public class TabooWordService {

	@Autowired
	private TabooWordRepository	tabooWordRepository;

	@Autowired
	private ActorService		actorService;


	public TabooWordService() {
		super();
	}

	public TabooWord create() {

		Assert.isTrue(this.actorService.isAdmin());

		TabooWord res;

		res = new TabooWord();

		return res;
	}

	public TabooWord save(final TabooWord tabooWord) {

		Assert.isTrue(this.actorService.isAdmin());
		Assert.notNull(tabooWord);

		return this.tabooWordRepository.save(tabooWord);
	}

	public void delete(final TabooWord tabooWord) {

		Assert.isTrue(this.actorService.isAdmin());
		Assert.notNull(tabooWord);
		Assert.isTrue(this.tabooWordRepository.exists(tabooWord.getId()));

		this.tabooWordRepository.delete(tabooWord);

		Assert.isTrue(!this.tabooWordRepository.exists(tabooWord.getId()));
	}

	public TabooWord findOne(final int tabooWordId) {
		return this.tabooWordRepository.findOne(tabooWordId);
	}

	public Collection<TabooWord> findAll() {
		return this.tabooWordRepository.findAll();
	}

	public String maskCharacters(final String cadenaIntroducida) {
		String cadena = cadenaIntroducida.toLowerCase();

		for (final TabooWord tw : this.findAll())
			if (cadenaIntroducida.contains(tw.getName()) || cadenaIntroducida.toLowerCase().contains(tw.getName().toLowerCase()) || cadenaIntroducida.toUpperCase().contains(tw.getName().toUpperCase()))
				cadena = cadena.replaceAll(tw.getName().toLowerCase(), "***");

		return cadena;
	}
}
