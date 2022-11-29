package src.model;

import java.awt.Color;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.annotation.processing.SupportedOptions;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.password4j.Password;
import src.Servidor;
import src.components.Controls;
import src.components.Dropdown;
import src.components.Option;
import src.components.Sensor;
import src.components.Slider;
import src.components.Switch;
import src.utils.ServerUtils;
import src.utils.UtilsSQLite;
import src.view.CustomsDialogs;
import src.view.Window;

public class Model implements ServerUtils{
    // App
	private ArrayList<String> switchs = new ArrayList<String>();
    private ArrayList<String> sliders = new ArrayList<String>();
    private ArrayList<String> dropdowns = new ArrayList<String>();
    private ArrayList<String> sensors = new ArrayList<String>();
    private ArrayList<String> controls = new ArrayList<String>();

    // Desktop
	private ArrayList<Switch> switchs_obj = new ArrayList<>();
    private ArrayList<Slider> sliders_obj = new ArrayList<>();
    private ArrayList<Dropdown> dropdowns_obj = new ArrayList<>();
    private ArrayList<Sensor> sensors_obj = new ArrayList<>();
    private ArrayList<Controls> control_obj = new ArrayList<>();

    private static Model instance;

    private Model(){

    }

    public static Model getModel(){
        if(instance == null){
            instance = new Model();
        }
        return instance;
    }

    /*

        Variables con GETTERS/SETTERS en vez de usar static

        1 METODO para obtener del XML y crear los objetos en los respectivos Arrays
        1 METODO para generar la interfaz, este metodo cogerá los 4 arrays y generará la
            interfaz desde 0 con los objetos de los arrays.
        1 METODO para recorrer los arrays y generar string para la App

        1 METODO para encontrar un objeto con una id en concreto y retornarlo //WIP
        1 METODO para actualizar el objeto deseado en los Arrays (Puede ser que también se deba de cambiar el XML) //WIP
        1 METODO para encriptar la contraseña //WIP
        1 METODO 'Salt&Pepper' para desencriptar la contraseña //WIP

     */


    public static void lecturaXML(File file) {

        if (!errorValidate.attributeNumValidate(file)){
            CustomsDialogs.numAttributeDialog(Window.getVentana());
            System.out.println("NUM DE ATRIBUTOS MAL");
        }
        else if (!errorValidate.checkoutControls(file)){
            CustomsDialogs.controlDialog(Window.getVentana());
            System.out.println("CONTROLS MAL");
        }
        else if (!errorValidate.idValidate(file)){
            CustomsDialogs.idDialog(Window.getVentana());
            System.out.println("ID MAL");
        }
        else if (!errorValidate.nullValues(file)){
            CustomsDialogs.nullValueDialog(Window.getVentana());
            System.out.println("VALOR NULO");
        }
        else if (!errorValidate.typeValueError(file)){
            CustomsDialogs.typeValueErrorDialog(Window.getVentana());
            System.out.println("TIPO DE DATO MAL");
        }
        else if (!errorValidate.switchTextValidate(file)){
            CustomsDialogs.switchTextDialog(Window.getVentana());
            System.out.println("SWITCH TEXT VAL");
        }
        else{
            try {
                getModel().getSwitchs().clear();
                getModel().getSliders().clear();
                getModel().getDropDowns().clear();
                getModel().getSensors().clear();
                getModel().getControls().clear();

                getModel().getSwitchsObj().clear();
                getModel().getSlidersObj().clear();
                getModel().getDropDownsObj().clear();
                getModel().getSensorsObj().clear();

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);

                doc.getDocumentElement().normalize();
                NodeList blockList = doc.getElementsByTagName("root");
                for(int cont = 0; cont < blockList.getLength(); cont++) {
                    NodeList listaControles = doc.getElementsByTagName("controls");
                    for(int i = 0; i < listaControles.getLength(); i++) {
                        Node nodeControls = listaControles.item(i);
                        if(nodeControls.getNodeType() == Node.ELEMENT_NODE) {
                            Element elmContr = (Element) nodeControls;
                            getModel().controls.add(elmContr.getAttribute("name"));

                        }

                    }

                    for(int cnt = 0; cnt < listaControles.getLength(); cnt++) {
                        Node nodeControl = listaControles.item(cnt);
                        if(nodeControl.getNodeType() == Node.ELEMENT_NODE) {
                            Element elm = (Element) nodeControl;
                            NodeList listaSwitch = elm.getElementsByTagName("switch");
                            for(int i = 0; i < listaSwitch.getLength(); i++) {
                                Node nodeSwitch = listaSwitch.item(i);
                                if(nodeSwitch.getNodeType() == Node.ELEMENT_NODE) {
                                    Element elmSwi = (Element) nodeSwitch;
                                    Switch switch_obj = new Switch(
                                        Integer.parseInt(elmSwi.getAttribute("id")),
                                        getModel().controls.get(cnt),
                                        elmSwi.getAttribute("default"),
                                        elmSwi.getTextContent()
                                    );
                                    getModel().getSwitchs().add(switch_obj.toString());
                                    getModel().getSwitchsObj().add(switch_obj);
                                }

                            }
                            NodeList listaSlider = elm.getElementsByTagName("slider");
                            for(int i = 0; i < listaSlider.getLength(); i++) {
                                Node nodeSlider = listaSlider.item(i);
                                if(nodeSlider.getNodeType() == Node.ELEMENT_NODE) {
                                    Element elmSli = (Element) nodeSlider;
                                    Slider slider = new Slider(
                                        Integer.parseInt(elmSli.getAttribute("id")),
                                        getModel().controls.get(cnt),
                                        Integer.parseInt(elmSli.getAttribute("default")),
                                        Integer.parseInt(elmSli.getAttribute("min")),
                                        Integer.parseInt(elmSli.getAttribute("max")),
                                        Integer.parseInt(elmSli.getAttribute("step")),
                                        elmSli.getTextContent());

                                    getModel().getSliders().add(slider.toString());
                                    getModel().getSlidersObj().add(slider);
                                }
                            }
                            NodeList listaDropDown = elm.getElementsByTagName("dropdown");
                            for(int i = 0; i < listaDropDown.getLength(); i++) {
                                Node nodeDropDown = listaDropDown.item(i);
                                if(nodeDropDown.getNodeType() == Node.ELEMENT_NODE) {
                                    Element elmDrop = (Element) nodeDropDown;
                                    int id = Integer.parseInt(elmDrop.getAttribute("id"));
                                    int def= Integer.parseInt(elmDrop.getAttribute("default"));
                                    String lab= elmDrop.getAttribute("label");
                                    ArrayList<String> option = new ArrayList<String>();

                                    NodeList listaOption= elmDrop.getElementsByTagName("option");
                                    for(int j = 0; j < listaOption.getLength(); j++) {
                                        Node nodeOption = listaOption.item(j);
                                        if(nodeOption.getNodeType() == Node.ELEMENT_NODE) {
                                            Element elmOpti = (Element) nodeOption;
                                            option.add(
                                                new Option(
                                                    Integer.parseInt(elmOpti.getAttribute("value")),
                                                    elmOpti.getTextContent()).toString()
                                                );
                                        }

                                    }
                                    Dropdown dropdown = new Dropdown(id,getModel().controls.get(cnt),def,lab,option);
                                    getModel().getDropDowns().add(dropdown.toString());
                                    getModel().getDropDownsObj().add(dropdown);
                                }
                            }

                            NodeList listaSensor = elm.getElementsByTagName("sensor");
                            for(int i = 0; i < listaSensor.getLength(); i++) {
                                Node nodeSensor = listaSensor.item(i);
                                if(nodeSensor.getNodeType() == Node.ELEMENT_NODE) {
                                    Element elmSen = (Element) nodeSensor;
                                    Sensor sensor = new Sensor(
                                        Integer.parseInt(elmSen.getAttribute("id")),
                                        getModel().controls.get(cnt),
                                        elmSen.getAttribute("units"),
                                        Integer.parseInt(elmSen.getAttribute("thresholdlow")),
                                        Integer.parseInt(elmSen.getAttribute("thresholdhigh")),
                                        elmSen.getTextContent());

                                    getModel().getSensors().add(sensor.toString());
                                    getModel().getSensorsObj().add(sensor);
                                }
                            }
                        }
                    }

                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        getModel().recorrerArrays();
    }


    public String recorrerArrays() {
        String appComponentes = "CF" + componentSep + getModel().controls.size() + componentSep;

        if(getModel().getSwitchsObj().size() != 0){
            for (int i = 0; i < getModel().getSwitchsObj().size(); i++) {
                System.out.println(getModel().getSwitchsObj().get(i).toString());
                appComponentes = appComponentes + getModel().getSwitchsObj().get(i).toString() + componentSep;
            }
        }

        if(getModel().getSlidersObj().size() != 0){
            for (int i = 0; i < getModel().getSlidersObj().size(); i++) {
                appComponentes = appComponentes + getModel().getSlidersObj().get(i).toString() + componentSep;
            }
        }

        if(getModel().getSensorsObj().size() != 0){
            for (int i = 0; i < getModel().getSensorsObj().size(); i++) {
                appComponentes = appComponentes + getModel().getSensorsObj().get(i).toString() + componentSep;
            }
        }

        if(getModel().getDropDownsObj().size() != 0){
            for (int i = 0; i < getModel().getDropDownsObj().size(); i++) {
                appComponentes = appComponentes + getModel().getDropDownsObj().get(i).toString() + componentSep;
            }
        }
        return appComponentes;
    }

    public JPanel createSwitch(String nameBlock) {
        JPanel panel1=new JPanel();
        panel1.setLayout(new GridLayout(0,2));

        for( int sw = 0 ; sw < getModel().getSwitchsObj().size() ; sw++ ){
            if (getModel().getSwitchsObj().get(sw).getBlockId().equals(nameBlock)){
                JToggleButton boton = new JToggleButton(
                    getModel().getSwitchsObj()
                    .get(sw)
                    .getDef()
                );
                boton.setName(String.valueOf(sw));
                boton.setBorderPainted(true);
                boton.setBackground(new Color(183,196,197));
                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (boton.getText().equalsIgnoreCase("on")) {
                            boton.setSelected(false);
                            boton.setText("off");
                            getModel().getSwitchsObj().get(Integer.parseInt(boton.getName())).setDef("off");
                            Servidor.getServidor().goUpdateApp();
                            boton.setBackground(new Color(183,196,197));
                        } else {
                            boton.setText("on");
                            boton.setSelected(false);
                            getModel().getSwitchsObj().get(Integer.parseInt(boton.getName())).setDef("on");
                            Servidor.getServidor().goUpdateApp();
                            boton.setBackground(new Color(116,222,231));                   
                        }
                    }
                });
                if (boton.getText().equalsIgnoreCase("on")){
                    boton.setBackground(new Color(116,222,231));
                }
                JPanel panel2=new JPanel();
                JLabel tag=new JLabel(getModel().getSwitchsObj().get(sw).getText());
                boton.setFocusable(false);
                panel2.add(tag);
                panel2.add(boton);
                panel2.setBackground(new Color(208,201,199));
                panel1.add(panel2);
                panel1.setBackground(new Color(208,201,199));
            }

        }
	    return panel1;
	}

	public JPanel createSlider(String nameBlock) {

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

        for( int sl = 0 ; sl < getModel().getSlidersObj().size() ; sl++ ){
            if (getModel().getSlidersObj().get(sl).getBlockId().equals(nameBlock)){
                JSlider slider = new JSlider(
                    getModel().getSlidersObj().get(sl).getMin(),
                    getModel().getSlidersObj().get(sl).getMax(),
                    getModel().getSlidersObj().get(sl).getDef()
                );
                slider.setPaintTrack(true);
                slider.setPaintTicks(true);
                slider.setPaintLabels(true);
                slider.setMajorTickSpacing(getModel().getSlidersObj().get(sl).getStep());
                slider.setName(String.valueOf(sl));
                slider.setBackground(new Color(208,201,199));
                slider.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        getModel().getSlidersObj().get(Integer.parseInt(slider.getName())).setDef(slider.getValue());
                        Servidor.getServidor().goUpdateApp();
                    }

                });

                JPanel panel2=new JPanel();
                panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
                JLabel tag=new JLabel(getModel().getSlidersObj().get(sl).getText());
                tag.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel2.add(tag);
                panel2.add(slider);
                panel2.setBackground(new Color(208,201,199));;
                panel1.add(Box.createVerticalStrut(10));
                panel1.add(panel2);
                panel1.setBackground(new Color(208,201,199));

            }
        }
	return panel1;
	}

	public JPanel createDropdown(String nameBlock) {
		JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0,2));


        for( int cb = 0 ; cb < getModel().getDropDownsObj().size() ; cb++ ){
            if (getModel().getDropDownsObj().get(cb).getBlockId().equals(nameBlock)){
                JComboBox combo = new JComboBox();
                for( int op = 0 ; op < getModel().getDropDownsObj().get(cb).getListOpt().size() ; op++ ){
                    String[] array = getModel().getDropDownsObj().get(cb).getListOpt().get(op).split("\\$");
                    Option pre=new Option(Integer.parseInt(array[0]), array[1]);
                    if (getModel().getDropDownsObj().get(cb).getDef() == pre.getValue()) {
                        combo.addItem(pre.getText());
                        combo.setSelectedIndex(op);
                    }
                    else {
                        combo.addItem(pre.getText());
                    }
                }
                combo.setPrototypeDisplayValue("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                combo.setName(String.valueOf(cb));
                combo.addItemListener(new ItemListener(){

                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        for( int op = 0 ; op < getModel().getDropDownsObj().get(Integer.parseInt(combo.getName())).getListOpt().size() ; op++ ){
                            String[] array=getModel().getDropDownsObj().get(Integer.parseInt(combo.getName())).getListOpt().get(op).split("\\$");
                            Option pre=new Option(Integer.parseInt(array[0]), array[1]);
                            if (pre.getText().equals(combo.getSelectedItem())){
                                getModel().getDropDownsObj().get(Integer.parseInt(combo.getName())).setDef(pre.getValue());
                                Servidor.getServidor().goUpdateApp();
                            }
                        }

                    }

                });
                JPanel panel2=new JPanel();
                JLabel tag=new JLabel(getModel().getDropDownsObj().get(cb).getLabel());
                combo.setMaximumSize(new Dimension(400, 50));
                panel2.add(tag);
                panel2.add(combo);
                panel2.setBackground(new Color(208,201,199));
                panel1.add(panel2);
                panel1.setBackground(new Color(208,201,199));
            }
        }
	    return panel1;
	}

	public JPanel createSensor(String nameBlock) {
		JPanel panel1=new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JTextField text=null;

        for( int ss = 0 ; ss < getModel().getSensorsObj().size() ; ss++ ){
            if (getModel().getSensorsObj().get(ss).getBlockId().equals(nameBlock)){
                text = new JTextField();
                text.setText(
                    "Temperature thresholdlow: " + getModel().getSensorsObj().get(ss).getThresholdlow() + " " + getModel().getSensorsObj().get(ss).getUnits()
                    + "\nTemperature Thresholdhigh: " + getModel().getSensorsObj().get(ss).getThresholdhigh() + " " + getModel().getSensorsObj().get(ss).getUnits());

                text.setEditable(false);
                JPanel panel2=new JPanel();
                JLabel tag=new JLabel(getModel().getSensorsObj().get(ss).getText());
                panel2.add(tag);
                panel2.add(text);
                panel2.setBackground(new Color(208,201,199));
                panel1.add(Box.createVerticalStrut(10));
                panel1.add(panel2);
                panel1.setBackground(new Color(208,201,199));
                
            }
        }
		return panel1;
	}

    public int findObjectWithId(int id){
        for (int i = 0; i < getModel().getSwitchsObj().size(); i++) {
            if(getModel().getSwitchsObj().get(i).getId() == id){
                return i;
            }
        }

        for (int i = 0; i < getModel().getSensorsObj().size(); i++) {
            if(getModel().getSensorsObj().get(i).getId() == id){
                return i;
            }
        }

        for (int i = 0; i < getModel().getSlidersObj().size(); i++) {
            if(getModel().getSlidersObj().get(i).getId() == id){
                return i;
            }
        }

        for (int i = 0; i < getModel().getDropDownsObj().size(); i++) {
            if(getModel().getDropDownsObj().get(i).getId() == id){
                return i;
            }
        }

        return -1;
    }

    public static String encrypt(String password, String pwdSalt, String pwdPepper){
        String hash = Password.hash(password).addSalt(pwdSalt).addPepper(pwdPepper).withArgon2().getResult();
        return hash;
    }
    public static int idUser(Connection conn, String name){
        ResultSet rs = UtilsSQLite.querySelect(conn, "SELECT id FROM User where userName= '"+name+"';");
        try {
            rs.next();
            return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public boolean passwordValidate(String username, String password){
        String filePath = System.getProperty("user.dir") + "/src/database.db";
        Connection conn=UtilsSQLite.connect(filePath);
        ResultSet rs = null;
        String hash;
        String salt;
        String pepper;
        int idUser=idUser(conn, username);
        if (idUser==0){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;

        }else{
            try {
                rs = UtilsSQLite.querySelect(conn, "SELECT hash FROM User where id= "+idUser+";");
                rs.next();
                hash=rs.getString("hash");

                rs = UtilsSQLite.querySelect(conn, "SELECT salt FROM Salt where idUser= "+idUser+";");
                rs.next();
                salt=rs.getString("salt");

                rs = UtilsSQLite.querySelect(conn, "SELECT pepper FROM Pepper where idUser= "+idUser+";");
                rs.next();
                pepper=rs.getString("pepper");

                boolean verified = Password.check(password, hash).addSalt(salt).addPepper(pepper).withArgon2();
                conn.close();
                return verified;

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public String sysDate(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        return formatter.format(date);
    }


    public void insertDatabase(){

        String config = Model.getModel().recorrerArrays();
        String query = "INSERT INTO Snapshoot (config, date) VALUES ('" + config + "','" + Model.getModel().sysDate() +"');";
        UtilsSQLite.queryUpdate(baseDades.conn, query);
    }

    public ArrayList<String> loadDates(){
        ResultSet rs = null;
        String query = ("SELECT date FROM Snapshoot;");
        rs = UtilsSQLite.querySelect(baseDades.conn, query);
        ArrayList<String> datesList = new ArrayList<String>();
        try {
            while (rs.next()) {
                int columnCount = rs.getMetaData().getColumnCount();
                for (int column = 1; column <= columnCount; column++) { //Read every column
                    String columnName = rs.getMetaData().getColumnName(column);
                    Object value = rs.getObject(columnName);
                    datesList.add(String.valueOf(value));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datesList;
    }

    // Getters/Setters
    public ArrayList<String> getSwitchs() { return switchs; }
    public ArrayList<String> getSliders() { return sliders; }
    public ArrayList<String> getDropDowns() { return dropdowns; }
    public ArrayList<String> getSensors() { return sensors; }
    public ArrayList<String> getControls() { return controls; }

    public ArrayList<Switch> getSwitchsObj() { return switchs_obj; }
    public ArrayList<Slider> getSlidersObj() { return sliders_obj; }
    public ArrayList<Dropdown> getDropDownsObj() { return dropdowns_obj; }
    public ArrayList<Sensor> getSensorsObj() { return sensors_obj; }

    public String getConfigById(int id) {
        String query = ("SELECT config FROM Snapshoot where id = '" + id + "';");
        ResultSet rs = UtilsSQLite.querySelect(baseDades.conn, query);
        try {
            while (rs.next()) { //Read every row
                int columnCount = rs.getMetaData().getColumnCount();
                for (int column = 1; column <= columnCount; column++) { //Read every column
                    String columnName = rs.getMetaData().getColumnName(column);
                    Object value = rs.getObject(columnName);
                    System.out.println(value);
                    return String.valueOf(value);
                }

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public void reloadArrays(String config){
        ArrayList<Switch> switches = new ArrayList<>();
        ArrayList<Sensor> sensors = new ArrayList<>();
        ArrayList<Slider> sliders = new ArrayList<>();
        ArrayList<Dropdown> dropdowns = new ArrayList<>();
        ArrayList<String> opt_String = new ArrayList<>();

        String[] components = config.split("··");

        for (int component = 0; component < components.length; component++) {
            if(component != 0 && component != 1){
                String componentID = components[component].substring(0, 2).toString();
                String[] attr = components[component].split("_");

                switch (componentID) {
                    case "SW":
                        switches.add(
                                new Switch(
                                        Integer.parseInt(attr[1]),
                                        attr[2],
                                        attr[3],
                                        attr[4]
                                )
                        );
                        break;
                    case "SL":
                        sliders.add(
                                new Slider(
                                        Integer.parseInt(attr[1]),
                                        attr[2],
                                        Integer.parseInt(attr[3]),
                                        Integer.parseInt(attr[4]),
                                        Integer.parseInt(attr[5]),
                                        Integer.parseInt(attr[6]),
                                        attr[7]
                                )
                        );
                        break;
                    case "SS":
                        sensors.add(
                                new Sensor(
                                        Integer.parseInt(attr[1]),
                                        attr[2],
                                        attr[3],
                                        Integer.parseInt(attr[4]),
                                        Integer.parseInt(attr[5]),
                                        attr[6]
                                )
                        );
                        break;
                    case "DD":
                        opt_String = new ArrayList<>();
                        String[] sepComas = attr[5].split(",");
                        for (int sepOpc = 0; sepOpc < sepComas.length; sepOpc++) {
                            if (sepOpc == sepComas.length - 1) {
                                sepComas[sepOpc] = sepComas[sepOpc].substring(1, sepComas[sepOpc].length() - 1);
                            } else {
                                sepComas[sepOpc] = sepComas[sepOpc].substring(1);
                            }
                        }

                        for (int sepOpc = 0; sepOpc < sepComas.length; sepOpc++) {
                            String[] options = sepComas[sepOpc].split("\\$");
                            opt_String.add(
                                            Integer.parseInt(options[0])+ componentAuxSep +
                                            options[1]);
                        }

                        dropdowns.add(
                                new Dropdown(
                                        Integer.parseInt(attr[1]),
                                        attr[2],
                                        Integer.parseInt(attr[3]),
                                        attr[4],
                                        opt_String
                                )
                        );
                        break;
                }
            }
        }

        getModel().switchs_obj.clear();
        getModel().sliders_obj.clear();
        getModel().sensors_obj.clear();
        getModel().dropdowns_obj.clear();

        getModel().switchs_obj = switches;
        getModel().sliders_obj = sliders;
        getModel().sensors_obj = sensors;
        getModel().dropdowns_obj = dropdowns;

        Servidor.getServidor().goUpdateApp();

    }

}

