package clean.ship61.absim.flow;

import java.awt.Point;

import clean.ship61.absim.grid.Grid;

/**
 * Flow from oil cell to its around cells which values > 0;
 *
 */
public class FlowAround extends AFlow {
	
	Grid grid;

	public FlowAround(Grid grid, float delta, float threshold) {
		super(grid, delta, threshold);
		this.grid = grid;
	}

	@Override
	public void update() {
		for (int row = 0; row < getGrid().getWidth(); row++) {
			for (int column = 0; column < getGrid().getLength(); column++) {
				
				Point p = getGrid().indexToPoint(row, column);
				this.flowToAroundCells(p);
			}
		}
	}
	
	private void flowToAroundCells(Point p) {
		/*
		 * Flow to the around cells like below diagram
		 * The point o in the center flow to its around cells(x)
		 * 
		 * 	   x
		 *   x x x
		 * x x o x x
		 *   x x x 
		 *     x
		 */

		flowTo(p, Direction.NORTH);
		flowTo(p, Direction.SOUTH);
		flowTo(p, Direction.WEST);
		flowTo(p, Direction.EAST);
		flowTo(p, Direction.NORTHWEST);
		flowTo(p, Direction.NORTHEAST);
		flowTo(p, Direction.SOUTHWEST);
		flowTo(p, Direction.SOUTHEAST);
		
		flowTo(grid.getUpPoint(p), Direction.NORTH);
		flowTo(grid.getDownPoint(p), Direction.SOUTH);
		flowTo(grid.getLeftPoint(p), Direction.WEST);
		flowTo(grid.getRightPoint(p), Direction.EAST);
		
	}

}
