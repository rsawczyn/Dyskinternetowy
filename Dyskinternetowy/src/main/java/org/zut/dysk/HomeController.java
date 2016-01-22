package org.zut.dysk;


import java.util.Locale;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.zut.dyskDomain.User;
import org.zut.dyskService.UserServiceImpl;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController 
{
	
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	private UserServiceImpl uservice;
	
	@Autowired
	public void setUservice(UserServiceImpl uservice) {
		this.uservice = uservice;
	}

	
	@RequestMapping(value = "/", method = RequestMethod.GET)	
	public String home( Model model) 
	{
		
		User u = new User();		
		model.addAttribute("User",u);
		return "login";
	
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)	
	public String Add(Locale locale, Model model,HttpServletRequest re) 
	{
		User u = new User();		
		
		model.addAttribute("User",u);
		System.out.println(getClass().getResourceAsStream("/users"));
		
		return "login";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)	
	public String Register(Locale locale, Model model) 
	{
		User u = new User();		
		model.addAttribute("User",u);
		
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
		public String RegisterPost(@ModelAttribute("User") User user,
			BindingResult result, Model model) 
	{
		Map<String,String> Errors = uservice.validateRegisterForm(user);
		if(Errors.containsKey("Invalid"))
		{
			for(Map.Entry<String,String>var:Errors.entrySet())
			{
				model.addAttribute(var.getKey(),var.getValue());
			}
			model.addAttribute("User", user);
			return "register";
		}
		else
		{
			uservice.addUser(user);
			model.addAttribute("registrationOK","Registration Succesfull!");
			User u = new User();
			model.addAttribute("User",u);
			return "redirect:/"; // Przekieruj na stronê logowania
		}
	}
	
	
	
	
}
