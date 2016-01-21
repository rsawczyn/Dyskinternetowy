package org.zut.dyskDAO;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.zut.dyskDomain.User;
import org.zut.dyskDomain.UserMapper;

public class UserDaoImpl implements UserDAO 
{
	private JdbcTemplate jdbcTemplate;
    private DataSource data;
    
    
	public boolean addUser(User u) 
	{
		String SQL = "insert into User (Imie,Nazwisko,Login,Haslo,Email) values (?,?,?,?,?)";	      
	    jdbcTemplate.update( SQL,u.getImie(),u.getNazwisko(),u.getLogin(),u.getHaslo(),u.getEmail());      
		return true;
		
	}

	public void setData(DataSource data) {
		this.data = data;
		this.jdbcTemplate = new JdbcTemplate(data);
	}

	@Override
	public boolean deleteUser(User u) 
	{
		String SQL ="delete from User where Id=?";
		try
		{
			jdbcTemplate.update( SQL,u.getId()); 
		}
		catch(DataAccessException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateUser(User u) 
	{
		String SQL ="update User set Imie = ?,Nazwisko =?,Haslo = ?,Email=? where Id = ?";
		jdbcTemplate.update(SQL,u.getImie(),u.getNazwisko(),u.getHaslo(),u.getEmail(),u.getId());
		
		return true;
	}

	@Override
	public User getUser(String UserName) 
	{
		try
		{
			String SQL = "select * from User where Login = ?";
		    User user = jdbcTemplate.queryForObject(SQL,new Object[]{UserName}, new UserMapper());
		    return user;
		// TODO Auto-generated method stub
		}
		catch(EmptyResultDataAccessException e)
		{
			return null;
		}
	   
		
	}

	@Override
	public int getLoginCount(String Login) 
	{
		String SQL = "SELECT COUNT(*) FROM User WHERE Login=?";
		int number = jdbcTemplate.queryForObject(SQL, Integer.class, Login);
		return number;
	}

}
