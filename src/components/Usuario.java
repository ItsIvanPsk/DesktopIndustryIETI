package src.components;
public class Usuario {
    String username;
    String password;
    Usuario(String username,String password){
        this.username=username;
        this.password=password;
    }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
