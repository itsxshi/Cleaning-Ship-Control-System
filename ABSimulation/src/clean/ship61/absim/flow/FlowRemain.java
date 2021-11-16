package clean.ship61.absim.flow;

import clean.ship61.absim.grid.Grid;

/**
 * The oil will remain in the origin position, won't flow.
 *
 */
public class FlowRemain extends AFlow {

	public FlowRemain(Grid grid) {
		super(grid);
	}

	@Override
	public void update() {
		// Nothing to do, remain the grid

	}

}
