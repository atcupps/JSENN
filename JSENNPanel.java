package graphics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The JSENNPanel allows for graphical representation of all environment
 * objects and Creature objects in the JSENN simulation. Using a timer
 * and ActionListener, JSENNPanel also updates all Creatures and environment
 * objects.
 * 
 * Copyright (C) 2022 Andrew Cupps, CC BY-SA
 * 
 * @author Andrew Cupps
 * @version 18 Dec 2022
 */
public class JSENNPanel extends JPanel implements ActionListener {
	
	/**
	 * Timer used for updating all objects necessary to update.
	 */
	private Timer timer;
	
	/**
	 * Screen size dimensions in pixels.
	 */
	private final int SIZE_X = 1920, SIZE_Y = 1080;
	
	/**
	 * Number of times per second objects should be updated.
	 */
	private final int UPDATE_RATE = 60;
	
	/**
	 * Sets the dimensions for the screen based on the SIZE_X and SIZE_Y
	 * values, creates a new timer based on the UPDATE_RATE value, 
	 */
	public JSENNPanel() {
		
	}
}
