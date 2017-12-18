package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import repositories.ConfigurationSystemRepository;
import domain.ConfigurationSystem;

public class StringToConfigurationSystemConverter implements Converter<String, ConfigurationSystem> {
	
	@Autowired ConfigurationSystemRepository configurationSystemRepository;

	@Override
	public ConfigurationSystem convert(String text) {
		ConfigurationSystem result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
				id = Integer.valueOf(text);
				result = configurationSystemRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
