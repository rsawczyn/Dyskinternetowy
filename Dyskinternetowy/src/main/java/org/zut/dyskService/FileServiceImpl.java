package org.zut.dyskService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zut.dyskDAO.FileDAOImpl;
import org.zut.dyskDomain.File;
import org.zut.dyskDomain.Komentaz;
import org.zut.dyskDomain.User;
import org.zut.dyskService.FileService;

public class FileServiceImpl implements FileService 
{
	private FileDAOImpl fileDAO;
	private String UserBasicDirPath;
	// Zmmiena BasicPath z basic.properties (Patrz servlet-context.xml)
	private String CurrDir;
	
	public String getCurrDir() {
		return CurrDir;
	}
	public void setCurrDir(String currDir) {
		CurrDir = currDir;
	}
	@Autowired
	public void setFileDAO(FileDAOImpl fileDAO) {
		this.fileDAO = fileDAO;
	}
	
	public String getUserBasicDirPath() {
		return UserBasicDirPath;
	} 
	@Autowired
	public void setUserBasicDirPath(String userBasicDirPath) {
		UserBasicDirPath = userBasicDirPath;
	}
	@Override
	public List<File> getFiles(User user, String location) {
		// UserBasicDirPath+location;  // full path to file
		List<File> files = fileDAO.getFiles(user, UserBasicDirPath+location);
		System.out.println("Location: "+UserBasicDirPath+location);
		System.out.println("UserId: "+user.getId());
		return files;
	}
	@Override
	public File getFile(User user, int id, String location) {
		System.out.println("Location: "+UserBasicDirPath+location);
		File file = fileDAO.getFile(user, id, UserBasicDirPath+location);
		return file;
	}
	public boolean addFile(User user, File file) {
		file.setLokalizacja(UserBasicDirPath+file.getLokalizacja());
		fileDAO.addFile(user, file);
		return true;
	}
	@Override
	public boolean deleteFile(User user, int fileId) {
		fileDAO.deleteFile(user, fileId);
		return true;
	}
	@Override
	public boolean editFileInfo(User user, int fileId, File file) {
		fileDAO.editFileInfo(user, fileId, file);
		return true;
	}
	@Override
	public boolean publishFile(User user, int fileId, String location) {
		fileDAO.publishFile(user, fileId, location);
		return true;
	}
	@Override
	public List<File> getAllFilesForUser(int Id) {
		// TODO Auto-generated method stub
		return fileDAO.getAllForUser(Id);
	}
	@Override
	public List<Komentaz> GetAllCommentForFile(int FileId)
	{	
		return fileDAO.GetAllCommentsForFile(FileId);
	}
	@Override
	public boolean AddComment(Komentaz k) {
		fileDAO.AddComment(k);
		return true;
	}
	@Override
	public boolean DelComment(Komentaz k) {
		// TODO Auto-generated method stub
		fileDAO.DelComment(k);
		return false;
	}
	@Override
	public File GetFileByLocation(String Location, User u) 
	{
		// TODO Auto-generated method stub
		return fileDAO.GetFileByLocation(Location, u);
	}

}
