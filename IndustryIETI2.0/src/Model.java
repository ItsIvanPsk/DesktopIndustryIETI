package src;

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
	private static ArrayList<String> switchs = new ArrayList<String>();
    private static ArrayList<String> sliders = new ArrayList<String>();
    private static ArrayList<String> dropdowns = new ArrayList<String>();
    private static ArrayList<String> sensors = new ArrayList<String>();
    public static void lecturaXMLApp(File file) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();
            
            switchs.clear();
            sliders.clear();
            dropdowns.clear();
            sensors.clear();

            NodeList listaControles = doc.getElementsByTagName("controls");

            for(int cnt = 0; cnt < listaControles.getLength(); cnt++) {
                Node nodeControl = listaControles.item(cnt);
                if(nodeControl.getNodeType() == nodeControl.ELEMENT_NODE) {
                    Element elm = (Element) nodeControl;
                    String nomControl = elm.getAttribute("name");
                    NodeList listaSwitch = elm.getElementsByTagName("switch");
                    for(int i = 0; i < listaSwitch.getLength(); i++) {
                        Node nodeSwitch = listaSwitch.item(i);
                        if(nodeSwitch.getNodeType() == nodeSwitch.ELEMENT_NODE) {
                            Element elmSwi = (Element) nodeSwitch;
                            switchs.add(new Switch(elmSwi.getAttribute("id"), elmSwi.getAttribute("default"), elmSwi.getTextContent() ).toString());
                        }
                    }
                    NodeList listaSlider = elm.getElementsByTagName("slider");
                    for(int i = 0; i < listaSlider.getLength(); i++) {
                        Node nodeSlider = listaSlider.item(i);
                        if(nodeSlider.getNodeType() == nodeSlider.ELEMENT_NODE) {
                            Element elmSli = (Element) nodeSlider;
                            sliders.add(new Slider(elmSli.getAttribute("id"), elmSli.getAttribute("default"),elmSli.getAttribute("min"),
                                    elmSli.getAttribute("max"),elmSli.getAttribute("step"),elmSli.getTextContent()).toString());
                        }
                    }
                    NodeList listaDropDown = elm.getElementsByTagName("dropdown");
                    for(int i = 0; i < listaDropDown.getLength(); i++) {
                        Node nodeDropDown = listaDropDown.item(i);
                        if(nodeDropDown.getNodeType() == nodeDropDown.ELEMENT_NODE) {
                            Element elmDrop = (Element) nodeDropDown;
                            String id = elmDrop.getAttribute("id");
                            String def= elmDrop.getAttribute("default");
                            ArrayList<String> option = new ArrayList<String>();

                            NodeList listaOption= elmDrop.getElementsByTagName("option");
                            for(int j = 0; j < listaOption.getLength(); j++) {
                                Node nodeOption = listaOption.item(j);
                                if(nodeOption.getNodeType() == nodeOption.ELEMENT_NODE) {
                                    Element elmOpti = (Element) nodeOption;
                                    option.add(new Option(elmOpti.getAttribute("value"),elmOpti.getTextContent()).toString());
                                }

                            }
                            dropdowns.add(new Dropdown(id,def,option).toString());
                        }
                    }

                    NodeList listaSensor = elm.getElementsByTagName("sensor");
                    for(int i = 0; i < listaSensor.getLength(); i++) {
                        Node nodeSensor = listaSensor.item(i);
                        if(nodeSensor.getNodeType() == nodeSensor.ELEMENT_NODE) {
                            Element elmSen = (Element) nodeSensor;
                            sensors.add(new Sensor(elmSen.getAttribute("id"),elmSen.getAttribute("units"),elmSen.getAttribute("thresholdlow"),
                                    elmSen.getAttribute("thresholdhigh"),elmSen.getTextContent()).toString());
                        }
                    }
                }
            }
            System.out.println(switchs);
            System.out.println(sliders);
            System.out.println(dropdowns);
            System.out.println(sensors);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> getSwitchs() {
        return switchs;
    }
    public ArrayList<String> getSliders() {
        return sliders;
    }
    public ArrayList<String> getDropDowns() {
        return dropdowns;
    }
    public ArrayList<String> getSensors() {
        return sensors;
    }
    public String recorrerArrays(ArrayList<String> switchs,ArrayList<String> sliders,ArrayList<String> dropdowns,ArrayList<String> sensors) {
        String appComponentes="CF%%";

        System.out.println(switchs.size());

        if(switchs.size() != 0){
            for (String _switch:switchs) {
                appComponentes = appComponentes + _switch + "%%";
            }
        }

        if(sliders.size() != 0){
            for (String _sliders:sliders) {
                appComponentes = appComponentes + _sliders + "%%";
            }
        }

        if(sensors.size() != 0){
            for (String _sensors:sensors) {
                appComponentes = appComponentes + _sensors + "%%";
            }
        }

        if(dropdowns.size() != 0){
            for (String _dropdown:dropdowns) {
                appComponentes = appComponentes + _dropdown + "%%";
            }
        }
        System.out.println(appComponentes);
        return appComponentes;
    }

    public JPanel createSwitch(File file) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        JPanel panel1=new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.setBorder(new EmptyBorder(0,300,0,0));
		JLabel label=new JLabel("Switchs");
        panel1.add(label);
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList listaControles = doc.getElementsByTagName("controls");

        for(int cnt = 0; cnt < listaControles.getLength(); cnt++) {
            Node nodeControl = listaControles.item(cnt);
            if(nodeControl.getNodeType() == nodeControl.ELEMENT_NODE) {
                Element elm = (Element) nodeControl;

                NodeList listaSwitch = elm.getElementsByTagName("switch");
                for(int i = 0; i < listaSwitch.getLength(); i++) {
                    Node nodeSwitch = listaSwitch.item(i);
                    if(nodeSwitch.getNodeType() == nodeSwitch.ELEMENT_NODE) {
                        Element elmSwi = (Element) nodeSwitch;
                        JToggleButton boton=new JToggleButton(elmSwi.getAttribute("default"));
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

                }
            }
        }
    } catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return panel1;
	}
	
	public JPanel createSlider(File file) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        JPanel panel1=new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JLabel label=new JLabel("Sliders");
        panel1.add(label);
        JSlider slider=null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        NodeList listaControles = doc.getElementsByTagName("controls");

        for(int cnt = 0; cnt < listaControles.getLength(); cnt++) {
            Node nodeControl = listaControles.item(cnt);
            if(nodeControl.getNodeType() == nodeControl.ELEMENT_NODE) {
                Element elm = (Element) nodeControl;

                NodeList listaSlider = elm.getElementsByTagName("slider");
                for(int i = 0; i < listaSlider.getLength(); i++) {
                    Node nodeSlider = listaSlider.item(i);
                    if(nodeSlider.getNodeType() == nodeSlider.ELEMENT_NODE) {
                    	Element elmSli = (Element) nodeSlider;
                    	//Cambio valor default porque no deja indicar un double (preguntar despues de presentacion)
                        slider=new JSlider(Integer.parseInt(elmSli.getAttribute("min")),Integer.parseInt(elmSli.getAttribute("max")),Integer.parseInt(elmSli.getAttribute("default")));
                        slider.setPaintTrack(true);
                        slider.setPaintTicks(true);
                        slider.setPaintLabels(true);
                        slider.setMajorTickSpacing(Integer.parseInt(elmSli.getAttribute("step")));
                    }
                    panel1.add(slider);
                	}
                        
                    }

        }
            
        
    } catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return panel1;
	}
	
	public JPanel createDropdown(File file) {
		JPanel panel1=new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JLabel label=new JLabel("Drop Downs");
        panel1.add(label);
        JComboBox combo=null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        

	        Document doc = dBuilder.parse(file);
	
	        doc.getDocumentElement().normalize();
	
	        NodeList listaControles = doc.getElementsByTagName("controls");
	
	        for(int cnt = 0; cnt < listaControles.getLength(); cnt++) {
	            Node nodeControl = listaControles.item(cnt);
	            if(nodeControl.getNodeType() == nodeControl.ELEMENT_NODE) {
	                Element elm = (Element) nodeControl;
			
				NodeList listaDropDown = elm.getElementsByTagName("dropdown");
		        for(int i = 0; i < listaDropDown.getLength(); i++) {
		            Node nodeDropDown = listaDropDown.item(i);
		            if(nodeDropDown.getNodeType() == nodeDropDown.ELEMENT_NODE) {
		                Element elmDrop = (Element) nodeDropDown;
		                String def= elmDrop.getAttribute("default");
		                combo=new JComboBox();
		
		                NodeList listaOption= elmDrop.getElementsByTagName("option");
		                for(int j = 0; j < listaOption.getLength(); j++) {
		                    Node nodeOption = listaOption.item(j);
		                    if(nodeOption.getNodeType() == nodeOption.ELEMENT_NODE) {
		                        Element elmOpti = (Element) nodeOption;
		                        if (elmOpti.getAttribute("value").equals(def)) {
		                        	combo.addItem(elmOpti.getTextContent());
		                        	combo.setSelectedIndex(j);
		                        }
		                        else {
		                        	combo.addItem(elmOpti.getTextContent());
		                        }
		                    }
		
		                }
		            }
		            panel1.add(combo);
	            }
	        }
        }
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return panel1;
	}
	
	public JPanel createSensor(File file) {
		JPanel panel1=new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        JLabel label=new JLabel("Text Fields");
        panel1.add(label);
        JTextField text=null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		
	        Document doc = dBuilder.parse(file);
	
	        doc.getDocumentElement().normalize();
	
	        NodeList listaControles = doc.getElementsByTagName("controls");
	
	        for(int cnt = 0; cnt < listaControles.getLength(); cnt++) {
	            Node nodeControl = listaControles.item(cnt);
	            if(nodeControl.getNodeType() == nodeControl.ELEMENT_NODE) {
	                Element elm = (Element) nodeControl;
					NodeList listaSensor = elm.getElementsByTagName("sensor");
			        for(int i = 0; i < listaSensor.getLength(); i++) {
			            Node nodeSensor = listaSensor.item(i);
			            if(nodeSensor.getNodeType() == nodeSensor.ELEMENT_NODE) {
			                Element elmSen = (Element) nodeSensor;
			                text=new JTextField();
			                text.setText("Temperature thresholdlow: "+elmSen.getAttribute("thresholdlow")+" "+elmSen.getAttribute("units")+
			                		" / Temperature Thresholdhigh: "+elmSen.getAttribute("thresholdhigh")+" "+elmSen.getAttribute("units"));
			                text.setEditable(false);
			            }
			            panel1.add(text);
			        }
	            }
	        }
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return panel1;
	}
}
