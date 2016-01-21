package org.zut.dyskService;

import java.util.Map;

import org.zut.dyskDomain.User;

public interface UserService 
{
	public boolean addUser(User u);
	public boolean deleteUser(User u);
	public boolean updateUser(User u);
	public User getUser(String UserName);
	public Map<String,String> validateRegisterForm(User u);
	public boolean Authorize(String Login,String Haslo);

}
