import javax.swing.*;  
import java.awt.event.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;  

public class SelectRestItem  
{  
	private DatabaseConnection dbService;
     SelectRestItem(){
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
				JOptionPane.showMessageDialog(null, "Failed to Login");
				e.printStackTrace();
			}
			
          final JList<String> list1 = new JList<>(l1);  
          list1.setBounds(100,100, 175,275);  
          f.add(list1);  f.add(b); f.add(label);  
          f.setSize(450,450);  
          f.setLayout(null);  
          f.setVisible(true);  
          b.addActionListener(new ActionListener() {  
              public void actionPerformed(ActionEvent e) {   
                 String data = "";  
                 if (list1.getSelectedIndex() != -1) {                       
                    int ID = map.get(list1.getSelectedValue());   
                    showMenu(ID, c);
                    System.out.println(ID);
                    f.dispose();
                 }  
     
                 label.setText(data);  
              }  
           });   
     }  
     
public void showMenu(int ID, Connection c) {
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
		JOptionPane.showMessageDialog(null, "Failed to Login");
		e.printStackTrace();
	}
	final JList<String> list1 = new JList<>(l1);  
    list1.setBounds(100,100, 175,275);  
    f.add(list1);  f.add(b); f.add(label1); f.add(label2);
    f.setSize(450,450);  
    f.setLayout(null);  
    f.setVisible(true);  
}

//public void createRecord
public static void main(String args[])  
    {  
		new SelectRestItem();  
    }
}  