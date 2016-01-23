package org.zut.dyskService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zut.dyskDAO.FileDAOImpl;
import org.zut.dyskDomain.File;
import org.zut.dyskDomain.User;

public class FileServiceImpl implements FileService 
{
	private FileDAOImpl fileDAO;
	private String UserBasicDirPath;
	// Zmmiena BasicPath z basic.properties (Patrz servlet-context.xml)
	@Autowired
	public void setFileDAO(FileDAOImpl fileDAO) {
		this.fileDAO = fileDAO;
	}	
	@Autowired
	public void setUserBasicDirPath(String userBasicDirPath) {
		UserBasicDirPath = userBasicDirPath;
	}
	@Override
	public List<File> getFiles(User user, String location) {
		// UserBasicDirPath+location;  // full path to file
		List<File> files = fileDAO.getFiles(user, UserBasicDirPath+location+"/");
		System.out.println("Location"+UserBasicDirPath+location);
		return files;
	}
	@Override
	public File getFile(User user, int id, String location) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean addFile(User user, File file) {
		fileDAO.addFile(user, file);
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
