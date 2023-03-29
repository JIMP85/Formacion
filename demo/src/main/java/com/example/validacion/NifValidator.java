package com.example.validacion;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NifValidator implements ConstraintValidator<NIF, String> {
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return ValidacionNIF.isNIF(value);
	}

}
