// In the friendly mode, when the game starts the server sends the same set of
// N questions, one after the other, to all users but the competition is not in 
// realtime: each session with a user/client is managed independently from the
// others and when all the users have answered all the questions then the final 4
// ranking is shown to all: use a timeout to conclude the quiz and print the final
// results

// NEED TO DISPLAY LEADERBOARD AT THE END
	// DONE BUT NEED TO TEST BY USING MORE THAN 1 CLIENT
	// NEEDS USERNAME ON THE LEADERBOARD AS WELL
// SERVER NEEDS TO STOP LISTENING FOR USER INPUT WHEN TIMER IS FINISHED
// TIMER NEEDS TO STOP IF QUIZ IS COMPLETED
	// CODED BUT DOES NOT WORK

public class FriendlyMode {
	static Client client;
	static String displayName;
	
	public static void start() {
		client = new Client(Client.getHost(), Client.getPort(), displayName); // STARTS CLIENT THREAD (WHICH CONNECTS TO SERVER)
		if(!client.start()) { // IF CONNECTION TO CLIENT FAILS BREAK THE LOOP
			return;
		}
		else {
			new GameTimer();
		}
	}
}