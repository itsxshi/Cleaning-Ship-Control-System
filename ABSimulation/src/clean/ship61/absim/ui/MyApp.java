package clean.ship61.absim.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clean.ship61.absim.ABSimulation;
import clean.ship61.absim.Config;
import clean.ship61.absim.Demo;

public class MyApp extends ABApp {
	
	private StatusPanel statusPanel;
	private GridCanvas canvas;
	private ConfigPanel configPanel;
	
	private ABSimulation simulation;
	
	public MyApp() {

	 	frame.setSize(Config.instance().frameWidth, Config.instance().frameLength);
		frame.setTitle(Config.instance().appTitle);
		
		menuMgr.createDefaultActions(); // Set up default menu items
		
		initSim(); 
		showUI();
	}
	
	private void initSim() {
		simulation = new ABSimulation();
		simulation.addObserver(statusPanel);
	}
	
	public void start() {
		simulation.start();
		simulation.observeMonitor(canvas);
		simulation.observeMonitor(statusPanel);
	}
	
	public void pause() {
		this.simulation.pause();
	}
	
	public void stop() {
		this.simulation.stop();
		canvas.clear();
	}
	
	public void restart() {
		this.stop();
		this.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.startsWith("Demo")) {
			this.showDemo(cmd);
			return;
		}
		
		switch (cmd) {
			case "Start":
				this.start();
				break;
			case "Pause/Resume":
				this.pause();
				break;	
			case "Stop":
				this.stop();
				break;
			default:
				break;
		}
	}
	
	@Override
	public JPanel getWestPanel() {
		configPanel = new ConfigPanel(this);
		configPanel.setPreferredSize(new Dimension(230, frame.getSize().height));
		configPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Flow controls
		configPanel.setBorder(BorderFactory.createCompoundBorder(
        	       BorderFactory.createEmptyBorder(10, 10, 10, 10),
        	       BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY)));
		
		return configPanel;
	}

	@Override
	public JPanel getCenterPanel() {
		canvas = new GridCanvas(); 
		return canvas;
	}
	
	@Override
	public JPanel getSouthPanel() {
		statusPanel = new StatusPanel();
		statusPanel.setPreferredSize(new Dimension(frame.getSize().width, 40));
		statusPanel.setBorder(BorderFactory.createCompoundBorder(
        	       BorderFactory.createEmptyBorder(10, 10, 10, 10),
        	       BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY)));
		return statusPanel;
	}
	
	@Override
	public void showHelp() {
		JOptionPane.showMessageDialog(null, Config.instance().aboutInfo, 
				"About",JOptionPane.PLAIN_MESSAGE);
    }
	
	private void showDemo(String cmd) {
		switch (cmd) {
		case "Demo 1":
			Demo.setDemoConfig1();
			break;
		case "Demo 2":
			Demo.setDemoConfig2();
			break;
		case "Demo 3":
			Demo.setDemoConfig3();
			break;
		default:
			break;
		}
		
		configPanel.removeAll();
		configPanel.init();
		configPanel.updateUI();
		
		int input = JOptionPane.showConfirmDialog(null, "Do you want to start demo now?", "Select an Option...",
					JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

		if(input == JOptionPane.YES_OPTION) {
			this.restart();
		}	
	}
	
	
	public static void main(String[] args) {
		new MyApp();
	}

}
