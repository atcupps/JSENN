package graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import creatures.Creature;
import environment.Tile;

/**
 * The JSENNPanel allows for graphical representation of all environment
 * objects and Creature objects in the JSENN simulation. Using a timer
 * and ActionListener, JSENNPanel also updates all Creatures and environment
 * objects.
 * 
 * Copyright (C) 2022 Andrew Cupps, CC BY 4.0
 * 
 * @author Andrew Cupps
 * @version 23 Dec 2022
 */
public class JSENNPanel extends JPanel implements ActionListener {
	
	/**
	 * Timer used for updating all objects necessary to update.
	 */
	private static Timer timer;
	
	/**
	 * Screen size dimensions in pixels.
	 */
	public static final int SIZE_X = 1440, SIZE_Y = 900;
	/**
	 * Number of times per second objects should be updated.
	 */
	private static final int UPDATE_RATE = 60;
	
	/**
	 * Size of tiles in pixels
	 */
	private static final int TILE_SIZE = 10;
	
	/**
	 * Minimum number of creatures
	 */
	private static final int MIN_NUM_CREATURES = 30;
	
	private static boolean isPaused = false;
	
	/**
	 * The set of all tiles that will be used in this simulation; size is 
	 * based on NUM_TILES_X and NUM_TILES_Y, which are based on the size of the
	 * screen divided by the size of size of a tile.
	 */
	private static final int NUM_TILES_X = SIZE_X / TILE_SIZE,
							 NUM_TILES_Y = SIZE_Y / TILE_SIZE;
	private static Tile[][] tiles = new Tile[NUM_TILES_X][NUM_TILES_Y];
	
	/*
	 * Assigning each index of the 2D tiles array to be a tile with elevation
	 * values according to KdotJPG's OpenSimplex2 noise generator. The 
	 * SEED double is randomly generated for each simulation;
	 * SCALING_FACTOR is used to scale the noise.
	 */
	private final long SEED = (long) (Math.random() * 10000000);
	private static final double SCALING_FACTOR = 0.03;
	{
		/*
		 * Creating non-border Tiles
		 */
		double i = 0.01, j = 0.01;
		for (int xIndex = 1; xIndex < NUM_TILES_X - 1; xIndex++) {
			for (int yIndex = 1; yIndex < NUM_TILES_Y - 1; yIndex++) {
				float noiseValue = OpenSimplex2S.noise2(SEED, i, j);
				tiles[xIndex][yIndex] = new Tile(noiseValue + 0.5);
				j += SCALING_FACTOR;
			}
			j = 0.01;
			i += SCALING_FACTOR;
		}
		
		/*
		 * Creating border Tiles
		 */
		for (int xIndex = 0; xIndex < NUM_TILES_X; xIndex++) {
			tiles[xIndex][0] = new Tile(true);
			tiles[xIndex][NUM_TILES_Y - 1] = new Tile(true);
		}
		for (int yIndex = 0; yIndex < NUM_TILES_Y; yIndex++) {
			tiles[0][yIndex] = new Tile(true);
			tiles[NUM_TILES_X - 1][yIndex] = new Tile(true);
		}
	}
	
	
	/**
	 * NOTE: CHUNK IMPLEMENTATION OF CREATURES NOT CURRENTLY USED (21 Dec 2022)
	 * 
	 * A 2D array of Collections which contains all the Creature objects in
	 * the simulation; this 2D array represents subsections of the tile grid
	 * used for the environment, where each chunk represents a certain area
	 * of the simulation in order to allow searches for Creatures to be done
	 * more efficiently in situations where creatures are searched for in
	 * a given radius or location. The size of chunks is dependent on the
	 * maximum size limit for Creatures, being 5 times the maximum radius
	 * of a Creature.
	 */
//	private static final int CHUNK_SIZE = 
//			3 * (int) Math.ceil(Creature.getCreatureSizeMax());
//	private static final int NUM_CHUNKS_X = NUM_TILES_X / CHUNK_SIZE,
//							 NUM_CHUNKS_Y = NUM_TILES_Y / CHUNK_SIZE;
//	private static Collection<Creature>[][] creatures = 
//			new Collection<Creature>[NUM_CHUNKS_X][NUM_CHUNKS_Y];
//	
	/*
	 * NOTE: CHUNK IMPLEMENTATION OF CREATURES NOT CURRENTLY USED (21 Dec 2022)
	 * 
	 * Assigning each index in the 2D creatures array to be a HashSet of
	 * Creatures.
	 */
//	static {
//		for (int i = 0; i < NUM_CHUNKS_X; i++) {
//			for (int j = 0; j < NUM_CHUNKS_Y; j++) {
//				creatures[i][j] = new HashSet<Creature>();
//			}
//		}
//	}
//	
	
	/*
	 * AS OF VERSION 21 DEC 2022:
	 * 
	 * Creatures are stored in a single Collection of all Creatures.
	 */
	private static List<Creature> creatures = new ArrayList<Creature>();
	{
		for (int i = 0; i < 60; i++) {
			creatures.add(new Creature());
		}
	}
	
	/**
	 * Sets the dimensions for the screen based on the SIZE_X and SIZE_Y
	 * values, creates a new timer based on the UPDATE_RATE value, and 
	 * begins the timer.
	 */
	public JSENNPanel() {
		this.setPreferredSize(new Dimension(SIZE_X, SIZE_Y));
		this.setBackground(Color.MAGENTA);  //Magenta stands out,
											//easy to spot missing texture.
		timer = new Timer(1000 / UPDATE_RATE, this);
		timer.start();
		
		//Used for debugging visually
		//Thread inputThread = new Thread(new InputThread());
		//inputThread.start();
	}
	
	/**
	 * Overrides the JPanel paint method; draws all Tiles and Creatures.
	 */
	@Override
	public void paint(Graphics og) {
		Graphics2D g = (Graphics2D) og;
		super.paint(g);
		
		/*
		 * Drawing all tiles
		 */
		for (int i = 0; i < NUM_TILES_X; i++) {
			for (int j = 0; j < NUM_TILES_Y; j++) {
				int left = i * TILE_SIZE;
				int top = j * TILE_SIZE;
				g.setPaint(tiles[i][j].getTileColor());
				g.fillRect(left, top, TILE_SIZE, TILE_SIZE);
			}
		}
		
		/*
		 * Drawing all creatures
		 */
		for (Creature c : creatures) {
			/*
			 * Drawing Creature bodies
			 */
			g.setPaint(c.getCreatureColor());
			int size = (int) c.getSize();
			int x = (int) c.getX();
			int y = (int) c.getY();
			g.fillOval(x - size, y - size, size * 2, size * 2);
			g.setPaint(new Color(0,0,0));
			g.setStroke(new BasicStroke(2));
			g.drawOval(x - size - 1, y - size - 1, size * 2 + 2, size * 2 + 2);
			
			/*
			 * Drawing Creature vision
			 */
			g.setPaint(Color.BLACK);
			g.setStroke(new BasicStroke(2));
			int visionX = (int) c.getVisionXGraphics();
			int visionY = (int) c.getVisionYGraphics();
			g.drawLine(x, y, visionX, visionY);
			g.fillOval(visionX - 2, visionY - 2, 4, 4);
		}
	}
	
	/**
	 * Updating all elements and graphics
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (!isPaused) {
			/*
			 * Updating all creatures, and removing some if necessary, or adding
			 * new ones
			 */
			int numCreatures = creatures.size();
			for (int i = 0; i < numCreatures; i++) {
				if (!creatures.get(i).update()) { //creature must be removed
					creatures.remove(i--);
					numCreatures--;
				} else {
					if (creatures.get(i).shouldReproduce()) {
						creatures.add(creatures.get(i++).reproduce());
						numCreatures++;
					}
				}
			}
			
			/*
			 * Updating all Tiles
			 */
			for (int i = 0; i < NUM_TILES_X; i++) {
				for (int j = 0; j < NUM_TILES_Y; j++) {
					tiles[i][j].update();
				}
			}
			
			while (creatures.size() < MIN_NUM_CREATURES) {
				creatures.add(new Creature());
			}
		}
		
		
		repaint();
	}
	
	public static double getTileEnergyRate(double x, double y) {
		return tiles[(int) x / TILE_SIZE][(int) y / TILE_SIZE].getTileEnergyRate();
	}
	
	public static double eat(double x, double y) {
		return tiles[(int) x / TILE_SIZE][(int) y / TILE_SIZE].eat();
	}
	
	public static Color getTileColor(double x, double y) {
		return tiles[(int) x / TILE_SIZE][(int) y / TILE_SIZE].getTileColor();
	}
	
	public static void togglePause() { 
		isPaused = !isPaused;
	}
}
