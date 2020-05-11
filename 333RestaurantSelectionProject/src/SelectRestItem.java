
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
				stm = c.prepareCall("{?=call dbo.getAllRestaurants}");
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
          list1.setBounds(100,100, 175,275);  
          f.add(list1);  f.add(b); f.add(label);  
          f.setSize(450,450);  
          f.setLayout(null);  
          f.setVisible(true);  
          JButton end = new JButton("Exit");
          end.setBounds(300,250, 80, 30);
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
                    System.out.println(ID);
                    f.dispose();
                 }  
              }  
           });  
          f.add(end);
         
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
		}
	} catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Failed to connect");
		e.printStackTrace();
	}
	final JList<String> list1 = new JList<>(l1);  
    list1.setBounds(100,100, 175,275);  
    f.add(list1);  f.add(b); f.add(label1); f.add(label2);
    f.setSize(450,450);  
    f.setLayout(null);  
    f.setVisible(true);  
    b.addActionListener(new ActionListener() {  
        public void actionPerformed(ActionEvent e) {
        	ArrayList<String> items = new ArrayList<String>();
        	items.addAll(list1.getSelectedValuesList());
           createOrderItem(c, items, username, ID);
           JOptionPane.showMessageDialog(null, "Your order has been successfully recorded.");
        }
     });
    JButton end = new JButton("Exit");
    end.setBounds(300,250, 80, 30);
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
    f.add(end);
    f.add(history);
}

protected void checkOrderHistory(Connection c, String username) {
	JFrame frame1 = new JFrame("Order History Result");

    frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame1.setLayout(new BorderLayout());
	CallableStatement stm = null;
    final JLabel label1 = new JLabel();
    label1.setSize(500,100);
    label1.setFont(new java.awt.Font("Times New Roman", 0, 25)); // NOI18N
    label1.setText("Order History");
    DefaultTableModel model = new DefaultTableModel();
    String[] columnNames = {"Restaurant Name", "Food Ordered"};
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
    try {
    	stm = c.prepareCall("select RestaurantName from haoy.OrderItem where UserName = " +username);
    	ResultSet rs = stm.executeQuery();
    	if(rs.next()) {
    		ResName = rs.getString("RestaurantName");
    		Order = rs.getString("Order");
    		//model.addRow(new Object[] {ResName});
    		model.addRow(new Object[] {ResName, Order});
    	}
    	
    	
    }catch (SQLException e) {
		JOptionPane.showMessageDialog(null, "Failed to display order history");
		e.printStackTrace();
	}
    frame1.add(scroll);
    frame1.setVisible(true);
    frame1.setSize(400, 300);
}

public void createOrderItem(Connection c, ArrayList<String> items, String username, int res) {
	CallableStatement stm = null;
	int index = 1;
	try {
		for(String item: items) {
			stm = c.prepareCall("{call haoy1.createOrderItem(?,?,?)}");
			stm.setString(1, username);
			stm.setString(2, item);
			stm.setString(3, String.valueOf(res));
			stm.setString(index, item);
		}
		
		stm.execute();
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