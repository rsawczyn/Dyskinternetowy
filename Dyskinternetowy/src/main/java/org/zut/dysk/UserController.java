package org.zut.dysk;

import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Calendar;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.zut.dyskDomain.File;
import org.zut.dyskDomain.User;
import org.zut.dyskService.FileServiceImpl;
import org.zut.dyskService.UserServiceImpl;

@Controller
@RequestMapping(value = "/user") // Kontroler musi mieæ na pocz¹tku /user/..
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
		String Login = principal.getName(); // principal ma nazwê Login u¿ytkownika zalogowanego
		System.out.println("Principal name = " + principal.getName()); // Wyœwietl login w konsoli
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
	@RequestMapping(value = "/{Login}/addDir", method = RequestMethod.POST)
	public String addDir(Model model,@PathVariable String Login,@RequestParam("currDir") String currDir, 
			@RequestParam("dirType") String dirType, @RequestParam("dirName") String dirName)
	{
		User user = uservice.getUser(Login);
		if (!dirName.isEmpty()) {
			//Jezeli plik jest katalogiem, tworzy nowy katalog
			File f = new File();
            f.setFolder(true);
    		f.setSumaKontrolna(null);
    		f.setRozmiar(null);
    		f.setFormat(null);
    		f.setOpis(null);
    		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
    		f.setDataDodania(date);
    		f.setWlasciciel(user.getId());
    		f.setNazwa(dirName);
    		f.setLokalizacja(currDir);
    		// DODANIE PLIKU DO BAZY DANYCH
    		fservice.addFile(user, f);
		}
		List<File> files= fservice.getFiles(user, currDir);
        model.addAttribute("currDir", currDir);
		model.addAttribute("nextDir", null);
		model.addAttribute("dirType", dirType);
		model.addAttribute("user", user);
		model.addAttribute("files", files);
        return "ViewDirs";
	}
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/addFile", method = RequestMethod.POST)
	public String addFile(Model model,@PathVariable String Login, @RequestParam("file") MultipartFile file,
			@RequestParam("currDir") String currDir, @RequestParam("dirType") String dirType)
	{
		// Loginy na podstawie Autoryzacji(Principal)
		// Pliki s¹ tworzone lokalnie na dysku serwera (poza projektem )
		User user = uservice.getUser(Login);
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                System.out.println("Name of file: " + file.getOriginalFilename());
                System.out.println("currDir: " + currDir);
                // Creating the directory to store file
                String rootPath = fservice.getUserBasicDirPath();
                java.io.File dir = new java.io.File(rootPath + java.io.File.separator + currDir);
                System.out.println("absolutePathOfDir: " + dir.getAbsolutePath());
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server
                java.io.File serverFile = new java.io.File(dir.getAbsolutePath()
                        + java.io.File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                File f = new File();
                f.setFolder(false);
        		f.setSumaKontrolna(null);
        		f.setRozmiar(String.valueOf(file.getSize()));
        		f.setFormat(file.getOriginalFilename().substring(
        				file.getOriginalFilename().lastIndexOf(".")));
        		f.setOpis(null);
        		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        		f.setDataDodania(date);
        		f.setWlasciciel(user.getId());
        		f.setNazwa(file.getOriginalFilename());
        		f.setLokalizacja(currDir);
        		// DODANIE PLIKU DO BAZY DANYCH
        		fservice.addFile(user, f);
                System.out.println("Server File Location=" + serverFile.getAbsolutePath());
            } catch (Exception e) {
                return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
            }        
    		List<File> files= fservice.getFiles(user, currDir);
            model.addAttribute("currDir", currDir);
    		model.addAttribute("nextDir", null);
    		model.addAttribute("dirType", dirType);
    		model.addAttribute("user", user);
    		model.addAttribute("files", files);
            return "ViewDirs";
        } else {
            return "You failed to upload " + file.getOriginalFilename()
                    + " because the file was empty.";
        }
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/deleteFile", method = RequestMethod.POST)
	public String deleteFile(Model model,@PathVariable String Login, 
			@RequestParam("currDir") String currDir, @RequestParam("dirType") String dirType,
			@RequestParam("fileId") int fileId)
	{
		System.out.println("currDir: " + currDir);
		System.out.println("dirType: " + dirType);
		User user = uservice.getUser(Login);
		fservice.deleteFile(user, fileId);
		List<File> files= fservice.getFiles(user, currDir);
        model.addAttribute("currDir", currDir);
		model.addAttribute("nextDir", null);
		model.addAttribute("dirType", dirType);
		model.addAttribute("user", user);
		model.addAttribute("files", files);
		return "ViewDirs";
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/updateFile", method = RequestMethod.POST)
	public String updateFile(@ModelAttribute("file") File file, Model model,
			@PathVariable String Login, @RequestParam("currDir") String currDir, 
			@RequestParam("dirType") String dirType)
	{
		User user = uservice.getUser(Login);		
		System.out.println("File id: " + file.getId());
		System.out.println("File nazwa: " + file.getNazwa());
		System.out.println("File opis: " + file.getOpis());
		fservice.editFileInfo(user, file.getId(), file);
		// Loginy na podstawie Autoryzacji(Principal)
		File f = fservice.getFile(user, file.getId(), file.getLokalizacja().substring(
				file.getLokalizacja().indexOf(Login)));
		model.addAttribute("currDir", currDir);
		model.addAttribute("nextDir", null);
		model.addAttribute("dirType", dirType);
		model.addAttribute("user", user);
		model.addAttribute("file", f);
		return "ViewFiles";
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/public", method = RequestMethod.GET)
	public String Public(Model model,@PathVariable String Login, @RequestParam("currDir") String currDir,
			@RequestParam("nextDir") String nextDir, @RequestParam("fileId") int fileId)
	{
		User user = uservice.getUser(Login);
		List<File> files= null;
/*		System.out.println("CurrDir: " +currDir);
		System.out.println("NextDir: " +nextDir);
		System.out.println("FileId: " +fileId);
		System.out.println("UserId: " +user.getId()); */
		File file =fservice.getFile(user, fileId, currDir);
		// jezeli jest plikiem
		if( file.isFolder() == false){
			// TODO wyswietlic dane pliku
			model.addAttribute("currDir", currDir);
			model.addAttribute("dirType", "public");
			model.addAttribute("user", user);
			model.addAttribute("file", file);
			return "ViewFiles";
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
		//System.out.println("Login in Path = "+Login);
		return "ViewDirs";
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/private", method = RequestMethod.GET)
	public String Private(Model model,@PathVariable String Login, @RequestParam("currDir") String currDir,
			@RequestParam("nextDir") String nextDir, @RequestParam("fileId") int fileId)
	{
		User user = uservice.getUser(Login);
		List<File> files= null;
	/*	System.out.println("CurrDir: " +currDir);
		System.out.println("NextDir: " +nextDir);
		System.out.println("FileId: " +fileId);
		System.out.println("UserId: " +user.getId()); */
		File file =fservice.getFile(user, fileId, currDir);
		// jezeli jest plikiem
		if( file.isFolder() == false){
			model.addAttribute("currDir", currDir);
			model.addAttribute("dirType", "public");
			model.addAttribute("user", user);
			model.addAttribute("file", file);
			return "ViewFiles";
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
		//System.out.println("Login in Path = "+Login);
		return "ViewDirs";
	}
	

}
