package utilities;

import java.io.File;

public class FolderUtility {
	// Create folder
	public void createFolder(String folderPath) {
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdir();
			System.out.println("Folder created in path: " + folderPath);
		} else {
			System.out.println("Folder already exists at path: " + folderPath);
		}
	}

	// Check if folder exists or not
	public boolean isFolderExists(String folderPath) {
		File folder = new File(folderPath);
		return folder.exists();
	}

	// Rename a folder
	public void renameFolder(String oldFolderPath, String newFolderPath) {
		File oldFolder = new File(oldFolderPath);
		File newFolder = new File(newFolderPath);
		if (oldFolder.exists()) {
			oldFolder.renameTo(newFolder);
			System.out.println("Folder renamed to path: " + newFolderPath);
		} else {
			System.out.println("Folder not found at path: " + oldFolderPath);
		}
	}

	// Delete a folder
	public void deleteFolder(String folderPath) {
		File folder = new File(folderPath);
		for (File file : folder.listFiles()) {
			file.delete();
		}
		folder.delete(); // Before deleting 1 folder, all files inside the folder need to be deleted.
		System.out.println("Folder deleted.");
	}

}
