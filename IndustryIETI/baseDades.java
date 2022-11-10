import java.io.File;
import java.security.Principal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class baseDades{
    static String basePath = System.getProperty("user.dir");
    static String filePath = basePath +File.separator +"database.db";
    static Connection conn = connect(filePath);
    static String query;

    //Check if the Data Base exists
    public static void checkDataBase(){
        //Es comprova si la Base de dades es creada
        
        
    }

    //Create Data Base
    public static void initDatabase(String filePath){
        try {
            Statement stmnt = conn.createStatement();
            //Es crea la query que volem executar
            query = "DROP TABLE IF EXISTS User;";

            stmnt.executeUpdate(query);

            //Es crea la nova taula Faccio
            query = "CREATE TABLE User ("
                    + "	id integer PRIMARY KEY AUTOINCREMENT,"
                    + "	userName Varchar(15) NOT NULL,"
                    + " password Varchar(15) NOT NULL);";
            //Afegeixo la taula a la base de dades
            stmnt.executeUpdate(query);

            // Es crea el nous elements de la taula Faccio
            //Faccio Wu Lin
            query = "INSERT INTO User (userName, password) VALUES ('Ivan', 'ivan1234');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO User (userName, password) VALUES ('Marc', 'marc1234');";
            stmnt.executeUpdate(query);
            query = "INSERT INTO User (userName, password) VALUES ('Borja', 'borja1234');";
            stmnt.executeUpdate(query);
            

            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("BBDD Carregada correctament");
         
    }

    //Conexion BBDD
    public static Connection connect (String filePath) {
        conn = null;
        
        try {
            String url = "jdbc:sqlite:" + filePath;
            conn = DriverManager.getConnection(url);
            File FileDB = new File(filePath);
            
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
           
        } catch (SQLException e) { e.printStackTrace(); }
        initDatabase(filePath); 
        return conn;
    }

    //Execute querys
    public static ResultSet executeQuerys(String query){
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
           
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Retorno el ResultSet
        return rs;
    }

    //Disconect BBDD
    public static void disconnect (Connection conn ) {
        try {
            if (conn != null) { 
                conn.close(); 
                System.out.println("DDBB SQLite desconnectada");
            }
        } catch (SQLException ex) { System.out.println(ex.getMessage()); }
    }

}
