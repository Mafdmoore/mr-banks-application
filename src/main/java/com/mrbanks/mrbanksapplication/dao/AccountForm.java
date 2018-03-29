package com.mrbanks.mrbanksapplication.dao;

public class AccountForm
{	 
    private Long accountId;
    private String firstName;
    private String lastName;
 
    public AccountForm()
    {
 
    }
 
    public AccountForm(Long accountId, String firstName, String lastName)
    {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
 
    public Long getAccountId()
    {
        return accountId;
    }
 
    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }
 
    public String getFirstName()
    {
        return firstName;
    }
 
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
 
    public String getLastName()
    {
        return lastName;
    }
 
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
}