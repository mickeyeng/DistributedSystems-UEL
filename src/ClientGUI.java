import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

/*
 * The Client with its GUI
 */
public class ClientGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	// will first hold "Username:", later on "Enter message"
//	private JLabel label;
	// to hold the Username and later on the messages
//	private JTextField tf;
	// to hold the server address an the port number
	private JTextField tfServer;
	// to Logout and get the list of the users
	private JButton join, logout, whoIsIn;
	// for the chat room
	private JTextArea ta;
	// if it is for connection
	private boolean connected;
	// the Client object
	private Client client;
	// the default port number
	private int defaultPort;
	private String defaultHost;
	
	static Timer timer;
	static int interval;

	// Constructor connection receiving a socket number
	ClientGUI() {

		super("Game Log");
//		defaultPort = port;
//		defaultHost = host;
		
		// The NorthPanel with:
		JPanel northPanel = new JPanel(new GridLayout(3,1));
		// the server name anmd the port number
		JPanel serverAndPort = new JPanel(new GridLayout(1,5, 1, 3));
		// the two JTextField with default value for server address and port number
		tfServer = new JTextField("");
//		tfPort = new JTextField("pass");
//		tfPort.setHorizontalAlignment(SwingConstants.RIGHT);

		serverAndPort.add(new JLabel("Username:  "));
		serverAndPort.add(tfServer);
//		serverAndPort.add(new JLabel("Port Number:  "));
//		serverAndPort.add(tfPort);
//		serverAndPort.add(new JLabel(""));
		// adds the Server an port field to the GUI
		northPanel.add(serverAndPort);
//		serverAndPort.add(uname);
//		serverAndPort.add(new JLabel("Password:  "));
//		serverAndPort.add(password);
//		serverAndPort.add(new JLabel(""));
//		// adds the Server an port field to the GUI
//		northPanel.add(serverAndPort);

		// the Label and the TextField
//		label = new JLabel("Enter your username below", SwingConstants.CENTER);
//		northPanel.add(label);
//		tf = new JTextField("Anonymous");
//		tf.setBackground(Color.WHITE);
//		northPanel.add(tf);
		getContentPane().add(northPanel, BorderLayout.NORTH);

		// The CenterPanel which is the game room
		ta = new JTextArea("Welcome to the Game room\n", 80, 80);
		JPanel centerPanel = new JPanel(new GridLayout(1,1));
		centerPanel.add(new JScrollPane(ta));
		ta.setEditable(false);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		tfServer.setEditable(false);
		
//		uname.setText("assia");
//		password.setText("pass");
		
		JLabel label = new JLabel("Time Left:  ");
		northPanel.add(label);
		JTextField clock = new JTextField();
		clock.setEditable(false);
		northPanel.add(clock);
		
		int secs = 1*60;
		int delay = 1000;
		int period = 1000;
		timer = new Timer();
        interval = secs;
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
            	int count = setInterval();
            	clock.setText(Integer.toString(count) + " seconds");

            }
        }, delay, period);

//      	stopwatch.setVisible(true);
//	}

		// the 3 buttons
		join = new JButton("Start");
		join.addActionListener(this);
		logout = new JButton("Logout");
		logout.addActionListener(this);
		logout.setEnabled(false);		// you have to login before being able to logout
		whoIsIn = new JButton("Who is in");
		whoIsIn.addActionListener(this);
		whoIsIn.setEnabled(false);		// you have to login before being able to Who is in

		JPanel southPanel = new JPanel();
		southPanel.add(join);
		southPanel.add(logout);
		southPanel.add(whoIsIn);
		getContentPane().add(southPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
//		tf.requestFocus();

	}
	
	private static final int setInterval() {
      if (interval == 1) {
      	JOptionPane.showMessageDialog(null, "Times Up!", "Warning", JOptionPane.ERROR_MESSAGE);
          timer.cancel(); //Terminate the timer thread
          for(int i1 = Server.al.size(); --i1 >= 0;) {
  			ClientThread ct = Server.al.get(i1);
  			System.out.println("\nFinal Score: " + Server.score + "\nGood Attempt!");
  			Server.al.remove(i1);
//  			if(clients.size() > 1) { // RUNS ONLY IF THERE IS MORE THAN ONE CLIENT CONNECTED	
//  				Server.leaderboard(); // CALLS THE LEADBOARD METHOD ON THIS CLASS TO PRINT THE FINAL RANKING
//  			}
          }
      }
      return --interval;
  }

	// called by the Client to append text in the TextArea 
	void append(String str) {
		ta.append(str);
		ta.setCaretPosition(ta.getText().length() - 1);
	}
	// called by the GUI is the connection failed
	// we reset our buttons, label, textfield
	void connectionFailed() {
		join.setEnabled(true);
		logout.setEnabled(false);
		whoIsIn.setEnabled(false);
//		label.setText("Enter your username below");
//		tf.setText("Anonymous");
		// reset port number and host name as a construction time
//		tfPort.setText("" + defaultPort);
		tfServer.setText(defaultHost);
		// let the user change them
		tfServer.setEditable(false);
//		tfPort.setEditable(false);
		// don't react to a <CR> after the username
//		tf.removeActionListener(this);
		connected = false;
	}
		
	/*
	* Button or JTextField clicked
	*/
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		// if it is the Logout button
		if(o == logout) {
			client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
			return;
		}
		// if it the who is in button
		if(o == whoIsIn) {
			client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));				
			return;
		}

		// ok it is coming from the JTextField
//		if(connected) {
//			// just have to send the message
//			client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, tf.getText()));				
//			tf.setText("");
//			return;
//		}
		

		if(o == join) {
			// ok it is a connection request
//			String username = tfServer.getText().trim();
			// empty username ignore it
//			if(username.length() == 0)
//				return;
			// empty serverAddress ignore it
			String server = Client.getHost();
//			if(server.length() == 0)
//				return;
			// empty or invalid port numer, ignore it
			int port = Client.getPort();
//			if(portNumber.length() == 0)
//				return;
//			int port = 0;
//			try {
//				port = Integer.parseInt(portNumber);
//			}
//			catch(Exception en) {
//				return;   // nothing I can do if port number is not valid
//			}

			// try creating a new Client with GUI
			client = new Client(server, port, "", this);
			// test if we can start the Client
			if(!client.start()) 
				return;
//			tf.setText("");
//			label.setText("Enter your message below");
			connected = true;
			
			// disable login button
			join.setEnabled(false);
			// enable the 2 buttons
			logout.setEnabled(true);
			whoIsIn.setEnabled(true);
			// disable the Server and Port JTextField
//			tfPort.setEditable(false);
			// Action listener for when the user enter a message
//			tf.addActionListener(this);
		}

	}

	// to start the whole thing the server
	public static void main(String[] args) {
		new ClientGUI();
	}
}