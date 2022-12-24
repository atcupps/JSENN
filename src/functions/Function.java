package functions;

/**
 * The Function class contains many mathematical operations useful for this
 * project.
 * 
 * Copyright (C) 2022 Andrew Cupps, CC BY 4.0
 * 
 * @author Andrew Cupps
 * @version 23 Dec 2022
 */
public class Function {

	/**
	 * Takes in a value input and returns a value between zero and one,
	 * where the limit as the value approaches negative infinity is zero and
	 * the limit as the value approaches infinity is one.
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
	
	/**
	 * Returns the given parameter value within the upper and lower bounds; so,
	 * if the value is greater than upper bound, this method will return the
	 * upper bound, and if it is less than lower bound, this method will return
	 * the lower bound.
	 * @param upperBound the upper bound that the value can be
	 * @param lowerBound the lower bound that the value can be
	 * @param value the value to be bound within the upper and lower bounds
	 * @return the value bounded within the upper and lower bounds
	 */
	public static double bound(double lowerBound, double upperBound, double value) {
		value = Math.min(upperBound, value);
		value = Math.max(lowerBound, value);
		return value;
	}
}
