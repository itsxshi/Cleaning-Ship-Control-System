package clean.ship61.absim.flow;

import java.awt.Point;

import clean.ship61.absim.grid.Grid;


/**
 * A flow could determine how value change inside the grid.
 * Used to simulate the oil spill on the ocean.
 * 
 * A flow can run as a thread individually
 * 
 *
 */

public abstract class AFlow extends Thread {
	
	private Grid grid;
	
	private float delta; // define the value change between neighboring cells
	private float threshold; // define the flow threshold between two cells.	
	
	private float speed; // define how fast the flow in one unit time; must > 0
	
	// control flag
	private boolean running = true;
	private boolean paused = false;

	
	public AFlow(Grid grid) {
		this.grid = grid;
	}
	
	public AFlow(Grid grid, float delta, float threshold) {
		this.grid = grid;
		this.delta = delta;
		this.threshold = threshold;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Grid getGrid() {
		return grid;
	}
	
 
	/**
	 * 
	 * How the grid updated each time. 
	 * Need to implement 
	 * 
	 */
	public abstract void update();
	
	/**
	 * 
	 * Check if the oil can flow to its neighbor or not.
	 * if the cell's neighbor not exists(on the edge), return false;
	 * 
	 * @param point
	 * @param direction
	 * @return
	 */
	public boolean canFlow(Point point, Direction direction) {
		if (point == null) return false;
		
		double value = this.getGrid().getValue(point);
		
		// if the cell value is 0, won't flow. nothing to flow.
		if (value == 0) {
			return false;
		}
		
		double neighborValue = this.getGrid().getNeighborPointValue(point, direction);
		
		// it no neighbor cell with this direction, 
		// which means the cell locates in the edges, or the neighbor is land mass, won't flow
		if (neighborValue < 0) {
			return false;
		}
		
		// if the difference value less than the threshold, won't flow
		return (value - neighborValue) > this.threshold;
	}
	
	/**
	 * Flow the oil to its neighbor accounting specific direction
	 * 
	 * @param Point p, the cell contain oil;
	 * @param The Target Direction
	 */
	public void flowTo(Point p, Direction d) {
		if (canFlow(p, d)) {
			double neighborValue = this.getGrid().getNeighborPointValue(p, d);
			double pointValue = this.getGrid().getValue(p);
			
			// update the value of current cell and its neighbor cell
			// delta defines the value changed each time
			this.getGrid().setValue(p, pointValue-this.delta);
			this.getGrid().setNeighborPointValue(p, d, neighborValue+this.delta);
		}
	}
	
	public void terminate() {
        running = false;
    }
	
	public void pause() {
		this.paused = !this.paused;
	}
	
	@Override
	public void run() {
		
		while(running) {
			
			if(!paused) {

				this.update();
				
				try {
					// calculate how much time cost for the value flow to another cell.
					// times 1000, use sleep to simulate the time cost.
					Thread.sleep((long) (1000 * (1.0/this.speed))); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				try {
					Thread.sleep(500L); // Wait for resume 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
		}
	}
	

}
