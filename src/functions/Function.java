package functions;

/**
 * The Function class contains many mathematical operations useful for this
 * project.
 * 
 * Copyright (C) 2022 Andrew Cupps, CC BY-SA
 * 
 * @author Andrew Cupps
 * @version 19 Dec 2022
 */
public class Function {

	/**
	 * Takes in a value input and returns a value between zero and one,
	 * where the limit as the value approaches negative infinity is zero and
	 * the limit 
	 * 
	 * @param value - the value to be input into the sigmoid function
	 * @param xScale - the amount the function should be stretched horizontally
	 * @param yScale - the amount the function should be stretched vertically
	 * @param xShift - the amount the function should be shifted right
	 * @param yShift - the amount the function should be shifted up
	 * @return the value of g / (1 - e^(-(bx - h))) + c
	 */
	public static double sigmoid(double value, double xScale, double yScale, double xShift, double yShift) {
		return yScale / (1 + Math.exp(-1 * (xScale * value - xShift))) + yShift;
	}
}
