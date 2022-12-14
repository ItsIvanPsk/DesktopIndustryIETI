package src.view;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import lib.UtilsSQLite;
import src.model.Model;
import src.model.baseDades;


public class Window {
	private static  JFrame ventana;
	private JScrollPane scrollGeneral;
    private JScrollPane scrollPanelSwitch;
	private JScrollPane scrollPanelSlider;
	private JScrollPane scrollPanelDropDown;
	private JScrollPane scrollPanelSensor;
	private JPanel allComponents;
	private static JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
	private static FileFilter filter = new FileNameExtensionFilter("File xml (.xml)", "xml");
	Model modelo = Model.getModel();
	private Container panelContenedor;

	public Window() {
		construirVentana();
	}
	private void construirVentana() {
		ventana= new JFrame("Industry IETI");
		panelContenedor=ventana.getContentPane();
		JMenuBar barraMenu=new JMenuBar();
		ventana.setJMenuBar(barraMenu);

		JMenu arxiu = new JMenu("Arxiu");
		barraMenu.add(arxiu);

		JMenu visualitzacio = new JMenu("Visualitzacions");
		barraMenu.add(visualitzacio);

		JMenu snapShoot = new JMenu("SnapShoot");
		barraMenu.add(snapShoot);

		JMenuItem carregarConfig = new JMenuItem("Carregar configuracio");
		carregarConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirArchivo();
			}
		});
		arxiu.add(carregarConfig);

		JMenu loadSnapshoot = new JMenu("Carrega Snapshoot");
		JMenuItem doSnapshoot = new JMenuItem("Fer Snapshoot");
		doSnapshoot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Model.getModel().insertDatabase();
				loadSnapshoot.removeAll();
				System.out.println("Load dates size: " + Model.getModel().loadDates().size());
				for (int i = 0; i < Model.getModel().loadDates().size(); i++) {
					JMenuItem snapshootCount = new JMenuItem();
					
					snapshootCount.setName(String.valueOf(i));
					snapshootCount.setText(Model.getModel().loadDates().get(i));

					snapshootCount.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int num = Integer.parseInt(snapshootCount.getName());
							Model.getModel().reloadArrays(Model.getModel().getConfigById(num+1));
							loadAllComponents();
						}
					});
					
		
					loadSnapshoot.add(snapshootCount);
		
				}
			}
		});
		snapShoot.add(doSnapshoot);
		snapShoot.add(loadSnapshoot);

		for (int i = 0; i < Model.getModel().loadDates().size(); i++) {
			JMenuItem snapshootCount = new JMenuItem();
			snapshootCount.setName(String.valueOf(i));
			snapshootCount.setText(Model.getModel().loadDates().get(i));

			snapshootCount.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int num = Integer.parseInt(snapshootCount.getName());
					Model.getModel().reloadArrays(Model.getModel().getConfigById(num+1));
					loadAllComponents();
				}
			});

			loadSnapshoot.add(snapshootCount);

		}

		JMenuItem deleteSnapShoots = new JMenuItem();
		deleteSnapShoots.setText("Delete snapshoots");
		deleteSnapShoots.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				baseDades.del();
				for (int i = 0; i < Model.getModel().loadDates().size(); i++) {
					JMenuItem snapshootCount = new JMenuItem();
					
					snapshootCount.setName(String.valueOf(i));
					snapshootCount.setText(Model.getModel().loadDates().get(i));

					snapshootCount.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int num = Integer.parseInt(snapshootCount.getName());
							Model.getModel().reloadArrays(Model.getModel().getConfigById(num+1));
							loadAllComponents();
						}
					});
					
					loadSnapshoot.add(snapshootCount);
					
				}
			}

		});

		snapShoot.add(deleteSnapShoots);

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
	    loadAllComponents();
	}
	public void loadAllComponents() {
		panelContenedor.removeAll();
		int bloques=modelo.getControls().size();
		if (bloques==1){;
			loadComponentsBlock(0);
		}
		else{
			scrollGeneral=new JScrollPane();
			panelContenedor.add(scrollGeneral);
			allComponents=new JPanel();
			allComponents.setLayout(new BoxLayout(allComponents, BoxLayout.Y_AXIS));
			for (int i=0;i<bloques;i++){
				loadComponentsBlock(i);
			}
			scrollGeneral.setViewportView(allComponents);
			SwingUtilities.updateComponentTreeUI(ventana);
		}
	}
	public void loadComponentsBlock(int posicionBloque){
		int bloques = modelo.getControls().size();
		scrollPanelSwitch = new JScrollPane();

		JPanel titleSwitch = new JPanel();
        titleSwitch.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
		titleSwitch.setBackground(new Color(159,123,222));
        scrollPanelSwitch.setColumnHeaderView(titleSwitch);

        JLabel labelSwitch = new JLabel("Switches");
		labelSwitch.setForeground(new Color(255,255,255));
		labelSwitch.setFont(new Font("Serif",Font.PLAIN,25));
        titleSwitch.add(labelSwitch);


		scrollPanelSlider= new JScrollPane();

		JPanel titleSlider= new JPanel();
        titleSlider.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        scrollPanelSlider.setColumnHeaderView(titleSlider);
		titleSlider.setBackground(new Color(159,123,222));

        JLabel labelSlider = new JLabel("Sliders");
		labelSlider.setForeground(new Color(255,255,255));
		labelSlider.setFont(new Font("Serif",Font.PLAIN,25));
        titleSlider.add(labelSlider);


		scrollPanelDropDown= new JScrollPane();

		JPanel titleDropDown = new JPanel();
        titleDropDown.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        scrollPanelDropDown.setColumnHeaderView(titleDropDown);
		titleDropDown.setBackground(new Color(159,123,222));

        JLabel labelDropDown = new JLabel("Drops Downs");
		labelDropDown.setForeground(new Color(255,255,255));
		labelDropDown.setFont(new Font("Serif",Font.PLAIN,25));
        titleDropDown.add(labelDropDown);


		scrollPanelSensor= new JScrollPane();

		JPanel titleSensor = new JPanel();
        titleSensor.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
        scrollPanelSensor.setColumnHeaderView(titleSensor);
		titleSensor.setBackground(new Color(159,123,222));

        JLabel labelSensors = new JLabel("Sensors");
		labelSensors.setForeground(new Color(255,255,255));
		labelSensors.setFont(new Font("Serif",Font.PLAIN,25));
        titleSensor.add(labelSensors);

		scrollPanelSwitch.setViewportView(modelo.createSwitch(modelo.getControls().get(posicionBloque)));
		scrollPanelSlider.setViewportView(modelo.createSlider(modelo.getControls().get(posicionBloque)));
		scrollPanelDropDown.setViewportView(modelo.createDropdown(modelo.getControls().get(posicionBloque)));
		scrollPanelSensor.setViewportView(modelo.createSensor(modelo.getControls().get(posicionBloque)));

		JPanel panelIndividual=new JPanel();
		panelIndividual.setLayout(new BoxLayout(panelIndividual, BoxLayout.Y_AXIS));

		JPanel titleBlock = new JPanel();
		titleBlock.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));

		JLabel labelBlock=new JLabel(modelo.getControls().get(posicionBloque));
		labelBlock.setFont(new Font("Serif",Font.BOLD,32));
		labelBlock.setForeground(new Color(255,255,255));
		titleBlock.setBackground(new Color(101,56,178));
		titleBlock.add(labelBlock);
		panelIndividual.add(titleBlock);

		if (bloques==1){
			JPanel panelScrolls=new JPanel();
			panelScrolls.setLayout(new GridLayout(2,2));
			panelScrolls.add(scrollPanelSwitch);
			panelScrolls.add(scrollPanelSlider);
			panelScrolls.add(scrollPanelDropDown);
			panelScrolls.add(scrollPanelSensor);

			panelIndividual.add(panelScrolls);
			panelContenedor.add(panelIndividual);
			SwingUtilities.updateComponentTreeUI(ventana);
		}
		else{
			JPanel panelForScrolls=new JPanel();
			panelForScrolls.setLayout(new GridLayout(2,2));
			int contador=0;
			for (int i=0;i<modelo.getSwitchsObj().size();i++){
				if (modelo.getSwitchsObj().get(i).getBlockId().equals(modelo.getControls().get(posicionBloque))){
					contador++;
				}
			}
			if (contador>0){
				panelForScrolls.add(scrollPanelSwitch);
			}

			contador=0;
			for (int i=0;i<modelo.getSlidersObj().size();i++){
				if (modelo.getSlidersObj().get(i).getBlockId().equals(modelo.getControls().get(posicionBloque))){
					contador++;
				}
			}
			if (contador>0){
				panelForScrolls.add(scrollPanelSlider);
			}

			contador=0;
			for (int i=0;i<modelo.getDropDownsObj().size();i++){
				if (modelo.getDropDownsObj().get(i).getBlockId().equals(modelo.getControls().get(posicionBloque))){
					contador++;
				}
			}
			if (contador>0){
				panelForScrolls.add(scrollPanelDropDown);
			}

			contador=0;
			for (int i=0;i<modelo.getSensorsObj().size();i++){
				if (modelo.getSensorsObj().get(i).getBlockId().equals(modelo.getControls().get(posicionBloque))){
					contador++;
				}
			}
			if (contador>0){
				panelForScrolls.add(scrollPanelSensor);
			}
			panelIndividual.add(panelForScrolls);

			allComponents.add(panelIndividual);


		}
	}

	public static JFrame getVentana(){
		return ventana;
	}
}

