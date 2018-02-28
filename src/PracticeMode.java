// In practice mode the user will receive N questions one after another and
// there is no timeout and no competition with other users: the final result 
// will be sent to the user after answering the last question.

// QUESTIONS NEED TO LOAD FROM THE SERVER

import java.io.*;
import java.util.*;

public class PracticeMode {
public static void main (String[] args) throws IOException {
//	Client client = new Client(Client.getHost(), Client.getPort());

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
    int score = 0;
    
    BufferedReader reader = new BufferedReader(new FileReader("practiceMode.txt"));
    Scanner sc = new Scanner(System.in);

    QuizClass run = new QuizClass(i, question, choice1, choice2, choice3, choice4, choice5, answer);

    while((line = reader.readLine()) != null){
        //Finds the question
        if(line.contains("?")){
            run.question = line;
            System.out.println(run.getQuestion());
        }

        //Finds answer "1"
        if(line.contains("1)")){
            run.choice1 = line;
            System.out.println(run.getChoice1());
        }

        //Finds answer "2"
        if(line.contains("2)")){
            run.choice2 = line;
            System.out.println(run.getChoice2());
        }

        //Finds answer "3"
        if(line.contains("3)")){
            run.choice3 = line; 
            System.out.println(run.getChoice3());
        }
        //Finds answer "4"
        if(line.contains("4)")){
            run.choice4 = line;
            System.out.println(run.getChoice4());
        }
        
      //Finds answer "5"
        if(line.contains("5)")){
            run.choice5 = line;
            System.out.println(run.getChoice5() + "\n");
        }

        //Finds the correct answer for the question
        if(line.contains("Correct Answer: ")){
            String[] a = line.split(": ");
            answer = a[1];
            run.answer = answer;

            System.out.print("Your Answer: ");
            userAnswer = sc.next();

                //Checks if the user's input matches the correct answer from the file
                if(userAnswer.equalsIgnoreCase(answer)){
                    System.out.println("Correct!\n");
                    i++;
                    score++;
                } 

                //Checks if the user's input doesn't match the correct answer from the file
                else if (!userAnswer.equalsIgnoreCase(answer)) {
                    System.out.println("Wrong, the correct answer was: " + run.getAnswer());
                    i++;
                }
            }
        }
    	
    	sc.close();
    	reader.close();
    	
        System.out.println("");
        System.out.println("Final score: " + score);
        System.out.println("Good practice run!");
	}	
}
