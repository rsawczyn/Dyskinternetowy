package org.zut.dysk;
import org.springframework.security.access.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.ModelAndView;
import org.zut.dyskDomain.User;
import org.zut.dyskService.UserServiceImpl;

import com.sun.xml.internal.bind.CycleRecoverable.Context;
import com.sun.xml.internal.ws.client.RequestContext;

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
		//if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) return"forward:/user/";
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
		boolean Valid = true;
		if(!(user.getImie().length() >0))
		{
			Valid= false;
			model.addAttribute("ImieError","Imie Puste");
		}
		if(!(user.getNazwisko().length() >0))
		{
			Valid= false;
			model.addAttribute("NazwiskoError","Nazwisko Puste");
		}
		if(!(user.getHaslo().length() >0))
		{
			Valid= false;
			model.addAttribute("HasloError","Haslo Puste");
		}
		if(!(user.getLogin().length() >0))
		{
			Valid= false;
			model.addAttribute("LoginError","Login Pusty lub Istnieje");
		}
		if(!(user.getEmail().length() >0))
		{
			Valid= false;
			model.addAttribute("EmailError","Niepoprawny Email");
			model.addAttribute("InValid", Valid);
		}
		if(!Valid)
		{
			model.addAttribute("User",user); 
			return "register";
		}
		else
		{
			uservice.addUser(user);
			model.addAttribute("registrationOK","Registration Succesfull!");
			User u = new User();
			model.addAttribute("User",u);
			return "login";
		}
	}
	
	
	
	
}
