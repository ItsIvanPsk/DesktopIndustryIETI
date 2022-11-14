import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Model {
	private ArrayList<String> switchs=new ArrayList<String>();
    private ArrayList<String> sliders=new ArrayList<String>();
    private ArrayList<String> dropdowns=new ArrayList<String>();
    private ArrayList<String> sensors=new ArrayList<String>();
    
	public void lecturaXML(File file) {

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
                            ArrayList<ArrayList<String>> dropDown1=new ArrayList<ArrayList<String>>();
                            String id = elmDrop.getAttribute("id");
                            String def= elmDrop.getAttribute("default");
                            ArrayList<String> option = new ArrayList<String>();

                            NodeList listaOption= elmDrop.getElementsByTagName("option");
                            for(int j = 0; j < listaOption.getLength(); j++) {
                                Node nodeOption = listaOption.item(j);
                                if(nodeOption.getNodeType() == nodeOption.ELEMENT_NODE) {
                                    Element elmOpti = (Element) nodeOption;
                                    option.add(new Options(elmOpti.getAttribute("value"),elmOpti.getTextContent()).toString());
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
		String texto="CF%%";
		for (String i:switchs) {
			texto+=i+"%%";
		}
		for (String i:sliders) {
			texto+=i+"%%";
		}
		for (String i:dropdowns) {
			texto+=i+"%%";
		}
		for (String i:sensors) {
			texto+=i+"%%";
		}
		texto=texto.substring(0,texto.length()-2);
		return texto;
		
	}
}
