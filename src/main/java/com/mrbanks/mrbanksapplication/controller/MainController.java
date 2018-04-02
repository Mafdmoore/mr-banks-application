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
	public String viewRegisterElement(Model model, @ModelAttribute("accountForm") AccountForm accountForm, BindingResult result)
	{
		//Account account = accountDAO.findAccountByAccountId(accountId);
		System.out.println(accountForm.getAccountId() + "\n" + accountForm.getFirstName() + "\n" + accountForm.getLastName());
		
		//AccountForm form = new AccountForm(account.getAccountId(), account.getFirstName(), account.getLastName());
		//If the program tries to display a form that isn't a new AccountForm object, it returns a whitelabel error
		//This is probably because the HTML page doesn't send an account ID when the Edit button is clicked, so the Java code is using an uninitialised Long to find an Account object
		
		model.addAttribute("accountForm", accountForm);

		return "editAccountPage";
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
	public String editRegisterElement(Model model, @ModelAttribute("accountForm") @Validated AccountForm accountForm, BindingResult result, final RedirectAttributes redirectAttributes)
	{
		Account newAccount = null;
		Long accountId = accountForm.getAccountId();
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