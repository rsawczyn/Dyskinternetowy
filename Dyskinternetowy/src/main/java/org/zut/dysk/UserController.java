package org.zut.dysk;

import java.io.Console;
import java.io.IOException;
import java.security.Principal;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zut.dyskDomain.User;

@Controller
@RequestMapping(value = "/user") // Kontroler musi mieæ na pocz¹tku /user/..
public class UserController 
{
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/", method = RequestMethod.GET)	
	public String mainPage(Model model,Principal pricinpal)
	{
		String Login = pricinpal.getName(); // principal ma nazwê Login u¿ytkownika zalogowanego
		System.out.println("Principal name = " + pricinpal.getName()); // Wyœwietl login w konsoli
		model.addAttribute("user",Login);
		return "UserPage";
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/addFile", method = RequestMethod.POST)
	public String addFile(Model model,Principal pricinpal)
	{
		// Loginy na podstawie Autoryzacji(Principal)
		// Pliki s¹ tworzone lokalnie na dysku serwera (poza projektem )
		
		String Login = pricinpal.getName();
		return "UserPage";
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/delFile", method = RequestMethod.POST)
	public String deleteFile(Model model,Principal pricinpal)
	{
		// Loginy na podstawie Autoryzacji(Principal)
		String Login = pricinpal.getName();
		return "UserPage";
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/updateFile", method = RequestMethod.POST)
	public String updateFile(Model model,Principal pricinpal)
	{
		// Loginy na podstawie Autoryzacji(Principal)
		String Login = pricinpal.getName();
		return "UserPage";
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/public", method = RequestMethod.GET)
	public String Public(Model model,@PathVariable String Login)
	{
	
		// Tylko folder publiczny , nazwa loginu z Path Variable
		System.out.println("Login in Path = "+Login);
		return "UserPage";
	}
	
	

}
