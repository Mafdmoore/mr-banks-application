package com.mrbanks.mrbanksapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
// import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mrbanks.mrbanksapplication.dao.AccountDAO;
import com.mrbanks.mrbanksapplication.dao.AccountForm;
import com.mrbanks.mrbanksapplication.model.Account;
import com.mrbanks.mrbanksapplication.validator.AccountValidator;
 
@Controller
public class MainController
{ 
   @Autowired
   private AccountDAO accountDAO;
 
   @Autowired
   private AccountValidator accountValidator;
 
   @InitBinder
   protected void initBinder(WebDataBinder dataBinder)
   {
      Object target = dataBinder.getTarget();
      if (target == null) return;
      System.out.println("Target=" + target);
 
      if (target.getClass() == AccountForm.class) dataBinder.setValidator(accountValidator);
   }
 
   @RequestMapping("/")
   public String viewHome(Model model)
   {
      return "welcomePage";
   }
 
   @RequestMapping("/accounts")
   public String viewMembers(Model model)
   {
      List<Account> list = accountDAO.getAccounts();
 
      model.addAttribute("members", list);
 
      return "accountsPage";
   }
 
   @RequestMapping("/createAccountSuccessful")
   public String viewRegisterSuccessful(Model model)
   {
      return "createAccountSuccessfulPage";
   }
 
   @RequestMapping(value = "/createAccount", method = RequestMethod.GET)
   public String viewRegister(Model model) {
 
	  AccountForm form = new AccountForm();
 
      model.addAttribute("accountForm", form);
 
      return "createAccountPage";
   }
 
   @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
   public String saveRegister(Model model, @ModelAttribute("accountForm") @Validated AccountForm accountForm, BindingResult result, final RedirectAttributes redirectAttributes)
   {
	  Account newUser= null;
      try
      {
    	  newUser = accountDAO.createAppUser(accountForm);
      }
      catch (Exception e)
      {
         model.addAttribute("errorMessage", "Error: " + e.getMessage());
         return "createAccountPage";
      }
 
      redirectAttributes.addFlashAttribute("flashUser", newUser);
       
      return "redirect:/createAccountSuccessful";
   }
 
}