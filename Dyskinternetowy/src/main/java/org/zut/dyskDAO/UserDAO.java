package org.zut.dyskDAO;

import org.zut.dyskDomain.User;

public interface UserDAO 
{
	public boolean addUser(User u);
	public boolean deleteUser(User u);
	public boolean updateUser(User u);
	public User getUser(String UserName);
	public int getLoginCount(String Login);
}
