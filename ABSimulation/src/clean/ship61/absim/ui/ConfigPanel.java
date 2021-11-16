package clean.ship61.absim.ui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import clean.ship61.absim.Config;
import clean.ship61.absim.flow.Direction;
import clean.ship61.absim.flow.FlowType;
import clean.ship61.absim.rule.RuleType;

/**
 * Panel to set/update the configurations, and control the simulation. 
 *
 */
public class ConfigPanel extends JPanel{

	private static final long serialVersionUID = 142153109940794091L;

	private Config config = Config.instance();
	
	private JLabel lBoatLocation;
	private JLabel lOilCells;
	private ABApp application;
	
	public ConfigPanel(ABApp application) {
		this.application = application;
		init();
	}
	
	public void init() {
		setOceanConfig();
		setBoatConfig();
		setFlowConfig();
		setOilConfig();
		setControlBtn();
	}

	private void setOceanConfig() {
		
		// Ocean Config Label
		Box vBox = Box.createVerticalBox();
		JLabel lOcean = new JLabel("Ocean Size");
		lOcean.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		vBox.add(lOcean);
        
		// Ocean Width
        Box hBox = Box.createHorizontalBox();
        hBox.add(new JLabel("Width:   "));
        hBox.add(new IntTextField(3, "oceanWidth"));
        hBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        vBox.add(hBox);
        
        // Ocean Length
        hBox = Box.createHorizontalBox();
        hBox.add(new JLabel("Length: "));
        hBox.add(new IntTextField(3, "oceanLength"));
        hBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        vBox.add(hBox);

        vBox.add(new JSeparator(SwingConstants.HORIZONTAL));
        this.add(vBox);
	}
	
	private void setFlowConfig() {
		Box vBox = Box.createVerticalBox();
		
		JComboBox<Direction> cbDirection = new JComboBox<>();
		
		// Flow Config Label
		JLabel lflow = new JLabel("Flow Config");
		lflow.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		vBox.add(lflow);
		
		// Flow Type ComboxBox
		Box hBox = Box.createHorizontalBox();
        hBox.add(new JLabel("Type:"));
        JComboBox<FlowType> cbFlowType = new JComboBox<>();
        cbFlowType.setModel(new DefaultComboBoxModel<>(FlowType.values()));
        cbFlowType.setPrototypeDisplayValue(FlowType.Around);
        cbFlowType.setSelectedItem(config.flowType);
        cbFlowType.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					config.flowType = (FlowType) cbFlowType.getSelectedItem();
					updateDirectionComboBox(cbDirection);
                }
				
			}
		});
        hBox.add(cbFlowType);
        hBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        vBox.add(hBox);
		
        // Flow Direction ComboxBox
        hBox = Box.createHorizontalBox();
        hBox.add(new JLabel("Direction:"));
        cbDirection.setModel(new DefaultComboBoxModel<>(Direction.values()));
        cbDirection.setPrototypeDisplayValue(Direction.NORTH);
        cbDirection.setSelectedItem(config.flowDirection);
        cbDirection.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					config.flowDirection = (Direction) cbDirection.getSelectedItem();
                }
			}
		});
        hBox.add(cbDirection);
        hBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        updateDirectionComboBox(cbDirection);
        vBox.add(hBox);
        
        // Flow Delta
		hBox = Box.createHorizontalBox();
        hBox.add(new JLabel("Delta:"));
        hBox.add(new FloatTextField(3, "flowDelta"));
        hBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        vBox.add(hBox);
        
        // Flow Threshold	
        hBox = Box.createHorizontalBox();
        hBox.add(new JLabel("Threshold:"));
        hBox.add(new FloatTextField(3, "flowThreshold"));
        hBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        vBox.add(hBox);
        
        // Flow Speed
        hBox = Box.createHorizontalBox();
        hBox.add(new JLabel("Speed:"));
        hBox.add(new FloatTextField(3, "flowSpeed"));
        hBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        vBox.add(hBox);
        
        vBox.add(new JSeparator(SwingConstants.HORIZONTAL));
		this.add(vBox);
		
	}
	
	private void setBoatConfig() {
		Box vBox = Box.createVerticalBox();
		
		// Boat Config Label
		JLabel bflow = new JLabel("Boat Config");
		bflow.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		vBox.add(bflow);

		// Rule ComboBox
		Box hBox = Box.createHorizontalBox();
        hBox.add(new JLabel("Rule:"));
        JComboBox<RuleType> cbRule = new JComboBox<>();
        cbRule.setModel(new DefaultComboBoxModel<>(RuleType.values()));
        cbRule.setPrototypeDisplayValue(RuleType.MaxValue);
        cbRule.setSelectedItem(config.ruleType);
        cbRule.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					config.ruleType = (RuleType) cbRule.getSelectedItem();
                }
			}
		});
        hBox.add(cbRule);
        hBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        vBox.add(hBox);
		
        // Boat Speed
        hBox = Box.createHorizontalBox();
        hBox.add(new JLabel("Speed:"));
        hBox.add(new FloatTextField(3, "boatSpeed"));
        hBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        vBox.add(hBox);
        
        // Boat Location
        hBox = Box.createHorizontalBox();
        hBox.add(new JLabel("Location:"));
        lBoatLocation = new JLabel(String.format("(%d, %d)", config.boatLocation.x, config.boatLocation.y));
        hBox.add(lBoatLocation);
        JButton changeBtn = new JButton("Reset");
        changeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateBoatLocationAction();
			}
		});
        hBox.add(changeBtn);
        hBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        vBox.add(hBox);
		
		vBox.add(new JSeparator(SwingConstants.HORIZONTAL));
		this.add(vBox);
	}
	
	private void setOilConfig() {
		Box vBox = Box.createVerticalBox();
		
		// Oil Spill Label
		JLabel lOil = new JLabel("Oil spill");
		lOil.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		vBox.add(lOil);
		
		// Oil Cells Content
		Box hBox = Box.createHorizontalBox();
		lOilCells = new JLabel();
		updateOilCellLabelContent();
		hBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		hBox.add(lOilCells);
		vBox.add(hBox);
		
		// Add Oil cell button
		hBox = Box.createHorizontalBox();
		JButton addOilBtn = new JButton("Add");
		addOilBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				addOilCellAction();
			}
		});
		hBox.add(addOilBtn);
		
		// Oil Clear button
		JButton clearOilBtn = new JButton("Clear");
		clearOilBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				int option = JOptionPane.showConfirmDialog(null, "Clear all oil spill cells?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					config.clearOilCells();
					updateOilCellLabelContent();
				}
			}
		});
        hBox.add(clearOilBtn);
        hBox.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        vBox.add(hBox);
		
		vBox.add(new JSeparator(SwingConstants.HORIZONTAL));
		this.add(vBox);
	}
	
	private void setControlBtn() {
		Box vBox = Box.createVerticalBox();
		
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				application.actionPerformed(e);
			}
		};
		
		// Control Label
		JLabel lControl = new JLabel("Control");
		lControl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		vBox.add(lControl);
		
		// Start Button
		JButton startBtn = new JButton("Start");
		startBtn.addActionListener(listener);
		vBox.add(startBtn);
		
		// Pause/Resume Button
		JButton pauseBtn = new JButton("Pause/Resume");
		pauseBtn.addActionListener(listener);
		vBox.add(pauseBtn);
		
		// Stop Button
		JButton stopBtn = new JButton("Stop");
		stopBtn.addActionListener(listener);
		vBox.add(stopBtn);
		
        vBox.add(new JSeparator(SwingConstants.HORIZONTAL));
		this.add(vBox);
	}
	
	/**
	 *  Construct the oil cells content
	 */
	private void updateOilCellLabelContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		for (Entry<Point, Integer> e : config.oilCells.entrySet()) {
			String s = String.format("Cell(%d, %d), %d<br>", e.getKey().x, e.getKey().y, e.getValue());
			sb.append(s);
		}
		sb.append("</html>");
		lOilCells.setText(sb.toString());
	}
	
	/**
	 *
	 * An integer text field, if not integer, popup ERROR dialog 
	 *
	 */
	class IntTextField extends JTextField {

		private static final long serialVersionUID = 6331219973494565675L;

		public IntTextField(int columns, String fieldName) {
			super(columns);
			
			this.setText(String.valueOf(config.getFieldValue(fieldName)));
			
			this.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
				}

				@Override
				public void focusLost(FocusEvent e) {
					try {
						int intValue = Integer.valueOf(IntTextField.this.getText());
						config.setFieldValue(fieldName, intValue);
					} catch (Exception e2) {
						showNumError();
						IntTextField.this.requestFocus();
					}
				}
			});
		}
	}
	
	/**
	 * An Float number text field, if not float, popup ERROR dialog 
	 *
	 * @author Yaojia Lyu
	 *
	 */
	class FloatTextField extends JTextField {
	
		private static final long serialVersionUID = -8155640462424884543L;

		public FloatTextField(int columns, String fieldName) {
			super(columns);
			
			this.setText(String.valueOf(config.getFieldValue(fieldName)));
			
			this.addFocusListener(new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
				}

				@Override
				public void focusLost(FocusEvent e) {
					try {
						float value = Float.valueOf(FloatTextField.this.getText());
						config.setFieldValue(fieldName, value);
					} catch (Exception e2) {
						showNumError();
						FloatTextField.this.requestFocus();
					}
				}
			});
		}
	}
	
	private void showNumError() {
		JOptionPane.showMessageDialog(
                null,
                "Please input valid number.",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
	}
	
	/**
	 * 
	 * Check if the point valid or not. if over the ocean size, it's invalid.
	 * @param p
	 * @return
	 */
	private boolean isValidLocation(Point p) {
		if(p.x > config.oceanLength -1 || p.y > config.oceanWidth-1) {
			return false;
		}
		return true;
	}
	
	private void showInvalidLocationDialog() {
		JOptionPane.showMessageDialog(
                null,
                "Cannot exceed the ocean size!",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
	}
	
	private void updateDirectionComboBox(JComboBox<Direction> cb) {
		if(config.flowType != FlowType.Direction) {
			cb.setEnabled(false);
		}
		else {
			cb.setEnabled(true);
		}
	}
	
	/**
	 * 
	 * Action when user click update boat location button
	 * 
	 */
	private void updateBoatLocationAction() {
		JTextField x = new JTextField();
		JTextField y = new JTextField();
		x.setText(String.valueOf(config.boatLocation.x));
		y.setText(String.valueOf(config.boatLocation.y));
		
		Object[] message = {
		    "X Position:", x,
		    "Y Position:", y
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Boat Location", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			
			int xValue = Integer.valueOf(x.getText());
			int yValue = Integer.valueOf(y.getText());
			Point location = new Point(xValue, yValue);
			
			if (!isValidLocation(location)) {
				showInvalidLocationDialog();
			}else {
				config.boatLocation = location;
				lBoatLocation.setText(String.format("(%d, %d)", config.boatLocation.x, config.boatLocation.y));
			}
			
		}
	}
	
	/**
	 * Action when user click Add oil cell button
	 */
	private void addOilCellAction() {
		JTextField x = new JTextField();
		JTextField y = new JTextField();
		JTextField amount = new JTextField();
		
		Object[] message = {
		    "X Position:", x,
		    "Y Position:", y,
		    "Oil spill amount:", amount,
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Add Oil Spill Location", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			
			int xValue = Integer.valueOf(x.getText());
			int yValue = Integer.valueOf(y.getText());
			Point location = new Point(xValue, yValue);
			int amountValue;
			
			if (!isValidLocation(location)) {
				showInvalidLocationDialog();
				return;
			} 
			try {
				amountValue = Integer.valueOf(amount.getText());
		    } catch (Exception e) {
		        showNumError();
		        return;
		    }
			
			config.addOilCell(location, amountValue);
			updateOilCellLabelContent();
			
		} else {
		    System.out.println("canceled");
		}
	}

}
