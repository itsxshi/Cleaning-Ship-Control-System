package clean.ship61.absim;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.HashMap;

import clean.ship61.absim.flow.Direction;
import clean.ship61.absim.flow.FlowType;
import clean.ship61.absim.rule.RuleType;

/**
 * The configuration storage.
 * Work as a memory db to store all configs needed to run the simulation.
 * 
 * With default configurations preseted.
 *
 *
 */
public class Config {
	
	// Application
	public final int frameWidth = 1000;
	public final int frameLength = 800;
	public final String appTitle = "AUTONOMOUS BOAT SIMULATION"; 
	public final String aboutInfo = appTitle + "\nVersion: v0.1\nAuthor: Yaojia Lyu\nDate: 2020/8/8";
	
	// Ocean
	public int oceanWidth = 50;		// the 2d array grid width
	public int oceanLength = 50;	// the 2d array grid length
	
	// Flow
	// The flow determine how oil spill to other cells.
	// 4 Flow Types: Around, One Direction, Random, Remain(no moving)
	public FlowType flowType = FlowType.Direction;	// flowType defines how oil spill between cells. 
	public float flowDelta = 1;		// delta defines the how much oil change between neighboring cells
	public float flowThreshold = 2; // flowThreshold defines the flow threshold between two cells, if less than the threshold, won't flow				
	public float flowSpeed = 4f;	// defines the flowing speed.
	public Direction flowDirection = Direction.SOUTH;	// only used with FlowType.Direction, specify the flow direction  
	
	// Rule
	// the rule used to figure out the moving target for the boat
	// Two rules: 1.find the nearest oil cell; 2. find the max value oil cell.  
	public RuleType ruleType = RuleType.Nearest;	
	
	// Boat
	public Point boatLocation = new Point(0, 0);
	public float boatSpeed = 3f;
	
	// Oil cells
	// represent the oil in the ocean
	public HashMap<Point, Integer> oilCells = new HashMap<>();

	// Preset default oil cells
	{
		this.addOilCell(new Point(38,40), 100);
		this.addOilCell(new Point(39,40), 200);
		this.addOilCell(new Point(40,40), 500);
		this.addOilCell(new Point(9,20), 300);
		this.addOilCell(new Point(10,20), 300);
	}
	
	private static Config config = null;
	private Config() {}
	
	/**
	 * Config is a singleton, only one config allowed in the simulation.
	 * @return
	 */
	public static Config instance() {
		if (config == null) {
			config = new Config();
		}
		return config;
	}
	
	/**
	 * Reflection, use string to get the config field value.
	 * @param name
	 * @return
	 */
	public Object getFieldValue(String name) {
		try {
			Field field = this.getClass().getField(name);
			return field.get(this);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Reflection, use string to set the config field value.
	 * @param name
	 * @param value
	 */
	public void setFieldValue(String name, Object value) {
		try {
			Field field = this.getClass().getField(name);
			field.set(this, value);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Add oil point.
	 * @param p
	 * @param value
	 */
	public void addOilCell(Point p, int value) {
		this.oilCells.put(p, value);
	}
	
	/**
	 * Remove all oil cells. 
	 */
	public void clearOilCells() {
		this.oilCells.clear();
	}

}
