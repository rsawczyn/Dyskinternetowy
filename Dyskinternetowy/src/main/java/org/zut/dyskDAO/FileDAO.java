package org.zut.dyskDAO;

import org.zut.dyskDomain.User;
import org.zut.dyskDomain.File;

import java.util.List;

public interface FileDAO {
	public List<File> getFiles(User user, String location);
	public File getFile(User user, int fileId, String location);
	public boolean deleteFile(User user, int fileId);
	public boolean editFileInfo(User user, int fileId, File file);
	public boolean publishFile(User user, int fileId, String location);
	public boolean addFile(User user, File file);
}
