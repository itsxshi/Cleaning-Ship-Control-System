package clean.ship61.absim;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import clean.ship61.absim.flow.*;
import clean.ship61.absim.grid.*;
import clean.ship61.absim.rule.*;

import java.util.Observable;
import java.util.Observer;


/**
 * ABSimulation is the core. It's used to:
 * 
 * 	1. Initialize the boat/ocean/rule/flow/monitor.
 *	2. Control the start/pasue/stop of the boat and flow.
 * 
 *
 */
public class ABSimulation extends Observable implements Runnable {
	
	private OceanGrid ocean;		
	private AFlow flow;			// The Flow used to control how the oil spill to other cell
	private IABRule rule;
	private Boat boat;
	
	private Thread thread = null; // the thread that runs my simulation
	private boolean paused = false;
	private boolean running = false; // set true if the simulation is running
	private String status = "Stop";
	
	private final Monitor monitor = new Monitor();
	
	private Config config = Config.instance();
	
	public ABSimulation() {
		init();
	}
	
	/**
	 * Initialize all the data needed to run a boat cleaning.
	 */
	private void init() {
		
		thread = new Thread(this); 
		running = false;
		paused = false;
		
		updateOcean();
		updateFlow();
		updateRule();
		updateBoat();	
		
		resetMonitor();
	}
	
	/**
	 * Create and set a new ocean based on the configuration.
	 * Also, reset all oil fill cells to this new ocean.
	 */
	private void updateOcean() {
		ocean = new OceanGrid(config.oceanWidth, config.oceanLength);
		
		for (Entry<Point, Integer> e : config.oilCells.entrySet()) {
			 ocean.setOil(e.getKey(), e.getValue());
		}
	}
	
	
	/**
	 * Create and set a new flow base on the configuration.
	 * There are three flow types.
	 */
	private void updateFlow() {
		Map<String, Object> param = new HashMap<>();
		param.put("ocean", ocean);
		param.put("delta", config.flowDelta);
		param.put("threshold", config.flowThreshold);
		param.put("speed", config.flowSpeed);
		param.put("direction", config.flowDirection);
		
		this.flow = FlowFactory.create(config.flowType, param);
	}
	
	/**
	 * Create and set a new boat sailing rule base on the configuration.
	 */
	private void updateRule() {
		this.rule = ABRuleFactory.create(config.ruleType);
	}
	
	/**
	 * Create and set a new boat, then assign the ocean to this boat.
	 */
	private void updateBoat() {
		boat = new Boat(config.boatLocation, config.boatSpeed);
		boat.setGrid(ocean);
	}

	/**
	 * Start running the boat, and the flow.
	 * Set status to "Running".
	 */
	public void start() {
		
		if (running == true) return; // A thread is already running
		
		init();
		
		running = true;
		thread.start();
		flow.start();
		
		setStatus("Running");
	}
	
	/**
	 * Pause the boat sailing and the flow. 
	 * Set status to "Pause".
	 */
	public void pause() {
		if (!running) return;
		
		this.paused = !this.paused;
		flow.pause();
		
		if(this.paused) {
			setStatus("Paused");
		}
		else {
			setStatus("Running");
		}
	}
	
	/**
	 * Stop running the boat and the flow.
	 * Set status to "Stop".
	 */
	public void stop() {
		if (!running) return;
		
		flow.terminate();
		monitor.reset();
		this.running = false;
		thread = null;
		
		setStatus("Stop");
	}
	
	public String getStatus() {
		return status;
	}

	/**
	 * Set s new status, and notify the Observes
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
		
		setChanged();
		notifyObservers(this);
	}

	/**
	 * A warp method for Thread.sleep
	 * @param millis
	 */
	private void sleep(long millis) {
    	try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * Reset the monitor if boat/ocean changed.
	 * Used the monitor to observe the data changing from the boat and the ocean.
	 */
	private void resetMonitor() {
		ocean.addObserver(monitor);
		boat.addObserver(monitor);
		this.addObserver(monitor);
		
		monitor.reset();
	}
	
	/**
	 * Provide api for UI panel to observe monitor.
	 * So that the UI could know how data changed. 
	 * @param any observer who want to know data change. 
	 */
	public void observeMonitor(Observer o) {
		monitor.addObserver(o);
	}
	
	/**
	 * Start to run the boat cleaning.
	 */
	@Override
	public void run() {
		
		while(running) {
			
			if(!paused) {
				
				// move the boat, and collect the oil
				boolean cleaning = boat.moveAndClean(rule);
				
				// if no need for any cleaning, means the boat finished its task.
				// all oil are collected.
				if (!cleaning) {
					this.stop();
					break;
				};
				
				// used to print the ocean data in terminal, only for testing
				System.out.println(ocean);	
				
				// calculate how much time cost for the boat to clean one cell.
				// times 1000, use sleep to simulate the time cost. 
				sleep((long) (1000 * (1.0/boat.getSpeed())));  
			}
			else {
				sleep(500);	// wait for pause
			}
		}
		thread = null;
	}
	
	
	/**
	 * Run the simulation without UI
	 * @param args
	 */
//	public static void main(String[] args) {
//		ABSimulation sim = new ABSimulation();
//		sim.start();	
//	}

}
