package org.zut.dyskService;


import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.Transactional;
import org.zut.dyskDAO.FileDAOImpl;
import org.zut.dyskDAO.UserDaoImpl;
import org.zut.dyskDomain.File;
import org.zut.dyskDomain.User;

public class UserServiceImpl implements UserService
{
	private UserDaoImpl userDao;
	private FileDAOImpl fileDao;
	private String UserDirBasicPath;

	public void setUserDao(UserDaoImpl userDao) {
		this.userDao = userDao;
	}
	public void setFileDAO(FileDAOImpl fileDao) {
		this.fileDao = fileDao;
	}	
	public void setUserDirBasicPath(String userDirBasicPath) {
		UserDirBasicPath = userDirBasicPath;
	}

	@Override
	@Transactional
	public boolean addUser(User u) 
	{
		int count = userDao.getLoginCount(u.getLogin());
		if(count>0)return false;
		boolean result =  userDao.addUser(u);
		File file = new File();
		file.setFolder(true);
		file.setPlikPrywatny(false);
		file.setRozmiar(null);
		file.setFormat(null);
		file.setOpis(null);
		file.setDataDodania(null);
		file.setWlasciciel(userDao.getUser(u.getLogin()).getId());
		file.setNazwa(u.getLogin());
		file.setLokalizacja(UserDirBasicPath);
		fileDao.addFile(u, file);
		file.setNazwa("public");
		file.setLokalizacja(UserDirBasicPath + u.getLogin()+"/");	
		fileDao.addFile(u, file);
		file.setNazwa("private");
		fileDao.addFile(u, file);
		return true ;
	}

	@Override
	@Transactional
	public boolean deleteUser(User u)
	{
		boolean result = userDao.deleteUser(u);
		return result;
	}

	@Override
	@Transactional
	public boolean updateUser(User u) 
	{
		boolean result = userDao.updateUser(u);
		return result;
	}

	@Override
	@Transactional
	public User getUser(String UserName)
	{
		User u = userDao.getUser(UserName);
		return u;
	}

	@Override	
	public Map<String,String> validateRegisterForm(User user) 
	{
		
		Map<String,String> Err = new HashMap<String,String>();
		boolean Valid = true;
		if(!(user.getImie().length() >0))
		{
			Valid= false;
			Err.put("ImieError","Imie Puste");
		}
		if(!(user.getNazwisko().length() >0))
		{
			Valid= false;
			Err.put("NazwiskoError","Nazwisko Puste");
		}
		if(!(user.getHaslo().length() >0))
		{
			Valid= false;
			Err.put("HasloError","Haslo Puste");
		}
		if(!(user.getLogin().length() >0))
		{
			Valid= false;
			Err.put("LoginError","Login Pusty lub Istnieje");
		}
		if(!(user.getEmail().length() >0))
		{
			Valid= false;
			Err.put("EmailError","Niepoprawny Email");
			
		}
		if(Valid == false)
		{
			Err.put("Invalid","yes");
		}
		return Err;
	}

	@Override
	public boolean Authorize(String Login, String Haslo) 
	{
		User u = userDao.getUser(Login);
		if(u == null)return false;
		if(u.getHaslo().equals(Haslo))return true;
		else return false;
	}
	@Override
	public List<User> getAll() {
		
		// TODO Auto-generated method stub
		return userDao.getAll();
	}
	@Override
	public User GetUserById(int id) {
		// TODO Auto-generated method stub
		return userDao.GetUserById(id);
	}

}
