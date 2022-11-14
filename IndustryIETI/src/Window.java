import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Window {
	private  JFrame ventana;
	private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
	private static FileFilter filter = new FileNameExtensionFilter("File xml (.xml)", "xml");
	Model modelo=new Model();
	
	Window() {
		construirVentana();
		
	}
	private void construirVentana() {
		ventana= new JFrame("Industry IETI");
		Container panelContenedor=ventana.getContentPane();
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
		
		ventana.pack();
		ventana.setSize(400, 400);
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
	    modelo.lecturaXML(selectedFile);
	    
	    
	}
	

}


