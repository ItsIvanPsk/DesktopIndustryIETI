package src.model;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import src.components.Slider;
import src.components.Controls;
import src.components.Dropdown;
import src.components.Option;
import src.components.Sensor;
import src.components.Switch;

public class errorValidate {
    private Model model = new Model();
    private static Switch button = new Switch();
    private static Sensor sensor = new Sensor();
    private static Slider slider = new Slider();
    private static Dropdown dropDown = new Dropdown();

    public boolean checkoutControls(File file) {
		boolean checkControl = true;
		try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();
            ArrayList<String> list=new ArrayList<String>();
            NodeList listaControles = doc.getElementsByTagName("controls");
            for(int cnt = 0; cnt < listaControles.getLength(); cnt++) {
                Node nodeControl = listaControles.item(cnt);
                if(nodeControl.getNodeType() == nodeControl.ELEMENT_NODE) {
                    Element elm = (Element) nodeControl;
                    list.add(elm.getAttribute("name"));
                }
            }
            
            for (int i=0;i<list.size()-1;i++) {
            	for (int j=i+1;j<list.size();j++) {
            		if (Objects.equals(list.get(i), list.get(j))) {
            			checkControl=false;
            		}
            	}
            }
		} catch(Exception e) { 
        	e.printStackTrace(); 
        }
		return checkControl;
	}

	public boolean attributeNumValidate(File file) {
		boolean check = true;
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
                    String nomControl=elm.getAttribute("name");
                    NodeList listaSwitch = elm.getElementsByTagName("switch");
                    for(int i = 0; i < listaSwitch.getLength(); i++) {
                        Node nodeSwitch = listaSwitch.item(i);
                        if(nodeSwitch.getNodeType() == nodeSwitch.ELEMENT_NODE) {
                            Element elmSwi = (Element) nodeSwitch;
                            if (elmSwi.getAttributes().getLength()!=2) {
                            	check=false;
                            }
                        }

                    }
                    NodeList listaSlider = elm.getElementsByTagName("slider");
                    for(int i = 0; i < listaSlider.getLength(); i++) {
                        Node nodeSlider = listaSlider.item(i);
                        if(nodeSlider.getNodeType() == nodeSlider.ELEMENT_NODE) {
                            Element elmSli = (Element) nodeSlider;
                            if (elmSli.getAttributes().getLength()!=5) {
                            	check=false;
                            }
                        }
                    }
                    NodeList listaDropDown = elm.getElementsByTagName("dropdown");
                    for(int i = 0; i < listaDropDown.getLength(); i++) {
                        Node nodeDropDown = listaDropDown.item(i);
                        if(nodeDropDown.getNodeType() == nodeDropDown.ELEMENT_NODE) {
                            Element elmDrop = (Element) nodeDropDown;
                            ArrayList<String> option = new ArrayList<String>();
                            if (elmDrop.getAttributes().getLength()!=3) {
                            	check=false;
                            }

                            NodeList listaOption= elmDrop.getElementsByTagName("option");
                            for(int j = 0; j < listaOption.getLength(); j++) {
                                Node nodeOption = listaOption.item(j);
                                if(nodeOption.getNodeType() == nodeOption.ELEMENT_NODE) {
                                    Element elmOpti = (Element) nodeOption;
                                    if (elmOpti.getAttributes().getLength()!=1) {
                                    	check=false;
                                    }
                                }
        
                            }
                        }

                    }
                    
                    NodeList listaSensor = elm.getElementsByTagName("sensor");
                    for(int i = 0; i < listaSensor.getLength(); i++) {
                        Node nodeSensor = listaSensor.item(i);
                        if(nodeSensor.getNodeType() == nodeSensor.ELEMENT_NODE) {
                            Element elmSen = (Element) nodeSensor;
                            if (elmSen.getAttributes().getLength()!=4) {
                            	check=false;
                            }
                        }

                    }
                    
                    
                }
            }
        } catch(Exception e) { 
        	e.printStackTrace(); 
        }
		return check;
	}

    public static boolean idValidate(File file){
        ArrayList<Controls> contrList = new ArrayList<Controls>();
        boolean check = true;
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
                    String nomControl=elm.getAttribute("name");
                    NodeList listaSwitch = elm.getElementsByTagName("switch");
                    for(int i = 0; i < listaSwitch.getLength(); i++) {
                        Node nodeSwitch = listaSwitch.item(i);
                        if(nodeSwitch.getNodeType() == nodeSwitch.ELEMENT_NODE) {
                            Element elmSwi = (Element) nodeSwitch;
                            if (elmSwi.getAttribute("id") == null) {
                            	check=false;
                            }
                            else{
                                button.setId(Integer.parseInt(elmSwi.getAttribute("id")));
                                button.setDef(elmSwi.getAttribute("default"));
                                button.setText(elmSwi.getTextContent());
                                contrList.add(button);
                            }
                        }

                    }
                    NodeList listaSlider = elm.getElementsByTagName("slider");
                    for(int i = 0; i < listaSlider.getLength(); i++) {
                        Node nodeSlider = listaSlider.item(i);
                        if(nodeSlider.getNodeType() == nodeSlider.ELEMENT_NODE) {
                            Element elmSli = (Element) nodeSlider;
                            if (elmSli.getAttribute("id") == null) {
                            	check=false;
                            }
                            else{
                                int id = Integer.parseInt(elmSli.getAttribute("id"));
                                int def = Integer.parseInt(elmSli.getAttribute("default"));
                                int min = Integer.parseInt(elmSli.getAttribute("min"));
                                int max = Integer.parseInt(elmSli.getAttribute("max"));
                                int step = Integer.parseInt(elmSli.getAttribute("step"));
                                String text = elmSli.getTextContent();
                                slider = new Slider(id, def, min, max, step, text);
                                contrList.add(slider);
                            }
                        }
                    }
                    NodeList listaDropDown = elm.getElementsByTagName("dropdown");
                    for(int i = 0; i < listaDropDown.getLength(); i++) {
                        Node nodeDropDown = listaDropDown.item(i);
                        if(nodeDropDown.getNodeType() == nodeDropDown.ELEMENT_NODE) {
                            Element elmDrop = (Element) nodeDropDown;
                            ArrayList<String> option = new ArrayList<String>();
                            if (elmDrop.getAttribute("id") == null) {
                            	check=false;
                            }else{
                                int id = Integer.parseInt(elmDrop.getAttribute("id"));
                                int def = Integer.parseInt(elmDrop.getAttribute("default"));
                                String label = elmDrop.getAttribute("label");
                                
                            
                                NodeList listaOption= elmDrop.getElementsByTagName("option");
                                for(int j = 0; j < listaOption.getLength(); j++) {
                                    Node nodeOption = listaOption.item(j);
                                    if(nodeOption.getNodeType() == nodeOption.ELEMENT_NODE) {
                                        Element elmOpti = (Element) nodeOption;
                                        if (elmOpti.getAttributes().getLength()!=1) {
                                            check=false;
                                        }
                                        option.add(new Option(elmOpti.getAttribute("value"),elmOpti.getTextContent()).toString());
                                    }
                                    

                                }
                                dropDown = new Dropdown(id,def,label,option);
                                contrList.add(dropDown);
                               
                            }
                        }

                    }
                    
                    NodeList listaSensor = elm.getElementsByTagName("sensor");
                    for(int i = 0; i < listaSensor.getLength(); i++) {
                        Node nodeSensor = listaSensor.item(i);
                        if(nodeSensor.getNodeType() == nodeSensor.ELEMENT_NODE) {
                            Element elmSen = (Element) nodeSensor;
                            if (elmSen.getAttribute("id") == null) {
                            	check=false;
                            }
                            else{
                                sensor.setId(Integer.parseInt(elmSen.getAttribute("id")));
                                sensor.setUnits(elmSen.getAttribute("units"));
                                sensor.setThresholdlow(Integer.parseInt(elmSen.getAttribute("thresholdlow")));
                                sensor.setThresholdhigh(Integer.parseInt(elmSen.getAttribute("thresholdhigh")));
                                sensor.setText(elmSen.getTextContent());
                                contrList.add(sensor);
                              
                            }
                        }
                        
                    }
                    
                    
                }
            }

            for (Controls contr : contrList){
                System.out.println(contr);
                String[] listSplit = contr.toString().split("#");

                ArrayList<int> idList = new ArrayList<int>(); 
            }

            
        } catch(Exception e) { 
        	e.printStackTrace(); 
        }
		return check;
    }
}
