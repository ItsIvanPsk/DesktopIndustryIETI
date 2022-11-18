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
            initDatabase(filePath);
        }
    }

    public static void initDatabase(String filePath){
        try {
            Statement stmnt = conn.createStatement();
            query = "DROP TABLE IF EXISTS User;";

            stmnt.executeUpdate(query);

            query = "CREATE TABLE User ("
                    + "	id integer PRIMARY KEY AUTOINCREMENT,"
                    + "	userName Varchar(15) NOT NULL,"
                    + " password Varchar(15) NOT NULL);";
            stmnt.executeUpdate(query);

            query = "INSERT INTO User (userName, password) VALUES ('Ivan', 'ivan1234');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO User (userName, password) VALUES ('Marc', 'marc1234');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO User (userName, password) VALUES ('Borja', 'borja1234');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO User (userName, password) VALUES ('test', 'test');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO User (userName, password) VALUES ('prueba', 'prueba');";
            stmnt.executeUpdate(query);
            
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
