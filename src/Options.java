
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class Options {

	public Options() {
		JFrame options = new JFrame();
		options.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // CLOSES GUI
		options.setTitle("Options"); // NAME OF GUI
		options.setSize(338, 184);
		options.setLocation(300, 300);
		options.getContentPane().setLayout(null);
		
		JLabel lblOptions = new JLabel("Options");
		lblOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptions.setBounds(63, 11, 175, 37);
		lblOptions.setFont(new Font("Tahoma", Font.BOLD, 20));
		options.getContentPane().add(lblOptions);
		
		JButton btnGroupChat = new JButton("Practice Mode");
		btnGroupChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.dispose(); // CLOSES GUI
				PracticeMode.start();
			}
		});
		btnGroupChat.setBounds(10, 59, 137, 23);
		options.getContentPane().add(btnGroupChat);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.dispose();
				Object[] options = {"Goodbye"};
				JOptionPane.showOptionDialog(null, "Goodbye!", "Logging out", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]); // LOADS WHEN USER SELECTS LOG OUT
				new Login();
			}
		});
		btnLogOut.setBounds(190, 93, 90, 23);
		options.getContentPane().add(btnLogOut);
		
		JButton btnFriendlyMode = new JButton("Friendly Mode");
		btnFriendlyMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.dispose(); // CLOSES GUI
				FriendlyMode.start();
			}
		});
		btnFriendlyMode.setBounds(168, 59, 137, 23);
		options.getContentPane().add(btnFriendlyMode);
		
		JButton btnTournamentMode = new JButton("Tournament Mode");
		btnTournamentMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.dispose(); // CLOSES GUI
				TournamentMode.start();
			}
		});
		btnTournamentMode.setBounds(10, 93, 145, 23);
		options.getContentPane().add(btnTournamentMode);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);

		options.setVisible(true);
	}
}