package converters;

import org.springframework.core.convert.converter.Converter;

import domain.ConfigurationSystem;

public class ConfigurationSystemToStringConverter implements Converter<ConfigurationSystem, String> {
	
	@Override
	public String convert(ConfigurationSystem configurationSystem) {
		
		String result;
		
		if(configurationSystem == null){
			result = null;
		}else{
			result = String.valueOf(configurationSystem.getId());
		}
		return result;
	}
}
