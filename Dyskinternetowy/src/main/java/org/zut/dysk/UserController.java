package org.zut.dysk;

import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zut.dyskDomain.AjaxDataHelper;
import org.zut.dyskDomain.File;
import org.zut.dyskDomain.Komentaz;
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
		model.addAttribute("root",true);
		return "UserPage";
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/Up", method = RequestMethod.GET)	
	public String GoUp(@RequestParam(value="currDir")String currDir,@RequestParam(value="Login")String Login
			,@RequestParam(value="dirType")String dirType,@RequestParam(value="type")String Type)
	{
		if(Type.equals("DirView") || Type.equals("FileView"))
		{
			User user = uservice.getUser(Login);
			String newcurr = null;
			
			for(int i = currDir.length() - 2;i>0;i--)
			{
				char a = currDir.charAt(i);
				
				System.out.println("CHAR = " + a);
				if(i != currDir.length()-1 && a =='/' )
				{
					if(Type.equals("FileView")) newcurr = currDir.substring(0,currDir.length());
					else newcurr = currDir.substring(0,i+1);				
					break;
				}
			}
			String Location = "C://ServerUsers/"+newcurr;
			System.out.println("LOCATION OF UP  = "+Location+ " newCur = "+ newcurr);
			
			File DirId = fservice.GetFileByLocation(Location, user);
			
			String Nazwa = null;		
			String ShortLoc = null;
			for(int i = Location.length() - 2;i>0;i--)
			{
				char a = Location.charAt(i);
				
				System.out.println("CHAR = " + a);
				if(i != Location.length()-1 && a =='/' )
				{
					Nazwa = Location.substring(i+1,Location.length()-1);
					ShortLoc = Location.substring(16,i+1);
					break;
				}
			}
			System.out.println("UP NAZWA (nextDir) = "+Nazwa+ " SHORT LOC (currDir) = "+ ShortLoc);
			return "redirect:/user/"+Login+"/public?currDir="+ShortLoc+"&nextDir="+Nazwa+"&fileId="+DirId.getId();
		}
		else if(Type.equals("FileView"))
		{
			//return "redirect:/user/"+Login+"/"+ dirType +"?currDir="+ShortLoc+"&nextDir="+Nazwa+"&fileId="+DirId.getId();
		}
		return Type;
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
    		f.setPlikPrywatny(false);
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
        //model.addAttribute("currDir", currDir);
		//model.addAttribute("nextDir", null);
		//model.addAttribute("dirType", dirType);
		//model.addAttribute("user", user);
		//model.addAttribute("files", files);
		String Location = "C://ServerUsers/"+currDir;
		System.out.println("LOCATION OF DIR ID = "+Location);
		File DirId = fservice.GetFileByLocation(Location, user);
		
		String Nazwa = null;		
		String ShortLoc = null;
		for(int i = Location.length() - 2;i>0;i--)
		{
			char a = Location.charAt(i);
			
			System.out.println("CHAR = " + a);
			if(i != Location.length()-1 && a =='/' )
			{
				Nazwa = Location.substring(i+1,Location.length()-1);
				ShortLoc = Location.substring(16,i+1);
				break;
			}
		}
        return "redirect:/user/"+Login+"/public?currDir="+ShortLoc+"&nextDir="+Nazwa+"&fileId="+DirId.getId();
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
              //  System.out.println("Name of file: " + file.getOriginalFilename());
               // System.out.println("currDir: " + currDir);
                // Creating the directory to store file
                String rootPath = fservice.getUserBasicDirPath();
                java.io.File dir = new java.io.File(rootPath + java.io.File.separator + currDir);
                String Resolveprivate = dir.getAbsolutePath();
                String pattern = "\\"+Login +"\\private";
                boolean Isprivate = Resolveprivate.contains(pattern);
                System.out.println("absolutePathOfDir: " + dir.getAbsolutePath() +"Pattern =  " + Isprivate);
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
                if(Isprivate)f.setPlikPrywatny(true);
                else f.setPlikPrywatny(false);
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
           // model.addAttribute("currDir", currDir);
    		//model.addAttribute("nextDir", null);
    		//model.addAttribute("dirType", dirType);
    		//model.addAttribute("user", user);
    		//model.addAttribute("files", files);
    		String Location = "C://ServerUsers/"+currDir;
    		System.out.println("LOCATION OF DIR ID = "+Location);
    		File DirId = fservice.GetFileByLocation(Location, user);
    		
    		String Nazwa = null;		
    		String ShortLoc = null;
    		for(int i = Location.length() - 2;i>0;i--)
    		{
    			char a = Location.charAt(i);
    			
    			System.out.println("CHAR = " + a);
    			if(i != Location.length()-1 && a =='/' )
    			{
    				Nazwa = Location.substring(i+1,Location.length()-1);
    				ShortLoc = Location.substring(16,i+1);
    				break;
    			}
    		}
            return "redirect:/user/"+Login+"/public?currDir="+ShortLoc+"&nextDir="+Nazwa+"&fileId="+DirId.getId();
        } else {
            return "You failed to upload " + file.getOriginalFilename()
                    + " because the file was empty.";
        }
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/deleteFile", method = RequestMethod.POST)
	public String deleteFile(Model model,@PathVariable String Login, 
			@RequestParam("currDir") String currDir, @RequestParam("dirType") String dirType,
			@RequestParam("fileId") int fileId , Principal pal)
	{
		//System.out.println("currDir: " + currDir);
		//System.out.println("dirType: " + dirType);
		User user = uservice.getUser(Login);
		if(Login == pal.getName()) model.addAttribute("owner",true);
		else
		model.addAttribute("owner",false);
		List<Komentaz> k = fservice.GetAllCommentForFile(fileId);
		for(Komentaz tmp : k)
		{
			fservice.DelComment(tmp); // Kasuj wszystkie komenta¿e nale¿¹ce do pliku (Klucz Obcy)
		}
		File f = fservice.getFile(user, fileId, currDir);
		fservice.deleteFile(user, fileId);		
		java.io.File file = new java.io.File(fservice.getUserBasicDirPath()+currDir+f.getNazwa());	
		file.delete();
		List<File> files= fservice.getFiles(user, currDir);
       // model.addAttribute("currDir", currDir);
		//model.addAttribute("nextDir", null);
		//model.addAttribute("dirType", dirType);
		//model.addAttribute("user", user);
		//model.addAttribute("files", files);
		String Location = "C://ServerUsers/"+currDir;
		//System.out.println("LOCATION OF DIR ID = "+Location);
		File DirId = fservice.GetFileByLocation(Location, user);
		
		String Nazwa = null;		
		String ShortLoc = null;
		for(int i = Location.length() - 2;i>0;i--)
		{
			char a = Location.charAt(i);
			
			//System.out.println("CHAR = " + a);
			if(i != Location.length()-1 && a =='/' )
			{
				Nazwa = Location.substring(i+1,Location.length()-1);
				ShortLoc = Location.substring(16,i+1);
				break;
			}
		}
		
		//System.out.println("DIRID = " + DirId.getId());
		return "redirect:/user/"+Login+"/public?currDir="+ShortLoc+"&nextDir="+Nazwa+"&fileId="+DirId.getId();
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/updateFile", method = RequestMethod.POST)
	public String updateFile(@ModelAttribute("file") File file, Model model,
			@PathVariable String Login, @RequestParam("currDir") String currDir, 
			@RequestParam("dirType") String dirType,Principal pal)
	{
		User user = uservice.getUser(Login);
		File oldFile = fservice.getFile(user, file.getId(), file.getLokalizacja().substring(
				file.getLokalizacja().indexOf(Login)));
		//System.out.println("Old file localization: " + oldFile.getLokalizacja()+oldFile.getNazwa());
		java.io.File oldName = new java.io.File(oldFile.getLokalizacja()+oldFile.getNazwa());
		//System.out.println("File id: " + file.getId());
		//System.out.println("File nazwa: " + file.getNazwa());
		//System.out.println("File opis: " + file.getOpis());
		
		fservice.editFileInfo(user, file.getId(), file);
		// Loginy na podstawie Autoryzacji(Principal)
		File f = fservice.getFile(user, file.getId(), file.getLokalizacja().substring(
				file.getLokalizacja().indexOf(Login)));
		java.io.File newName = new java.io.File(f.getLokalizacja()+f.getNazwa());
	    oldName.renameTo(newName);
	    
		//model.addAttribute("currDir", currDir);
		//model.addAttribute("nextDir", null);
		//model.addAttribute("dirType", dirType);
		model.addAttribute("user", user);
		model.addAttribute("file", f);
		List<Komentaz>Comments = fservice.GetAllCommentForFile(f.getId());
		model.addAttribute("comments",Comments);
		Komentaz k = new Komentaz();
		model.addAttribute("Kom",k);
		model.addAttribute("Login",Login);
		model.addAttribute("p",pal.getName());
		Map<Integer,String> UserIdMap = new HashMap<Integer, String>();
		
		for(Komentaz com : Comments)
		{
			if(UserIdMap.containsKey(com.getTworca()))continue;
			String ULogin = uservice.GetUserById(com.getTworca()).getLogin();
			UserIdMap.put(com.getTworca(),ULogin);
		}
		model.addAttribute("UserIdMap",UserIdMap);
		return "redirect:public?currDir="+currDir+"&nextDir=&fileId="+f.getId();
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/public", method = RequestMethod.GET)
	public String Public(Model model,@PathVariable String Login, @RequestParam("currDir") String currDir,
			@RequestParam("nextDir") String nextDir, @RequestParam("fileId") int fileId,Principal pal)
	{
		User user = new User();
		boolean IsOwner = false;
		if(pal.getName().equals(Login))
		{
			user = uservice.getUser(pal.getName());// Twój w³asny katalog public
			IsOwner = true;
		}
		else
		{
			user = uservice.getUser(Login);//Czyjœ katalog public 
			IsOwner = false;
		}		
		if(IsOwner == false)
		{
			String pattern = user.getLogin()+"/private/";
			if(currDir.contains(pattern) || (currDir.contains("wpar/")&& nextDir.equals("private")))
			{
				return "redirect:/user/";
			}
		}
		List<File> files= null;
		//System.out.println("CurrDir: " +currDir);
		//System.out.println("NextDir: " +nextDir);
		//System.out.println("FileId: " +fileId);
		//System.out.println("UserId: " +user.getId()); */
		File file =fservice.getFile(user, fileId, currDir);
		// jezeli jest plikiem
		if( file.isFolder() == false){
			// TODO wyswietlic dane pliku
			model.addAttribute("currDir", currDir);
			
			model.addAttribute("user", user);
			model.addAttribute("file", file);
			model.addAttribute("IsOwner",IsOwner);
			List<Komentaz>Comments = fservice.GetAllCommentForFile(file.getId());
			model.addAttribute("comments",Comments);
			
			Komentaz k = new Komentaz();
    		model.addAttribute("Kom",k);
			model.addAttribute("p",pal.getName());
			model.addAttribute("Login",Login);
			Map<Integer,String> UserIdMap = new HashMap<Integer, String>();
    		for(Komentaz com : Comments)
    		{
    			if(UserIdMap.containsKey(com.getTworca()))continue;
    			String ULogin = uservice.GetUserById(com.getTworca()).getLogin();
    			UserIdMap.put(com.getTworca(),ULogin);
    		}
    		model.addAttribute("UserIdMap",UserIdMap);
    		if(file.getPlikPrywatny() == true)
    		{
    			model.addAttribute("dirType", "private");
    		}
    		else
    		{
    			model.addAttribute("dirType", "public");
    		}
			return "ViewFiles";
		}
		else {
			// jezeli jestesmy w nastepnym katalogu
			if(nextDir != null) {
				/*fservice.setCurrDir(fservice.getCurrDir().concat(nextDir));
				files = fservice.getFiles(user, fservice.getCurrDir()); */
				currDir += nextDir+"/";
				//System.out.println("CurrDir: " +currDir);
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
		model.addAttribute("owner",IsOwner);
		// Tylko folder publiczny , nazwa loginu z Path Variable
		//System.out.println("Login in Path = "+Login);
		System.out.println("CURR DIR FOR SECURITY = " + currDir);
		if(currDir.equals(Login+"/") || ( currDir.equals(Login+"/public/") && !Login.equals(pal.getName() )  ) )
		{
			model.addAttribute("root",true);
		}
		return "ViewDirs";
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "{Login}/private", method = RequestMethod.GET)
	public String Private(Model model,Principal sec, @RequestParam("currDir") String currDir,
			@RequestParam("nextDir") String nextDir, @RequestParam("fileId") int fileId)
	{
		User user = uservice.getUser(sec.getName()); // principal
		List<File> files= null;
	/*	System.out.println("CurrDir: " +currDir);
		System.out.println("NextDir: " +nextDir);
		System.out.println("FileId: " +fileId);
		System.out.println("UserId: " +user.getId()); */
		File file =fservice.getFile(user, fileId, currDir);
		// jezeli jest plikiem
		if( file.isFolder() == false){
			model.addAttribute("currDir", currDir);
			model.addAttribute("dirType", "private");
			model.addAttribute("user", user);
			model.addAttribute("file", file);
			List<Komentaz>Comments = fservice.GetAllCommentForFile(file.getId());
			model.addAttribute("comments",Comments);
			Komentaz k = new Komentaz();
			model.addAttribute("Kom",k);
			model.addAttribute("p",sec.getName());
			Map<Integer,String> UserIdMap = new HashMap<Integer, String>();
			for(Komentaz com : Comments)
    		{
    			if(UserIdMap.containsKey(com.getTworca()))continue;
    			String ULogin = uservice.GetUserById(com.getTworca()).getLogin();
    			UserIdMap.put(com.getTworca(),ULogin);
    		}
    		model.addAttribute("UserIdMap",UserIdMap);
			return "ViewFiles";
		}
		else {
			// jezeli jestesmy w nastepnym katalogu
			if(nextDir != null) {
				/*fservice.setCurrDir(fservice.getCurrDir().concat(nextDir));
				files = fservice.getFiles(user, fservice.getCurrDir()); */
				currDir += nextDir+"/";
				//System.out.println("CurrDir: " +currDir);
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
		model.addAttribute("owner",true);
		// Tylko folder publiczny , nazwa loginu z Path Variable
		//System.out.println("Login in Path = "+Login);
		if(currDir.equals(user.getLogin()+"/") || ( currDir.equals(user.getLogin()+"/private") && !user.getLogin().equals(sec.getName() )  ) )
		{
			model.addAttribute("root",true);
		}
		return "ViewDirs";
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/downloadFile", method = RequestMethod.POST)
	public void downloadFile(HttpServletResponse response, Model model,
			@PathVariable String Login, @RequestParam("currDir") String currDir, 
			@RequestParam("dirType") String dirType, 
			@RequestParam("fileName") String fileName)
	{
		User user = uservice.getUser(Login);
		//tell browser program going to return an application file 
        //instead of html page
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename="+fileName);
        try 
    	{
	        java.io.File file = new java.io.File(fservice.getUserBasicDirPath()+currDir+fileName);
	        System.out.println("absolutePathOfFile: " + file.getAbsolutePath());
	        FileInputStream fileIn = new FileInputStream(file);
	        ServletOutputStream out = response.getOutputStream();
	
	        byte[] outputByte = new byte[4096];
	        //copy binary contect to output stream
	        while(fileIn.read(outputByte, 0, 4096) != -1)
	        {
	        	out.write(outputByte, 0, 4096);
	        }
	        fileIn.close();
	        out.flush();
	        out.close();
    	}
        catch(Exception e) {
        	  System.out.println("You failed to download" + fileName + 
        			  " => " + e.getMessage());
        }
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/{Login}/publishFile", method = RequestMethod.POST)
	public String publishFile(Model model,@PathVariable String Login, 
			@RequestParam("currDir") String currDir, @RequestParam("dirType") String dirType, 
			@RequestParam("fileName") String fileName, @RequestParam("fileId") int fileId)
	{
		User user = uservice.getUser(Login);
        try 
    	{
        	String oldPath = fservice.getUserBasicDirPath()+currDir;
        	String newPath = fservice.getUserBasicDirPath()+Login+"/public/";
        	FileUtils.moveFile(
        		      FileUtils.getFile(oldPath+fileName), 
        		      FileUtils.getFile(newPath+fileName));
        	System.out.println("movefrom path: "+ oldPath+fileName);
        	System.out.println("target path: "+ newPath+fileName);

    		fservice.publishFile(user, fileId, newPath);
    		File f = fservice.getFile(user, fileId, Login+"/public/");
    		
    		
    		//model.addAttribute("currDir", currDir);
    		//model.addAttribute("nextDir", null);
    		//model.addAttribute("dirType", "public");
    		//model.addAttribute("user", user);
    		//model.addAttribute("file", f);
    		List<Komentaz>Comments = fservice.GetAllCommentForFile(f.getId());
    		Map<Integer,String> UserIdMap = new HashMap<Integer, String>();
    		for(Komentaz com : Comments)
    		{
    			if(UserIdMap.containsKey(com.getTworca()))continue;
    			String ULogin = uservice.GetUserById(com.getTworca()).getLogin();
    			UserIdMap.put(com.getTworca(),ULogin);
    		}
    		model.addAttribute("UserIdMap",UserIdMap);
    		model.addAttribute("comments",Comments);
    		System.out.println("Publish FIN");
    		Komentaz k = new Komentaz();
    		model.addAttribute("Kom",k);
    		
    		String Location = "C://ServerUsers/"+currDir;    		
    		//System.out.println("LOCATION OF DIR ID = "+Location);
    		File DirId = fservice.GetFileByLocation(Location, user);
    		
    		String Nazwa = null;		
    		String ShortLoc = null;
    		for(int i = Location.length() - 2;i>0;i--)
    		{
    			char a = Location.charAt(i);
    			
    			System.out.println("CHAR = " + a);
    			if(i != Location.length()-1 && a =='/' )
    			{
    				Nazwa = Location.substring(i+1,Location.length()-1);
    				ShortLoc = Location.substring(16,i+1);
    				break;
    			}
    		}
    		File fp = fservice.GetFileByLocation("C://ServerUsers/"+ShortLoc+Nazwa+"/", user);
    		System.out.println("Nazwa = " + Nazwa + " ShortLOC = " + ShortLoc);
    		return "redirect:/user/"+Login+"/public?currDir="+ShortLoc+"&nextDir="+Nazwa+"&fileId="+fp.getId();
    	}
        catch(Exception e) {
        	  return "You failed to publish file " + fileName + 
        			  " => " + e.getMessage();
        }
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/UserIndex", method = RequestMethod.GET)
	public String UserList(Model model)
	{
		
		List<User> u = uservice.getAll();
		Map<String,String[]> usersFiles = new HashMap<String, String[]>();		
		for(User user : u )
		{			
			
			File tmp = fservice.GetFileByLocation("C://ServerUsers/"+user.getLogin()+"/public/", user);
			usersFiles.put(user.getLogin(),new String[]{user.getLogin()+"/public",String.valueOf(tmp.getId())});
			
		}
		model.addAttribute("usersFiles",usersFiles);
		model.addAttribute("users",u);
		
		return "UserListIndex";
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/AddComment", method = RequestMethod.POST)
	public String AddComment(@RequestParam(value="tworca")String Tworca,@RequestParam(value="login")String Login,
			@RequestParam(value="curDir")String Dir,@RequestParam(value="file")int Fid,@RequestParam(value="tresc")String Cont
			)
	{
		User u = uservice.getUser(Tworca);
		Komentaz k = new Komentaz();
		Date d = new Date();		
		k.setDate(d);
		k.setPrzypisany(Fid);
		k.setTresc(Cont);
		k.setTworca(u.getId());
		fservice.AddComment(k);
		return "redirect:"+Login+"/public?currDir="+Dir+"&nextDir="+"&fileId="+Fid;
		
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/DelComment", method = RequestMethod.POST)
	public String DeleteComment(@RequestParam(value="commentId")String Id,@RequestParam(value="login")String Login,
			@RequestParam(value="curDir")String Dir,@RequestParam(value="file")int Fid)
	{
		
		Komentaz k = new Komentaz();
		k.setId(Integer.valueOf(Id));
		fservice.DelComment(k);
		return "redirect:"+Login+"/public?currDir="+Dir+"&nextDir="+"&fileId="+Fid;
		
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String Admin(Model model,Principal principal)
	{
		User u = uservice.getUser(principal.getName());
		model.addAttribute("Email",u.getEmail());
		model.addAttribute("Nazwisko",u.getNazwisko());
		model.addAttribute("Imie",u.getImie());
		model.addAttribute("Haslo",u.getHaslo());
		model.addAttribute("Login", principal.getName());
		return "Admin";
		
	}
	
	@PreAuthorize("hasRole('ROLE_NORMALUSER') && isAuthenticated()")
	@RequestMapping(value = "/changepass", method = RequestMethod.POST)
	public String PassChange(@RequestParam(value="pass")String NewPassword,Principal p)
	{
		User u = new User();
		u.setId(uservice.getUser(p.getName()).getId());
		u.setImie(uservice.getUser(p.getName()).getImie());
		u.setNazwisko(uservice.getUser(p.getName()).getNazwisko());
		u.setEmail(uservice.getUser(p.getName()).getEmail());
		u.setLogin(uservice.getUser(p.getName()).getLogin());
		u.setHaslo(NewPassword);
		uservice.updateUser(u);
		return "redirect:/user/admin";
		
	}
	
	
}
