package src.model;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class baseDades{
    static String filePath = System.getProperty("user.dir") + "/src/database.db";
    static Connection conn = connect(filePath);
    static String query;

    public static void checkDataBase(){
        File db = new File(filePath);
        if(!db.exists()){
            System.out.println("si puto entro");
            initDatabase(filePath);
        }
    }

    public static void initDatabase(String filePath){
        try {
            String pwdSalt = "ac4re21";
            String pwdPepper = "pepperCool";
            Statement stmnt = conn.createStatement();
            query = "DROP TABLE IF EXISTS User;";
            stmnt.executeUpdate(query);

            query = "DROP TABLE IF EXISTS Salt;";
            stmnt.executeUpdate(query);

            query = "DROP TABLE IF EXISTS Pepper;";
            stmnt.executeUpdate(query);

            query = "CREATE TABLE User ("
                    + "	id integer PRIMARY KEY AUTOINCREMENT,"
                    + "	userName Varchar(15) NOT NULL,"
                    + " hash Varchar(300) NOT NULL);";
            stmnt.executeUpdate(query);

            query = "CREATE TABLE Salt ("
                    + "	id integer PRIMARY KEY AUTOINCREMENT,"
                    + "	idUser integer NOT NULL,"
                    + " salt Varchar(100) NOT NULL,"
                    + " foreign key(idUser) references User(id));";
            stmnt.executeUpdate(query);

            query = "CREATE TABLE Pepper ("
                    + "	id integer PRIMARY KEY AUTOINCREMENT,"
                    + "	idUser integer NOT NULL,"
                    + " pepper Varchar(100) NOT NULL,"
                    + " foreign key(idUser) references User(id));";
            stmnt.executeUpdate(query);

            query = "INSERT INTO User (userName, hash) VALUES ('Ivan', '"+Model.encrypt("ivan1234", pwdSalt, pwdPepper)+"');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO User (userName, hash) VALUES ('Marc', '"+Model.encrypt("marc1234", pwdSalt, pwdPepper)+"');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO User (userName, hash) VALUES ('Borja', '"+Model.encrypt("borja1234", pwdSalt, pwdPepper)+"');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO User (userName, hash) VALUES ('test', '"+Model.encrypt("test", pwdSalt, pwdPepper)+"');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO User (userName, hash) VALUES ('prueba', '"+Model.encrypt("prueba", pwdSalt, pwdPepper)+"');";
            stmnt.executeUpdate(query);

            query = "INSERT INTO Salt (idUser, salt) VALUES ('"+Model.idUser(conn,"Ivan")+"', '"+pwdSalt+"');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO Salt (idUser, salt) VALUES ('"+Model.idUser(conn,"Marc")+"', '"+pwdSalt+"');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO Salt (idUser, salt) VALUES ('"+Model.idUser(conn,"Borja")+"', '"+pwdSalt+"');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO Salt (idUser, salt) VALUES ('"+Model.idUser(conn,"test")+"', '"+pwdSalt+"');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO Salt (idUser, salt) VALUES ('"+Model.idUser(conn,"prueba")+"', '"+pwdSalt+"');";
            stmnt.executeUpdate(query);

            query = "INSERT INTO Pepper (idUser, pepper) VALUES ('"+Model.idUser(conn,"Ivan")+"', '"+pwdPepper+"');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO Pepper (idUser, pepper) VALUES ('"+Model.idUser(conn,"Marc")+"', '"+pwdPepper+"');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO Pepper (idUser, pepper) VALUES ('"+Model.idUser(conn,"Borja")+"', '"+pwdPepper+"');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO Pepper (idUser, pepper) VALUES ('"+Model.idUser(conn,"test")+"', '"+pwdPepper+"');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO Pepper (idUser, pepper) VALUES ('"+Model.idUser(conn,"prueba")+"', '"+pwdPepper+"');";
            stmnt.executeUpdate(query);
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("BBDD Carregada correctament");
         
    }

    public static Connection connect (String filePath) {
        conn = null;
        try {
            String url = "jdbc:sqlite:" + filePath;
            conn = DriverManager.getConnection(url);            
           
        } catch (SQLException e) { e.printStackTrace(); }
        initDatabase(filePath); 
        return conn;
    }

    public static void disconnect (Connection conn ) {
        try {
            if (conn != null) { 
                conn.close(); 
                System.out.println("DDBB SQLite desconnectada");
            }
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }


}
