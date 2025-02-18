package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {
	public FileInputStream fi;
	public FileOutputStream fo;
	public XSSFWorkbook workBook;
	public XSSFSheet excelSheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path;

	public ExcelUtility(String path) {
		this.path = path;
	}

	public int getRowCount(String sheetName) throws IOException {
		fi = new FileInputStream(path);
		workBook = new XSSFWorkbook(fi);
		excelSheet = workBook.getSheet(sheetName);
		int rowCount = excelSheet.getLastRowNum();
		workBook.close();
		fi.close();
		return rowCount;
	}

	public int getCellCount(String sheetName, int rowNum) throws IOException {
		fi = new FileInputStream(path);
		workBook = new XSSFWorkbook(fi);
		excelSheet = workBook.getSheet(sheetName);
		row = excelSheet.getRow(rowNum);
		int cellCount = row.getLastCellNum();
		workBook.close();
		fi.close();
		return cellCount;
	}

	public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
		fi = new FileInputStream(path);
		workBook = new XSSFWorkbook(fi);
		excelSheet = workBook.getSheet(sheetName);
		row = excelSheet.getRow(rowNum);
		cell = row.getCell(colNum);

		DataFormatter formatter = new DataFormatter();
		String data;

		try {
			// data=cell.toString();
			data = formatter.formatCellValue(cell);// Returns the formatted value of cell as a string
		} catch (Exception e) {
			data = "";
		}
		workBook.close();
		fi.close();
		return data;
	}

	public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
		File xlfile = new File(path);
		if (!xlfile.exists()) { // If file not exists then create new file
			workBook = new XSSFWorkbook();
			fo = new FileOutputStream(path);
			workBook.write(fo);
			workBook.close();
		}

		fi = new FileInputStream(path);
		workBook = new XSSFWorkbook(fi);

		if (workBook.getSheetIndex(sheetName) == -1) { // If sheet not exists then create new Sheet
			workBook.createSheet(sheetName);
		}
		excelSheet = workBook.getSheet(sheetName);

		if (excelSheet.getRow(rowNum) == null) { // If row not exists then create new Row
			excelSheet.createRow(rowNum);
		}
		row = excelSheet.getRow(rowNum);
		cell = row.createCell(colNum);
		cell.setCellValue(data);
		fo = new FileOutputStream(path);
		workBook.write(fo);
		workBook.close();
		fi.close();
		fo.close();
	}

	public void fillGreenColor(String sheetName, int rowNum, int colNum) throws IOException {
		fi = new FileInputStream(path);
		workBook = new XSSFWorkbook(fi);
		excelSheet = workBook.getSheet(sheetName);
		row = excelSheet.getRow(rowNum);
		cell = row.getCell(colNum);

		style = workBook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		fo = new FileOutputStream(path);
		workBook.write(fo);
		workBook.close();
		fi.close();
		fo.close();
	}

	public void fillRedColor(String sheetName, int rownum, int column) throws IOException {
		fi = new FileInputStream(path);
		workBook = new XSSFWorkbook(fi);
		excelSheet = workBook.getSheet(sheetName);
		row = excelSheet.getRow(rownum);
		cell = row.getCell(column);

		style = workBook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		fo = new FileOutputStream(path);
		workBook.write(fo);
		workBook.close();
		fi.close();
		fo.close();
	}

}
