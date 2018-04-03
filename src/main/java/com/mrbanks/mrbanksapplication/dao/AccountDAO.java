package com.mrbanks.mrbanksapplication.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.mrbanks.mrbanksapplication.model.Account;
 
@Repository
public class AccountDAO
{
    @Autowired
    private static final Map<Long, Account> ACCOUNTS_MAP = new HashMap<>();
    
    static
    {
        initDATA();
    }
 
    private static void initDATA()
    {
        Account tom = new Account(1L, "Tom", "Tom");
        Account jerry = new Account(2L, "Jerry", "Jerry");
 
        ACCOUNTS_MAP.put(tom.getAccountId(), tom);
        ACCOUNTS_MAP.put(jerry.getAccountId(), jerry);
    }
 
    public Long getMaxAccountId()
    {
        long max = 0;
        for (Long id : ACCOUNTS_MAP.keySet())
        {
            if (id > max) max = id;
        }
        
        
        return max;
    }
 
    public Account findAccountByAccountId(Long accountId)
    {
        Collection<Account> accounts = ACCOUNTS_MAP.values();
        for (Account u : accounts)
        {
            if (u.getAccountId().equals(accountId)) return u;
        }
        
        
        return null;
    }
 
    public List<Account> getAccounts()
    {
        List<Account> list = new ArrayList<>();
        list.addAll(ACCOUNTS_MAP.values());
        
        
        return list;
    }
 
    public Account createAccount(AccountForm form)
    {
    	Long accountId = this.getMaxAccountId() + 1;
        Account account = new Account(accountId, form.getFirstName(), form.getLastName());
        ACCOUNTS_MAP.put(accountId, account);
        
        
        return account;
    }
 
    public void deleteAccount(Long accountId)
    {
    	ACCOUNTS_MAP.remove(accountId, findAccountByAccountId(accountId));
    }
    
    public Account editAccount(Long accountId, AccountForm form)
    {
    	Account account = new Account(accountId, form.getFirstName(), form.getLastName());
    	ACCOUNTS_MAP.replace(accountId, account);
    	
    	
    	return account;
    }
}