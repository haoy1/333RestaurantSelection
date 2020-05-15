import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportData {

	public static void main(String[] args) throws IOException {
		new ImportData();
	}

	ImportData() throws IOException {
		importRes();
		importMenu();
		System.out.println("Finish importing data");
	}

	private void importMenu() throws IOException {
		DatabaseConnection dbService;
		String excelFilePath = "dishes.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = firstSheet.iterator();
		dbService = new DatabaseConnection("golem.csse.rose-hulman.edu", "RestaurantSelection");
		dbService.connect("haoy1", "Horryno1");
		Connection c = dbService.getConnection();
		CallableStatement stm = null;
		String err= "";
		try {
			while (rowIterator.hasNext()) {
				stm = c.prepareCall("{call dbo.CreateMenuItem(?,?,?)}");
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();

					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {
					case 0:
						String name = nextCell.getStringCellValue();
						err = name;
						stm.setString(1, name);
						// System.out.println(name);
						break;
					case 1:
						double price = nextCell.getNumericCellValue();
						stm.setFloat(2, (float) price);
						break;
					case 2:
						int resID = (int) nextCell.getNumericCellValue();
						stm.setInt(3, resID);
						break;

					}

				}
				//stm.execute();
				//if(stm.getInt(0)==2){
					System.out.print(err+" ");
					System.out.println("already imported");
				//}
			}
			
		
			System.out.println("Import menu data successfully");

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Failed to connect");
			e.printStackTrace();
		}

	}

	private void importRes() throws IOException {
		// Auto-generated method stub
		DatabaseConnection dbService;
		String excelFilePath = "r.xlsx";
		FileInputStream inputStream = new FileInputStream(excelFilePath);

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = firstSheet.iterator();
		dbService = new DatabaseConnection("golem.csse.rose-hulman.edu", "RestaurantSelection");
		dbService.connect("haoy1", "Horryno1");
		Connection c = dbService.getConnection();
		CallableStatement stm = null;
		String err = "";
		try {
			stm = c.prepareCall("{call chex11.addResTest(?,?,?,?,?,?)}");
			while (rowIterator.hasNext()) {
				Row nextRow = rowIterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();

				while (cellIterator.hasNext()) {
					Cell nextCell = cellIterator.next();

					int columnIndex = nextCell.getColumnIndex();

					switch (columnIndex) {
					case 0:
						String name = nextCell.getStringCellValue();
						err = name;
						stm.setString(1, name);
						// System.out.println(name);
						break;
					case 1:
						String add = nextCell.getStringCellValue();
						stm.setString(6, add);
						break;
					case 2:
						int num = (int) nextCell.getNumericCellValue();
						stm.setInt(5, num);
						break;

					case 3:
						String cat = nextCell.getStringCellValue();
						stm.setString(4, cat);
						break;
					case 4:
						//String op =  nextCell.getStringCellValue();
						stm.setString(2,"'11:00:00.0000'");
						break;
					case 5:
						//String ct = nextCell.getStringCellValue();
						stm.setString(3, "'23:00:00.0000'");
						break;
					}

				}
				//stm.execute();
				//if(stm.getInt(0)==2){
					System.out.print(err+" ");
					System.out.println("already imported");
				//}
			}
			System.out.println("Import restaurant data successfully");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Failed to connect");
			e.printStackTrace();
		}
	}

}
