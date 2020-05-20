import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportData {

	static DatabaseConnection dbService;
	
	public static void main(String[] args) throws IOException, EncryptedDocumentException, NumberFormatException, SQLException {
		DatabaseConnection dbService;
		System.out.print("Import Excel Data ------>\n");
		dbService = new DatabaseConnection("golem.csse.rose-hulman.edu", "RestaurantSelection");
		dbService.connect("haoy1", "Horryno1");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("Import from Excel\n" + "1: Insert MenuItem\n" + "2: Insert Restaurant\n"
					+ "3: Insert users\n" + "4: Insert FoodRate\n" + "5: Insert Order Item\n" 
					+ "6: Clear all tables\n"+"7: Exit\n");
			String type = scanner.nextLine();
			switch (type) {
			case "1":
				importMenu();
				System.out.println("something");
				break;
			case "2":
				importRes();
				break;
			case "3":
				importUser();
				break;
			case "4":
				insertFoodRate();
				break;
			case "5":
				insertOrderItem();
				break;
			case "6":
				ClearAllTable();
				break;
			case "7":
				System.out.println("Exit!");
				dbService.closeConnection();
				System.exit(0);
				break;
			}
		}

	}
		

	private static void insertOrderItem() {
//		System.out.println("Enter Excel data: ");
//		Scanner input = new Scanner(System.in);
//		String excelName = input.nextLine();
//
//		FileInputStream fis = new FileInputStream(excelName);
//		XSSFWorkbook wb = (XSSFWorkbook) WorkbookFactory.create(fis);
//		XSSFSheet sh = wb.getSheet("Sheet1");
//		ArrayList<Object> list = new ArrayList<>();
//		ImportDataService ds = new ImportDataService(dbService);
//
//		// Iterate through each rows one by one
//		Iterator<Row> rowIterator = sh.iterator();
//		while (rowIterator.hasNext()) {
//			Row row = rowIterator.next();
//			if (row.getRowNum() == 0) {
//				continue; // just skip the rows if row number is 0 or 1
//			} else {
//				Iterator<Cell> cellIterator = row.cellIterator();
//				while (cellIterator.hasNext()) {
//					Cell cell = cellIterator.next();
//					list.add(cell.toString());
//				}
//				ds.InsertOrderItem(list.get(0), list.get(1), (int) Double.parseDouble((list.get(2))));
//				list = new ArrayList<>();
//			}
//		}
//		System.out.println("Import Complete");
	}


	private static void insertFoodRate() throws IOException, SQLException {
		String excelFilePath = "foodrate.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheet("Sheet1");
		ImportDataService is = new ImportDataService(dbService);
		ArrayList<String> list = new ArrayList<>();
		Iterator<Row> rowIterator = firstSheet.iterator();
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() == 0) {
				continue; // just skip the rows if row number is 0 or 1
			} else {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					list.add(cell.toString());
				}
				is.InsertFoodRate(list.get(0),list.get(1), list.get(2),list.get(3));
				list = new ArrayList<>();
			}
		}
		System.out.println("Import Complete");
	}


	private static void importUser()throws IOException, NumberFormatException, SQLException  {
		// TODO Auto-generated method stub
		String excelFilePath = "user.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheet("Sheet1");
		ImportDataService is = new ImportDataService(dbService);
		ArrayList<String> list = new ArrayList<>();
		Iterator<Row> rowIterator = firstSheet.iterator();
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() == 0) {
				continue; // just skip the rows if row number is 0 or 1
			} else {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					list.add(cell.toString());
				}
				is.InsertUser(list.get(0),list.get(1));
				list = new ArrayList<>();
			}
		}
		System.out.println("Import Complete");
	}


	private static void ClearAllTable()
			throws EncryptedDocumentException, IOException, NumberFormatException, SQLException {
		ImportDataService ds = new ImportDataService(dbService);
		ds.ClearALL();


		System.out.println("Clear Complete");
	}


	private static void importMenu() throws IOException, NumberFormatException, SQLException {
		String excelFilePath = "menu.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheet("Sheet1");
		ImportDataService is = new ImportDataService(dbService);
		ArrayList<String> list = new ArrayList<>();
		Iterator<Row> rowIterator = firstSheet.iterator();
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getRowNum() == 0) {
				continue; // just skip the rows if row number is 0 or 1
			} else {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					list.add(cell.toString());
				}
				is.InsertMenu(list.get(0),list.get(1), list.get(2));
				list = new ArrayList<>();
			}
		}
		System.out.println("Import Complete");
	}

	private static void importRes() throws IOException {
		// Auto-generated method stub
		String excelFilePath = "restaurant.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		ImportDataService is = new ImportDataService(dbService);
		ArrayList<String> list = new ArrayList<>();
		Iterator<Row> rowIterator = firstSheet.iterator();
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					list.add(cell.toString());
					
				}
				is.InsertRestaurant(list.get(0),list.get(1), list.get(2),list.get(3), list.get(4),list.get(5));
				list = new ArrayList<>();
			
		}
		System.out.println("Import Complete");
	}

}
