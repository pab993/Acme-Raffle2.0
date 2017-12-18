
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.PrizeRepository;
import domain.Prize;

@Component
@Transactional
public class StringToPrizeConverter implements Converter<String, Prize> {

	@Autowired
	PrizeRepository	prizeRepository;


	@Override
	public Prize convert(final String text) {
		Prize result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.prizeRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
