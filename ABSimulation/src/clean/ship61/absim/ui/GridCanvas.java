
package clean.ship61.absim.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import clean.ship61.absim.Monitor;


/**
 * The grid panel, to display the ocean, oil spill and how boat moved.
 *
 */
public class GridCanvas extends JPanel implements Observer {

	private static final long serialVersionUID = 8645652865858250847L;
	
	private double[][] grid;

	// Swing calls when a redraw is needed
	public void paint(Graphics g) {
		if (grid == null) {
			return;
		}
		
		drawCanvas(g);
	}

	// Draw the contents of the panel
	private void drawCanvas(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		drawOceanGrid(g2d);  
	}

	private int lineSize = 10; // How big each cell should be

	private void drawOceanGrid(Graphics2D g2d) {

		Color oceanColor = new Color(0, 191, 255);
		Color boatColor = new Color(255, 255, 0);
		Color landColor = new Color(255, 239, 219);
		Color oilColor = new Color(0, 0, 0);
		
		int rows = grid.length;
		int cols = grid[0].length;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				
				int startx = col * lineSize;
				int starty = row * lineSize;
				
				double value = grid[row][col];

				if (value > 0) {
					float alpha = (float) (Math.min(10, value) / 10f);
					Color c = Blend(oilColor, oceanColor, alpha);
					g2d.setColor(c);
				}
				
				else if (value == -1) {	
					g2d.setColor(landColor);
				}
				else if (value == -999) {	
					g2d.setColor(boatColor);
				}
				else {
					g2d.setColor(oceanColor);
				}
				
				g2d.fillRect(startx, starty, lineSize-1, lineSize-1);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof Monitor) {
			grid = (double[][]) ((Monitor) arg).getGrid();
		}
		repaint(); 
	}

	private Color Blend(Color clOne, Color clTwo, float d) {
	    float fInverse = 1 - d;

	    float afOne[] = new float[3];
	    clOne.getColorComponents(afOne);
	    float afTwo[] = new float[3]; 
	    clTwo.getColorComponents(afTwo);    

	    float afResult[] = new float[3];
	    afResult[0] = afOne[0] * d + afTwo[0] * fInverse;
	    afResult[1] = afOne[1] * d + afTwo[1] * fInverse;
	    afResult[2] = afOne[2] * d + afTwo[2] * fInverse;

	    return new Color (afResult[0], afResult[1], afResult[2]);
	}

	public void clear() {
		this.grid = null;
		repaint(); 
	}

}
