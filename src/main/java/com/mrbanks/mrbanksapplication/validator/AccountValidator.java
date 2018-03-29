package com.mrbanks.mrbanksapplication.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.mrbanks.mrbanksapplication.dao.AccountDAO;
import com.mrbanks.mrbanksapplication.dao.AccountForm;
 
@Component
public class AccountValidator implements Validator
{
    @Autowired
    private AccountDAO appUserDAO;
 
    @Override
    public boolean supports(Class<?> clazz)
    {
        return clazz == AccountForm.class;
    }
 
    @Override
    public void validate(Object target, Errors errors)
    {
    	AccountForm accountForm = (AccountForm) target;
 
        //Check the fields of AccountForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.appUserForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.appUserForm.lastName");
 
        if (accountForm.getAccountId() == null)
        {
            //Account dbAccount = appUserDAO.findAccountByAccountId(accountForm.getEmail());
            //if (dbAccount != null)
            //{
                //Email has been used by another account.
            //    errors.rejectValue("email", "Duplicate.appUserForm.email");
            //}
        }
    }
}