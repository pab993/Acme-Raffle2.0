package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SpamKeyword;

@Component
@Transactional
public class SpamToStringConverter implements Converter<SpamKeyword, String>{

	@Override
	public String convert(SpamKeyword entity) {
		
		String result;
		if (entity == null)
			result = null;
		else
			result = String.valueOf(entity.getId());
	
		return result;
	}
}