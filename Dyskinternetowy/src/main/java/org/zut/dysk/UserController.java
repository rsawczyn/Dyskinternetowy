package org.zut.dysk;

import java.io.Console;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.zut.dyskDomain.File;
import org.zut.dyskDomain.User;
import org.zut.dyskService.FileServiceImpl;
import org.zut.dyskService.UserServiceImpl;

@Controller
@RequestMapping(value = "/user") // Kontroler musi mie� na pocz�tku /user/..
public class UserController 
{
	private UserServiceImpl uservice;
	private FileServiceImpl fservice;

	@Autowired
	public void setUservice(UserServiceImpl uservice) {
		this.uservice = uservice;
	}
	@Autowired
	public void setFservice(FileServiceImpl fservice) {
		this.fservice = fservice;
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/", method = RequestMethod.GET)	
	public String mainPage(Model model,Principal principal)
	{
		String Login = principal.getName(); // principal ma nazw� Login u�ytkownika zalogowanego
		System.out.println("Principal name = " + principal.getName()); // Wy�wietl login w konsoli
		User user = uservice.getUser(principal.getName());
		fservice.setCurrDir(user.getLogin());
		List<File> files = fservice.getFiles(user, user.getLogin()+"/");
		File pub = files.get(0);
		File priv = files.get(1);
		for(File f : files){
			System.out.println("File name: " +f.getNazwa());
		}
		System.out.println("File name: " +pub.getNazwa());
		System.out.println("File name: " +priv.getNazwa());
		model.addAttribute("pub", pub);
		model.addAttribute("priv", priv);
		model.addAttribute("user",user);
		return "UserPage";
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/addFile", method = RequestMethod.POST)
	public String addFile(Model model,Principal pricinpal)
	{
		// Loginy na podstawie Autoryzacji(Principal)
		// Pliki s� tworzone lokalnie na dysku serwera (poza projektem )
		
		String Login = pricinpal.getName();
		return "UserPage";
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/delFile", method = RequestMethod.POST)
	public String deleteFile(Model model,Principal pricinpal)
	{
		fservice.getCurrDir();
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
	public String Public(Model model,@PathVariable String Login, @RequestParam("currDir") String currDir,
			@RequestParam("nextDir") String nextDir, @RequestParam("fileId") int fileId)
	{
		User user = uservice.getUser(Login);
		List<File> files= null;
		System.out.println("CurrDir: " +currDir);
		System.out.println("NextDir: " +nextDir);
		System.out.println("FileId: " +fileId);
		System.out.println("UserId: " +user.getId());
		File file =fservice.getFile(user, fileId, currDir);
		// jezeli jest plikiem
		if( file.isFolder() == false){
			// TODO wyswietlic dane pliku
		}
		else {
			// jezeli jestesmy w nastepnym katalogu
			if(nextDir != null) {
				/*fservice.setCurrDir(fservice.getCurrDir().concat(nextDir));
				files = fservice.getFiles(user, fservice.getCurrDir()); */
				currDir += nextDir+"/";
				System.out.println("CurrDir: " +currDir);
				files = fservice.getFiles(user, currDir);	
				for(File f : files){
					System.out.println("File name: " +f.getNazwa());
				}
			}
			
		}
		model.addAttribute("currDir", currDir);
		model.addAttribute("nextDir", nextDir);
		model.addAttribute("dirType", "public");
		model.addAttribute("user", user);
		model.addAttribute("files", files);
		
		// Tylko folder publiczny , nazwa loginu z Path Variable
		System.out.println("Login in Path = "+Login);
		return "ViewDirs";
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/private", method = RequestMethod.GET)
	public String Private(Model model,@PathVariable String Login, @RequestParam("currDir") String currDir,
			@RequestParam("nextDir") String nextDir, @RequestParam("fileId") int fileId)
	{
		User user = uservice.getUser(Login);
		List<File> files= null;
		System.out.println("CurrDir: " +currDir);
		System.out.println("NextDir: " +nextDir);
		System.out.println("FileId: " +fileId);
		System.out.println("UserId: " +user.getId());
		File file =fservice.getFile(user, fileId, currDir);
		// jezeli jest plikiem
		if( file.isFolder() == false){
			// TODO wyswietlic dane pliku
		}
		else {
			// jezeli jestesmy w nastepnym katalogu
			if(nextDir != null) {
				/*fservice.setCurrDir(fservice.getCurrDir().concat(nextDir));
				files = fservice.getFiles(user, fservice.getCurrDir()); */
				currDir += nextDir+"/";
				System.out.println("CurrDir: " +currDir);
				files = fservice.getFiles(user, currDir);	
				for(File f : files){
					System.out.println("File name: " +f.getNazwa());
				}
			}
			
		}	
		model.addAttribute("currDir", currDir);
		model.addAttribute("nextDir", nextDir);
		model.addAttribute("dirType", "private");
		model.addAttribute("user", user);
		model.addAttribute("files", files);
		// Tylko folder publiczny , nazwa loginu z Path Variable
		System.out.println("Login in Path = "+Login);
		return "ViewDirs";
	}
	

}
