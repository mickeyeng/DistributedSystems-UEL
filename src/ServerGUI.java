
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ServerGUI extends JFrame implements ActionListener {
	JButton start;
	static JTextArea eventUpdate;
	static JTextField portN;
	JTextField hostName;
	static int p;
	
	public static void main(String[] arg) {
		new ServerGUI(); // LOADS GUI
	}

	ServerGUI() {
		setTitle("Chat Server"); // NAME OF GUI
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel north = new JPanel();
		north.add(new JLabel("Host: "));
		hostName = new JTextField(Server.getHost()); // GETS HOST AND DISPLAYS IT FROM SERVER CLASS
		hostName.setHorizontalAlignment(SwingConstants.CENTER);
		hostName.setEditable(false);
		north.add(hostName);
		
		north.add(new JLabel("Port number: "));
		portN = new JTextField("  " + Server.getPort()); // GETS PORT AND DISPLAYS IT FROM SERVER CLASS
		portN.setHorizontalAlignment(SwingConstants.CENTER);
		portN.setEditable(false);
		north.add(portN);

		start = new JButton("Start");
		start.addActionListener(this);
		north.add(start);
		getContentPane().add(north, BorderLayout.NORTH);

		JPanel center = new JPanel(new GridLayout(-5,1));		
		eventUpdate = new JTextArea(80,80);
		eventUpdate.setEditable(false);
		appendEvent("Events Update:\n");
		center.add(new JScrollPane(eventUpdate));	
		getContentPane().add(center);
		
		setSize(512, 399);
		setVisible(true);
	}		

	public static void appendEvent(String str) { // EDITS EVENT LOG ON SERVER GUI
		eventUpdate.append(str);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(start)) {
			ServerRunning sr = new ServerRunning(); // CALLS CLASS
			sr.start(); // STARTS THREAD
			start.setText("Stop");
			portN.setEditable(false);
		}		
	}

	class ServerRunning extends Thread {
		public void run() { // RUNS WHEN '.START' IS USED
			Server server = new Server(Server.getPort(), null); // CREATES SERVER OBJECT
			server.start(); // STARTS SERVER OBJECT
			
			start.setText("Start");
			portN.setEditable(false);
			Server.display("Server Stopped\n");
		}
	}
}