import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportData {

	static DatabaseConnection dbService;
	static Connection c;

	public static void main(String[] args)
			throws IOException, EncryptedDocumentException, NumberFormatException, SQLException {

		System.out.print("Import Excel Data ------>\n");
		dbService = new DatabaseConnection("golem.csse.rose-hulman.edu", "RestaurantSelection");
		dbService.connect("haoy1", "Horryno1");
		c = dbService.getConnection();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("1: Insert MenuItem\n" + "2: Insert Restaurant\n" + "3: Insert users\n"
					+ "4: Insert FoodRate\n" + "5: Insert Order Item\n" + "6: Insert Restaurant rate\n"
					+ "7: Insert Administrator\n" + "8: Clear all tables\n" + "9: import all tables\n" + "10: Exit\n");
			String type = scanner.nextLine();
			switch (type) {
			case "1":
				importMenu();
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
				insertRestaurantRate();
				break;
			case "7":
				insertAdministrator();
				break;
			case "8":
				ClearAllTable();
				break;
			case "9":
				importRes();
				importMenu();
				importUser();
				insertOrderItem();
				insertFoodRate();
				insertRestaurantRate();
				insertAdministrator();
				System.out.println("Finish importing all the tables");
				break;
			case "10":
				System.out.println("Exit!");
				dbService.closeConnection();
				System.exit(0);
				break;
			}
		}

	}

	private static void insertAdministrator() throws IOException, SQLException {
		String excelFilePath = "C:\\Users\\chenx11\\Desktop\\ad.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheet("Sheet1");
		ImportDataService is = new ImportDataService(c);
		ArrayList<String> list = new ArrayList<>();
		Iterator<Row> rowIterator = firstSheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				list.add(cell.toString());
			}
			System.out.println("ready to import");
			is.InsertAd(list.get(0), list.get(1));
			list = new ArrayList<>();
		}
		System.out.println("Import administrator Complete");
	}

	private static void insertRestaurantRate() throws IOException, SQLException {
		String excelFilePath = "C:\\Users\\chenx11\\Desktop\\resRate.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheet("Sheet1");
		ImportDataService is = new ImportDataService(c);
		ArrayList<String> list = new ArrayList<>();
		Iterator<Row> rowIterator = firstSheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				list.add(cell.toString());
			}

			is.InsertResRate(list.get(0), list.get(1), list.get(2), list.get(3));
			list = new ArrayList<>();
		}
		System.out.println("Import restaurant rate Complete");
	}

	private static void insertOrderItem() throws IOException, SQLException {
		String excelFilePath = "C:\\Users\\chenx11\\Desktop\\order.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheet("Sheet1");
		ImportDataService is = new ImportDataService(c);
		ArrayList<String> list = new ArrayList<>();
		Iterator<Row> rowIterator = firstSheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				list.add(cell.toString());
			}

			is.InsertOrder(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
			list = new ArrayList<>();
		}
		System.out.println("Import order item Complete");
	}

	private static void insertFoodRate() throws IOException, SQLException {
		String excelFilePath = "C:\\Users\\chenx11\\Desktop\\foodRate.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheet("Sheet1");
		ImportDataService is = new ImportDataService(c);
		ArrayList<String> list = new ArrayList<>();
		Iterator<Row> rowIterator = firstSheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				list.add(cell.toString());
			}

			is.InsertFoodRate(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4));
			list = new ArrayList<>();
		}
		System.out.println("Import food rate Complete");
	}

	private static void importUser() throws IOException, NumberFormatException, SQLException {
		// TODO Auto-generated method stub
		String excelFilePath = "C:\\Users\\chenx11\\Desktop\\user.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheet("Sheet1");
		ImportDataService is = new ImportDataService(c);
		ArrayList<String> list = new ArrayList<>();
		Iterator<Row> rowIterator = firstSheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String data = new DataFormatter().formatCellValue(cell);
				list.add(data);
			}
			System.out.println("ready to import");
			is.InsertUser(list.get(0), list.get(1));
			list = new ArrayList<>();
		}
		System.out.println("Import user Complete");
	}

	private static void ClearAllTable()
			throws EncryptedDocumentException, IOException, NumberFormatException, SQLException {
		ImportDataService ds = new ImportDataService(c);
		ds.ClearALL();

		System.out.println("Clear Complete");
	}

	private static void importMenu() throws IOException, NumberFormatException, SQLException {
		String excelFilePath = "C:\\Users\\chenx11\\Desktop\\menu.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheet("Sheet1");
		ImportDataService is = new ImportDataService(c);
		ArrayList<String> list = new ArrayList<>();
		Iterator<Row> rowIterator = firstSheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				
				list.add(cell.toString());
			}
			is.InsertMenu(list.get(0), list.get(1), list.get(2));
			list = new ArrayList<>();
		}
		System.out.println("Import menu Complete");
	}

	private static void importRes() throws IOException {
		// Auto-generated method stub
		String excelFilePath = "C:\\Users\\chenx11\\Desktop\\restaurant.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);
		@SuppressWarnings("resource")
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		ImportDataService is = new ImportDataService(c);
		ArrayList<String> list = new ArrayList<>();
		Iterator<Row> rowIterator = firstSheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				list.add(cell.getStringCellValue());
				
			}
			System.out.println(list.get(2));
			is.InsertRestaurant(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5));
			list = new ArrayList<>();

		}
		System.out.println("Import restaurant Complete");
	}

}
