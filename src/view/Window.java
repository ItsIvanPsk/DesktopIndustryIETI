package src.view;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import src.model.Model;


public class Window {
	private  JFrame ventana;
    private JScrollPane scrollPanelSwitch;
	private JScrollPane scrollPanelSlider;
	private JScrollPane scrollPanelDropDown;
	private JScrollPane scrollPanelSensor;
	private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
	private static FileFilter filter = new FileNameExtensionFilter("File xml (.xml)", "xml");
	Model modelo=new Model();
	private Container panelContenedor;
	
	public Window() {
		construirVentana();
		
	}
	private void construirVentana() {
		ventana= new JFrame("Industry IETI");
		panelContenedor=ventana.getContentPane();
		JMenuBar barraMenu=new JMenuBar();
		ventana.setJMenuBar(barraMenu);
		
		JMenu arxiu=new JMenu("Arxiu");
		barraMenu.add(arxiu);
		
		JMenu visualitzacio=new JMenu("Visualitzacions");
		barraMenu.add(visualitzacio);
		
		JMenuItem carregarConfig=new JMenuItem("Carregar configuracio");
		carregarConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirArchivo();
				
			}
		});
		arxiu.add(carregarConfig);
		panelContenedor.setLayout(new GridLayout(2,2));
		
		ventana.setSize(1500, 1000);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - ventana.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - ventana.getHeight()) / 2);
	    ventana.setLocation(x, y);
	    ventana.setResizable(false);
	    ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ventana.setVisible(true);
	}
	private  void abrirArchivo(){
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
	    int returnVal = fileChooser.showOpenDialog(ventana);

	    if(returnVal != JFileChooser.APPROVE_OPTION) {
	        return;  
	    }
	    File selectedFile = fileChooser.getSelectedFile();
	    Model.lecturaXML(selectedFile);
	    loadComponents();
	    
	    
	}
	private void loadComponents() {
		panelContenedor.removeAll();
		scrollPanelSwitch= new JScrollPane();
        panelContenedor.add(scrollPanelSwitch);

		JPanel titleSwitch = new JPanel();
        titleSwitch.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        scrollPanelSwitch.setColumnHeaderView(titleSwitch);

        JLabel labelSwitch = new JLabel("Switchs");
        titleSwitch.add(labelSwitch);
		

		scrollPanelSlider= new JScrollPane();
        panelContenedor.add(scrollPanelSlider);

		JPanel titleSlider= new JPanel();
        titleSlider.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        scrollPanelSlider.setColumnHeaderView(titleSlider);

        JLabel labelSlider = new JLabel("Sliders");
        titleSlider.add(labelSlider);


		scrollPanelDropDown= new JScrollPane();
        panelContenedor.add(scrollPanelDropDown);

		JPanel titleDropDown = new JPanel();
        titleDropDown.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        scrollPanelDropDown.setColumnHeaderView(titleDropDown);

        JLabel labelDropDown = new JLabel("Drops Downs");
        titleDropDown.add(labelDropDown);


		scrollPanelSensor= new JScrollPane();
        panelContenedor.add(scrollPanelSensor);

		JPanel titleSensor = new JPanel();
        titleSensor.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
        scrollPanelSensor.setColumnHeaderView(titleSensor);

        JLabel labelSensors = new JLabel("Sensors");
        titleSensor.add(labelSensors);

		scrollPanelSwitch.setViewportView(modelo.createSwitch());
		scrollPanelSlider.setViewportView(modelo.createSlider());
		scrollPanelDropDown.setViewportView(modelo.createDropdown());
		scrollPanelSensor.setViewportView(modelo.createSensor());
		SwingUtilities.updateComponentTreeUI(ventana);
	}
}

