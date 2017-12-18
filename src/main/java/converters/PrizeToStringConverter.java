
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Prize;

@Component
@Transactional
public class PrizeToStringConverter implements Converter<Prize, String> {

	@Override
	public String convert(final Prize prize) {

		String result;
		if (prize == null)
			result = null;
		else
			result = String.valueOf(prize.getId());
		return result;
	}

}
