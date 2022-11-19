package src.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import src.components.Dropdown;
import src.components.Option;
import src.components.Sensor;
import src.components.Slider;
import src.components.Switch;

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
                                elmSwi.getAttribute("id"), 
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
                                elmSli.getAttribute("id"), 
                                elmSli.getAttribute("default"),
                                elmSli.getAttribute("min"),
                                elmSli.getAttribute("max"),
                                elmSli.getAttribute("step"),
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
                            String id = elmDrop.getAttribute("id");
                            String def= elmDrop.getAttribute("default");
                            ArrayList<String> option = new ArrayList<String>();

                            NodeList listaOption= elmDrop.getElementsByTagName("option");
                            for(int j = 0; j < listaOption.getLength(); j++) {
                                Node nodeOption = listaOption.item(j);
                                if(nodeOption.getNodeType() == Node.ELEMENT_NODE) {
                                    Element elmOpti = (Element) nodeOption;
                                    option.add(new Option(elmOpti.getAttribute("value"),elmOpti.getTextContent()).toString());
                                }

                            }
                            Dropdown dropdown = new Dropdown(id,def,option);
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
                                elmSen.getAttribute("id"),
                                elmSen.getAttribute("units"),
                                elmSen.getAttribute("thresholdlow"),
                                elmSen.getAttribute("thresholdhigh"),
                                elmSen.getTextContent());

                            model.getSensors().add(sensor.toString());
                            model.getSensorsObj().add(sensor);
                        }
                    }
                }
            }
            System.out.println(model.getSensors().toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public String recorrerArrays() {
        String appComponentes = "CF%%";

        System.out.println(model.getSwitchs().size());
        System.out.println(model.getSliders().size());
        System.out.println(model.getDropDowns().size());
        System.out.println(model.getSensors().size());
        
        if(model.getSwitchs().size() != 0){
            for (String _switch : model.getSliders()) {
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

    public JPanel createSwitch() {
        // Generate the background and the header
        JPanel panel1=new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.setBorder(new EmptyBorder(0,300,0,0));
		JLabel label=new JLabel("Switchs");
        panel1.add(label);

        for( int sw = 0 ; sw < model.getSwitchsObj().size() ; sw++ ){
            JToggleButton boton = new JToggleButton(
                model.getSwitchsObj()
                .get(sw)
                .getDef()
            );
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (boton.isSelected()) {
                        boton.setText("off");
                    } else {
                        boton.setText("on");
                    }
                    
                }
            });
            boton.setFocusable(false);
            panel1.add(Box.createVerticalStrut(10));
            panel1.add(boton);
        }
	    return panel1;
	}
	
	public JPanel createSlider() {
	
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Sliders");
        panel1.add(label);
        JSlider slider=null;

        for( int sl = 0 ; sl < model.getSlidersObj().size() ; sl++ ){
            slider = new JSlider(
                Integer.parseInt(model.getSlidersObj().get(sl).getMin()),
                Integer.parseInt(model.getSlidersObj().get(sl).getMax()),
                Integer.parseInt(model.getSlidersObj().get(sl).getDef())
            );
            slider.setPaintTrack(true);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setMajorTickSpacing(Integer.parseInt(model.getSlidersObj().get(sl).getDef()));
            panel1.add(slider);
        }
	return panel1;
	}
	
	public JPanel createDropdown() {
		JPanel panel1=new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JLabel label=new JLabel("Drop Downs");
        panel1.add(label);
        JComboBox combo;

        for( int cb = 0 ; cb < model.getDropDownsObj().size() ; cb++ ){
            combo=new JComboBox();
            for( int op = 0 ; op < model.getDropDownsObj().get(cb).getListOpt().size() ; op++ ){
                // <combo.add(model.getDropDownsObj().get(cb).getListOpt().get(op));
            }
        }
	    return panel1;
	}
	
	public JPanel createSensor() {
		JPanel panel1=new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JLabel label=new JLabel("Text Fields");
        panel1.add(label);
        JTextField text=null;

        for( int ss = 0 ; ss < model.getSensorsObj().size() ; ss++ ){
            text = new JTextField();
			text.setText(
                "Temperature thresholdlow: " + model.getSensorsObj().get(ss).getThresholdlow() + " " + model.getSensorsObj().get(ss).getUnits()
                + "\nTemperature Thresholdhigh: " + model.getSensorsObj().get(ss).getThresholdhigh() + " " + model.getSensorsObj().get(ss).getUnits());
            panel1.add(text);
        }
		return panel1;
	}

    public Object findObjectWithId(int id){
        
        return new Object();
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
