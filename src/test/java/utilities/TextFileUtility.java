package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextFileUtility {
	// Create file
	public void createFile(String filePath) {
		File file = new File(filePath);
		try {
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			} else {
				System.out.println("Failed to create file at path: " + filePath);
			}
		} catch (IOException e) {
			System.out.println("An error occurred while creating the file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Write data in to file
	public void writeFile(String filePath, String data) {
		try {
			FileWriter writer = new FileWriter(filePath);
			writer.write(data);
			System.out.println("Data written to file: " + filePath);
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occurred while writing to the file: " + e.getMessage());
		}
	}

	// Read data from a file
	public void readFile(String filePath) {
		File file = new File(filePath);
		try {
			Scanner reader = new Scanner(file);
			while (reader.hasNext()) {
				String line = reader.nextLine();
				System.out.println(line);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + e.getMessage());
		}
	}

	// Rename file
	public void renameFolder(String oldFilePath, String newFilePath) {
		File oldFile = new File(oldFilePath);
		File newFile = new File(newFilePath);
		if (oldFile.exists()) {
			oldFile.renameTo(newFile);
			System.out.println("File renamed");
		} else {
			System.out.println("File can not be renamed");
		}
	}

	// Delete a file
	public void deleteFolder(String filePath) {
		File file = new File(filePath);
		if (file.delete()) {
			System.out.println("File deleted succesfully: " + file.getName());
		} else {
			System.out.println("Failde to delete the file.");
		}
	}
}
