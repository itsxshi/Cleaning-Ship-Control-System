package clean.ship61.absim.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;


public abstract class ABApp implements ActionListener {

	protected JFrame frame;
	protected MenuManager menuMgr;
	
	public ABApp() {
		initGUI();
	}
	

	/**
	 * Initialize the Graphical User Interface
	 */
    public void initGUI() {
    	frame = new JFrame();
		frame.setTitle("ABApp");

		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //JFrame.DISPOSE_ON_CLOSE)
		
		
		menuMgr = new MenuManager(this);
		
		frame.setJMenuBar(menuMgr.getMenuBar()); // Add a menu bar to this application
		
		frame.setLayout(new BorderLayout());
		frame.add(getWestPanel(), BorderLayout.WEST);
		frame.add(getSouthPanel(), BorderLayout.SOUTH);
		frame.add(getCenterPanel(), BorderLayout.CENTER);
    }
 
    
    /**
     * Override this method to provide the control panel panel.
     * @return a JPanel, which contains the West content of of your application
     */
    public abstract JPanel getWestPanel();
    
    /**
     * Override this method to provide the main content panel.
     * @return a JPanel, which contains the main content of of your application
     */
    public abstract JPanel getCenterPanel();
    
    

    /**
     * Override this method to provide the control panel panel.
     * @return a JPanel, which contains the South content of of your application
     */
    public abstract JPanel getSouthPanel();


    /**
     * A convenience method that uses the Swing dispatch threat to show the UI.
     * This prevents concurrency problems during component initialization.
     */
    public void showUI() {
    	
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
            	frame.setVisible(true); // The UI is built, so display it;
            }
        });
    	
    }
    
    /**
     * Shut down the application
     */
    public void exit() {
    	frame.dispose();
    	System.exit(0);
    }

    /**
     * Override this method to show an About Dialog
     */
    public void showHelp() {
    	
    }
    
    public void showDemo(int i) {
    	
    }

 
}

