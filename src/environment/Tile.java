package environment;

import java.awt.Color;

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
 * @version 19 Dec 2022
 */
public class Tile {

	private float elevation, baseNutrition, baseRed, baseGreen, baseBlue,
								nutrition, energyUse;
	private int red, green, blue;
	private TileType tileType;
	
	/**
	 * All Tiles with post-sigmoid elevation below WATER_ELEVATION will be
	 * water tiles; all Tiles with post-sigmoid elevation above MOUNTAIN_ELEVATION
	 * will be mountain tiles.
	 */
	private static final double	WATER_ELEVATION = 0.2189,
								MOUNTAIN_ELEVATION = 0.8;
	
	/**
	 * Amount of energy per update required for Creatures to stay in a Tile.
	 */
	private static final double WATER_ENERGY_USE_RATE = 1.0,
								MOUNTAIN_ENERGY_USE_RATE = 0.8,
								SOIL_ENERGY_USE_RATE = 0.3;
	
	/**
	 * Maximum base nutrition of a soil tile, and the rate at which base
	 * nutrition decreases with elevation
	 */
	private static final double SOIL_MAX_BASE_NUTRITION = 1000;
	private static final double SOIL_NUTRITION_RATE = 250;
	
	/**
	 * Inputs for the Function.sigmoid(...) function used to process elevation.
	 */
	private static final double SIGMOID_SHIFT_X = 1.0, 
								SIGMOID_SHIFT_Y = 0.0, 
								SIGMOID_SCALE_X = 1.0,
								SIGMOID_SCALE_Y = 1.0;
	
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
		
		/*
		 * Determination of base nutritional value, energy use, and base colors
		 */
		switch (tileType) {
		
		case WATER: 
			baseNutrition = 0;
			nutrition = baseNutrition;
			energyUse = (float) WATER_ENERGY_USE_RATE;
			baseRed = 2;
			baseGreen = 0 + (100 / ((float) WATER_ELEVATION)) * elevation;
			baseBlue = 99 + (100 / ((float) WATER_ELEVATION)) * elevation;
			red = (int) baseRed;
			green = (int) baseGreen;
			blue = (int) baseBlue;
			break;
		
		case MOUNTAIN: 
			baseNutrition = 0;
			nutrition = baseNutrition;
			energyUse = (float) MOUNTAIN_ENERGY_USE_RATE;
			baseRed = 200 + (55 / ((float) MOUNTAIN_ELEVATION)) * elevation;
			baseGreen = 200 + (55 / ((float) MOUNTAIN_ELEVATION)) * elevation;
			baseBlue = 200 + (55 / ((float) MOUNTAIN_ELEVATION)) * elevation;
			red = (int) baseRed;
			green = (int) baseGreen;
			blue = (int) baseBlue;
			break;
			
		default:
			baseNutrition = (float) (SOIL_MAX_BASE_NUTRITION - SOIL_NUTRITION_RATE * elevation);
			nutrition = baseNutrition;
			energyUse = (float) SOIL_ENERGY_USE_RATE;
			baseRed = 16 + (200 / (float) (MOUNTAIN_ELEVATION - WATER_ELEVATION)) 
					* (float) (elevation - WATER_ELEVATION);
			baseGreen = 82 + (100 / (float) (MOUNTAIN_ELEVATION - WATER_ELEVATION))
					* (float) (elevation - WATER_ELEVATION);
			baseBlue = 0 + (200 / (float) (MOUNTAIN_ELEVATION - WATER_ELEVATION))
					* (float) (elevation - WATER_ELEVATION);
			red = (int) baseRed;
			green = (int) baseGreen;
			blue = (int) baseBlue;
		}
	}
	
	public enum TileType {
		SOIL, MOUNTAIN, WATER;
	}
	
	/**
	 * Returns the color of a Tile object.
	 * @return a new Color constructed with the Tile's red, green, and blue fields.
	 */
	public Color getTileColor() {
		try {
			return new Color(red, green, blue);
		} catch (Exception e) {
			System.out.println("Invalid colors: Red: " + red + ", Green: " + green + ", Blue" + blue);
		}
		
		return null;
	}
	
	//public Color getTileColor() { return new Color(red, green, blue); }
}
