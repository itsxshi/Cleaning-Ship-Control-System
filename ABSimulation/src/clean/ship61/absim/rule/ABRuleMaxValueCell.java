package clean.ship61.absim.rule;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import clean.ship61.absim.grid.Grid;

/**
 * Try to find the max value cell (max oil spill), and return the position
 *
 *
 */
public class ABRuleMaxValueCell implements IABRule {
	
	@Override
	public List<Point> getRoute(Point currentPosition, Grid grid) {
		
		Point target = findMaximumCell(currentPosition, grid);
		return IABRule.findLine(currentPosition, target);
	}

	
	/**
	 * Try to find the max value cell.
	 * If there are multiple equal max value, compare the distance with current cell, return the nearest max cell.
	 * 
	 * @param currentPosition
	 * @param grid
	 * @return
	 */
	private Point findMaximumCell(Point currentPosition, Grid grid) {
		double[][] numbers = grid.getGrid();
		
		double maxValue = Double.NEGATIVE_INFINITY;
		
		ArrayList<Point> list = new ArrayList<>();
		
		// Get the cells which value > 0
		for (int i = 0; i < grid.getWidth(); i++) {
			for (int k = 0; k < grid.getLength(); k++) {
				
				if (numbers[i][k] > 0) {
					list.add(grid.indexToPoint(i, k));
				}
			}
		}
		
		// Sort cells by the value
		list.sort(new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				return (int) (grid.getValue(o2) - grid.getValue(o1));
			}
		});
		
		if (list.size() == 0) {
			return null; // can not find max value;
		}
		

		Point maxCell = list.get(0);
		maxValue = grid.getValue(maxCell);
		double distance = currentPosition.distance(maxCell);

		// Consider the value, then consider the distance with current position.
		for (Point point : list) {
			if (grid.getValue(point) < maxValue) {
				break;
			}
			
			if (currentPosition.distance(point) < distance) {
				maxCell = point;
			}
		}

		return maxCell;
    }

	
	
}
