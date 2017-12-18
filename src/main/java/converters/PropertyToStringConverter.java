
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Property;

@Component
@Transactional
public class PropertyToStringConverter implements Converter<Property, String> {

	@Override
	public String convert(final Property property) {

		String result;
		if (property == null)
			result = null;
		else
			result = String.valueOf(property.getId());
		return result;
	}

}
