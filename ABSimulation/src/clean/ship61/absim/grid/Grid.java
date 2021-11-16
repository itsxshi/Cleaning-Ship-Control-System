package clean.ship61.absim.grid;

import java.awt.Point;
import java.util.Observable;

import clean.ship61.absim.flow.Direction;

/**
 * A 2D array to store point data.
 *
 */
public class Grid extends Observable {
	
	private double[][] grid;
	private int width;
	private int length;
	
	public Grid(int size) {
		this.width = this.length = size;
		grid = new double[size][size];
	}
	
	public Grid(int width, int length) {
		this.width = width;
		this.length = length;
		grid = new double[width][length];
	}
	
	public synchronized double getValue(Point p) {
		// Get Point Value, using rectangular coordinate point (x, y)
		int[] index = pointToIndex(p);
		return this.grid[index[0]][index[1]];
	}
	
	public synchronized void setValue(Point p, double value) {
		// Set Point Value
		int[] index = pointToIndex(p);
		this.grid[index[0]][index[1]] = value;
		
		
		// TODO NOTE
		setChanged();
		notifyObservers(this);
	}
	
	public int[] pointToIndex(Point p) {
		// Convert Point(x,y) to the 2D Array index
		int _x = (int) p.getX();
		int _y = (int) (this.width - p.getY() - 1);
		
		return new int[]{_y, _x};
	}
	
	public Point indexToPoint(int row, int column) {
		// Convert Array index to Point(x, y)
		return new Point(column, this.width-row-1);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (double[] x : grid) {
			for (double y : x) {
				sb.append(y + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public double[][] getGrid() {
		return grid;
	}

	public int getWidth() {
		return width;
	}

	public int getLength() {
		return length;
	}
	
	public Point getLeftPoint(Point p) {
		if (p.x == 0) {
			return null;
		}
		return new Point(p.x-1, p.y);
	}
	
	public Point getUpPoint(Point p) {
		if (p.y == this.width-1) {
			return null;
		}
		return new Point(p.x, p.y+1);
	}
	
	public Point getRightPoint(Point p) {
		if (p.x == this.length-1) {
			return null;
		}
		return new Point(p.x+1, p.y);
	}
	
	public Point getDownPoint(Point p) {
		if (p.y == 0) {
			return null;
		}
		return new Point(p.x, p.y-1);
	}
	
	public Point getUpLeftPoint(Point p) {
		if (p.y == this.width-1 || p.x == 0) {
			return null;
		}
		return new Point(p.x-1, p.y+1);
	}
	
	public Point getUpRightPoint(Point p) {
		if (p.y == this.width-1 || p.x == this.length-1) {
			return null;
		}
		return new Point(p.x+1, p.y+1);
	}
	
	public Point getDownLeftPoint(Point p) {
		if (p.y == 0 || p.x == 0) {
			return null;
		}
		return new Point(p.x-1, p.y-1);
	}
	
	public Point getDownRightPoint(Point p) {
		if (p.y == 0 || p.x == this.length-1) {
			return null;
		}
		return new Point(p.x+1, p.y-1);
	}
	
	private Point getNeighborPoint(Point p, Direction d) {
		Point neighbor = null;
		switch (d) {
			case WEST:
				neighbor = getLeftPoint(p);
				break;
			case NORTH:
				neighbor = getUpPoint(p);
				break;
			case SOUTH:
				neighbor = getDownPoint(p);
				break;
			case EAST:
				neighbor = getRightPoint(p);
				break;
			case NORTHWEST:
				neighbor = getUpLeftPoint(p);
				break;
			case NORTHEAST:
				neighbor = getUpRightPoint(p);
				break;
			case SOUTHWEST:
				neighbor = getDownLeftPoint(p);
				break;
			case SOUTHEAST:
				neighbor = getDownRightPoint(p);
				break;
			default:
				break;
		}
		return neighbor;
	}
	
	public double getNeighborPointValue(Point p, Direction n) {
		Point neighbor = getNeighborPoint(p, n);
		if (neighbor != null) {
			return getValue(neighbor);
		}
		return -1;
	}
	
	public void setNeighborPointValue(Point p, Direction n, double val) {
		Point neighbor = getNeighborPoint(p, n);
		if (neighbor != null) {
			setValue(neighbor, val);
		}
	}
	

}
