package org.zut.dyskService;

public class FileServiceImpl implements FileService 
{
	private String UserBasicDirPath;
	// Zmmiena BasicPath z basic.properties (Patrz servlet-context.xml)
	public void setUserBasicDirPath(String userBasicDirPath) {
		UserBasicDirPath = userBasicDirPath;
	}

}
