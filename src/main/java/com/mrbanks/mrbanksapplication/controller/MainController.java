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
import org.springframework.web.bind.annotation.RequestParam;
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

	//User visits welcomePage
	@RequestMapping("/")
	public String viewHome(Model model)
	{
		return "welcomePage";
	}

	//User visits accountsPage
	@RequestMapping("/accounts")
	public String viewMembers(Model model)
	{
		List<Account> list = accountDAO.getAccounts();
		model.addAttribute("accounts", list);

		
		return "accountsPage";
	}

	//User visits createAccountSuccessfulPage or editAccountSuccessfulPage
	@RequestMapping("/createAccountSuccessful")
	public String viewRegisterSuccessful(Model model)
	{
		return "createAccountSuccessfulPage";
	}

	@RequestMapping("/editAccountSuccessful")
	public String editRegisterSuccessful(Model model)
	{
		return "editAccountSuccessfulPage";
	}

	//User visits createAccountPage
	@RequestMapping(value = "/createAccount", method = RequestMethod.GET)
	public String viewRegister(Model model)
	{
		AccountForm form = new AccountForm();
		model.addAttribute("accountForm", form);

		
		return "createAccountPage";
	}

	//User visits editAccountPage
	@RequestMapping(value = "/editAccount", method = RequestMethod.POST)
	public String viewRegisterElement(Model model, @RequestParam(name = "id") Long accountId)
	{
		Account account = accountDAO.findAccountByAccountId(accountId);
		AccountForm form = new AccountForm(account.getAccountId(), account.getFirstName(), account.getLastName());
		model.addAttribute("accountForm", form);

		
		return "editAccountPage";
	}
	
	//User clicks Delete on accountsPage
	@RequestMapping(value = "/accounts", method = RequestMethod.POST)
	public String deleteRegisterElement(Model model, @RequestParam(name = "id") Long accountId)
	{
		accountDAO.deleteAccount(accountId);
		
		List<Account> list = accountDAO.getAccounts();
		model.addAttribute("accounts", list);
		
		
		return "accountsPage";
	}

	//User clicks Submit on createAccountPage
	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	public String saveRegister(Model model, @ModelAttribute("accountForm") @Validated AccountForm accountForm, BindingResult result, final RedirectAttributes redirectAttributes)
	{
		Account newAccount = null;
		try
		{
			newAccount = accountDAO.createAccount(accountForm);
		}
		catch (Exception e)
		{
			model.addAttribute("errorMessage", "Error: " + e.getMessage());
			return "createAccountPage";
		}

		redirectAttributes.addFlashAttribute("flashUser", newAccount);

		
		return "redirect:/createAccountSuccessful";
	}

	//User clicks Submit on editAccountPage
	@RequestMapping(value = "/editAccountSubmit", method = RequestMethod.POST)
	public String editRegisterElement(Model model, @ModelAttribute("accountForm") @Validated AccountForm accountForm, @RequestParam(name = "id") Long accountId, BindingResult result, final RedirectAttributes redirectAttributes)
	{
		Account newAccount = null;
		try
		{
			newAccount = accountDAO.editAccount(accountId, accountForm);
		}
		catch (Exception e)
		{
			model.addAttribute("errorMessage", "Error: " + e.getMessage());
			return "editAccountPage";
		}

		redirectAttributes.addFlashAttribute("flashUser", newAccount);

		
		return "redirect:/editAccountSuccessful";
	}
}