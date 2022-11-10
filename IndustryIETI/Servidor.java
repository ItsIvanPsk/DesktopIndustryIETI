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


public class Servidor extends WebSocketServer {

    String basePath = System.getProperty("user.dir") + "/";
    String bbddPath = basePath + "database.db";
    private static Scanner sc=new Scanner(System.in);
    private static int port = 4810; 
    private static Servidor socket;

    public static void main(String[] args) throws InterruptedException, IOException {
        

        boolean running = true;

        // Deshabilitar SSLv3 per clients Android
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
    //Function for when the client connects
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

    }
    //Function for when the client disconnects
    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    //Function that receives the user and password and checks if it exists.
    @Override
    public void onMessage(WebSocket conn, String usuario) {
        boolean existe=false;
        ArrayList<String> usuarioArray=new ArrayList<String>(Arrays.asList(usuario.split("#")));
        Connection conection = UtilsSQLite.connect(bbddPath);
        ResultSet rs = UtilsSQLite.querySelect(conection, "SELECT * FROM User;");
        try {
            while (rs.next()) {
                Usuario user=new Usuario(rs.getString("userName"), rs.getString("password"));
                if (user.getUsername().equals(usuarioArray.get(0)) && user.getPassword().equals(usuarioArray.get(1))){
                existe=true;
                }
            }
        if (existe){
            conn.send("true");
        }
        else{

            conn.send("false");
        }
        } catch (SQLException e) {
            System.out.println("Error relacionado con sql");
        }
    }
    //Function for error messages
    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }
    //Function for server startup
    @Override
    public void onStart() {
        
    }

    public String getConnectionId (WebSocket connection) {
        String name = connection.toString();
        return name.replaceAll("org.java_websocket.WebSocketImpl@", "").substring(0, 3);
    }
    
    
    }
 

    
 