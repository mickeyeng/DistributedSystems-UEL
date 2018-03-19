
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;

public class QuizTimer {
	static Timer timer;
	static JTextField textField;  
	static int interval;

    public QuizTimer() {
    	JFrame stopwatch = new JFrame();
    	stopwatch.setTitle("Timer");
    	stopwatch.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	stopwatch.setSize(259, 115);
    	stopwatch.setLocation(300, 300);
    	stopwatch.getContentPane().setLayout(null);
        
        JLabel lblTimeLeft = new JLabel("Time Left:");
        lblTimeLeft.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTimeLeft.setBounds(10, 22, 69, 32);
        stopwatch.getContentPane().add(lblTimeLeft);
        
        textField = new JTextField();
        textField.setEditable(false);
        textField.setBounds(99, 22, 123, 32);
        stopwatch.getContentPane().add(textField);
        textField.setColumns(10);
        
        System.out.println("Timer Started!");
        JOptionPane.showMessageDialog(null, "Timer Started!", "Warning", JOptionPane.ERROR_MESSAGE);
        
        int secs = 1*60;
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = secs;
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
            	int count = setInterval();
            	textField.setText(Integer.toString(count) + " seconds");

            }
        }, delay, period);

        stopwatch.setVisible(true);
    }

    private static final int setInterval() {
        if (interval == 1) {
        	JOptionPane.showMessageDialog(null, "Times Up!", "Warning", JOptionPane.ERROR_MESSAGE);
            timer.cancel(); //Terminate the timer thread
            for(int i1 = Server.clients.size(); --i1 >= 0;) {
    			ServerThread ct = Server.clients.get(i1);
    			System.out.println("\nFinal Score: " + Server.score + "\nGood Attempt!");
    			Server.clients.remove(i1);
//    			if(clients.size() > 1) { // RUNS ONLY IF THERE IS MORE THAN ONE CLIENT CONNECTED	
    				Server.leaderboard(); // CALLS THE LEADBOARD METHOD ON THIS CLASS TO PRINT THE FINAL RANKING
//    			}
            }
        }
        return --interval;
    }
}
