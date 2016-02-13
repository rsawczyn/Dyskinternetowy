package org.zut.dyskDAO;

import java.util.List;

import org.zut.dyskDomain.User;

public interface UserDAO 
{
	public User GetUserById(int id);
	public boolean addUser(User u);
	public boolean deleteUser(User u);
	public boolean updateUser(User u);
	public User getUser(String UserName);
	public int getLoginCount(String Login);
	public List<User> getAll();
}
