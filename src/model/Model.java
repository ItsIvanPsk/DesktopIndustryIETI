package src.model;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

import src.components.Controls;
import src.components.Dropdown;
import src.components.Option;
import src.components.Sensor;
import src.components.Slider;
import src.components.Switch;
import src.utils.UtilsSQLite;
import src.view.CustomsDialogs;
import src.view.Window;

public class Model {
    // App
	private ArrayList<String> switchs = new ArrayList<String>();
    private ArrayList<String> sliders = new ArrayList<String>();
    private ArrayList<String> dropdowns = new ArrayList<String>();
    private ArrayList<String> sensors = new ArrayList<String>();
    // Desktop
	private ArrayList<Switch> switchs_obj = new ArrayList<>();
    private ArrayList<Slider> sliders_obj = new ArrayList<>();
    private ArrayList<Dropdown> dropdowns_obj = new ArrayList<>();
    private ArrayList<Sensor> sensors_obj = new ArrayList<>();

    static Model modelo = new Model();
    
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
        modelo.getSwitchs().clear();
        modelo.getSliders().clear();
        modelo.getDropDowns().clear();
        modelo.getSensors().clear();

        modelo.getSwitchsObj().clear();
        modelo.getSlidersObj().clear();
        modelo.getDropDownsObj().clear();
        modelo.getSensorsObj().clear();
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
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);

                doc.getDocumentElement().normalize();
                
                NodeList listaControles = doc.getElementsByTagName("controls");

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
                                    elmSwi.getAttribute("default"), 
                                    elmSwi.getTextContent()
                                );
                                modelo.getSwitchs().add(switch_obj.toString());
                                modelo.getSwitchsObj().add(switch_obj);
                            }
                        }
                        NodeList listaSlider = elm.getElementsByTagName("slider");
                        for(int i = 0; i < listaSlider.getLength(); i++) {
                            Node nodeSlider = listaSlider.item(i);
                            if(nodeSlider.getNodeType() == Node.ELEMENT_NODE) {
                                Element elmSli = (Element) nodeSlider;
                                Slider slider = new Slider(
                                    Integer.parseInt(elmSli.getAttribute("id")), 
                                    Integer.parseInt(elmSli.getAttribute("default")),
                                    Integer.parseInt(elmSli.getAttribute("min")),
                                    Integer.parseInt(elmSli.getAttribute("max")),
                                    Integer.parseInt(elmSli.getAttribute("step")),
                                    elmSli.getTextContent());

                                modelo.getSliders().add(slider.toString());
                                modelo.getSlidersObj().add(slider);
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
                                        option.add(new Option(Integer.parseInt(elmOpti.getAttribute("value")),elmOpti.getTextContent()).toString());
                                    }

                                }
                                Dropdown dropdown = new Dropdown(id,def,lab,option);
                                modelo.getDropDowns().add(dropdown.toString());
                                modelo.getDropDownsObj().add(dropdown);
                            }
                        }

                        NodeList listaSensor = elm.getElementsByTagName("sensor");
                        for(int i = 0; i < listaSensor.getLength(); i++) {
                            Node nodeSensor = listaSensor.item(i);
                            if(nodeSensor.getNodeType() == Node.ELEMENT_NODE) {
                                Element elmSen = (Element) nodeSensor;
                                Sensor sensor = new Sensor(
                                    Integer.parseInt(elmSen.getAttribute("id")),
                                    elmSen.getAttribute("units"),
                                    Integer.parseInt(elmSen.getAttribute("thresholdlow")),
                                    Integer.parseInt(elmSen.getAttribute("thresholdhigh")),
                                    elmSen.getTextContent());

                                modelo.getSensors().add(sensor.toString());
                                modelo.getSensorsObj().add(sensor);
                            }
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String recorrerArrays() {
        String appComponentes = "CF%%";

        if(modelo.getSwitchs().size() != 0){
            for (String _switch : modelo.getSwitchs()) {
                appComponentes = appComponentes + _switch + "%%";
            }
        }

        if(modelo.getSliders().size() != 0){
            for (String _sliders : modelo.getSliders()) {
                appComponentes = appComponentes + _sliders + "%%";
            }
        }

        if(modelo.getSensors().size() != 0){
            for (String _sensors:modelo.getSensors()) {
                appComponentes = appComponentes + _sensors + "%%";
            }
        }

        if(modelo.getDropDowns().size() != 0){
            for (String _dropdown:modelo.getDropDowns()) {
                appComponentes = appComponentes + _dropdown + "%%";
            }
        }

        return appComponentes;
    }

    public Controls getArrayObject(ArrayList<Controls> array, int id){

        for (int i = 0; i < array.size(); i++) {
            System.out.println(i);
        }
        return new Controls();
    }

    public JPanel createSwitch() {
        // Generate the background and the header
        JPanel panel1=new JPanel();
        panel1.setLayout(new GridLayout(0,2));

        for( int sw = 0 ; sw < modelo.getSwitchsObj().size() ; sw++ ){
            JToggleButton boton = new JToggleButton(
                modelo.getSwitchsObj()
                .get(sw)
                .getDef()
            );
            boton.setName(String.valueOf(sw));
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (boton.getText().equalsIgnoreCase("on")) {
                        boton.setText("off");
                        modelo.getSwitchsObj().get(Integer.parseInt(boton.getName())).setDef("off");
                    } else {
                        boton.setText("on");
                        modelo.getSwitchsObj().get(Integer.parseInt(boton.getName())).setDef("on");

                    }
                }
            });
            if (boton.getText().equalsIgnoreCase("on")){
                boton.setSelected(true);
            }
            JPanel panel2=new JPanel();
            JLabel tag=new JLabel(modelo.getSwitchsObj().get(sw).getText());
            boton.setFocusable(false);
            panel2.add(tag);
            panel2.add(boton);
            panel1.add(panel2);

        }
	    return panel1;
	}
	
	public JPanel createSlider() {
	
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

        for( int sl = 0 ; sl < modelo.getSlidersObj().size() ; sl++ ){
            JSlider slider = new JSlider(
                modelo.getSlidersObj().get(sl).getMin(),
                modelo.getSlidersObj().get(sl).getMax(),
                modelo.getSlidersObj().get(sl).getDef()
            );
            slider.setPaintTrack(true);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setMajorTickSpacing(modelo.getSlidersObj().get(sl).getStep());
            slider.setName(String.valueOf(sl));
            slider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    modelo.getSlidersObj().get(Integer.parseInt(slider.getName())).setDef(slider.getValue());;
                }
                
            });
            
            JPanel panel2=new JPanel();
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
            JLabel tag=new JLabel(modelo.getSlidersObj().get(sl).getText());
            tag.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel2.add(tag);
            panel2.add(slider);
            panel1.add(Box.createVerticalStrut(10));
            panel1.add(panel2);
            
        }
	return panel1;
	}
	
	public JPanel createDropdown() {
		JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0,2));


        for( int cb = 0 ; cb < modelo.getDropDownsObj().size() ; cb++ ){
            JComboBox combo = new JComboBox();
            for( int op = 0 ; op < modelo.getDropDownsObj().get(cb).getListOpt().size() ; op++ ){
                String[] array=modelo.getDropDownsObj().get(cb).getListOpt().get(op).split("//");
                Option pre=new Option(Integer.parseInt(array[0]), array[1]);
                if (modelo.getDropDownsObj().get(cb).getDef() == pre.getValue()) {
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
                    // TODO Auto-generated method stub
                    for( int op = 0 ; op < modelo.getDropDownsObj().get(Integer.parseInt(combo.getName())).getListOpt().size() ; op++ ){
                        String[] array=modelo.getDropDownsObj().get(Integer.parseInt(combo.getName())).getListOpt().get(op).split("//");
                        Option pre=new Option(Integer.parseInt(array[0]), array[1]);
                        if (pre.getText().equals(combo.getSelectedItem())){
                            modelo.getDropDownsObj().get(Integer.parseInt(combo.getName())).setDef(pre.getValue());
                        }
                    }
                    
                }
                
            });
            JPanel panel2=new JPanel();
            JLabel tag=new JLabel(modelo.getDropDownsObj().get(cb).getLabel());
            combo.setMaximumSize(new Dimension(400, 50));
            panel2.add(tag);
            panel2.add(combo);
            panel1.add(panel2);
        }
	    return panel1;
	}
	
	public JPanel createSensor() {
		JPanel panel1=new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JTextField text=null;

        for( int ss = 0 ; ss < modelo.getSensorsObj().size() ; ss++ ){
            text = new JTextField();
			text.setText(
                "Temperature thresholdlow: " + modelo.getSensorsObj().get(ss).getThresholdlow() + " " + modelo.getSensorsObj().get(ss).getUnits()
                + "\nTemperature Thresholdhigh: " + modelo.getSensorsObj().get(ss).getThresholdhigh() + " " + modelo.getSensorsObj().get(ss).getUnits());
            
            text.setEditable(false);
            JPanel panel2=new JPanel();
            JLabel tag=new JLabel(modelo.getSensorsObj().get(ss).getText());
            panel2.add(tag);
            panel2.add(text);
            panel1.add(Box.createVerticalStrut(10));
            panel1.add(panel2);
        }
		return panel1;
	}

    public static int findObjectWithId(int id){
        System.out.println("IVAN: " + modelo.getSwitchsObj().size());
        for (int i = 0; i < modelo.getSwitchsObj().size(); i++) {
            if(modelo.getSwitchsObj().get(i).getId() == id){
                return i;
            }
        }

        for (int i = 0; i < modelo.getSensorsObj().size(); i++) {
            if(modelo.getSensorsObj().get(i).getId() == id){
                return i;
            }
        }

        for (int i = 0; i < modelo.getSlidersObj().size(); i++) {
            if(modelo.getSlidersObj().get(i).getId() == id){
                return i;
            }
        }

        for (int i = 0; i < modelo.getDropDownsObj().size(); i++) {
            if(modelo.getDropDownsObj().get(i).getId() == id){
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
            // TODO Auto-generated catch block
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

    // Getters/Setters
    public ArrayList<String> getSwitchs() { return switchs; }
    public ArrayList<String> getSliders() { return sliders; }
    public ArrayList<String> getDropDowns() { return dropdowns; }
    public ArrayList<String> getSensors() { return sensors; }

    public ArrayList<Switch> getSwitchsObj() { return switchs_obj; }
    public ArrayList<Slider> getSlidersObj() { return sliders_obj; }
    public ArrayList<Dropdown> getDropDownsObj() { return dropdowns_obj; }
    public ArrayList<Sensor> getSensorsObj() { return sensors_obj; }

}

