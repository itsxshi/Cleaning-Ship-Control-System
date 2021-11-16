package clean.ship61.absim.rule;

import java.awt.Point;
import java.util.List;

import clean.ship61.absim.grid.Grid;


/**
 * Find the nearest cell with value greater than 0, which means the closest oil spill cell
 * Return the position
 *
 */
public class ABRuleNearestCell implements IABRule {
	
	

	@Override
	public List<Point> getRoute(Point currentPosition, Grid grid) {
		Point nearestPoint = findNearestCell(currentPosition, grid);
		return IABRule.findLine(currentPosition, nearestPoint);
	}
	
	private Point findNearestCell(Point currentPosition, Grid grid) {
		
		double minDistance = Float.MAX_VALUE;
		Point nearestCell = null;


		for (int i = 0; i < grid.getWidth(); i++) {
			for (int j = 0; j < grid.getLength(); j++) {

				if(grid.getGrid()[i][j] > 0) {
					Point tmpCell = grid.indexToPoint(i, j);
					
					double tmpDistance = currentPosition.distance(tmpCell);
					if (tmpDistance < minDistance) {
						minDistance = tmpDistance; 
						nearestCell = tmpCell;
					}
					
				}
			}
		}
		
		return nearestCell;
	}
	
}
