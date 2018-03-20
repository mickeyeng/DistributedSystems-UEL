// In practice mode the user will receive N questions one after another and
// there is no timeout and no competition with other users: the final result 
// will be sent to the user after answering the last question.

public class PracticeMode {
	static Client client;
	static String displayName;
	
	public static void start() {
		client = new Client(Client.getHost(), Client.getPort(), displayName, null); // STARTS CLIENT THREAD (WHICH CONNECTS TO SERVER)
		if(!client.start()) { // IF CONNECTION TO CLIENT FAILS BREAK THE LOOP
			return;
		}
	}
}