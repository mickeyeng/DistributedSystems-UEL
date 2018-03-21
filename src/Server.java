// NEED A LEADERBOARD FOR FRIENDLY AND TOURNAMENT MODE
	// NEED TO TEST WITH MORE THAN 1 CLIENT
// NEED TO ASK USER HOW MANY PLAYERS 
// NEED TO CREATE THREADS TO MAKE USERS WAIT FOR EACH OTHER IF CLIENT IS MORE THAN 1
	// FOR FRIENDLY AND TOURNAMENT MODE
// CLIENT FROM ANOTHER COMPUTER IS ABLE TO CONNECT BUT QUESTIONS LOAD HERE NOT ON THAT COMPUTER

// CHANGE CLIENT GUI SO INPUT IS FROM JOPTIONPANE
	// IF IT CANT BE DONE LEAVE IT
// ADD TIMER ON CLIENT GUI INSTEAD OF HAVING ANOTHER FRAME OPENED
	// DONE BUT TIMER NEEDS TO START ONCE QUIZ STARTS
		// FOR NOW IT STARTS WHEN GUI IS OPENED
// NEED LEADERBOARD TO BE ON CLIENT GUI AND NOT AS A JOPTIONPANE
// NEED PORT AND HOST TO SHOW FROM FILE
// IF WANTED SERVER GUI CAN BE ADDED BACK LATER BUT DOES NOT CARRY MARKS AND IS MORE CODE

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javax.swing.JOptionPane;

/*
 * The server that can be run both as a console application or a GUI
 */
public class Server {
	// an ArrayList to keep the list of the Client
	static ArrayList<ClientThread> al;
	// the port number to listen for connection
	static int port;
	static String host;
	// the boolean that will be turned of to stop the server
	boolean keepGoing;
	
	public Server(int p) {
		port = p;
		// ArrayList for the Client list
		al = new ArrayList<ClientThread>();
	}
	
	public void start() {
		keepGoing = true;
		/* create socket server and wait for connection requests */
		try 
		{
			// the socket used by the server
			ServerSocket serverSocket = new ServerSocket(port);

			// infinite loop to wait for connections
			while(keepGoing) 
			{
				// format message saying we are waiting
				display("Server waiting for Clients on port " + port + ".");
				
				Socket socket = serverSocket.accept();  	// accept connection
				// if I was asked to stop
				if(!keepGoing)
					break;
				ClientThread t = new ClientThread(socket);  // make a thread of it
				al.add(t);									// save it in the ArrayList
				t.start();
				startGame();
			}
			// I was asked to stop
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
					tc.sInput.close();
					tc.sOutput.close();
					tc.socket.close();
					}
					catch(IOException ioE) {
						// not much I can do
					}
				}
			}
			catch(Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}
		// something went bad
		catch (IOException e) {
            String msg = "Exception on new ServerSocket: " + e + "\n";
			display(msg);
		}
	}		
    /*
     * For the GUI to stop the server
     */
	public void stop() {
		keepGoing = false;
		try {
			new Socket(host, port);
		}
		catch(Exception e) {
			// nothing I can really do
		}
	}
	/*
	 * Display an event (not a message) to the console or the GUI
	 */
	static void display(String msg) {
		System.out.println(msg);
	}
	
	static int score = 0;
	static String userAnswer;
	
	public void startGame() {
		String question = "";
	    String choice1 = "";
	    String choice2 = "";
	    String choice3 = "";
	    String choice4 = "";
	    String choice5 = "";
	    String answer = "";
	    String line;
	    int i = 0;

		try {
			BufferedReader reader = new BufferedReader(new FileReader("questions.txt")); // FINDS THE 'questions.txt' FILE ON THE LOCAL MACHINE
			Scanner sc = new Scanner(System.in);
			QuizClass run = new QuizClass(i, question, choice1, choice2, choice3, choice4, choice5, answer);

			while((line = reader.readLine()) != null){
			    //Finds the question
			    if(line.contains("?")){
			        run.question = line + "\n";
			        
			        for(int i1 = al.size(); --i1 >= 0;) {
						ClientThread ct = al.get(i1);
						if(!ct.writeMsg(run.question)) {
							al.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
			    }

			    //Finds answer "1"
			    if(line.contains("1)")){
			        run.choice1 = line + "\n";
			        
			        for(int i1 = al.size(); --i1 >= 0;) {
						ClientThread ct = al.get(i1);
						if(!ct.writeMsg(run.choice1)) {
							al.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
			    }

			    //Finds answer "2"
			    if(line.contains("2)")){
			        run.choice2 = line + "\n";
			        
			        for(int i1 = al.size(); --i1 >= 0;) {
						ClientThread ct = al.get(i1);
						if(!ct.writeMsg(run.choice2)) {
							al.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
			    }

			    //Finds answer "3"
			    if(line.contains("3)")){
			        run.choice3 = line + "\n"; 
			        
			        for(int i1 = al.size(); --i1 >= 0;) {
						ClientThread ct = al.get(i1);
						if(!ct.writeMsg(run.choice3)) {
							al.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
			    }
			    
			    //Finds answer "4"
			    if(line.contains("4)")){
			        run.choice4 = line + "\n";
			        
			        for(int i1 = al.size(); --i1 >= 0;) {
						ClientThread ct = al.get(i1);
						if(!ct.writeMsg(run.choice4)) {
							al.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
			    }
			    
			    //Finds answer "5"
			    if(line.contains("5)")){
			        run.choice5 = line + "\n";
			        
			        for(int i1 = al.size(); --i1 >= 0;) {
						ClientThread ct = al.get(i1);
						if(!ct.writeMsg(run.choice5)) {
							al.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
			    }
			    
		    	//Finds the correct answer for the question
			    if(line.contains("Correct Answer: ")){
			        String[] a = line.split(": ");
			        answer = a[1];
			        run.answer = answer;

					for(int i3 = al.size(); --i3 >= 0;) {
						ClientThread ct = al.get(i3);
						if(!ct.writeMsg("Please enter you answer now\n\n")) {
							al.remove(i3);
							display("Disconnected Client. User was removed from list");
						}
					}
						
//				 System.out.print("\nYour Answer: ");
//				 userAnswer = sc.next();
					
				String whatTheUserEntered = JOptionPane.showInputDialog(null, "Your Answer: ");

				if (whatTheUserEntered == null) {
					System.out.println("The user canceled");
				}
				else {
					userAnswer = whatTheUserEntered;
					//Checks if the user's input matches the correct answer from the file
		            if(userAnswer.equalsIgnoreCase(answer)){
		            	for(int i1 = al.size(); --i1 >= 0;) {
		            		ClientThread ct = al.get(i1);
							int usersInput = JOptionPane.showOptionDialog(null, "\nCorrect!\n\n", "Correct Answer", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
							String inputAnswer = "Your Answer: " + userAnswer;
							
							if(!ct.writeMsg(inputAnswer)) {
								al.remove(i1);
								display("Disconnected Client. User was removed from list");
							}
							
							if(!ct.writeMsg(Integer.toString(usersInput))) {
								al.remove(i1);
								display("Disconnected Client. User was removed from list");
							}
						}
		                i++;
		                score++;
		            } 

		            //Checks if the user's input doesn't match the correct answer from the file
		            else if (!userAnswer.equalsIgnoreCase(answer)) {
		            	for(int i1 = al.size(); --i1 >= 0;) {
							ClientThread ct = al.get(i1);
							int usersInput = JOptionPane.showOptionDialog(null, "\nWrong Answer. Correct Answer Was: " + run.getAnswer() + "\n\n", "Wrong Answer", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
							String inputAnswer = "Your Answer: " + userAnswer;
							
							if(!ct.writeMsg(inputAnswer)) {
								al.remove(i1);
								display("Disconnected Client. User was removed from list");
							}
							
							if(!ct.writeMsg(Integer.toString(usersInput))) {
								al.remove(i1);
								display("Disconnected Client. User was removed from list");
							}
						}			            	
		            	i++;
		            }
				}
		    }
	    } 
	    sc.close();
    	reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i1 = al.size(); --i1 >= 0;) {
			ClientThread ct = al.get(i1);
			System.out.println("Final Score: " + score + "\nGood Attempt!");
//			if(clients.size() > 1) { // RUNS ONLY IF THERE IS MORE THAN ONE CLIENT CONNECTED	
				leaderboard(); // CALLS THE LEADBOARD METHOD ON THIS CLASS TO PRINT THE FINAL RANKING
				ClientGUI.timer.cancel();
//			}
			if(!ct.writeMsg("Final Score: \nGood Attempt!")) {
				al.remove(i1);
				display("Disconnected Client. User was removed from list");
			}
		}
	}
	
	public static void leaderboard() {
		// CODE BELOW WORKS BUT ONLY HAS SCORES NO USERNAMES
	//		int n = 100, max;
	//		int scores[] = new int[n];
	//		for(int e = 0; e < n; e++)
	//        {
	//			scores[e] = score;
	//        }
	//		max = scores[0];
	//        for(int m = 0; m < n; m++)
	//        {
	//            if(max < scores[m])
	//            {
	//                max = scores[m];
	//            }
	//        }
	//						
	//        Arrays.sort(scores);
	//        int[] scoreList = Arrays.copyOfRange(scores, scores.length-1, scores.length);
	//        System.out.println("\nLeaderboard: " + Arrays.toString(scoreList));
		
		// CODE BELOW HAS USERNAME AND SCORE BUT NEED TO TEST WITH MORE THAN 1 CLIENT
		// ALSO NEED TO NOT SHOW LEADERBOARD IF IT IS PRACTICE MODE WHICH CAN BE CHANGED IN BROADCAST METHOD
		// NEED TO SEE IF THE LEADERBOARD PRINTS HIGHEST SCORE TO LOWEST
		
		Map<String,Integer> myMap = new HashMap<String, Integer>(); // MAP TO STORE USERNAMES AND SCORES
		String uname = ClientThread.username; // GETS THE USERNAME FROM THE SERVERTHREAD CLASS
		
		myMap.put(uname, score); // ADDS 2 VALUES TO THE MAP OBJECT CREATED - USERNAME FROM THE SERVERTHREAD CLASS AND SCORE VALUE
		int usersInput = JOptionPane.showOptionDialog(null, myMap.toString(), "Leaderboard", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
		
		for(int i1 = al.size(); --i1 >= 0;) {
			ClientThread ct = al.get(i1);

			if(!ct.writeMsg(Integer.toString(usersInput))) {
				al.remove(i1);
				display("Disconnected Client. User was removed from list");
			}
		}
	}
	
	/*
	 *  to broadcast a message to all Clients
	 */	
//	synchronized static void broadcast(String message) {
//		System.out.print(message);
//		
//		// we loop in reverse order in case we would have to remove a Client
//		// because it has disconnected
//		for(int i = al.size(); --i >= 0;) {
//			ClientThread ct = al.get(i);
//			// try to write to the Client if it fails remove it from the list
//			if(!ct.writeMsg(message)) {
//				al.remove(i);
//				display("Disconnected Client " + ct.username + " removed from list.");
//			}
//		}
//	}
	
	public static String getHost() { // GET HOST METHOD
		try {
			host = InetAddress.getLocalHost().getHostName(); // USES INET TO GET HOSTNAME
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return host; // RETURNS VARIABLE HOST
	}
	
	public static int getPort() { // GET PORT
		try {
			Properties prop = new Properties(); // PROP FILE OBJECT CREATED
			FileInputStream in = new FileInputStream("Server.prop"); // GETS 'SERVER' PROP FILE FROM SYSTEM
			prop.load(in);
			in.close();
			
			port = Integer.parseInt(prop.getProperty("Port")); // GETS PORT FROM THE PROP FILE
		} catch (IOException e) {
			System.out.println(e);
		}
		return port; // RETURNS VARIABLE PORT
	}

	// for a client who logoff using the LOGOUT message
	synchronized static void remove(int id) {
		// scan the array list until we found the Id
		for(int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			// found it
			if(ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}

	public static void main(String[] args) {
		// create a server object and start it
		Server server = new Server(getPort());
		server.start();
	}
	
	// METHOD BELOW TO STORE NEW USER ACCOUNTS TO DATABASE WHICH IS CALLED ON THE CLIENT SIDE THROUGH THE REGISTER CLASS	
		public void Database(String user, String pass, String name) // CONSTRUCTOR WITH PARAMETERS --> USER(USERNAME FROM REGISTER GUI), PASS(PASSWORD FROM REGISTER GUI), NAME(NAME FROM REGISTER GUI)
		{
			try {
				Class.forName("org.h2.Driver"); 
				Connection con = DriverManager.getConnection("jdbc:h2:~/test","sa","sa"); // CONNECTION TO DATABASE WITH PASSWORD AND USERNAME 'SA'
				Statement state = con.createStatement();
							
				String table = "CREATE TABLE IF NOT EXISTS SAVEDUSERS (USERNAME VARCHAR(255) PRIMARY KEY, PASSWORD VARCHAR(255), NAME VARCHAR(255));"; // CREATES TABLE IN DATABASE IF IT DOESNT ALREADY EXISTS
				state.executeUpdate(table);

				String insertQ = "INSERT INTO SAVEDUSERS VALUES('" + user + "', '" + pass + "', '" + name + "');"; // CREATES A NEW ROW IN THE TABLE ON THE DATABASE WITH THE VALUES PASSED THROUGH TO THE CLASS
				state.executeUpdate(insertQ);

				state.close();
				con.close();
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
}