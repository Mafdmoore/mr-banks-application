package com.mrbanks.mrbanksapplication.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.mrbanks.mrbanksapplication.dao.AccountForm;
 
@Component
public class AccountValidator implements Validator
{
    @Override
    public boolean supports(Class<?> clazz)
    {
        return clazz == AccountForm.class;
    }
 
    @Override
    public void validate(Object target, Errors errors)
    {
    	//Check the fields of AccountForm object
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.accountForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.accountForm.lastName");
    }
}