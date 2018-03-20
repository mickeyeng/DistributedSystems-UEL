
import java.net.*;
import java.io.*;
import java.util.*;

public class Client  {

	ObjectInputStream in; // OBJECT TO READ INPUT
	static ObjectOutputStream out; // OBJECT TO SEND OUTPUT
	Socket socket; // SOCKET FOR CONNECTIONS
	static PracticeModeGUI cgui; // CLIENT GUI VARIABLE (THIS WILL BE THE GROUP CHAT ROOM WHEN OPENED)
	static String host; // HOST VARIABLE
	static String username; // USERNAME VARIABLE (NOT WORKING HOW IT SHOULD)
	static int port; // PORT VARIABLE

	Client(String server, int p, String user 
			, PracticeModeGUI cg
			) { // CONSTRUCTOR WHICH NEEDS TO BE PASSED THE SERVER NAME, PORT NUMBER, USERNAME, CHAT ROOM GUI
		host = server; // SERVER VARIABLE IS PASSED TO HOST VARIABLE
		port = p; // P VARIABLE IS PASSED TO PORT VARIABLE
		username = user; // USER VARIABLE PASSED TO USERNAME VARIABLE
		cgui = cg; // GUI PASSED TO GUI VARIABLE
	}

	public boolean start() { // STARTS WHEN CONNECTION IS ALIVE (WHICH IS WHY ITS BOOLEAN)
		try {
			socket = new Socket(host, port); // SOCKET CONNECTS TO HOST AND PORT GIVEN
		} 
		catch(Exception ec) {
			display("Error connectiong to server:" + ec); // IF THERE IS NO HOST AND PORT SET OR RUNNING
			return false;
		}
		
		String msg = "Connection sucessful to server host " + getHost() + " on port " + getPort() + "\nWelcome " + username; // MESSAGE TO CLIENT SAYING THEY HAVE CONNECTED TO SERVER
		display(msg); // DISPLAYS THE MESSAGE
	
		try
		{
			in  = new ObjectInputStream(socket.getInputStream()); // OBJECT FOR INPUT
			out = new ObjectOutputStream(socket.getOutputStream()); // OBJECT FOR OUTPUT
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO); // IF OBJECTS CAN NOT BE CREATED
			return false;
		}

		new ListenFromServer().start(); // STARTS THE THREAD CLASS
		
		try
		{
			out.writeObject(username); // WRITES USERNAME TO THE OUTPUT STREAM
		}
		catch (IOException e) {
			display("Exception doing login : " + e); // IF THE USERNAME CAN NOT BE SENT
			disconnect(); // DISCONNECTS THE USER
			return false;
		}
		return true;
	}

	public static void display(String msg) { // TO DISPLAY MESSAGES TO CLIENT
		cgui.append(msg + "\n"); // EDITS THE CHAT ROOM CHAT LOG AREA
//		System.out.println(msg + "\n");
	}
	
	public static void players(String numPlayers){
		System.out.println(numPlayers + "\n");
	}

	public static void sendMessage(MessageHandler msg) { // TO SEND A MESSAGE ON THE CHAT ROOM
		try {
			out.writeObject(msg); // WRITES THE MESSAGE ON THE CHAT LOG AREA IN THE CHAT ROOM
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client(host, port, username, cgui); // STARTS CLIENT WITH GIVEN VARIABLES
		if(!client.start()) { // IF CONNECTION CAN NOT START FOR SOME REASON LIKE NO HOST GIVEN
			return; // BREAKS THE LOOP
		}

		Scanner scan = new Scanner(System.in);
		while(true) {
			String msg = scan.next();		
			client.sendMessage(new MessageHandler(MessageHandler.MESSAGE, msg)); // SENDS MESSAGE TO THE USER THROUGH 'SENDMESSAGE' METHOD
		}	
	}
	
	public void disconnect() { // IF CONNECTION IS FAILED AND THIS METHOD IS CALLED
		try { 
			if(in != null) in.close(); // CLOSES THE INPUT STREAM IF OPENED
		}
		catch(Exception e) {
			e.printStackTrace();
		} 
		try {
			if(out != null) out.close(); // CLOSES THE OUTPUT STREAM IF OPENED
		}
		catch(Exception e) {
			e.printStackTrace();
		}
        try{
			if(socket != null) socket.close(); // CLOSES THE SOCKET IF OPENED
		}
		catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static String getHost() { // GETS HOST
		String host = ""; // LOCAL HOST VARIABLE
		try {
			Properties prop = new Properties(); // PROP VARIABLE
			FileInputStream in = new FileInputStream("Client.prop"); // GETS 'CLIENT' PROP FILE
			prop.load(in); // LOADS THE PROP FILE
			in.close(); // CLOSES THE FILEINPUTSTREAM
			
			host = prop.getProperty("Host"); // GETS THE VARIABLE 'HOST' FROM THE PROP FILE AND PUSHES IT TO THE LOCAL HOST VARIABLE
		} catch (IOException e) {
			e.printStackTrace();
		}
		return host; // RETURNS THE VARIABLE HOST 
	}
	
	public static int getPort() { // GET PORT
		try {
			Properties prop = new Properties(); // PROP VARIABLE
			FileInputStream in = new FileInputStream("Client.prop"); // READS PROP FILE CALLED 'CLIENT'
			prop.load(in); // LOADS THE DATA FROM THE FILE
			in.close(); // CLOSES THE FILEINPUTSTREAM
			
			port = Integer.parseInt(prop.getProperty("Port")); // GLOABL PORT VARIABLE (AT TOP OF CLASS) GIVEN THE PORT VALUE FROM THE FILE
		} catch (IOException e) {
			e.printStackTrace();
		}
		return port; // RETURNS THE VALUE OF PORT
	}

	class ListenFromServer extends Thread { // THREAD CLASS FOR CLIENT

		public void run() { // RUNS WHEN '.START()' IS CALLED
			while(true) {
				try {
					String msg = (String) in.readObject(); // READS MESSAGES INCOMING
					cgui.append(msg); // DISPLAYS THE MESSAGES ON THE CHAT LOG OF THE GUI
//					System.out.println(msg);
				}
				catch(IOException e) {
					display("Server had to close the connection: " + e); // IF SERVER SHUTS OFF OF CONNECTION TO THE CLIENT IS LOST
					break;
				}
				catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
