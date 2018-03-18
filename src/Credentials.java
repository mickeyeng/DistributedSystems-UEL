
public class Credentials {
     private String password;
     private String username;
     
     public String passwordGetter(){
    	 return password;
     }
     public String usernameGetter(){
    	 return username;
     }
     public void passwordSetter(String password){
    	 this.password = password;
     }
     public void usernameSetter(String username){
    	 this.username = username;
     }
     public int equals(Credentials obj){
    	 int flage = 0;
    	 if(this.username.equals(obj.username)){
    	     if(this.password.equals(obj.password)){
    	     }
    	     else{
    	    	 flage=1;
    	     }
 		}else{
 			flage=2;
 		}
    	 
    	 return flage;
     }
}