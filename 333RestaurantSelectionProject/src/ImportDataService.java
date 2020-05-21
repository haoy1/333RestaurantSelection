
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Types;

import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class ImportDataService {

	DatabaseConnection dbService = null;
	static final Random RANDOM = new SecureRandom();
	static final Base64.Encoder enc = Base64.getEncoder();
	Connection c;

	public ImportDataService(Connection con) {
		c = con;
	}

	public void InsertMenu(String name, String price, String id) throws SQLException {

		CallableStatement stm = null;
		try {
			stm = c.prepareCall("{?= call [chex11].[import_Menu](?,?,?)}");
			stm.registerOutParameter(1, Types.INTEGER);
			stm.setString(2, name);
			stm.setString(3, price);
			stm.setString(4, id);
			stm.execute();
			System.out.println(name + " item import complete");
			int retVal = stm.getInt(1);
			if (retVal == 1) {
				System.out.println("ERROR: Price must be positive.");
				return;
			} else if (retVal == 2) {
				System.out.println("ERROR: Restaurant ID is invalid." + id);
				return;
			} else if (retVal == 3) {
				System.out.println("ERROR: Menue Item already exist.");
				return;
			}
			return;
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return;

	}

	public void ClearALL() throws SQLException {

		CallableStatement cs = null;
		try {
			cs = c.prepareCall("{call [chex11].[clear_table]}");
			cs.execute();
			return;
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return;
	}

	public void InsertRestaurant(String name, String addr, String num, String cat, String opent, String closet) {
		CallableStatement stm = null;
		try {
			stm = c.prepareCall("{?= call [chex11].[addResTest](?,?,?,?,?,?)}");
			stm.registerOutParameter(1, Types.INTEGER);
			stm.setString(2, name);
			stm.setString(3, addr);
			stm.setString(4, num);
			stm.setString(5, cat);
			stm.setString(6, opent);
			stm.setString(7, closet);
			stm.execute();
			System.out.println("Import " + name + " Restaurant Complete");
			int retVal = stm.getInt(1);
			if (retVal == 1) {
				System.out.println("ERROR: Restaurant name cannot be null or empty.");
				return;
			} else if (retVal == 2) {
				System.out.println("ERROR: Restaurant name already exists.");
				return;
			}
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;

	}

	public void InsertFoodRate(String user, String star, String review, String item, String res) {

		CallableStatement stm = null;
		try {
			stm = c.prepareCall("{call [chex11].[import_food_rate](?,?,?,?,?)}");
			// stm.registerOutParameter(1, Types.INTEGER);
			stm.setString(1, user);
			stm.setFloat(2, (int) Double.parseDouble(star));
			stm.setString(3, review);
			stm.setString(4, item);
			stm.setString(5, res);
			stm.execute();
			System.out.println("Import food rate for " + item + " Complete");
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;

	}

	public void InsertUser(String name, String pass) {
		CallableStatement stm = null;
		byte[] salt = getNewSalt();
		try {
			stm = c.prepareCall("{?=call dbo.addUser(?,?,?)}");
			stm.registerOutParameter(1, Types.INTEGER);
			stm.setString(2, name);
			stm.setString(3, hashPassword(salt, pass));
			stm.setBytes(4, salt);
			stm.execute();
			System.out.println("Import user " + name + " Complete");
			if (stm.getInt(1) == 1)
				System.out.println("Username already existed");
			else if (name.isEmpty() || pass.isEmpty())
				System.out.println("Username and Password can't be empty");
			else
				System.out.println("Successfully Registered");
			return;
		} catch (SQLException e) {
			System.out.println("Failed ");
			e.printStackTrace();
		}
		return;
	}

	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public void InsertOrder(String item, String res, String user, String time, String qty) {
		CallableStatement stm = null;
		try {
			stm = c.prepareCall("{call chex11.insert_order(?,?,?,?,?)}");
			stm.setString(1, item);
			stm.setString(2, res);
			stm.setString(3, user);
			stm.setString(4, time);
			stm.setInt(5, (int) Double.parseDouble(qty));
			stm.execute();
			System.out.println("Import one order Complete");
			return;
		} catch (SQLException e) {
			System.out.println("Failed");
			e.printStackTrace();
		}
		return;
	}

	public void InsertResRate(String user, String res, String star, String review) {
		CallableStatement stm = null;
		try {
			stm = c.prepareCall("{call [chex11].[insert_RestaurantRate](?,?,?,?)}");
			stm.setString(1, user);
			stm.setString(2, res);
			stm.setInt(3, (int) Double.parseDouble(star));
			stm.setString(4, review);
			stm.execute();
			System.out.println("Import one restaurant rate Complete");
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;

	}

	public void InsertAd(String name, String pass) {
		CallableStatement stm = null;
		byte[] salt = getNewSalt();
		try {
			stm = c.prepareCall("{?=call [dbo].[CreateAdministrator](?,?,?)}");
			stm.registerOutParameter(1, Types.INTEGER);
			stm.setString(2, name);
			stm.setString(3, hashPassword(salt, pass));
			stm.setBytes(4, salt);
			stm.execute();
			System.out.println("Import one administrator Complete");
			if (stm.getInt(1) == 1)
				System.out.println("Username already existed");
			else if (name.isEmpty() || pass.isEmpty())
				System.out.println("Username and Password can't be empty");
			else
				System.out.println("Successfully Registered");
			return;
		} catch (SQLException e) {
			System.out.println("Failed ");
			e.printStackTrace();
		}
		return;
	}

}