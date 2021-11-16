package clean.ship61.absim.flow;

import java.awt.Point;
import java.util.Random;

import clean.ship61.absim.grid.Grid;

/**
 * Randomly flow to the neighbor cells.
 *
 */
public class FlowRandom extends AFlow {
	
	private static final Direction[] VALUES = Direction.values();
	private static final Random RANDOM = new Random();

	public FlowRandom(Grid grid, float delta, float threshold) {
		super(grid, delta, threshold);
	}

	@Override
	public void update() {
		
		for (int row = 0; row < getGrid().getWidth(); row++) {
			for (int column = 0; column < getGrid().getLength(); column++) {
				
				Point p = getGrid().indexToPoint(row, column);
				
				// Randomly flow to two neighbor cells each time.
				flowTo(p, randomDirection());
				flowTo(p, randomDirection());
			}
		}
	}
	
	/**
	 * Randomly pick one direction 
	 * @return
	 */
	public static Direction randomDirection() {
		return VALUES[RANDOM.nextInt(VALUES.length)];
	}
}
