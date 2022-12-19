package graphics;

import javax.swing.JFrame;

import functions.Function;

/**
 * JSENNFrame is the main class of JSENN; JSENNFrame contains the main
 * method used to drive the entire JSENN project; furthermore, it allows
 * for creation, update, and graphical representation of all JSENN objects.
 * 
 * Copyright (C) 2022 Andrew Cupps, CC BY-SA
 * 
 * @author Andrew Cupps
 * @version 19 Dec 2022
 */
public class JSENNFrame extends JFrame {

	/**
	 * Constructor for a JSENNFrame which creates a new JSENNPanel and adds
	 * it to this JFrame, then setting up graphical capabilities for this
	 * project.
	 */
	public JSENNFrame() {
		
		JSENNPanel panel = new JSENNPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("JSENN");
	}
	
	/**
	 * Main method which creates a new JSENNFrame object.
	 * @param args
	 */
	public static void main(String[] args) {
		new JSENNFrame();
	}
}
