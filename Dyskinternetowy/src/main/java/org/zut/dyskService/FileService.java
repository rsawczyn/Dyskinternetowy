package org.zut.dyskService;

import java.util.List;

import org.zut.dyskDomain.File;
import org.zut.dyskDomain.Komentaz;
import org.zut.dyskDomain.User;

public interface FileService {
	public List<File> getFiles(User user, String location);
	public File getFile(User user, int id, String location);
	public boolean deleteFile(User user, int id);
	public boolean editFileInfo(User user, int id, File file);
	public boolean publishFile(User user, int id, String location);	
	public boolean addFile(User user, File file);
	public List<File> getAllFilesForUser(int Id);
	public List<Komentaz>GetAllCommentForFile(int FileId);
	public boolean AddComment(Komentaz k);
	public boolean DelComment(Komentaz k);
	public File GetFileByLocation(String Location,User u);
}
 