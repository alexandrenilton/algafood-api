package com.algaworks.algafood.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {
	
	/** faz a configuracao do BeanValidation com Spring */
	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		
		/* indicamos que o ValidationMessageSources é o messageSource. 
		 * ou seja: o ValidationMessage.properties passa a ser o messages.properties
		 */
		bean.setValidationMessageSource(messageSource);
		return bean;
	}
}
