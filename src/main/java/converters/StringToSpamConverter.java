package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SpamRepository;
import domain.SpamKeyword;


@Component
@Transactional
public class StringToSpamConverter implements Converter<String, SpamKeyword>{

	@Autowired
	SpamRepository spamRepository;

	@Override
	public SpamKeyword convert(String text) {
		SpamKeyword result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = spamRepository.findOne(id);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
