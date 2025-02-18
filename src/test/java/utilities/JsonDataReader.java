package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDataReader {
	public static List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {

		// Read json content from file
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		/* String to hash map Jackson bind */
		// Initialize ObjectMapper
		ObjectMapper mapper = new ObjectMapper();

		// Read JSON content into a List<HashMap<String, String>> using TypeReference
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}
}
