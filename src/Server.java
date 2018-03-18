// NEED TO READ USER INPUT IN GUI INSTEAD OF CMD
	// CAN BE DONE LAST AFTER ALL MODES ARE ATTEMPTED
// NEED A LEADERBOARD FOR FRIENDLY AND TOURNAMENT MODE
	// NEED TO TEST WITH MORE THAN 1 CLIENT
// NEED TO ASK USER HOW MANY PLAYERS 
// NEED TO CREATE THREADS TO MAKE USERS WAIT FOR EACH OTHER IF CLIENT IS MORE THAN 1
	// FOR FRIENDLY AND TOURNAMENT MODE

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.Date;
import java.sql.*;

public class Server {
	static ArrayList<ServerThread> clients = new ArrayList<ServerThread>(); // LIST OF ONLINE USERS
	static ServerGUI sgui;
	static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	static int port;
	boolean keepGoing;
	ServerSocket serverSocket;
	static Socket socket;

	public Server(int p, ServerGUI sg) { // CONTRUCTOR
		sgui = sg;
		port = p;		
	}
	
	public void start() {
		keepGoing = true;
		try 
		{
			serverSocket = new ServerSocket(port); // CREATES SERVER SOCKET
			display("Server waiting for Clients on port " + port);
			
			while(keepGoing) 
			{				
				socket = serverSocket.accept(); // LISTENS FOR USER JOINING TO SERVER
				
				if(!keepGoing) {
					break;
				}
				
				ServerThread client = new ServerThread(socket); // CREATES SERVERTHREAD  
				clients.add(client); // ADDS CLIENT TO THE ARRAY						
				client.start(); // STARTS THE THREAD
				broadcast(null);
			}

			try {
				serverSocket.close();
				for(int i = 0; i < clients.size(); ++i) {
					ServerThread tc = clients.get(i);
					try {
						tc.in.close();
						tc.out.close();
						tc.socket.close();
					}
					catch(IOException e) {
						e.printStackTrace();
					}
					finally {
						try {
							socket.close();
						} catch(IOException e) {
							e.printStackTrace();
						}
						try {
							serverSocket.close();
						} catch(IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			catch(Exception e) {
				display("Exception closing the server and clients: " + e);
				e.printStackTrace();
			}
		}
		
		catch (IOException e) {
			display(sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n");
			e.printStackTrace();
		}
	}		

	public static void display(String msg) { // DISPLAY METHOD TO UPDATE EVENT LOG ON SERVER GUI
		String eventUpdate = sdf.format(new Date()) + " - " + msg + "\n";
		sgui.appendEvent(eventUpdate);
	}

	static int score = 0;
	
	public synchronized static void broadcast(String message) { // BROADCAST TO SEND MESSAGE TO ALL CLIENT CONNECTED
		String question = "";
	    String choice1 = "";
	    String choice2 = "";
	    String choice3 = "";
	    String choice4 = "";
	    String choice5 = "";
	    String answer = "";
	    String line;
	    String userAnswer;
	    int i = 0;
	    String uname = ServerThread.username; // GETS USERNAME FROM SERVERTHREAD CLASS
	    
		try {
			BufferedReader reader = new BufferedReader(new FileReader("questions.txt")); // FINDS THE 'questions.txt' FILE ON THE LOCAL MACHINE
			Scanner sc = new Scanner(System.in);
			QuizClass run = new QuizClass(i, question, choice1, choice2, choice3, choice4, choice5, answer);

			while((line = reader.readLine()) != null){
			    //Finds the question
			    if(line.contains("?")){
			        run.question = line;
			        System.out.println(run.question);
			        sgui.appendEvent("\nQuestion: " + run.getQuestion() + "\n");
			        
			        for(int i1 = clients.size(); --i1 >= 0;) {
						ServerThread ct = clients.get(i1);
						if(!ct.writeMsg(run.question)) {
							clients.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
			    }

			    //Finds answer "1"
			    if(line.contains("1)")){
			        run.choice1 = line + "\n";
			        System.out.print(run.choice1);
			        sgui.appendEvent(run.getChoice1());
			        for(int i1 = clients.size(); --i1 >= 0;) {
						ServerThread ct = clients.get(i1);
						if(!ct.writeMsg(run.choice1)) {
							clients.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
			    }

			    //Finds answer "2"
			    if(line.contains("2)")){
			        run.choice2 = line + "\n";
			        System.out.print(run.choice2);
			        sgui.appendEvent(run.getChoice2());
			        for(int i1 = clients.size(); --i1 >= 0;) {
						ServerThread ct = clients.get(i1);
						if(!ct.writeMsg(run.choice2)) {
							clients.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
			    }

			    //Finds answer "3"
			    if(line.contains("3)")){
			        run.choice3 = line + "\n"; 
			        System.out.print(run.choice3);
			        sgui.appendEvent(run.getChoice3());
			        for(int i1 = clients.size(); --i1 >= 0;) {
						ServerThread ct = clients.get(i1);
						if(!ct.writeMsg(run.choice3)) {
							clients.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
			    }
			    
			    //Finds answer "4"
			    if(line.contains("4)")){
			        run.choice4 = line + "\n";
			        System.out.print(run.choice4);
			        sgui.appendEvent(run.getChoice4());
			        for(int i1 = clients.size(); --i1 >= 0;) {
						ServerThread ct = clients.get(i1);
						if(!ct.writeMsg(run.choice4)) {
							clients.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
			    }
			    
			    //Finds answer "5"
			    if(line.contains("5)")){
			        run.choice5 = line + "\n";
			        System.out.print(run.choice5);
			        sgui.appendEvent(run.getChoice5());
			        for(int i1 = clients.size(); --i1 >= 0;) {
						ServerThread ct = clients.get(i1);
						if(!ct.writeMsg(run.choice5)) {
							clients.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
			    }
			    
		    	//Finds the correct answer for the question
			    if(line.contains("Correct Answer: ")){
			        String[] a = line.split(": ");
			        answer = a[1];
			        run.answer = answer;

					sgui.appendEvent("Waiting for users answer\n"); 
					for(int i3 = clients.size(); --i3 >= 0;) {
						ServerThread ct = clients.get(i3);
						if(!ct.writeMsg("Please enter you answer now\n\n")) {
							clients.remove(i3);
							display("Disconnected Client. User was removed from list");
						}
					}
						
				 System.out.print("\nYour Answer: ");
				 userAnswer = sc.next();
				
				sgui.appendEvent(uname + " has entered: " + userAnswer + "\n"); 

				for(int i3 = clients.size(); --i3 >= 0;) {
					ServerThread ct = clients.get(i3);
					if(!ct.writeMsg("Your answer: " + userAnswer)) {
						clients.remove(i3);
						display("Disconnected Client. User was removed from list");
					}
				}
						
	            //Checks if the user's input matches the correct answer from the file
	            if(userAnswer.equalsIgnoreCase(answer)){
	            	for(int i1 = clients.size(); --i1 >= 0;) {
						ServerThread ct = clients.get(i1);
						System.out.println("Correct!\n");
						sgui.appendEvent(uname + " got the correct answer\n\n");
						if(!ct.writeMsg("\nCorrect!\n\n")) {
							clients.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}
	                i++;
	                score++;
	            } 

	            //Checks if the user's input doesn't match the correct answer from the file
	            else if (!userAnswer.equalsIgnoreCase(answer)) {
	            	for(int i1 = clients.size(); --i1 >= 0;) {
						ServerThread ct = clients.get(i1);
						System.out.println("Wrong Answer. Correct Answer Was: " + run.getAnswer() + "\n");
						sgui.appendEvent(uname + " got the wrong answer. The correct answer was: " + run.getAnswer() + "\n\n");
						if(!ct.writeMsg("\nWrong Answer. Correct Answer Was: " + run.getAnswer() + "\n\n")) {
							clients.remove(i1);
							display("Disconnected Client. User was removed from list");
						}
					}			            	
	            	i++;
	            }
		    }
	    } 
	    sc.close();
    	reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(int i1 = clients.size(); --i1 >= 0;) {
			ServerThread ct = clients.get(i1);
			System.out.println("Final Score: " + score + "\nGood Attempt!");
//			if(clients.size() > 1) { // RUNS ONLY IF THERE IS MORE THAN ONE CLIENT CONNECTED				
				leaderboard(); // CALLS THE LEADBOARD METHOD ON THIS CLASS TO PRINT THE FINAL RANKING
//			}
			if(!ct.writeMsg("Final Score: " + score + "\nGood Attempt!")) {
				clients.remove(i1);
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
		String uname = ServerThread.username; // GETS THE USERNAME FROM THE SERVERTHREAD CLASS
		
		myMap.put(uname, score); // ADDS 2 VALUES TO THE MAP OBJECT CREATED - USERNAME FROM THE SERVERTHREAD CLASS AND SCORE VALUE
        System.out.println(myMap.toString()); // PRINTS THE MAP VALUES TO THE CMD
	}
	
	public static String getHost() { // GET HOST METHOD
		String host = "";
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
	
	public static boolean writeMsg(String msg) { 
	    if(!socket.isConnected()) {
	        try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    return false;
	}
	
	// METHOD BELOW NEEDS TO BE ADDEDD IF THE GUI IS CREATED AS USERS ONLINE NEED TO BE SHOWN
	
	//	public synchronized static void whosonline(){
	//		for(int i = 0; i < clients.size(); ++i) {
	//			ServerThread ct = clients.get(i);
	//	        writeMsg((i+1) + ") " + ct.username);
	//	    }
	//	}
	
	// METHOD BELOW TO STORE NEW USER ACCOUNTS TO DATABASE WHICH IS CALLED ON THE CLIENT SIDE THROUGH THE REGISTER CLASS	
	public void Database(String user, String pass, String name) // CONSTRUCTOR WITH PARAMETERS --> USER(USERNAME FROM REGISTER GUI), PASS(PASSWORD FROM REGISTER GUI), NAME(NAME FROM REGISTER GUI)
	{
		try {
			Class.forName("org.h2.Driver"); 
			Connection con = DriverManager.getConnection("jdbc:h2:~/test","sa","sa"); // CONNECTION TO DATABASE WITH PASSWORD AND USERNAME 'SA'
			Statement state = con.createStatement();
						
			String table = "CREATE TABLE IF NOT EXISTS SAVEDUSERS (USERNAME VARCHAR(255) PRIMARY KEY, PASSWORD VARCHAR(255), NAME VARCHAR(255));"; // CREATES TABLE IN DATABASE IF IT DOESNT ALREADY EXISTS
			int n = state.executeUpdate(table);

			String insertQ = "INSERT INTO SAVEDUSERS VALUES('" + user + "', '" + pass + "', '" + name + "');"; // CREATES A NEW ROW IN THE TABLE ON THE DATABASE WITH THE VALUES PASSED THROUGH TO THE CLASS
			int n1 = state.executeUpdate(insertQ);

			state.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}