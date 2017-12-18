
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Code;

@Component
@Transactional
public class CodeToStringConverter implements Converter<Code, String> {

	@Override
	public String convert(final Code code) {

		String result;
		if (code == null)
			result = null;
		else
			result = String.valueOf(code.getId());
		return result;
	}

}
