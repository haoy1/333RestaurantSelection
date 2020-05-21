import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;  
 
public class SelectRestItem  
{  
	private DatabaseConnection dbService;
	private int selectedRestID;
     SelectRestItem(String username){
     	this.dbService = new DatabaseConnection("golem.csse.rose-hulman.edu", "RestaurantSelection");
     	dbService.connect("haoy1", "Horryno1");
        JFrame f= new JFrame();  
        final JLabel label = new JLabel();          
        label.setSize(500,100);
        label.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
        label.setText("Select restaurant");
        JButton b = new JButton("Select");  
        b.setBounds(300,150,80,30);  
        final DefaultListModel<String> l1 = new DefaultListModel<>();
        HashMap<String, Integer> map = new HashMap<>(); 
        Connection c = this.dbService.getConnection();
		CallableStatement stm = null;
		
			try {
				stm = c.prepareCall("{?=call haoy1.getAllRestaurants}");
				stm.registerOutParameter(1, Types.VARCHAR);
				stm.execute();
				ResultSet result = stm.executeQuery();
				while(result.next()) {
					l1.addElement(result.getString("Name"));
					map.put(result.getString("Name"), result.getInt("ID"));
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Failed to connect");
				e.printStackTrace();
			}
          final JList<String> list1 = new JList<>(l1);  
          list1.setBounds(100,100, 175,1075);  
          f.add(list1);  f.add(b); f.add(label);  
          f.setSize(450,1200);  
          f.setLayout(null);  
          f.setVisible(true); 
          
          JButton rate = new JButton("Rate");
          rate.setBounds(300, 200, 80, 30);
          rate.addActionListener(new ActionListener() {

      		@Override
      		public void actionPerformed(ActionEvent e) {
      			int restID = map.get(list1.getSelectedValue());   
      			Rating_Form rating_form = new Rating_Form(username, restID, list1.getSelectedValue(), true);
      			rating_form.setVisible(true);
      		} });
          
          JButton check = new JButton("Check");
          check.setBounds(300, 250, 80, 30);
          check.addActionListener(new ActionListener() {
      		@Override
      		public void actionPerformed(ActionEvent e) {
      			if(list1.getSelectedValuesList().size() != 1)
      				JOptionPane.showMessageDialog(null, "You have to check exactly 1 item each time.");
      			else {
      				int restID = map.get(list1.getSelectedValue());
      				checkReview(c, restID, true);
      			}
      				
      		} });
          
          JButton end = new JButton("Exit");
          end.setBounds(300, 300, 80, 30);
          end.addActionListener(new ActionListener() {

     		@Override
     		public void actionPerformed(ActionEvent e) {
     			System.exit(0);
     			
     		} });
          b.addActionListener(new ActionListener() {  
              public void actionPerformed(ActionEvent e) {   
                 if (list1.getSelectedIndex() != -1) {                       
                    int ID = map.get(list1.getSelectedValue());   
              
					showMenu(ID, c, username);
                    f.dispose();
                 }  
              }  
           });  
          f.add(end);
          f.add(check);
          f.add(rate);
     }  
     
public void showMenu(int ID, Connection c, String username) {
	CallableStatement stm = null;
	 JFrame f= new JFrame();  
     final JLabel label1 = new JLabel();
     final JLabel label2 = new JLabel();
     label1.setSize(500,100);
     label1.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
     label1.setText("Select Food");
     label2.setSize(500,100);
     label2.setLocation(0,35);
     label2.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N
     label2.setText("Hold ctrl to select more");
     final DefaultListModel<String> l1 = new DefaultListModel<>();
     
     HashMap<String, Integer> map = new HashMap<>(); 
     JButton b = new JButton("Select");  
     b.setBounds(300,150,80,30);  
	try {
		stm = c.prepareCall("{?=call haoy1.getItemNameByRestID(?)}");
		stm.registerOutParameter(1, Types.VARCHAR);
		stm.setInt(2, ID);
		stm.execute();
		ResultSet result = stm.executeQuery();
		while(result.next()) {
			l1.addElement(result.getString("Name"));
			map.put(result.getString("Name"), result.getInt("ID"));
		}
	} catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Failed to connect");
		e.printStackTrace();
	}
	final JList<String> list1 = new JList<>(l1);
	ArrayList <String> list1All = new ArrayList<String>();
    for (int i = 0; i < list1.getModel().getSize(); i++) {
         list1All.add(String.valueOf(list1.getModel().getElementAt(i)));
    }
	 HashMap<String, Integer> addMap = new HashMap<>();
	 for(String item: list1All) {
			addMap.put(item, 0);
	 }
	    JButton addButton = new JButton("Add");  
	    addButton.setBounds(300,100,80,30);  
	    addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addMap.put(list1.getSelectedValue(), addMap.get(list1.getSelectedValue()+1));
			} });
		
	    JButton rate = new JButton("Rate");
	    rate.setBounds(300, 200, 80, 30);
	    rate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> items = new ArrayList<String>();
	        	items.addAll(list1.getSelectedValuesList());
	        	if(items.size() != 1)
	        		JOptionPane.showMessageDialog(null, "You have to rate exactly 1 item each time.");
	        	else {
		        	int itemID = map.get(items.get(0));
					Rating_Form rating_form = new Rating_Form(username, itemID, list1.getSelectedValue(), false);
					rating_form.setVisible(true);
	        	}
			} });
    list1.setBounds(100,100, 175,275);  
    f.add(list1);  f.add(b); f.add(label1); f.add(label2);
    f.setSize(450,450);  
    f.setLayout(null);  
    f.setVisible(true);  
    b.addActionListener(new ActionListener() {  
        public void actionPerformed(ActionEvent e) {
        	ArrayList<String> items = new ArrayList<String>();
        	items.addAll(list1.getSelectedValuesList());
           createOrderItem(c, items, username, ID, addMap);
           JOptionPane.showMessageDialog(null, "Your order has been successfully recorded.");
        }
     });
    
   
    
    JButton check = new JButton("Check");
    check.setBounds(300, 250, 80, 30);
    check.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(list1.getSelectedValuesList().size() != 1)
				JOptionPane.showMessageDialog(null, "You have to check exactly 1 item each time.");
			else {
				int foodID = map.get(list1.getSelectedValue());
				checkReview(c, foodID, false);
			}
				
		} });
    
    JButton end = new JButton("Exit");
    end.setBounds(300,300, 80, 30);
    end.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			
		} });
    JButton history = new JButton("Order History");  
    history.setBounds(200,50,180,30);  
    history.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			checkOrderHistory(c,username);
			
		}});
    f.add(addButton);
    f.add(end);
    f.add(rate);
    f.add(check);
    f.add(history);
}
protected void checkReview(Connection c, int xID, boolean isRestaurant) {
	JFrame frame1 = new JFrame("Reviews from all customers");

    frame1.setLayout(new BorderLayout());
	CallableStatement stm = null;
    final JLabel label1 = new JLabel();
    label1.setSize(500,100);
    label1.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
    label1.setText("Order History");
    DefaultTableModel model = new DefaultTableModel();
    String[] columnNames = {"Comments", "Star"};
	model.setColumnIdentifiers(columnNames);
    JTable table = new JTable();
    table.setModel(model);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.setFillsViewportHeight(true);
    JScrollPane scroll = new JScrollPane(table);
    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    String reviewText = "";
    int star;
    if(isRestaurant) {
	    try {
	    	stm = c.prepareCall("select ReviewText, Star from chex11.RestaurantRate where RestaurantID = '"+xID+"' ");
	    	ResultSet rs = stm.executeQuery();
	    	while(rs.next()) {
	    		reviewText = rs.getString("ReviewText");
	    		star = rs.getInt("star");
	    		model.addRow(new Object[] {reviewText, star});
	    	}
	    }catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Failed to display Restaurant Review");
			e.printStackTrace();
		}
    }else {
    	try {
	    	stm = c.prepareCall("select ReviewText, Stars from haoy1.FoodRate where MenuItemID = '"+xID+"' ");
	    	ResultSet rs = stm.executeQuery();
	    	while(rs.next()) {
	    		reviewText = rs.getString("ReviewText");
	    		star = rs.getInt("stars");
	    		model.addRow(new Object[] {reviewText, star});
	    	}
	    }catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Failed to display Food Review");
			e.printStackTrace();
		}
    }
    frame1.add(scroll);
    frame1.setVisible(true);
    frame1.setSize(400, 300);
}


protected void checkOrderHistory(Connection c, String username) {
	JFrame frame1 = new JFrame("Order History Result");
    frame1.setLayout(new BorderLayout());
	CallableStatement stm = null;
    final JLabel label1 = new JLabel();
    label1.setSize(500,100);
    label1.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
    label1.setText("Order History");
    DefaultTableModel model = new DefaultTableModel();
    String[] columnNames = {"ID","Restaurant Name", "Food Ordered","Created Time", "Quantity"};
	model.setColumnIdentifiers(columnNames);
    JTable table = new JTable();
    table.setModel(model);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.setFillsViewportHeight(true);
    JScrollPane scroll = new JScrollPane(table);
    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    String ResName = "";
    String Order = "";
    String created_at = "";
    int quantity =0;
    int id=0;
    try {
    	stm = c.prepareCall("{call chex11.checkHistory(?)}");
    	stm.setString(1, username);
    	ResultSet rs = stm.executeQuery();
    	while(rs.next()) {
    		id = rs.getInt("ID");
    		ResName = rs.getString("RestaurantName");
    		Order = rs.getString("Item");
    		created_at = rs.getString("created_at");
    		quantity = rs.getInt("Quantity");
			model.addRow(new Object[] {id, ResName, Order, created_at, quantity});
    	}
    	
    	
    }catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Failed to display order history");
		e.printStackTrace();
	}
    frame1.add(scroll);
    frame1.setVisible(true);
    frame1.setSize(400, 300);
}

public void createOrderItem(Connection c, ArrayList<String> items, String username, int res, HashMap<String, Integer> map) {
	CallableStatement stm = null;
	CallableStatement stm2 = null;
	CallableStatement stm3 = null;
	//int index = 1;
	int temp = 1;
	try {
		for(String item: items) {
//			if(map.get(item) != 0) {
				stm = c.prepareCall("{call haoy1.createOrderItem(?,?,?,?)}");
				
				stm.setString(1,item);
				
				stm.setInt(2,res);
				stm.setString(3, username);
				System.out.println(map.get(item));
				stm.setInt(4, map.get(item));
				//stm.setInt(4, map.get(item));
				stm.execute();
//			}
//				temp++;
//			}else {
//				int orderID=0;
//				stm2 = c.prepareCall("select top 1 ID from haoy1.OrderItem Order by ID desc");
//				ResultSet rs = stm2.executeQuery();
//				if (rs.next())
//					orderID = rs.getInt(1);
//				stm3 = c.prepareCall("{call chex11.AddOrder(?,?,?,?)}");
//				stm3.setInt(1, orderID);
//				stm3.setString(2,item);      
//				stm3.setInt(3,res);
//				stm3.setString(4, username);
//				stm3.execute();
//			}
		}
	} catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Failed to createOrder.");
		e.printStackTrace();
	}
}
public static void main(String username)  
    {  
		new SelectRestItem(username);
    }
}   