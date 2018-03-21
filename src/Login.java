
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Login implements ActionListener {
	static JFrame login;
	JButton btnLogin, btnExit, btnRegister;
	static JTextField username;
	JPasswordField password;
	
	public static void main(String[] args) {
		new Login(); // CALLS LOGIN GUI                                           
	}   
	
	public Login() {
		login = new JFrame ("Login Screen"); // NAME OF GUI
		login.setSize(248,215);
		login.setLocation(300,300);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // CLOSES GUI 
		login.getContentPane().setLayout(null);
		
		JLabel lblwelcome = new JLabel("Welcome");
		lblwelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblwelcome.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblwelcome.setBounds(60, 11, 99, 25);
		login.getContentPane().add(lblwelcome);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(21, 47, 68, 25);
		login.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(21, 78, 68, 25);
		login.getContentPane().add(lblPassword);
		
		username = new JTextField();
		username.setBounds(110, 51, 86, 20);
		login.getContentPane().add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.setBounds(110, 82, 86, 20);
		login.getContentPane().add(password);
		
		btnRegister = new JButton("Register");
		btnRegister.setBounds(10, 114, 89, 23);
		login.getContentPane().add(btnRegister);
		btnRegister.addActionListener(this);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(134, 114, 89, 23);
		login.getContentPane().add(btnLogin);
		btnLogin.addActionListener(this);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(70, 148, 89, 23);
		login.getContentPane().add(btnExit);
		btnExit.addActionListener(this);
		
		login.setVisible(true);  
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLogin) { // RUNS IF LOGIN BUTTON WAS PRESSED
			if(!(username.getText().equals("")) && !(password.getText().equals(""))) // IF THE USERNAME AND PASSWORD FIELDS HAVE BEEN ENETERED
			{
				String uname = username.getText(); // GETS USERNAME FIELD STRING
				String pass = password.getText(); // GETS PASSWORD FIEL STRING
				
//				PracticeMode.displayName = uname;
//				FriendlyMode.displayName = uname;
//				TournamentMode.displayName = uname;
									
				checkLogin(uname, pass); // PASSES THE USERNAME AND PASSWORD TO THE METHO 'CHECKLOGIN'			
			}
			else {
				JOptionPane.showMessageDialog(null, "Please enter all login details", "Error", JOptionPane.ERROR_MESSAGE); // IF USERNAME OR/AND PASSWORD NOT ENTERED
			}
		}
		else if(e.getSource() == btnRegister) { // RUNS IF REGISTER BUTTON WAS PRESSED
			login.dispose(); // CLOSES GUI
//			new Register(); // OPENS REGISTER GUI
		}
		else {
			login.dispose(); // CLOSES GUI IF BUTTON 'EXIT' IS CLOSED
		}
	}
	
	public static void checkLogin(String user, String password) // CALLED WHEN BUTTON 'LOGIN' IS PRESSED
	{
		try{
			Class.forName("org.h2.Driver");
			Connection con = DriverManager.getConnection("jdbc:h2:~/test","sa","sa"); // CONNECTS TO DATABASE
			Statement state = con.createStatement();
			
			String query = "SELECT * FROM SAVEDUSERS WHERE USERNAME='" + user + "' and PASSWORD='" + password + "';"; // GETS EVERYTHING FROM THE TABLE IN THE DATABASE
			ResultSet rs = state.executeQuery(query);
			String Uname = null;
			String pass = null;
									
			while (rs.next()){
				Uname = rs.getString("USERNAME"); // GETS THE USERNAME COLUMN STRINGS
				pass = rs.getString("PASSWORD"); // GETS THE PASSWORD COLUMN VALUES
			}
			
			if(user.equals(Uname) && (password.equals(pass))) // IF THE CORRECT USERNAME AND PASSWORD HAS BEEN ENETERED
			{			
				login.dispose(); // GUI CLOSES
//				new Options();
				new ClientGUI();
			}
			else {
				JOptionPane.showMessageDialog(null, "Username or/and Password not correct. Please try again or register", "Error", JOptionPane.ERROR_MESSAGE); // MESSAGE SAYING USERNAME OR PASSWORD IS WRONG AND ALLOWS THEM TO TRY AGAIN
			}
		} catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
}