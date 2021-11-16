package clean.ship61.absim;

import java.util.Observable;
import java.util.Observer;

import clean.ship61.absim.grid.OceanGrid;


/**
 * A Monitor is used to observe simulation's status, and the ocean/boat data changing.
 * For any data changing, the monitor could also notify its own observer. 
 *
 */
public class Monitor extends Observable implements Observer {

	private double oilSpill = 0;
	private double loadUsage = 0;
	private String status = "Stop";
	private double[][] grid;
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof OceanGrid) {
			OceanGrid ocean = (OceanGrid) arg;
			updateOilSpill(ocean);	// observer the oil spill amount
			updateGrid(ocean);		// observer the grid data (how oil spill)
		}
		else if (arg instanceof Boat) {
			Boat boat = (Boat) arg;
			updateBoatLoadUsage(boat);	// observer the boat sailing
		}
		else if (arg instanceof ABSimulation) {
			ABSimulation sim = (ABSimulation) arg;
			updateSimStatus(sim);		// observer the simulation status
		}
		
		// notify observers
		setChanged();
		notifyObservers(this);
	}
	
	private void updateGrid(OceanGrid ocean) {
		this.grid = ocean.getGrid();
	}

	private void updateOilSpill(OceanGrid ocean) {
		this.oilSpill = ocean.totalOil();
	}
	
	private void updateBoatLoadUsage(Boat boat) {
		this.loadUsage = boat.getLoadUsage();
	}
	
	private void updateSimStatus(ABSimulation sim) {
		this.status = sim.getStatus();
	}
	
	public double getTotalOilSpill() {
		return oilSpill;
	}
	
	public double getTotalLoadUsage() {
		return loadUsage;
	}
	
	public String getStatus() {
		return status;
	}
	
	public double[][] getGrid(){
		return grid;
	}
	
	public void reset() {
		this.loadUsage = 0;
		this.oilSpill = 0;
		this.status = "Stop";
		
		setChanged();
		notifyObservers(this);
	}

}
