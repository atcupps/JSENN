package creatures;

import java.awt.Color;

import functions.Function;
import graphics.JSENNPanel;
import neural.Layer;
import neural.Network;

/**
 * Creatures are the most important part of the JSENN program; Creatures are
 * controlled by neural networks; elements of these networks as well as fields
 * within Creatures are inherited with slight variations whenever Creatures
 * reproduce. The goal of the entire simulation is to see how natural
 * selection and interactions between Creatures, the environment, and other
 * Creatures influences long-term trends in the evolution of various 
 * traits in Creatures and their neural networks.
 * 
 * Copyright (C) 2022 Andrew Cupps, CC BY 4.0
 * 
 * @author Andrew Cupps
 * @version 21 Dec 2022
 */
public class Creature {
	
	/*
	 * Maximum maximum radius of any Creature in pixels
	 */
	private static final double CREATURE_SIZE_MAX = 10;
	
	/*
	 * Minimum radius of any Creature in pixels
	 */
	private static final double CREATURE_SIZE_MIN = 4;
	
	/*
	 * Maximum maxHealth of any Creature
	 */
	private static final double CREATURE_MAXHEALTH_MAX = 100;
	
	/*
	 * Minimum maxHealth of any Creature
	 */
	private static final double CREATURE_MAXHEALTH_MIN = 40;
	
	/*
	 * Maximum Attack damage of any Creature
	 */
	private static final double CREATURE_ATTACK_MAX = 50;
	
	/*
	 * Minimum Attack damage of any Creature
	 */
	private static final double CREATURE_ATTACK_MIN = 0;
	
	/*
	 * Maximum Defense damage of any Creature
	 */
	private static final double CREATURE_DEFENSE_MAX = 50;
	
	/*
	 * Minimum Defense damage of any Creature
	 */
	private static final double CREATURE_DEFENSE_MIN = 0;
	
	/*
	 * Maximum amount of genetic variance between two generations of a Creature
	 */
	private static final double CREATURE_VARIANCE_MAX = 1;
	
	/*
	 * Minimum amount of genetic variance between two generations of a Creature
	 */
	private static final double CREATURE_VARIANCE_MIN = 0;
	
	/*
	 * Maximum linearVelocity of a creature in pixels / update
	 */
	private static final double CREATURE_LINEAR_V_MAX = 10;
	
	/*
	 * Maximum angularVelocity of a creature in degrees / update
	 */
	private static final double CREATURE_ANGULAR_V_MAX = 30;
	
	/*
	 * Maximum maxEnergy of a creature
	 */
	private static final double CREATURE_MAXENERGY_MAX = 1000;
	
	/*
	 * Minimum maxEnergy of a creature
	 */
	private static final double CREATURE_MAXENERGY_MIN = 400;
	
	/*
	 * Minimum energyUseRate per update
	 */
	private static final double CREATURE_ENERGY_USE_RATE_MIN = 4;
	
	/*
	 * Energy use rate scaling per pixel of size; energy use rate scales
	 * linearly with size
	 */
	private static final double CREATURE_ENERGY_USE_RATE_SCALING = 0.75;
	
	/*
	 * Health regeneration rate in health per update
	 */
	private static final double CREATURE_HEALTH_REGENERATION_RATE = 0.2;
	
	/*
	 * Inherited characteristic fields of a Creature
	 */
	private double attack, defense, red, blue, green, size, markerValue, geneticVariance, maxLinearVelocity,
					visionRed, visionGreen, visionBlue;
	
	/*
	 * Characteristic fields of a Creature which are based off other 
	 * characteristic fields
	 */
	private double maxEnergy, energyUseRate, maxHealth;
	
	/*
	 * Computational fields of a Creature
	 */
	private double health, linearVelocity, x, y, angle, angularVelocity, energy;
	
	/*
	 * Neural Network which controls a Creature
	 */
	private Network network;
	
	/**
	 * Default constructor for creating a new Creature; when a new Creature
	 * is generated without inheriting data from another Creature, all
	 * data is randomized to a reasonable degree.
	 */
	public Creature() {
		attack = Math.random() * (CREATURE_ATTACK_MAX - CREATURE_ATTACK_MIN) + CREATURE_ATTACK_MIN;
		defense = Math.random() * (CREATURE_DEFENSE_MAX - CREATURE_DEFENSE_MIN) + CREATURE_DEFENSE_MIN;
		red = Math.random() * 255;
		blue = Math.random() * 255;
		green = Math.random() * 255;
		size = Math.random() * (CREATURE_SIZE_MAX - CREATURE_SIZE_MIN) + CREATURE_SIZE_MIN;
		markerValue = Math.random() * 100;
		geneticVariance = Math.random() * (CREATURE_VARIANCE_MAX - CREATURE_VARIANCE_MIN) + CREATURE_VARIANCE_MAX;
		maxLinearVelocity = Math.random() * CREATURE_LINEAR_V_MAX;
		
		maxEnergy = (CREATURE_MAXENERGY_MAX - CREATURE_MAXENERGY_MIN) / (CREATURE_SIZE_MAX - CREATURE_SIZE_MIN) * 
					(size - CREATURE_SIZE_MIN);
		energyUseRate = CREATURE_ENERGY_USE_RATE_MIN + CREATURE_ENERGY_USE_RATE_SCALING * size;
		maxHealth = (CREATURE_MAXHEALTH_MAX - CREATURE_MAXHEALTH_MIN) / (CREATURE_SIZE_MAX - CREATURE_SIZE_MIN) * 
					(size - CREATURE_SIZE_MIN);
		
		health = maxHealth;
		linearVelocity = 0;
		x = Math.random() * JSENNPanel.SIZE_X;
		y = Math.random() * JSENNPanel.SIZE_Y;
		angle = 0;
		angularVelocity = 0;
		energy = maxEnergy;
		Color startingColor = JSENNPanel.getTileColor(x, y);
		visionRed = startingColor.getRed();
		visionGreen = startingColor.getGreen();
		visionBlue = startingColor.getBlue();
		
		/*
		 * Currently, all Networks have 3 layers, with the following inputs 
		 * and outputs: (others will be added later as new features 
		 * are implemented)
		 * 
		 * INPUTS:
		 * 0 - linearVelocity
		 * 1 - angle
		 * 2 - health
		 * 3 - energy
		 * 4 - visionRed
		 * 5 - visionGreen
		 * 6 - visionBlue
		 * 7 - memoryA
		 * 8 - memoryB
		 * 
		 * (currently, vision data is just the Tile directly beneath the Creature,
		 * as vision has not been implemented yet)
		 * 
		 * OUTPUTS:
		 * 0 - angularVelocity
		 * 1 - linearVelocity
		 * 2 - eat
		 * 3 - reproduce
		 * 4 - memoryA
		 * 5 - memoryB
		 */
		network = new Network(3, new int[] {9, 7, 6});
		
		Layer inputLayer = network.getInputs();
		inputLayer.input(new double[]{
			linearVelocity,
			angle,
			health,
			energy,
			visionRed,
			visionGreen,
			visionBlue,
			Math.random(),
			Math.random()
		});
		
		network.transferData();
	}
	
	/**
	 * Updates all fields of the Creature based on its current position,
	 * circumstances, and the outputs of its neural network. Based on energy
	 * and health, if this Creature should survive this update, then this 
	 * method should make further calculations and have the network 
	 * recalculate all data, then return true; otherwise, this method should
	 * return false so that JSENNPanel can remove this Creature.
	 * @returns true if this creature survives this update, false otherwise.
	 */
	public boolean update() {
		Layer outputLayer = network.getOutputs();
		
		double angularVelocityOutput = Function.sigmoid(outputLayer.get(0).getData(), 1, 2, 0, 0) - 1;
		angle += CREATURE_ANGULAR_V_MAX * angularVelocityOutput;
		
		double linearVelocityOutput = Function.sigmoid(outputLayer.get(1).getData(), 1, 1, 0, 0);
		linearVelocity = maxLinearVelocity * linearVelocityOutput;

		double energyDecrease = energyUseRate + JSENNPanel.getTileEnergyRate(x, y);
		
		x += linearVelocity * Math.cos((Math.PI * angle) / 180);
		y += linearVelocity * Math.sin((Math.PI * angle) / 180);
		x = Math.max(x, 0);
		x = Math.min(x, JSENNPanel.SIZE_X - 1);
		y = Math.max(y, 0);
		y = Math.min(y, JSENNPanel.SIZE_Y - 1);
		
		health += CREATURE_HEALTH_REGENERATION_RATE;
		health = Math.min(health, maxHealth);
		
		energy += energyDecrease + 
				((Function.sigmoid(outputLayer.get(2).getData(), 1, 1, 0, 0) > 0.8) ? JSENNPanel.eat(x, y) - 25 : 0);
		energy = Math.min(energy, maxEnergy);
		
		if (energy <= 0 || health <= 0) {
			return false;
		}
		
		Color tileColor = JSENNPanel.getTileColor(x, y);
		visionRed = tileColor.getRed();
		visionGreen = tileColor.getGreen();
		visionBlue = tileColor.getBlue();
		
		double memoryA = Function.sigmoid(outputLayer.get(4).getData(), 1, 1, 0, 0);
		double memoryB = Function.sigmoid(outputLayer.get(5).getData(), 1, 1, 0, 0);
		
		Layer inputLayer = network.getInputs();
		inputLayer.input(new double[] {
			linearVelocity,
			angle,
			health,
			energy,
			visionRed,
			visionGreen,
			visionBlue,
			memoryA,
			memoryB
		});
		
		network.transferData();
		
		return true;
	}
	
	public Color getCreatureColor() {
		try {
			return new Color((int) red, (int) green, (int) blue);
		} catch (Exception e) {
			System.out.println("Invalid colors: Red: " + red + ", Green: " + green + ", Blue" + blue);
		}
		
		return null;
	}
	
	public double getSize() { return size; }
	public double getX() { return x; }
	public double getY() { return y; }
	
	/**
	 * Gets the maximum possible size of any Creature.
	 * @return the static float CREATURE_SIZE_LIMIT.
	 */
	public static double getCreatureSizeMax() { return CREATURE_SIZE_MAX; }
}
