// In tournament mode, when the game starts the server send to all the clients
// a random question among N quizzes with the 5 possible answers that will be
// visualized to the users (only one is correct).
	// a. The first user sending the correct answer will get 1 point.
	// b. The winning user and the correct answer will be communicated to all
	// the users, along with the current rank.
	// c. After 10 questions the game ends and the final rank is visualized: a
	// congratulation message is sent to the winner and a consolation
	// message is sent to the others.

// NEED TO DISPLAY LEADERBOARD AT THE END
	// DONE BUT NEED TO TEST BY USING MORE THAN 1 CLIENT
	// NEEDS USERNAME ON THE LEADERBOARD AS WELL
// SERVER NEEDS TO STOP LISTENING FOR USER INPUT WHEN TIMER IS FINISHED
// TIMER NEEDS TO STOP IF QUIZ IS COMPLETED
	// CODED BUT DOES NOT WORK

public class TournamentMode {
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