package src.view;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import src.model.Model;


public class Window {
	private  JFrame ventana;
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
		
		ventana.pack();
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
	    Model.lecturaXMLApp(selectedFile);
	    loadComponents(selectedFile);
	    
	    
	}
	private void loadComponents(File file) {
		panelContenedor.add(modelo.createSwitch(file));
		panelContenedor.add(modelo.createSlider(file));
		panelContenedor.add(modelo.createDropdown(file));
		panelContenedor.add(modelo.createSensor(file));
		ventana.pack();
		ventana.setSize(1500,1000);
	}

}


