package org.zut.dyskDomain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class KomentazMapper implements RowMapper<Komentaz> {

	@Override
	public Komentaz mapRow(ResultSet rs, int rowNum) throws SQLException 
	{
		Komentaz k= new Komentaz(rs.getInt("Id"),rs.getInt("Tworca"),rs.getDate("DataStworzenia"),rs.getString("Tresc"),rs.getInt("Przypisany"));
		return k;
	}

}
