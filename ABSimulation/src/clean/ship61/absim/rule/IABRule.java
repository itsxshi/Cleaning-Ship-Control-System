package clean.ship61.absim.rule;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import clean.ship61.absim.grid.Grid;

/**
 * 
 * The ABRule is used to guide the Boat how to get to the target location base on its strategics.
 * According to the rule and the grid values, return the route to the target position
 * 
 */
public interface IABRule {
	
	List<Point> getRoute(Point currentPosition, Grid grid);
	
	
    /**
     * To find the line connecting the two cells in the grid.
     * 
     * Base on Bresenham's line algorithm
     * https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
     * 
     * @param start
     * @param destination
     * @return Points for the line
     */
    public static List<Point> findLine(Point start, Point destination) {    
    	if (start==null || destination==null) {
    		return Collections.emptyList();
    	}
    	
    	int x0 = start.x;
    	int y0 = start.y;
    	
    	int x1 = destination.x;
    	int y1 = destination.y;
    	
        List<Point> line = new ArrayList<Point>();
 
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
 
        int sx = x0 < x1 ? 1 : -1; 
        int sy = y0 < y1 ? 1 : -1; 
 
        int err = dx-dy;
        int e2;
 
        while (true) {
            if (x0 == x1 && y0 == y1) 
                break;
 
            e2 = 2 * err;
            if (e2 > -dy) 
            {
                err = err - dy;
                x0 = x0 + sx;
            }
 
            if (e2 < dx) 
            {
                err = err + dx;
                y0 = y0 + sy;
            }
            
            line.add(new Point(x0, y0));
        }                                
        return line;
    }

}