package org.zut.dyskService;


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
	public boolean validateRegisterForm(User u) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Authorize(String Login, String Haslo) 
	{
		User u = userDao.getUser(Login);
		if(u.getHaslo().equals(Haslo))return true;
		else return false;
	}

}
