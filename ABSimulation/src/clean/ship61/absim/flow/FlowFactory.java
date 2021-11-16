package clean.ship61.absim.flow;

import java.util.Map;

import clean.ship61.absim.grid.Grid;

public class FlowFactory {
	
	/**
	 * Return the specific Flow, use to create Flow instance.
	 * 
	 * @param flowType
	 * @param param the parameters to create Flow.
	 * @return
	 */
	public static AFlow create(FlowType flowType, Map<String, Object> param) {
		
		Grid grid = (Grid) param.get("ocean");
		float delta = (float) param.get("delta");
		float threshold = (float) param.get("threshold");
		float speed = (float) param.get("speed");
		
		Direction direction = Direction.EAST; // For default initialize
		if (param.get("direction") != null) {
			direction = (Direction) param.get("direction");
		}
		
		AFlow flow = null;
		
		switch (flowType) {
		case Around:
			flow = new FlowAround(grid, delta, threshold);
			break;
		case Random:
			flow = new FlowRandom(grid, delta, threshold);
			break;
		case Remain:
			flow = new FlowRemain(grid);
			break;
		case Direction:
			flow = new FlowOneDirection(grid, delta, threshold, direction);
			break;
		default:
			break;
		}
		
		flow.setSpeed(speed);
		
		return flow;
		
	}

}
