package org.zut.dyskDAO;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.zut.dyskDomain.File;
import org.zut.dyskDomain.FileMapper;
import org.zut.dyskDomain.Komentaz;
import org.zut.dyskDomain.KomentazMapper;
import org.zut.dyskDomain.User;
import org.zut.dyskDomain.UserMapper;

public class FileDAOImpl implements FileDAO {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("dbDataSource")
    private DataSource data;
    
	/*public void setData(DataSource data) {
		this.data = data;
		this.jdbcTemplate = new JdbcTemplate(data);
	}*/
	
	@Override
	public List<File> getFiles(User user, String location) {
		jdbcTemplate = new JdbcTemplate(data);
		String SQL = "select * from plik where Wlasciciel = ? AND Lokalizacja = ?";
		List<File> files;
		try {
			files= jdbcTemplate.query(SQL,new Object[]{user.getId(), location}, new FileMapper());
		}
		catch(EmptyResultDataAccessException e) {
			return null;
		}
		return files;
	}

	@Override
	public File getFile(User user, int id, String location) {
		jdbcTemplate = new JdbcTemplate(data);
		String SQL = "select * from plik where Id = ? AND Wlasciciel = ? AND Lokalizacja = ?";
		File file;
	//	try {
			file = jdbcTemplate.queryForObject(SQL,new Object[]{id, user.getId(), location}, new FileMapper());
	//	}
	/*	catch(EmptyResultDataAccessException e) {
			return null;
		} */
		return file;
	}
	@Override
	public boolean addFile(User user, File file){
		jdbcTemplate = new JdbcTemplate(data);
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
	public boolean deleteFile(User user, int id) {
		jdbcTemplate = new JdbcTemplate(data);
		String SQL ="delete from plik where Id=? AND Wlasciciel=?";
		jdbcTemplate.update( SQL, id, user.getId());
		return true;
	}

	@Override
	public boolean editFileInfo(User user, int id, File file) {
		jdbcTemplate = new JdbcTemplate(data);
		String SQL ="update plik set Nazwa = ?,Opis =? where Id = ? AND Wlasciciel = ?";
		jdbcTemplate.update( SQL,file.getNazwa(),file.getOpis(), id, user.getId()); 
		return true;
	}

	@Override
	public boolean publishFile(User user, int id, String location) {
		jdbcTemplate = new JdbcTemplate(data);
		String SQL ="update plik set Lokalizacja = ? where Id = ? AND Wlasciciel = ?";
		jdbcTemplate.update( SQL, location, id, user.getId());
		return true;
	}
	public List<File> getAllForUser(int UserId)
	{
		jdbcTemplate = new JdbcTemplate(data);
		List<File> f;
		String SQL = "SELECT * FROM plik WHERE Wlasciciel = ? AND Nazwa = ?";
		f = jdbcTemplate.query(SQL, new Object[]{UserId,"public"},new FileMapper());
		return f;
	}

	@Override
	public boolean AssignKomentaz(Komentaz k) 
	{
		jdbcTemplate = new JdbcTemplate(data);
		String SQL = "INSERT INTO komentaz (Tworca,DataStworzenia,Tresc,Przypisany) VALUES(?,?,?,?) ";
		jdbcTemplate.update(SQL,k.getTworca(),k.getDate(),k.getTresc(),k.getPrzypisany());
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public List<Komentaz> GetAllCommentsForFile(int FileId) 
	{
		jdbcTemplate = new JdbcTemplate(data);
		String SQL="SELECT * FROM komentaz WHERE Przypisany = ?";
		List<Komentaz>CommentList = jdbcTemplate.query(SQL,new Object[]{FileId},new KomentazMapper());		
		return CommentList;
	}

	@Override
	public boolean DelComment(Komentaz k) {
		jdbcTemplate = new JdbcTemplate(data);
		String SQL="DELETE FROM komentaz WHERE Id = ?";
		jdbcTemplate.update(SQL,k.getId());
		return true;
	}

	

}
