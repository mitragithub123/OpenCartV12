package utilities;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLFileUtility {

	// Write data to an XML file
	public void writeToXML(String filePath, String rootElement, String[][] data) {
		try {
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();

			// Create root element
			Element root = document.createElement(rootElement);
			document.appendChild(root);

			// Create child elements
			for (String[] row : data) {
				Element item = document.createElement("Item");
				for (int i = 0; i < row.length; i++) {
					Element element = document.createElement("Element" + (i + 1));
					element.appendChild(document.createTextNode(row[i]));
					item.appendChild(element);
				}
				root.appendChild(item);
			}

			// Create and configure the transformer
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(filePath));

			// Write to XML file
			transformer.transform(domSource, streamResult);
			System.out.println("Data written to XML file successfully.");

		} catch (ParserConfigurationException | javax.xml.transform.TransformerException e) {
			System.out.println("An error occurred while writing to the XML file: " + e.getMessage());
		}
	}

	// Read data from an XML file
	public void readFromXML(String filePath) {
		try {
			File file = new File(filePath);
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);

			document.getDocumentElement().normalize();

			// Get all items
			NodeList nodeList = document.getElementsByTagName("Item");

			for (int i = 0; i < nodeList.getLength(); i++) {
				Element item = (Element) nodeList.item(i);
				NodeList childNodes = item.getChildNodes();

				// Print all elements
				for (int j = 0; j < childNodes.getLength(); j++) {
					if (childNodes.item(j).getNodeType() == 1) {
						System.out.print(childNodes.item(j).getTextContent() + " ");
					}
				}
				System.out.println();
			}
			System.out.println("Data read from XML file successfully.");

		} catch (Exception e) {
			System.out.println("An error occurred while reading from the XML file: " + e.getMessage());
		}
	}

}



//To use in main method
/*public static void main(String[] args) {
	XMLFileUtility xmlUtil = new XMLFileUtility();

	// Sample data to write to XML
	String[][] dataToWrite = { { "John Doe", "30", "New York" }, { "Jane Smith", "25", "Los Angeles" },
			{ "Mike Johnson", "40", "Chicago" } };

	String filePath = "D:\\sample.xml";
	String rootElement = "Users";

	// Write data to XML
	xmlUtil.writeToXML(filePath, rootElement, dataToWrite);

	// Read data from XML
	xmlUtil.readFromXML(filePath);
}*/











