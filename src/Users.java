import java.util.ArrayList;
import java.util.List;

public class Users {
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
