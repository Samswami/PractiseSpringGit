package com.sam.org.utilty;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileUtility {

	/**
	 * Delete an existing file
	 * 
	 * @param fileName
	 *            - file to be deleted
	 * @throws Exception
	 */
	public void deleteFile(String fileName) throws Exception {
		File file = new File(fileName);
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("Successfully delete file " + fileName);
				Thread.sleep(1000);
			} else {
				System.out.println("Cannot delete file " + fileName);
			}
		}
	}
	
	
	/*public String readFile(String fileName){
		byte[] buffer = new byte[(int) new File(fileName).length()];
		BufferedInputStream f = null;
		String fileValue = null;
		try {
			f = new BufferedInputStream(new FileInputStream(fileName));
			f.read(buffer);
			fileValue = new String(buffer);
		} catch (FileNotFoundException e) {
			fileValue = new String(e.getMessage());
		} catch (IOException e) {
			fileValue = new String(e.getMessage());
		} finally {
			if (f != null)
				try {
					f.close();
				} catch (IOException ignored) {
					fileValue = new String(ignored.getMessage());
				}
		}
		return fileValue;
	}*/
	
	public String readFile(String fileName) throws IOException{
		byte[] buffer = new byte[(int) new File(fileName).length()];
		BufferedInputStream f = null;
		String fileValue = null;
			f = new BufferedInputStream(new FileInputStream(fileName));
			f.read(buffer);
			fileValue = new String(buffer);
			if (f != null)
				try {
					f.close();
				} catch (IOException ignored) {
					fileValue = new String(ignored.getMessage());
				}
		return fileValue;
	}
	
	public int deleteInnerFiles(String folderPath){
		File innerFolderToDelete = new File(folderPath);
		File[] listOfFiles = innerFolderToDelete.listFiles();
		int totalNoOfFiles = listOfFiles.length;
		for (int i = 0; i < listOfFiles.length; i++) {
			if(listOfFiles[i].exists()) listOfFiles[i].delete();
		}
		return totalNoOfFiles;
	}
	
}
