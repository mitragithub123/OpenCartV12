package utilities;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;

public class DataProviders {
	// Login data provider using excel file
	// Parallel=true -> Test data will run parallellly not sequentially.
	// If parallel is used then run from .xml file
	// If all test data will run parallely then it may display unexpected test
	// result
	// So, use data-provider-thread-count="2" at suite level in xml file
	// 2 means 2 will be run parallel
	// indices = {0,1}: 1st 2 data will run else will skip
	@DataProvider(name = "LoginData", parallel = true, indices = { 0, 1 })
	public String[][] getData() throws IOException {
		String path = ".\\testData\\loginData.xlsx";
		ExcelUtility xlUtil = new ExcelUtility(path);
		int totalRows = xlUtil.getRowCount("Sheet1");
		int totalCols = xlUtil.getCellCount("Sheet1", 1);
		String loginData[][] = new String[totalRows][totalCols];
		for (int i = 1; i <= totalRows; i++) {
			for (int j = 0; j < totalCols; j++) {
				loginData[i - 1][j] = xlUtil.getCellData("Sheet1", i, j);
			}
		}
		return loginData;
	}

	// Login data provider using Hashed map
	@DataProvider(name = "LoginDataHashMap")
	public Object[][] getLoginData() {
		// Creating the login data maps directly within this method
		Map<String, String> loginDataMap1 = new HashMap<>();
		loginDataMap1.put("email", "andolasoft.user133@gmail.com");
		loginDataMap1.put("password", "mitra@1234"); // Valid

		Map<String, String> loginDataMap2 = new HashMap<>();
		loginDataMap2.put("email", "abc1@gmail.com");
		loginDataMap2.put("password", "mitra@1235"); // Invalid

		Map<String, String> loginDataMap3 = new HashMap<>();
		loginDataMap3.put("email", "abc2@gmail.com");
		loginDataMap3.put("password", "mitra@1236");// Invalid

		Map<String, String> loginDataMap4 = new HashMap<>();
		loginDataMap4.put("email", "abc3@gmail.com");
		loginDataMap4.put("password", "mitra@1237");// Invalid
		
		Map<String, String> loginDataMap5 = new HashMap<>();
		loginDataMap5.put("email", "");
		loginDataMap5.put("password", ""); // Invalid


		 return new Object[][] { 
	            { loginDataMap1 }, 
	            { loginDataMap2 }, 
	            { loginDataMap3 }, 
	            { loginDataMap4 }, 
	            { loginDataMap5 }
	        };
	}
	
	// Login data provider using json
	@DataProvider(name = "loginDataJson")
	public Object[][] getLoginDataUsingJson() throws IOException {
		List<HashMap<String, String>> data = JsonDataReader.getJsonDataToMap(".\\testData\\login.json");

		Object[][] testData = new Object[data.size()][];
		for (int i = 0; i < data.size(); i++) {
			testData[i] = new Object[] { data.get(i) };
		}
		return testData;
	}

}
