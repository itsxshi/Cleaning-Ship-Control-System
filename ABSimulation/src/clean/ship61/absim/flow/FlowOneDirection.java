package clean.ship61.absim.flow;

import java.awt.Point;

import clean.ship61.absim.grid.Grid;

/**
 * Flow to the specific direction from the cells which values > 0;
 *
 */
public class FlowOneDirection extends AFlow {
	
	private Direction direction;

	public FlowOneDirection(Grid grid, float delta, float threshold, Direction d) {
		super(grid, delta, threshold);
		this.direction = d;
	}

	@Override
	public void update() {
		for (int row = 0; row < getGrid().getWidth(); row++) {
			for (int column = 0; column < getGrid().getLength(); column++) {
				
				Point p = getGrid().indexToPoint(row, column);
				flowToDirection(p, this.direction);
			}
		}
	}
	
	private void flowToDirection(Point p, Direction d) {
		/*
		 * Like the diagram, Flow to EAST,
		 * The point o in the west side flow to the east side, cells(x)
		 * 
		 *     x
		 *   x x
		 * o x x
		 *   x x
		 *     x
		 */
		
		switch (d) {
		
		case WEST:
			flowTo(p, Direction.NORTHWEST);
			flowTo(p, Direction.WEST);
			flowTo(p, Direction.SOUTHWEST);
			break;
			
		case NORTHWEST:
			flowTo(p, Direction.NORTHWEST);
			flowTo(p, Direction.WEST);
			break;
			
		case SOUTHWEST:
			flowTo(p, Direction.SOUTHWEST);
			flowTo(p, Direction.WEST);
			break;
			
		case EAST:
			flowTo(p, Direction.NORTHEAST);
			flowTo(p, Direction.EAST);
			flowTo(p, Direction.SOUTHEAST);
			break;
			
		case NORTHEAST:
			flowTo(p, Direction.NORTHEAST);
			flowTo(p, Direction.EAST);
			break;
			
		case SOUTHEAST:
			flowTo(p, Direction.SOUTHEAST);
			flowTo(p, Direction.EAST);
			break;
		
		case NORTH:
			flowTo(p, Direction.NORTHWEST);
			flowTo(p, Direction.NORTH);
			flowTo(p, Direction.NORTHEAST);
			break;
			
		case SOUTH:
			flowTo(p, Direction.SOUTHWEST);
			flowTo(p, Direction.SOUTH);
			flowTo(p, Direction.SOUTHEAST);
			break;
		default:
			break;
		}
	}

}
