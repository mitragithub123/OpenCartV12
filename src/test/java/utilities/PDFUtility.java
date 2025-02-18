package utilities;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class PDFUtility {

	// Method to write content to a PDF file
	public static void writeToPDF(String filePath, String content) {
		PDDocument document = new PDDocument();
		try {
			PDPage page = new PDPage();
			document.addPage(page);

			PDPageContentStream contentStream = new PDPageContentStream(document, page);

			contentStream.setFont(PDType1Font.HELVETICA, 12);
			contentStream.beginText();
			contentStream.setLeading(14.5f); // Set the line spacing

			// Set the starting position for the content
			contentStream.newLineAtOffset(25, 750);

			// Split content by line breaks and write each line
			for (String line : content.split("\n")) {
				contentStream.showText(line);
				contentStream.newLine();
			}

			contentStream.endText();
			contentStream.close();

			document.save(filePath);
			System.out.println("PDF created at: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				document.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// Method to read content from a PDF file
	public static String readFromPDF(String filePath) {
		StringBuilder content = new StringBuilder();
		try (PDDocument document = PDDocument.load(Files.newInputStream(Paths.get(filePath)))) {
			document.getPages().forEach(page -> {
				try {
					content.append(new PDFTextStripper().getText(document));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toString();
	}
}

//To use in main method
/*public static void main(String[] args) {
    String filePath = "D:\\example.pdf";
    String content = "This is a sample PDF content. \nAutomating PDF file handling using PDFBox in Java.";

    // Write to PDF
    System.out.println("Writing to PDF...");
    PDFUtility.writeToPDF(filePath, content);

    // Read from PDF
    System.out.println("\nReading from PDF...");
    String extractedText = PDFUtility.readFromPDF(filePath);

    // Print extracted text
    System.out.println("\nExtracted Text: ");
    System.out.println(extractedText);

}*/
