package src;

import java.io.IOException;
import java.net.InetSocketAddress;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.annotation.processing.SupportedOptions;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import src.model.Model;
import src.model.baseDades;
import src.utils.UtilsSQLite;
import src.view.Window;


public class Servidor extends WebSocketServer {

    String bbddPath = System.getProperty("user.dir") + "/src/database.db";
    String configPath = System.getProperty("user.dir") + "/src/config/config.xml";
    private static Scanner sc=new Scanner(System.in);
    private static int port = 8888;
    private static Servidor socket;
    Window window = new Window();
    static Model modelo = Model.getModel();
    WebSocket conn;

    private Servidor(){

    }

    public static Servidor getServidor(){
        if(socket == null){
            socket = new Servidor();
        }
        return socket;
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        baseDades.checkDataBase();
        boolean running = true;

        java.lang.System.setProperty("jdk.tls.client.protocols", "TLSv1,TLSv1.1,TLSv1.2");

        socket = new Servidor(port);
        getServidor().start();

        while (running) {
            String line = sc.nextLine();
            if (line.equals("exit")) {
                running = false;
            }
        }

        getServidor().stop(1000);

    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("MESSAGE: " + message);
        String token = message.substring(0, 3);
        if(token.equals("UV#")){
            userValidation(conn, message);
        } else if(token.equals("CF#")){
            updateApp(conn);
        } else if (token.equals("AC#")){
            appChanges(message);
        }
    }

    public void userValidation(WebSocket conn, String message){
        ArrayList<String> usuarioArray=new ArrayList<String>(Arrays.asList(message.split("#")));
            Connection conection = UtilsSQLite.connect(bbddPath);
            ResultSet rs = UtilsSQLite.querySelect(conection, "SELECT * FROM User;");
            try {
                while (rs.next()) {
                    String userName = rs.getString("userName");
                    String hash = rs.getString("hash");
                    if (modelo.passwordValidate(usuarioArray.get(1),usuarioArray.get(2))) {
                        String msg = "UV#" + userName + "#" + hash.substring(hash.length() - 10) + "#true";
                        System.out.println("Server sends: " + msg);
                        conn.send(msg);
                        break;
                    } else {
                        String msg = "UV#" + userName + "#" + hash.substring(hash.length() - 10) + "#false";
                        System.out.println("Server sends: " + msg);
                        conn.send(msg);
                        break;
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error relacionado con sql");
            }
    }

    public void updateApp(WebSocket conn){
        String msg = Model.getModel().recorrerArrays();
        conn.send(msg);
    }

    public void appChanges(String message){
        System.out.println(message);
        String[] splitedMessage = message.split("#");
        System.out.println(modelo.getSwitchsObj().size());
        switch (splitedMessage[1]) {
            case "SW":
                Integer sw_index = modelo.findObjectWithId(Integer.parseInt(splitedMessage[2]));
                if(splitedMessage[3].equals("true")){
                    modelo.getSwitchsObj().get(sw_index).setDef("on");
                } else {
                    modelo.getSwitchsObj().get(sw_index).setDef("off");
                }
                window.loadAllComponents();
                goUpdateApp();
                break;
            case "SL":
                Integer sl_index = modelo.findObjectWithId(Integer.parseInt(splitedMessage[2]));
                modelo.getSlidersObj().get(sl_index).setDef(Integer.parseInt(splitedMessage[3]));
                window.loadAllComponents();
                goUpdateApp();
                break;
            case "DD":
                System.out.println(splitedMessage[3]);
                Integer dd_index = modelo.findObjectWithId(Integer.parseInt(splitedMessage[2]));
                modelo.getDropDownsObj().get(dd_index).setDef(Integer.parseInt(splitedMessage[3]));
                window.loadAllComponents();
                goUpdateApp();
                break;
        }
    }

    public void goUpdateApp(){
        broadcast(Model.getModel().recorrerArrays());
        System.out.println("goUpdateApp");
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
        setupConn(conn);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println(conn + " s'ha desconnectat");
    }

    public void setupConn(WebSocket _conn){
        this.conn = _conn;
    }
    public WebSocket getConn(){
        return this.conn;
    }


    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() { }

}