package org.zut.dyskDomain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int arg1) throws SQLException
	{
		User u = new User(rs.getInt("Id"),rs.getString("Imie"),rs.getString("Nazwisko"),rs.getString("Login")
				,rs.getString("Haslo"),rs.getString("Email"));
		
		return u;


	}

	

}
