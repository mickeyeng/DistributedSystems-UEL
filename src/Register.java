
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Register {
	static JFrame register;
	
	public Register() {
		register = new JFrame();
		register.setTitle("Register Form"); // NAME OF GUI
		register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		register.setSize(279, 240);
		register.setLocation(300, 300);
		register.getContentPane().setLayout(null);
		
		JLabel lblRegister = new JLabel("Register");
		lblRegister.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRegister.setBounds(64, 11, 94, 24);
		register.getContentPane().add(lblRegister);
		
		JLabel lblName = new JLabel("Name");
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(32, 58, 62, 14);
		register.getContentPane().add(lblName);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsername.setBounds(24, 83, 78, 14);
		register.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(24, 108, 78, 14);
		register.getContentPane().add(lblPassword);
		
		JLabel lblReenterPassword = new JLabel("Re-enter Password");
		lblReenterPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblReenterPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReenterPassword.setBounds(10, 133, 138, 14);
		register.getContentPane().add(lblReenterPassword);
		
		JTextField name = new JTextField();
		name.setBounds(150, 49, 86, 20);
		register.getContentPane().add(name);
		name.setColumns(10);
		
		JTextField username = new JTextField();
		username.setBounds(150, 74, 86, 20);
		register.getContentPane().add(username);
		username.setColumns(10);
		
		JPasswordField password = new JPasswordField();
		password.setBounds(152, 102, 84, 20);
		register.getContentPane().add(password);
		
		JPasswordField password2 = new JPasswordField();
		password2.setBounds(152, 127, 84, 20);
		register.getContentPane().add(password2);
				
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {  // RUNS IF THE BUTTON 'SUBMIT
			public void actionPerformed(ActionEvent arg0) {
										
				if(!(name.getText().equals("")) && !(username.getText().equals("")) && !(password.getText().equals("")) && !(password2.getText().equals(""))) // CHECKS IF THE FIELDS ARE NOT BLANKED 
		 		{
									
					String nameentered = name.getText();
					String usernameentered = username.getText();
					String password1 = password.getText();
					String password1b = password2.getText();
					
					if(password1.equals(password1b)) // IF THE TWO PASSWORDS ENTERED ARE EQUALLED, THIS RUNS 
					{
						checkUsername(usernameentered, password1, nameentered); // SENDS TO THE METHOD
					}
					else {
						JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE); // IF THE TWO PASSWORD ENTERED DO NOT MATCH
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE); // IF ALL FIELDS ARE NOT FILLED IN
				}
			}
		});
		btnSubmit.setBounds(150, 155, 89, 23);
		register.getContentPane().add(btnSubmit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				register.dispose();
				new Login();
			}
		});
		btnCancel.setBounds(32, 155, 89, 23);
		register.getContentPane().add(btnCancel);
				
		register.setVisible(true);
	}
	
	public static void checkUsername(String user, String password, String Name) // METHOD TO SAVE DETAILS ENTERED
	{
		try{
			Class.forName("org.h2.Driver");
			Connection con = DriverManager.getConnection("jdbc:h2:~/test","sa","sa"); // CONNECTS TO DATABASE
			Statement state = con.createStatement();
			
			String query = "SELECT * FROM SAVEDUSERS WHERE USERNAME='" + user + "';"; // LOADS DATA FROM THE TABLE
			ResultSet rs = state.executeQuery(query);
			String Uname = null;
			
			while (rs.next()){
				Uname = rs.getString("USERNAME"); // GETS THE VALUES IN THE USERNAME COLUMN
			}
			
			if(user.equals(Uname)) // IF THE USERNAME IS EQUALLED TO A USERNAME ON THE DATABASE
			{
				JOptionPane.showMessageDialog(null, "Username already in use. Please use another", "Error", JOptionPane.ERROR_MESSAGE); // POP UP TELLS USER USERNAME IS TAKEN SO THEY HAVE TO ENTER ANOTHER ONE
			}
			else if(!(user.equals(Uname))){ // IF USERNAME DOES NOT EXIST
				register.dispose(); // CLOSES GUI
				new Options();
				
				Object[] options = {"OK"};
				JOptionPane.showOptionDialog(null, "Account created! Click 'OK' to continue", "Account created", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]); // INFORMS USER THE ACCOUNT HAS BEEN MADE
				
				Server set = new Server(Server.port, null);
				set.Database(user, password, Name); // SENDS VALUES TO DATABASE FOR ACCOUNT TO BE CREATED
			}
		} catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
}