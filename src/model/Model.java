package src.model;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import src.components.Controls;
import src.components.Dropdown;
import src.components.Option;
import src.components.Sensor;
import src.components.Slider;
import src.components.Switch;

public class Model {
    // App
	private static ArrayList<String> switchs = new ArrayList<String>();
    private static ArrayList<String> sliders = new ArrayList<String>();
    private static ArrayList<String> dropdowns = new ArrayList<String>();
    private static ArrayList<String> sensors = new ArrayList<String>();
    // Desktop
	private static ArrayList<Switch> switchs_obj = new ArrayList<>();
    private static ArrayList<Slider> sliders_obj = new ArrayList<>();
    private static ArrayList<Dropdown> dropdowns_obj = new ArrayList<>();
    private static ArrayList<Sensor> sensors_obj = new ArrayList<>();

    static Model model = new Model();
    
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
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();
            
            model.getSwitchs().clear();
            model.getSliders().clear();
            model.getDropDowns().clear();
            model.getSensors().clear();

            model.getSwitchsObj().clear();
            model.getSlidersObj().clear();
            model.getDropDownsObj().clear();
            model.getSensorsObj().clear();

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
                            model.getSwitchs().add(switch_obj.toString());
                            model.getSwitchsObj().add(switch_obj);
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

                            model.getSliders().add(slider.toString());
                            model.getSlidersObj().add(slider);
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
                            model.getDropDowns().add(dropdown.toString());
                            model.getDropDownsObj().add(dropdown);
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

                            model.getSensors().add(sensor.toString());
                            model.getSensorsObj().add(sensor);
                        }
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String recorrerArrays() {
        String appComponentes = "CF%%";

        if(model.getSwitchs().size() != 0){
            for (String _switch : model.getSwitchs()) {
                appComponentes = appComponentes + _switch + "%%";
            }
        }

        if(model.getSliders().size() != 0){
            for (String _sliders : model.getSliders()) {
                appComponentes = appComponentes + _sliders + "%%";
            }
        }

        if(model.getSensors().size() != 0){
            for (String _sensors:model.getSensors()) {
                appComponentes = appComponentes + _sensors + "%%";
            }
        }

        if(model.getDropDowns().size() != 0){
            for (String _dropdown:model.getDropDowns()) {
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

        for( int sw = 0 ; sw < model.getSwitchsObj().size() ; sw++ ){
            JToggleButton boton = new JToggleButton(
                model.getSwitchsObj()
                .get(sw)
                .getDef()
            );
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (boton.getText().equalsIgnoreCase("on")) {
                        boton.setText("off");
                    } else {
                        boton.setText("on");
                    }
                    
                }
            });
            if (boton.getText().equalsIgnoreCase("on")){
                boton.setSelected(true);
            }
            JPanel panel2=new JPanel();
            JLabel tag=new JLabel(model.getSwitchsObj().get(sw).getText());
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
        JSlider slider=null;

        for( int sl = 0 ; sl < model.getSlidersObj().size() ; sl++ ){
            slider = new JSlider(
                model.getSlidersObj().get(sl).getMin(),
                model.getSlidersObj().get(sl).getMax(),
                model.getSlidersObj().get(sl).getDef()
            );
            slider.setPaintTrack(true);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setMajorTickSpacing(model.getSlidersObj().get(sl).getStep());
            JPanel panel2=new JPanel();
            panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
            JLabel tag=new JLabel(model.getSlidersObj().get(sl).getText());
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
        JComboBox combo = null;

        for( int cb = 0 ; cb < model.getDropDownsObj().size() ; cb++ ){
            combo=new JComboBox();
            for( int op = 0 ; op < model.getDropDownsObj().get(cb).getListOpt().size() ; op++ ){
                String[] array=model.getDropDownsObj().get(cb).getListOpt().get(op).split("//");
                Option pre=new Option(Integer.parseInt(array[0]), array[1]);
                if (model.getDropDownsObj().get(cb).getDef() == pre.getValue()) {
                    combo.addItem(pre.getText());
                    combo.setSelectedIndex(op);
                }
                else {
                    combo.addItem(pre.getText());
                }
            }
            JPanel panel2=new JPanel();
            JLabel tag=new JLabel(Model.getDropDownsObj().get(cb).getText());
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

        for( int ss = 0 ; ss < Model.getSensorsObj().size() ; ss++ ){
            text = new JTextField();
			text.setText(
                "Temperature thresholdlow: " + Model.getSensorsObj().get(ss).getThresholdlow() + " " + Model.getSensorsObj().get(ss).getUnits()
                + "\nTemperature Thresholdhigh: " + Model.getSensorsObj().get(ss).getThresholdhigh() + " " + Model.getSensorsObj().get(ss).getUnits());
            
            text.setEditable(false);
            JPanel panel2=new JPanel();
            JLabel tag=new JLabel(Model.getSensorsObj().get(ss).getText());
            panel2.add(tag);
            panel2.add(text);
            panel1.add(Box.createVerticalStrut(10));
            panel1.add(panel2);
        }
		return panel1;
	}

    public static int findObjectWithId(int id){
        System.out.println("IVAN: " + Model.getSwitchsObj().size());
        for (int i = 0; i < Model.getSwitchsObj().size(); i++) {
            if(Model.getSwitchsObj().get(i).getId() == id){
                return i;
            }
        }

        for (int i = 0; i < Model.getSensorsObj().size(); i++) {
            if(Model.getSensorsObj().get(i).getId() == id){
                return i;
            }
        }

        for (int i = 0; i < Model.getSlidersObj().size(); i++) {
            if(Model.getSlidersObj().get(i).getId() == id){
                return i;
            }
        }

        for (int i = 0; i < Model.getDropDownsObj().size(); i++) {
            if(Model.getDropDownsObj().get(i).getId() == id){
                return i;
            }
        }
        
        return -1;
    }

    // Getters/Setters
    public static ArrayList<String> getSwitchs() { return switchs; }
    public static ArrayList<String> getSliders() { return sliders; }
    public static ArrayList<String> getDropDowns() { return dropdowns; }
    public static ArrayList<String> getSensors() { return sensors; }

    public static ArrayList<Switch> getSwitchsObj() { return switchs_obj; }
    public static ArrayList<Slider> getSlidersObj() { return sliders_obj; }
    public static ArrayList<Dropdown> getDropDownsObj() { return dropdowns_obj; }
    public static ArrayList<Sensor> getSensorsObj() { return sensors_obj; }

}

