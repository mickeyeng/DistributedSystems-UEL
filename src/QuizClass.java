
public class QuizClass {
String choice1 = "";
String choice2 = "";
String choice3 = "";
String choice4 = "";
String choice5 = "";
String answer = "";
String question = "";
int i = 0;
public QuizClass(int i, String question, String choice1, String choice2, String choice3, String choice4, String choice5, String answer) {
this.choice1 = choice1;
this.choice2 = choice2;
this.choice3 = choice3;
this.choice4 = choice4;
this.choice5 = choice5;
this.answer = answer;
this.question = question;
this.i = i;
}
public String getChoice1() {
return choice1;
}
public String getChoice2() {
return choice2;
}
public String getChoice3() {
return choice3;
}
public String getChoice4() {
return choice4;
}
public String getChoice5() {
return choice5;
}
public String getQuestion() {
return question;
}
public String getAnswer() {
return answer;
}
}