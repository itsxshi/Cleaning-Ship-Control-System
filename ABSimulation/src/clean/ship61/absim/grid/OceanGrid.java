package clean.ship61.absim.grid;

import java.awt.Point;

public class OceanGrid extends Grid {

	public OceanGrid(int width, int length) {
		super(width, length);
	}
	
	public OceanGrid(int size) {
		super(size);
	}

	/**
	 * Set Land, any point's value less then 0, will regard as land. 
	 * So that the oil will not flow to.
	 * 
	 * @param p
	 */
	public void setAsLand(Point p) {
		this.setValue(p, -1);
	}
	
	
	/**
	 * Method to add oil to the ocean(gird).
	 * 
	 * @param p
	 * @param oil
	 */
	public void setOil(Point p, float oil) {
		this.setValue(p, oil);
	}
	
	/**
	 * Calculate the oil in the ocean.
	 * 
	 * @return
	 */
	public double totalOil() {
		int rows = this.getWidth();
		int cols = this.getLength();
		
		double sum = 0;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (this.getGrid()[row][col] > 0) {
					sum += this.getGrid()[row][col];
				}
			}
		}
		
		return sum;
	}

}
