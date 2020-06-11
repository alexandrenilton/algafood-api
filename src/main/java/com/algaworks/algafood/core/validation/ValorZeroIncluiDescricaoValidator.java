package com.algaworks.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object>{
	
	private String valorField;
	private String descricaoField;
	private String descricaoObrigatoria;
	
	
	@Override
	public void initialize(ValorZeroIncluiDescricao constraint) {
		this.valorField = constraint.valorField();
		this.descricaoField = constraint.descricaoField();
		this.descricaoObrigatoria = constraint.descricaoObrigatoria();
	}
	
	@Override
	public boolean isValid(Object objectValidation, ConstraintValidatorContext context) {
		boolean valid = true;
		
		try {
			BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), valorField)
					.getReadMethod().invoke(objectValidation);
			String descricao = (String)BeanUtils.getPropertyDescriptor(objectValidation.getClass(), descricaoField)
					.getReadMethod().invoke(objectValidation);
			
			if (valor != null 
				&& BigDecimal.ZERO.compareTo(valor) == 0
				&& descricao != null ) {
				
				valid = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());
			}
			
		} catch (Exception ex) {
			throw new ValidationException(ex);
		}
		return valid;
	}

}
