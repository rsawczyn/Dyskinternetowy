package org.zut.dyskService;


import java.util.HashMap;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.transaction.annotation.Transactional;
import org.zut.dyskDAO.UserDaoImpl;
import org.zut.dyskDomain.User;

public class UserServiceImpl implements UserService
{
	private UserDaoImpl userDao;
	private String UsersResPath;
	
	public void setUsersResPath(String usersResPath) {
		UsersResPath = usersResPath;
	}

	public void setUserDao(UserDaoImpl userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public boolean addUser(User u) 
	{
		int count = userDao.getLoginCount(u.getLogin());
		if(count>0)return false;
		boolean result =  userDao.addUser(u);
		//Create User DIR at resources/users/user_login		
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
		if(u.getHaslo().equals(Haslo))return true;
		else return false;
	}

}
