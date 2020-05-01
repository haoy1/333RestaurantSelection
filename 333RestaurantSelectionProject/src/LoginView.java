import java.awt.Button;
import java.awt.Dialog;
import java.awt.TextField;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.GroupLayout.Alignment;

@SuppressWarnings("serial")
@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends HorizontalLayout {
        public LoginView(Button log, Button register) {
		super(log, register);
		// TODO Auto-generated constructor stub
	}

		public static final String ROUTE = "login";


}
