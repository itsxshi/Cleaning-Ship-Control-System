package clean.ship61.absim;

import java.awt.Point;

import clean.ship61.absim.flow.Direction;
import clean.ship61.absim.flow.FlowType;
import clean.ship61.absim.rule.RuleType;

/**
 * Preset three demos' configuration.
 * Used to run simulation without manual setting config. 
 */
public class Demo {
	
	private static Config config = Config.instance();
	
	public static void setDemoConfig1() {
		// Ocean 
		config.oceanWidth = 50;
		config.oceanLength = 50;
		
		// flow
		config.flowType = FlowType.Random;
		config.flowDelta = 1;
		config.flowThreshold = 2; 
		config.flowSpeed = 3;
		
		// Boat
		config.ruleType = RuleType.Nearest;
		config.boatLocation = new Point(0, 0);
		config.boatSpeed = 5;
		
		// Oil
		config.clearOilCells();
		config.addOilCell(new Point(40, 20), 300);
		config.addOilCell(new Point(10, 5), 300);
		config.addOilCell(new Point(30, 30), 300);
		config.addOilCell(new Point(5, 45), 300);
	}
	
	public static void setDemoConfig2() {
		// Ocean 
		config.oceanWidth = 50;
		config.oceanLength = 50;
		
		// flow
		config.flowType = FlowType.Direction;
		config.flowDirection = Direction.NORTH;
		config.flowDelta = 1;
		config.flowThreshold = 2; 
		config.flowSpeed = 5;
		
		// Boat
		config.ruleType = RuleType.MaxValue;
		config.boatLocation = new Point(10, 49);
		config.boatSpeed = 8;
		
		// Oil
		config.clearOilCells();
		config.addOilCell(new Point(30, 10), 300);
		config.addOilCell(new Point(31, 10), 300);
		config.addOilCell(new Point(32, 10), 300);
		config.addOilCell(new Point(33, 10), 300);
		config.addOilCell(new Point(34, 10), 300);
		
		config.addOilCell(new Point(10, 20), 300);
		config.addOilCell(new Point(11, 20), 300);
		config.addOilCell(new Point(12, 20), 300);
		
	}
	
	public static void setDemoConfig3() {
		// Ocean 
		config.oceanWidth = 50;
		config.oceanLength = 80;
		
		// flow
		config.flowType = FlowType.Around;
		config.flowDelta = 1;
		config.flowThreshold = 2; 
		config.flowSpeed = 4;
		
		// Boat
		config.ruleType = RuleType.Nearest;
		config.boatLocation = new Point(0, config.oceanWidth-1);
		config.boatSpeed = 5;
		
		// Oil
		config.clearOilCells();
		config.addOilCell(new Point(50, 40), 300);
		config.addOilCell(new Point(10, 30), 100);
		config.addOilCell(new Point(65, 10), 1000);
	}
	

}
