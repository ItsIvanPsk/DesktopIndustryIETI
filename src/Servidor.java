package src;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import src.model.Model;
import src.model.baseDades;
import src.utils.UtilsSQLite;
import src.view.Window;


public class Servidor extends WebSocketServer {

    String bbddPath = System.getProperty("user.dir") + "/src/database.db";
    String configPath = System.getProperty("user.dir") + "/src/config.xml";
    private static Scanner sc=new Scanner(System.in);
    private static int port = 8888; 
    private static Servidor socket;
    static Model modelo;

    public static void main(String[] args) throws InterruptedException, IOException {
        
        baseDades.checkDataBase();
        modelo = new Model();
        new Window();

        boolean running = true;

        java.lang.System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2");

        socket = new Servidor(port);
        socket.start();

        while (running) {
            String line = sc.nextLine();
            if (line.equals("exit")) {
                running = false;
            }
        }    

        socket.stop(1000);
    }

    public Servidor(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    public Servidor(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        String host = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        System.out.println(host + " s'ha connectat");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn + " s'ha desconnectat");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("MESSAGE: " + message);
        String token = message.substring(0, 3);
        if(token.equals("UV#")){
            ArrayList<String> usuarioArray=new ArrayList<String>(Arrays.asList(message.split("#")));
            Connection conection = UtilsSQLite.connect(bbddPath);
            ResultSet rs = UtilsSQLite.querySelect(conection, "SELECT * FROM User;");
            try {
                while (rs.next()) {
                    String userName = rs.getString("userName");
                    String password = rs.getString("password");
                    if (userName.equals(usuarioArray.get(1)) 
                            && password.equals(usuarioArray.get(2))){
                        String msg = "UV#" + userName + "#" + password + "#true";
                        conn.send(msg);
                        break;
                    } else{
                        String msg = "UV#" + userName + "#" + password + "#false";
                        conn.send(msg);
                        break;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error relacionado con sql");
            }
        } else if(token.equals("CF#")){
            Model.lecturaXMLApp(new File(configPath));
            conn.send(
                modelo.recorrerArrays(
                    modelo.getSwitchs(),
                    modelo.getSliders(),
                    modelo.getDropDowns(),
                    modelo.getSensors()
                )
            );
        }
    }
    @Override
    public void onError(WebSocket conn, Exception ex) { ex.printStackTrace(); }
    @Override
    public void onStart() { }

}