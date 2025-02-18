package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVFileUtility {

	// Write data to a CSV file
	public void writeToCSV(String filePath, List<String[]> data) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			for (String[] row : data) {
				writer.write(String.join(",", row));
				writer.newLine();
			}
			System.out.println("Data written to CSV file successfully.");
		} catch (IOException e) {
			System.out.println("An error occurred while writing to the CSV file: " + e.getMessage());
		}
	}

	// Read data from a CSV file
	public List<String[]> readFromCSV(String filePath) {
		List<String[]> data = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] row = line.split(",");
				data.add(row);
			}
			System.out.println("Data read from CSV file successfully.");
		} catch (IOException e) {
			System.out.println("An error occurred while reading from the CSV file: " + e.getMessage());
		}
		return data;
	}
}

// To use in main method

/*public static void main(String[] args) {
    CSVFileUtility csvUtil = new CSVFileUtility();

    // Sample data to write to CSV
    List<String[]> dataToWrite = new ArrayList<>();
    dataToWrite.add(new String[]{"Name", "Age", "Location"});
    dataToWrite.add(new String[]{"John Doe", "30", "New York"});
    dataToWrite.add(new String[]{"Jane Smith", "25", "Los Angeles"});
    dataToWrite.add(new String[]{"Mike Johnson", "40", "Chicago"});

    String filePath = "C:\\Users\\YourUsername\\Desktop\\sample.csv";

    // Write data to CSV
    csvUtil.writeToCSV(filePath, dataToWrite);

    // Read data from CSV
    List<String[]> dataRead = csvUtil.readFromCSV(filePath);

    // Print the data read from CSV
    for (String[] row : dataRead) {
        System.out.println(String.join(", ", row));
    }
}*/
