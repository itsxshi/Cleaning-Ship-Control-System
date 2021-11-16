package clean.ship61.absim;

import java.awt.Point;
import java.util.List;
import java.util.Observable;

import clean.ship61.absim.grid.Grid;
import clean.ship61.absim.rule.IABRule;

public class Boat extends Observable {
	
	private static int autoIncrementId = 1; 
	
	private int id;					// Boat ID

	private Point location;     	// defines the initial location of the boat
	private float speed; 			// meters per unit time
	
	private double loadUsage;    	// defines the current oil collected 
	
	private Grid grid; 				// defines the ocean(grid) for the boat sailing and cleaning
	
	public Boat() {
		this.setLocation(new Point(0,0));
		this.loadUsage = 0;
		this.id = Boat.autoIncrementId++;
	}
	
	public Boat(Point location, float speed) {
		this.location = location;
		this.speed = speed;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	
	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public float getSpeed() {
		return speed;
	}

	public double getLoadUsage() {
		return loadUsage;
	}

	public void setLoadUsage(double loadUsage) {
		this.loadUsage = loadUsage;
	}
	
	/**
	 * Update load usage, and notify observers.
	 * @param usage
	 */
	public void addLoadUsage(double usage) {
		this.loadUsage += usage;
		
		setChanged();
		notifyObservers(this);
	}
	
	/**
	 * Empty the load, simulate the boat pour out all the oil it collected.
	 * (not used so far)
	 */
	public void emptyLoadUsage() {
		this.setLoadUsage(0);
	}

	/**
	 * Move to next cell, and collect the oil.
	 * 
	 * @param what strategy rule used to decide next cell.
	 * @return true if success move and clean. false if nothing to clean
	 */
	public boolean moveAndClean(IABRule rule) {
		
		// use the rule to figure out, how to get to the target location
		// return the route to the target.
		// a target may have the most oil, or near the boat, accounting to the rule.
		List<Point> route = rule.getRoute(location, grid);
		
		// if no route return, means the boat don't need to move.
		if(route==null || route.size()==0) {
			return false;
		}
		
		// get the first value of the route, which means the next cell for the boat to move.
		Point next = route.get(0);

		clean(next);
		moveTo(next);
		
		return true;
	}

	private void moveTo(Point target) {
		// The boat leave the cell, resume to 0, so that the oil can flow to this cell.
		this.grid.setValue(this.location, 0); 

		// Move the boat to new location.
		this.location = target;
		
		// prevent oil flow to the boat's location, the boat location always stay clean.
		this.grid.setValue(this.location, -999);  
	}
	
	private void clean(Point target) {
		// get how much oil in this cell
		double oilValue = this.grid.getValue(target);
		
		// add to the boat, which means the boat collect the oil.
		this.addLoadUsage(oilValue);
		
		// set the cell's oil value to 0, means it's clean now.
		this.grid.setValue(target, 0);
	}
	
	@Override
	public String toString() {
		String info = String.format(
				"** BOAT INFO ** \n" +
				"- ID: %s; Location: %s; Speed: %d\n" +
				"- Load Usage: %.2f/%.1f;\n" +
				this.id, this.location, this.speed, this.loadUsage);
		return info;
	}

}
