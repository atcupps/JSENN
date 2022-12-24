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
 * @version 24 Dec 2022
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
	private static final double CREATURE_LINEAR_V_MAX = 2.5;
	
	/*
	 * Maximum angularVelocity of a creature in degrees / update
	 */
	private static final double CREATURE_ANGULAR_V_MAX = 90;
	
	/*
	 * Maximum maxEnergy of a creature
	 */
	private static final double CREATURE_MAXENERGY_MAX = 300;
	
	/*
	 * Minimum maxEnergy of a creature
	 */
	private static final double CREATURE_MAXENERGY_MIN = 40;
	
	/*
	 * Minimum energyUseRate per update
	 */
	private static final double CREATURE_ENERGY_USE_RATE_MIN = 1.0;
	
	/*
	 * Energy use rate scaling per pixel of size; energy use rate scales
	 * linearly with size
	 */
	private static final double CREATURE_ENERGY_USE_RATE_SCALING = 0.625;
	
	/*
	 * Health regeneration rate in health per update
	 */
	private static final double CREATURE_HEALTH_REGENERATION_RATE = 0.1;
	
	/*
	 * Value of reproduction output in network at which this creature
	 * can reproduce.
	 */
	private static final double CREATURE_REPRODUCTION_NETWORK_THRESHOLD = 0.7;
	
	/*
	 * Amount of energy needed for a creature to reproduce as a factor of its
	 * maxEnergy.
	 */
	private static final double CREATURE_REPRODUCTION_ENERGY_THRESHOLD = 0.7;
	
	/*
	 * Amount of health needed for a creature to reproduce as a factor of its
	 * maxHealth.
	 */
	private static final double CREATURE_REPRODUCTION_HEALTH_THRESHOLD = 0.7;
	
	/*
	 * Amount of energy used when a Creature eats
	 */
	private static final double CREATURE_EAT_COST = 12.5;
	
	/*
	 * The amount of ticks before a creature can reproduce
	 */
	private static final int CREATURE_REPRODUCTION_TIME = 80;
	
	/*
	 * Maximum distance a Creature can see
	 */
	private static final double CREATURE_MAX_VISION_DISTANCE = 50;
	
	/*
	 * Inherited characteristic fields of a Creature
	 */
	private double attack, defense, red, blue, green, size, markerValue, geneticVariance, maxLinearVelocity, maxAngularVelocity;
	
	/*
	 * Characteristic fields of a Creature which are based off other 
	 * characteristic fields
	 */
	private double maxEnergy, energyUseRate, maxHealth;
	
	/*
	 * Computational fields of a Creature
	 */
	private double health, linearVelocity, x, y, angle, angularVelocity, energy,
		visionX, visionY, visionRed, visionGreen, visionBlue, visionDistance,
		belowRed, belowGreen, belowBlue;
	private double visionXGraphics, visionYGraphics; //Used only for graphics purposes
	private int reproductionTimer = CREATURE_REPRODUCTION_TIME;
	
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
		maxAngularVelocity = Math.random() * CREATURE_ANGULAR_V_MAX;
		
		maxEnergy = (CREATURE_MAXENERGY_MAX - CREATURE_MAXENERGY_MIN) / (CREATURE_SIZE_MAX - CREATURE_SIZE_MIN) * 
					(size - CREATURE_SIZE_MIN);
		energyUseRate = CREATURE_ENERGY_USE_RATE_MIN + CREATURE_ENERGY_USE_RATE_SCALING * size;
		maxHealth = (CREATURE_MAXHEALTH_MAX - CREATURE_MAXHEALTH_MIN) / (CREATURE_SIZE_MAX - CREATURE_SIZE_MIN) * 
					(size - CREATURE_SIZE_MIN);
		health = maxHealth;
		linearVelocity = 0;
		x = Math.random() * JSENNPanel.SIZE_X;
		y = Math.random() * JSENNPanel.SIZE_Y;
		visionX = x + size;
		visionY = y + size;
		visionXGraphics = visionX;
		visionYGraphics = visionY;
		visionX = (visionX + JSENNPanel.SIZE_X) % JSENNPanel.SIZE_X;
		visionY = (visionY + JSENNPanel.SIZE_Y) % JSENNPanel.SIZE_Y; 
		visionDistance = size;
		angle = Math.random() * 360;
		angularVelocity = 0;
		energy = maxEnergy;
		
		Color belowColor = JSENNPanel.getTileColor(x, y);
		belowRed = belowColor.getRed();
		belowGreen = belowColor.getGreen();
		belowBlue = belowColor.getBlue();
		
		Color visionColor = JSENNPanel.getTileColor(visionX, visionY);
		visionRed = visionColor.getRed();
		visionGreen = visionColor.getGreen();
		visionBlue = visionColor.getBlue();
		
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
		 * 9 - visionDistance
		 * 10 - belowRed
		 * 11 - belowGreen
		 * 12 - belowBlue
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
		 * 6 - visionDistance
		 */
		network = new Network(4, new int[] {13, 9, 9, 7});
		
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
			Math.random(),
			visionDistance,
			belowRed,
			belowGreen,
			belowBlue
		});
		
		network.transferData();
	}
	
	/**
	 * Genetic algorithmic constructor for a Creature inheriting fields from
	 * another Creature; this is used when Creatures reproduce to create new
	 * Creatures.
	 * @param toInherit the Creature to be inherited
	 * @param maxVariance the maximum variance of fields
	 */
	public Creature(Creature toInherit, double maxVariance) {
		attack = toInherit.attack + Math.random() * maxVariance * 2 - maxVariance;
		attack = Function.bound(CREATURE_ATTACK_MIN, CREATURE_ATTACK_MAX, attack);
		defense = toInherit.defense + Math.random() * maxVariance * 2 - maxVariance;
		defense = Function.bound(CREATURE_DEFENSE_MIN, CREATURE_DEFENSE_MAX, defense);
		red = toInherit.red + Math.random() * maxVariance * 200 - maxVariance * 100;
		red = Function.bound(0, 255, red);
		green = toInherit.green + Math.random() * maxVariance * 200 - maxVariance * 100;
		green = Function.bound(0, 255, green);
		blue = toInherit.blue + Math.random() * maxVariance * 200 - maxVariance * 100;
		blue = Function.bound(0, 255, blue);
		size = Function.bound(CREATURE_SIZE_MIN, CREATURE_SIZE_MAX, 
				toInherit.size + Math.random() * maxVariance * 2 - maxVariance);
		markerValue = toInherit.markerValue + Math.random() * maxVariance * 2 - maxVariance;
		geneticVariance = toInherit.geneticVariance + Math.random() * maxVariance / 2 - maxVariance / 4;
		geneticVariance = Function.bound(CREATURE_VARIANCE_MIN, CREATURE_VARIANCE_MAX, geneticVariance);
		maxLinearVelocity = toInherit.maxLinearVelocity + Math.random() * maxVariance * 2 - maxVariance;
		maxLinearVelocity = Function.bound(0, CREATURE_LINEAR_V_MAX, maxLinearVelocity);
		maxAngularVelocity = toInherit.maxAngularVelocity + Math.random() * maxVariance * 2 - maxVariance;
		maxAngularVelocity = Function.bound(0, CREATURE_ANGULAR_V_MAX, maxAngularVelocity);
		
		maxEnergy = (CREATURE_MAXENERGY_MAX - CREATURE_MAXENERGY_MIN) / (CREATURE_SIZE_MAX - CREATURE_SIZE_MIN) * 
				(size - CREATURE_SIZE_MIN);
		energyUseRate = CREATURE_ENERGY_USE_RATE_MIN + CREATURE_ENERGY_USE_RATE_SCALING * size;
		maxHealth = (CREATURE_MAXHEALTH_MAX - CREATURE_MAXHEALTH_MIN) / (CREATURE_SIZE_MAX - CREATURE_SIZE_MIN) * 
				(size - CREATURE_SIZE_MIN);
		
		health = toInherit.health / 2;
		linearVelocity = 0;
		x = toInherit.x + Math.random() * 50;
		y = toInherit.y + Math.random() * 50;
		x = (x + JSENNPanel.SIZE_X) % JSENNPanel.SIZE_X;
		y = (y + JSENNPanel.SIZE_Y) % JSENNPanel.SIZE_Y;
		visionX = x + size;
		visionY = y + size;
		visionXGraphics = visionX;
		visionYGraphics = visionY;
		visionX = (visionX + JSENNPanel.SIZE_X) % JSENNPanel.SIZE_X;
		visionY = (visionY + JSENNPanel.SIZE_Y) % JSENNPanel.SIZE_Y; 
		visionDistance = size;
		angle = Math.random() * 360;
		angularVelocity = 0;
		energy = toInherit.energy;
	
		Color belowColor = JSENNPanel.getTileColor(x, y);
		belowRed = belowColor.getRed();
		belowGreen = belowColor.getGreen();
		belowBlue = belowColor.getBlue();
		
		Color visionColor = JSENNPanel.getTileColor(visionX, visionY);
		visionRed = visionColor.getRed();
		visionGreen = visionColor.getGreen();
		visionBlue = visionColor.getBlue();
		
		network = new Network(toInherit.network, maxVariance);
		Layer inputLayer = network.getInputs();
		inputLayer.input(new double[]{
				linearVelocity,
				angle,
				health,
				energy,
				visionRed,
				visionGreen,
				visionBlue,
				Math.random(), //memory is randomized at the beginning
				Math.random(),
				visionDistance,
				belowRed,
				belowGreen,
				belowBlue
		});
		network.transferData();
		
	}
	
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
	 * 9 - visionDistance
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
	 * 6 - visionDistance
	 */
	
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
		angularVelocity = maxAngularVelocity * angularVelocityOutput;
		angle += angularVelocity;
		
		double linearVelocityOutput = Function.sigmoid(outputLayer.get(1).getData(), 1, 1, 0, 0) - 0;
		linearVelocity = maxLinearVelocity * linearVelocityOutput;

		double energyDecrease = energyUseRate + JSENNPanel.getTileEnergyRate(x, y) * linearVelocityOutput * size;
		
		x += linearVelocity * Math.cos((Math.PI * angle) / 180);
		y += linearVelocity * Math.sin((Math.PI * angle) / 180);
		x = (x + JSENNPanel.SIZE_X) % JSENNPanel.SIZE_X;
		y = (y + JSENNPanel.SIZE_Y) % JSENNPanel.SIZE_Y; 
		
		health += CREATURE_HEALTH_REGENERATION_RATE;
		health = Math.min(health, maxHealth);
		
		energy += -energyDecrease + 
				((Function.sigmoid(outputLayer.get(2).getData(), 1, 1, 0, 0) > 0.8) ? 
						JSENNPanel.eat(x, y) - CREATURE_EAT_COST : 0);
		energy = Math.min(energy, maxEnergy);
		
		reproductionTimer--;
		
		if (energy <= 0 || health <= 0) {
			return false;
		}
		
		double visionDistanceOutput = Function.sigmoid(outputLayer.get(6).getData(), 1, 1, 0, 0);
		visionDistance = visionDistanceOutput * CREATURE_MAX_VISION_DISTANCE;
		visionDistance = Function.bound(size, CREATURE_MAX_VISION_DISTANCE, visionDistance);
		visionX = x + visionDistance * Math.cos((Math.PI * angle) / 180);
		visionY = y + visionDistance * Math.sin((Math.PI * angle) / 180);
		visionXGraphics = visionX;
		visionYGraphics = visionY;
		visionX = (visionX + JSENNPanel.SIZE_X) % JSENNPanel.SIZE_X;
		visionY = (visionY + JSENNPanel.SIZE_Y) % JSENNPanel.SIZE_Y; 
		
		Color belowColor = JSENNPanel.getTileColor(x, y);
		belowRed = belowColor.getRed();
		belowGreen = belowColor.getGreen();
		belowBlue = belowColor.getBlue();
		
		Color visionColor = JSENNPanel.getTileColor(visionX, visionY);
		visionRed = visionColor.getRed();
		visionGreen = visionColor.getGreen();
		visionBlue = visionColor.getBlue();
		
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
			memoryB,
			visionDistance,
			visionRed,
			visionGreen,
			visionBlue
		});
		
		network.transferData();
		
		return true;
	}
	
	/**
	 * Returns true if the conditions are met that this Creature should
	 * reproduce; flase otherwise.
	 */
	public boolean shouldReproduce() {
		return 	Function.sigmoid(network.getOutputs().get(3).getData(), 1, 1, 0, 0) > CREATURE_REPRODUCTION_NETWORK_THRESHOLD &&
				health > maxHealth * CREATURE_REPRODUCTION_HEALTH_THRESHOLD &&
				energy > maxEnergy * CREATURE_REPRODUCTION_ENERGY_THRESHOLD &&
				reproductionTimer <= 0;
	}
	
	/**
	 * Decreases health and energy by a factor of 4, then
	 * Returns a new Creature inheriting genetic values from the current
	 * Creature object using the genetic Creature constructor.
	 */
	public Creature reproduce() {
		reproductionTimer = CREATURE_REPRODUCTION_TIME / 2;
		energy /= 2;
		health /= 2;
		// Small chance for larger genetic variation in offspring
		return new Creature(this, (Math.random() > 0.95) ? Math.sqrt(geneticVariance) + 0.1 : geneticVariance);
	}
	
	public Color getCreatureColor() {
		try {
			return new Color((int) Function.bound(0, 255, red), 
					(int) Function.bound(0, 255, green), (int) Function.bound(0, 255, blue));
		} catch (Exception e) {
			System.out.println("Invalid colors: Red: " + red + ", Green: " + green + ", Blue" + blue);
		}
		
		return null;
	}
	
	public double getSize() { return size; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getVisionXGraphics() { return visionXGraphics; }
	public double getVisionYGraphics() { return visionYGraphics; }
	public int getEnergy() { return (int) energy; }
	public int getHealth() { return (int) health; }
	
	/**
	 * Gets the maximum possible size of any Creature.
	 * @return the static float CREATURE_SIZE_LIMIT.
	 */
	public static double getCreatureSizeMax() { return CREATURE_SIZE_MAX; }
}
