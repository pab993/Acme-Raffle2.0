
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.TabooWordRepository;
import domain.TabooWord;

@Component
@Transactional
public class StringToTabooWordConverter implements Converter<String, TabooWord> {

	@Autowired
	TabooWordRepository	tabooWordRepository;


	@Override
	public TabooWord convert(String text) {
		TabooWord result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = tabooWordRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
