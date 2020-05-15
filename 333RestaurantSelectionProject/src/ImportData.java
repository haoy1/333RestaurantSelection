import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
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
	 ImportData() throws IOException{
		 importRes();
		 importMenu();
		
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
	try {
		stm = c.prepareCall("call [dbo].[CreateMenuItem](?,?,?)");
		 while (rowIterator.hasNext()) {
               Row nextRow = rowIterator.next();
               Iterator<Cell> cellIterator = nextRow.cellIterator();

               while (cellIterator.hasNext()) {
                   Cell nextCell = cellIterator.next();

                   int columnIndex = nextCell.getColumnIndex();

                   switch (columnIndex) {
                   case 0:
                       String name = nextCell.getStringCellValue();
                       stm.setString(1, name);
                      // System.out.println(name);
                       break;
                   case 1:
                   	 double price = nextCell.getNumericCellValue();
	                     stm.setFloat(2, (float) price);
	                     break;
                   case 2:
                   	 int resID = (int) nextCell.getNumericCellValue();
	                     stm.setInt(3,resID);
	                     break;
	                     
//                   case 3:
//                       String cat = nextCell.getStringCellValue();
//                       stm.setString(4, cat);
//                       break;
//                   case 4:
//                   	 int num = (int) nextCell.getNumericCellValue();
//	                     stm.setInt(5, num);
//	                     break;
//                   case 5:
//                   	String add = nextCell.getStringCellValue();
//	                     stm.setString(6, add);
//	                     break;
                   }

               }
		 }
		 System.out.println("Import menu data successfully");
	
	} catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Failed to connect");
		e.printStackTrace();
	}
		
	}
//		   System.out.print("imported data successfully\n");
//	        String excelFilePath = "r.xlsx";
//	        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
//	         
//	        Workbook workbook = new XSSFWorkbook(inputStream);
//	        Sheet firstSheet = workbook.getSheetAt(0);
//	        Iterator<Row> iterator = firstSheet.iterator();
//	         
//	        while (iterator.hasNext()) {
//	            Row nextRow = iterator.next();
//	            Iterator<Cell> cellIterator = nextRow.cellIterator();
//	            int i = 1;
//	            String res = "";
//	            String add = "";
//	            String num = "";
//	            String cat = "";
//	            String op = "";
//	            String ct = "";
//	            while (cellIterator.hasNext()) {
//	                Cell cell = cellIterator.next();
//	                if(i==1) {
//	                	res = cell.getStringCellValue();
//	                	 System.out.print(res);
//	                	i++;
//	                }else if(i==2) {
//	                	add = cell.getStringCellValue();
//	                	System.out.print(add);
//	                	i++;
//	                }else if(i==3) {
//	                	num = cell.getStringCellValue();
//	                	System.out.print(num);
//	                	i++;
//	                }else if (i==4) {
//	                	cat =  cell.getStringCellValue();
//	                	System.out.print(cat);
//	                	i++;
//	                }else if (i==5){
//	                	op =  cell.getStringCellValue();
//	                	System.out.print(op);
//	                	i++;
//	                }else {
//	                	ct =  cell.getStringCellValue();
//	                	System.out.print(ct);
//	                }
//	              
//	         
//	                System.out.print(" - ");
//	            }
//	            System.out.println();
//	        }
//	         
//	        workbook.close();
//	        inputStream.close();
	private void importRes() throws IOException {
		// TODO Auto-generated method stub
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
		try {
			stm = c.prepareCall("call [chex11].[addResTest](?,?,?,?)");
			 while (rowIterator.hasNext()) {
	                Row nextRow = rowIterator.next();
	                Iterator<Cell> cellIterator = nextRow.cellIterator();
	 
	                while (cellIterator.hasNext()) {
	                    Cell nextCell = cellIterator.next();
	 
	                    int columnIndex = nextCell.getColumnIndex();
	 
	                    switch (columnIndex) {
	                    case 0:
	                        String name = nextCell.getStringCellValue();
	                        stm.setString(1, name);
	                       // System.out.println(name);
	                        break;
	                    case 1:
	                    	 String add = nextCell.getStringCellValue();
		                     stm.setString(2, add);
		                     break;
	                    case 2:
	                    	 int num = (int) nextCell.getNumericCellValue();
		                     stm.setInt(3,num);
		                     break;
		                     
	                    case 3:
	                        String cat = nextCell.getStringCellValue();
	                        stm.setString(4, cat);
	                        break;
//	                    case 4:
//	                    	 int num = (int) nextCell.getNumericCellValue();
//		                     stm.setInt(5, num);
//		                     break;
//	                    case 5:
//	                    	String add = nextCell.getStringCellValue();
//		                     stm.setString(6, add);
//		                     break;
	                    }
	 
	                }
			 }
			 System.out.println("Import restaurant data successfully");
		
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Failed to connect");
			e.printStackTrace();
		}
	}

	

}
