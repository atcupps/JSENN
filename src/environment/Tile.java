package environment;

import functions.Function;

/**
 * Tiles compose the environment that Creatures are simulated to inhabit.
 * Each Tile has an elevation value; from the elevation value, base 
 * tile nutrition value is also calculated; from the combination of these, 
 * Tile color is calculated.
 * 
 * Copyright (C) 2022 Andrew Cupps, CC BY-SA
 * 
 * @author Andrew Cupps
 * @version 18 Dec 2022
 */
public class Tile {

	private float elevation, baseNutrition, baseRed, baseGreen, baseBlue,
								nutrition, red, green, blue, energyUse;
	private TileType tileType;
	
	/**
	 * All Tiles with post-sigmoid elevation below WATER_ELEVATION will be
	 * water tiles; all Tiles with post-sigmoid elevation above MOUNTAIN_ELEVATION
	 * will be mountain tiles.
	 */
	private static final double	WATER_ELEVATION = 0.2689,
								MOUNTAIN_ELEVATION = 0.9;
	
	/**
	 * Inputs for the Function.sigmoid(...) function used to process elevation.
	 */
	private static final double SIGMOID_SHIFT_X = 1, 
								SIGMOID_SHIFT_Y = 0, 
								SIGMOID_SCALE_X = 1,
								SIGMOID_SCALE_Y = 1;
	
	/**
	 * Assigns the parameter elevation to the elevation field, then calculates
	 * other factors based off elevation.
	 * @param elevation - the elevation of the Tile before sigmoidification
	 */
	public Tile(double elevationPreprocessed) {
		
		/*
		 * Processing of elevation value
		 */
		elevation = (float) Function.sigmoid(elevationPreprocessed, 
				SIGMOID_SCALE_X, SIGMOID_SCALE_Y, SIGMOID_SHIFT_X, SIGMOID_SHIFT_Y);
		
		/*
		 * Determination of TileType based on processed elevation value
		 */
		if (elevation <= WATER_ELEVATION) {
			tileType = TileType.WATER;
		}
		else if (elevation >= MOUNTAIN_ELEVATION) {
			tileType = TileType.MOUNTAIN;
		}
		else {
			tileType = TileType.SOIL;
		}
	}
	
	public enum TileType {
		SOIL, MOUNTAIN, WATER;
	}
}
