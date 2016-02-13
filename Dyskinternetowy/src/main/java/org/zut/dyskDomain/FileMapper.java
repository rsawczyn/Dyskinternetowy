package org.zut.dyskDomain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FileMapper implements RowMapper<File> {
		@Override
		public File mapRow(ResultSet rs, int arg1) throws SQLException
		{
			File file = new File();
			file.setId(rs.getInt("Id"));
			file.setDataDodania(rs.getDate("DataDodania"));
			file.setFolder(rs.getBoolean("Folder"));
			file.setFormat(rs.getString("Format"));
			file.setLokalizacja(rs.getString("Lokalizacja"));
			file.setNazwa(rs.getString("Nazwa"));
			file.setOpis(rs.getString("Opis"));
			file.setRozmiar(rs.getString("Rozmiar"));
			file.setSumaKontrolna(rs.getString("SumaKontrolna"));
			file.setWlasciciel(rs.getInt("Wlasciciel"));

			return file;
		}
}
