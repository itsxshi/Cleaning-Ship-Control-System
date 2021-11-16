package clean.ship61.absim.ui;

import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import clean.ship61.absim.Monitor;

/**
 * Status Panel, used to show the status of simulation, the statistics data of oil and boat load usage.
 *
 */
public class StatusPanel extends JPanel implements Observer {
	
	private static final long serialVersionUID = 3043938416125604326L;
	
	private double oilSpill = 0;
	private double loadUsage = 0;
	private String status = "Stop";
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        String s = String.format("Status: %s;  Ocean Oil Spill: %.2f; Boat Load Usage: %.2f", status, oilSpill, loadUsage);
        		
        g.drawString(s,10,30);
    }  

	/**
	 *Observes the 
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof Monitor) {
			Monitor monitor = (Monitor) arg;
			oilSpill = monitor.getTotalOilSpill();
			loadUsage = monitor.getTotalLoadUsage();
			status = monitor.getStatus();
		}
		
		repaint();
		
	}

}
