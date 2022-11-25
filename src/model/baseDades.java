package src.model;

import lib.*;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class baseDades{
    static String filePath = System.getProperty("user.dir") + "/src/database.db";
    static Connection conn = UtilsSQLite.connect(filePath);
    static String query;

    public static void checkDataBase(){
        File db = new File(filePath);
        if(!db.exists()){
            initDatabase(filePath);
        }
    }

    public static void initDatabase(String filePath){
        String pwdSalt = "ac4re21";
        String pwdPepper = "pepperCool";
        query = "DROP TABLE IF EXISTS User;";
        UtilsSQLite.queryUpdate(conn, query);

        query = "DROP TABLE IF EXISTS Salt;";
        UtilsSQLite.queryUpdate(conn, query);

        query = "DROP TABLE IF EXISTS Pepper;";
        UtilsSQLite.queryUpdate(conn, query);

        query = "CREATE TABLE User ("
                + "	id integer PRIMARY KEY AUTOINCREMENT,"
                + "	userName Varchar(15) NOT NULL,"
                + " hash Varchar(300) NOT NULL);";
        UtilsSQLite.queryUpdate(conn, query);

        query = "CREATE TABLE Snapshoot ("
                + "	id integer PRIMARY KEY AUTOINCREMENT,"
                + "	config Varchar(10000) NOT NULL,"
                + " date Data);";
        UtilsSQLite.queryUpdate(conn, query);

        query = "CREATE TABLE Salt ("
                + "	id integer PRIMARY KEY AUTOINCREMENT,"
                + "	idUser integer NOT NULL,"
                + " salt Varchar(100) NOT NULL,"
                + " foreign key(idUser) references User(id));";
        UtilsSQLite.queryUpdate(conn, query);

        query = "CREATE TABLE Pepper ("
                + "	id integer PRIMARY KEY AUTOINCREMENT,"
                + "	idUser integer NOT NULL,"
                + " pepper Varchar(100) NOT NULL,"
                + " foreign key(idUser) references User(id));";
        UtilsSQLite.queryUpdate(conn, query);

        query = "INSERT INTO User (userName, hash) VALUES ('Ivan', '"+Model.encrypt("ivan1234", pwdSalt, pwdPepper)+"');";
        UtilsSQLite.queryUpdate(conn, query);
        query = "INSERT INTO User (userName, hash) VALUES ('Marc', '"+Model.encrypt("marc1234", pwdSalt, pwdPepper)+"');";
        UtilsSQLite.queryUpdate(conn, query);
        query = "INSERT INTO User (userName, hash) VALUES ('Borja', '"+Model.encrypt("borja1234", pwdSalt, pwdPepper)+"');";
        UtilsSQLite.queryUpdate(conn, query);
        query = "INSERT INTO User (userName, hash) VALUES ('test', '"+Model.encrypt("test", pwdSalt, pwdPepper)+"');";
        UtilsSQLite.queryUpdate(conn, query);
        query = "INSERT INTO User (userName, hash) VALUES ('prueba', '"+Model.encrypt("prueba", pwdSalt, pwdPepper)+"');";
        UtilsSQLite.queryUpdate(conn, query);

        query = "INSERT INTO Salt (idUser, salt) VALUES ('"+Model.idUser(conn,"Ivan")+"', '"+pwdSalt+"');";
        UtilsSQLite.queryUpdate(conn, query);
        query = "INSERT INTO Salt (idUser, salt) VALUES ('"+Model.idUser(conn,"Marc")+"', '"+pwdSalt+"');";
        UtilsSQLite.queryUpdate(conn, query);
        query = "INSERT INTO Salt (idUser, salt) VALUES ('"+Model.idUser(conn,"Borja")+"', '"+pwdSalt+"');";
        UtilsSQLite.queryUpdate(conn, query);
        query = "INSERT INTO Salt (idUser, salt) VALUES ('"+Model.idUser(conn,"test")+"', '"+pwdSalt+"');";
        UtilsSQLite.queryUpdate(conn, query);
        query = "INSERT INTO Salt (idUser, salt) VALUES ('"+Model.idUser(conn,"prueba")+"', '"+pwdSalt+"');";
        UtilsSQLite.queryUpdate(conn, query);

        query = "INSERT INTO Pepper (idUser, pepper) VALUES ('"+Model.idUser(conn,"Ivan")+"', '"+pwdPepper+"');";
        UtilsSQLite.queryUpdate(conn, query);
        query = "INSERT INTO Pepper (idUser, pepper) VALUES ('"+Model.idUser(conn,"Marc")+"', '"+pwdPepper+"');";
        UtilsSQLite.queryUpdate(conn, query);
        query = "INSERT INTO Pepper (idUser, pepper) VALUES ('"+Model.idUser(conn,"Borja")+"', '"+pwdPepper+"');";
        UtilsSQLite.queryUpdate(conn, query);
        query = "INSERT INTO Pepper (idUser, pepper) VALUES ('"+Model.idUser(conn,"test")+"', '"+pwdPepper+"');";
        UtilsSQLite.queryUpdate(conn, query);
        query = "INSERT INTO Pepper (idUser, pepper) VALUES ('"+Model.idUser(conn,"prueba")+"', '"+pwdPepper+"');";
        UtilsSQLite.queryUpdate(conn, query);

        UtilsSQLite.disconnect(conn);

        System.out.println("BBDD Carregada correctament");

    }

}
