import java.util.ArrayList;
import java.util.List;

public class Users {
	
    static private int id = 0;
	
    List<User> user;
    
    public Users(){
        this.user = new ArrayList<User>();
    }
    
    public void addUser(){
    }
}
class User{
    int userId;
    
    User(int uid){
        this.userId = uid;
    }

}
