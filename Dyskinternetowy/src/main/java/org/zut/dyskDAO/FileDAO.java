package org.zut.dyskDAO;

import org.zut.dyskDomain.User;
import org.zut.dyskDomain.File;

import java.util.List;

public interface FileDAO {
	public List<File> getFiles(User user, String location);
	public File getFile(User user, int id, String location);
	public boolean deleteFile(User user, int id, String location);
	public boolean editFileInfo(User user, int id);
	public boolean publishFile(User user, int id, String location);
	public boolean addFile(User user, File file);
}
