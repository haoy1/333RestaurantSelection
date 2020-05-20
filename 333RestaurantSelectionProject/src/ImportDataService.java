import java.io.FileInputStream;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ImportDataService {

	private DatabaseConnection dbService = null;

	public ImportDataService() {
	}

	public ImportDataService(DatabaseConnection dbService) {
		this.dbService = dbService;
	}

	public boolean InsertMenu(String name, String price, String id) throws SQLException {
		this.dbService = new DatabaseConnection("golem.csse.rose-hulman.edu", "RestaurantSelection");
     	dbService.connect("haoy1", "Horryno1");
     	Connection c = this.dbService.getConnection();
 		CallableStatement stm = null;
		try {
			stm = c.prepareCall("{?= call [chex11].[import_Menu](?,?,?)}");
			stm.registerOutParameter(1, Types.INTEGER);
			stm.setString(2, name);
			stm.setString(3, price);
			stm.setString(4, id);
			stm.execute();
			System.out.println("Import xx Complete");
			int retVal = stm.getInt(1);
			if (retVal == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Price must be positive.");
				return false;
			} else if (retVal == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Restaurant ID is invalid.");
				return false;
			} else if (retVal == 3) {
				JOptionPane.showMessageDialog(null, "ERROR: Menue Item already exist.");
				return false;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return true;

	}

	

	public boolean ClearALL() throws SQLException {
		Connection con = this.dbService.getConnection();
		CallableStatement cs = null;
		try {
			cs = con.prepareCall("{call Clear_Table}");
			cs.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return true;
	}

	public void InsertOrderItem(Object object, Object object2, int parseDouble) {
		// TODO Auto-generated method stub
		
	}

	public void InsertRestaurant(String name, String addr, String string, String cat, String opent, String closet) {
		this.dbService = new DatabaseConnection("golem.csse.rose-hulman.edu", "RestaurantSelection");
     	dbService.connect("haoy1", "Horryno1");
     	Connection c = this.dbService.getConnection();
 		CallableStatement stm = null;
		try {
			stm = c.prepareCall("{?= call [chex11].[addResTest](?,?,?,?,?,?)}");
			stm.registerOutParameter(1, Types.INTEGER);
			stm.setString(2, name);
			stm.setString(3, addr);
			stm.setString(4, string);
			stm.setString(5, cat);
			stm.setString(6, opent);
			stm.setString(7, closet);
			stm.execute();
			System.out.println("Import xx Complete");
			int retVal = stm.getInt(1);
			if (retVal == 1) {
				JOptionPane.showMessageDialog(null, "ERROR: Restaurant name cannot be null or empty.");
				return;
			} else if (retVal == 2) {
				JOptionPane.showMessageDialog(null, "ERROR: Restaurant name already exists.");
				return;
			}
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;

	}

	public void InsertFoodRate(String star, String review, String res, String user) {
		// Auto-generated method stub
		this.dbService = new DatabaseConnection("golem.csse.rose-hulman.edu", "RestaurantSelection");
     	dbService.connect("haoy1", "Horryno1");
     	Connection c = this.dbService.getConnection();
 		CallableStatement stm = null;
		try {
			stm = c.prepareCall("{ call [chex11].[import_food_rate](?,?,?,?)}");
			//stm.registerOutParameter(1, Types.INTEGER);
			stm.setString(1, star);
			stm.setString(2, review);
			stm.setString(3, res);
			stm.setString(4, user);
			stm.execute();
			System.out.println("Import xx Complete");
			
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;

	}

	public void InsertUser(String name, String pass) {
		Register_Form rf = new Register_Form(name, pass);
		rf.addUser(name, pass);
		
	}

}