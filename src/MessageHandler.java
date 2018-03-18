import java.io.*;

public class MessageHandler implements Serializable { // CLASS TO SEND MESSAGES BETWEEN CLIENT AND SERVER
	
	static final int MESSAGE = 1;
	int type;
	String message;
	
	MessageHandler(int t, String mess) { // CONSTRUCTOR
		type = t;
		message = mess;
	}

	public int getType() { // TYPE OF MESSAGE (IS IT INT OR STRING)
		return type;
	}
	
	public String getMessage() { // GETS MESSAGE
		return message;
	}
}