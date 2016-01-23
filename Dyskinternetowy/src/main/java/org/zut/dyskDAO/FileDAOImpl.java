package org.zut.dyskDAO;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.zut.dyskDomain.File;
import org.zut.dyskDomain.FileMapper;
import org.zut.dyskDomain.User;
import org.zut.dyskDomain.UserMapper;

public class FileDAOImpl implements FileDAO {

	private JdbcTemplate jdbcTemplate;
    private DataSource data;
    
	public void setData(DataSource data) {
		this.data = data;
		this.jdbcTemplate = new JdbcTemplate(data);
	}
	
	@Override
	public List<File> getFiles(User user, String location) {
		String SQL = "select * from plik where Wlasciciel = ? AND Lokalizacja = ?";
		List<File> files = jdbcTemplate.query(SQL,new Object[]{user.getId(), location}, new FileMapper());
		return files;
	}

	@Override
	public File getFile(User user, int id, String location) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean addFile(User user, File file){
		String SQL = "insert into plik (Lokalizacja,Folder,Nazwa,SumaKontrolna,Rozmiar,Format,"
				+ "Wlasciciel,Opis,DataDodania) values (?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update( SQL,file.getLokalizacja(),file.getFolder(),file.getNazwa(),
				file.getSumaKontrolna(),file.getRozmiar(),file.getFormat(),file.getWlasciciel(),
				file.getOpis(),file.getDataDodania()); 
		
		//Create User DIR at resources/users/user_login	
		java.io.File f = new java.io.File(file.getLokalizacja() + file.getNazwa());
		if(file.getFolder())
			f.mkdir(); // Mkdir /ServerUsers/....Logi_ Name
		//else
			//f.createNewFile(); TODO
		
		return true;
	}
	@Override
	public boolean deleteFile(User user, int id, String location) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editFileInfo(User user, int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean publishFile(User user, int id, String location) {
		// TODO Auto-generated method stub
		return false;
	}

}
