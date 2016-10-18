package edu.clarkson.batest.ee242;

import java.util.Random;
/**
 * Created to represent any sided die
 * This die can be rolled, looked at, and compared with another die
 * @author Troy
 *
 */
public class Die implements Comparable<Die>{
	/**
	 * Represents the top number on the die
	 */
	protected int topNumber = 1; //create a private integer which represents the number on the top of the die. A value of 1 is decided in case someone looks at the die before rolling it it
	protected int numberOfSides;
	/**
	 * Creates a static random number generator
	 */
	protected static Random rand = new Random();
	/**
	*Initializes the Die 
	*Calls the constructor 
	*/
	Die() throws IllegalArgumentException {
		this(6);
	}
	Die(int n) throws IllegalArgumentException{
		if(n<3){
			throw new IllegalArgumentException();
	}
		
		numberOfSides=n;
	}
	/**
	 * The roll function returns a random variable between 1 and 6.
	 * This represents the value that would be displayed on the top of the die.
	 * @return the integer rolled
	 */
	int roll(){
		return topNumber = rand.nextInt(numberOfSides)+1; //generates a number from 0 to 5 and adds 1
	}
	/**
	 * getTop returns the value on the top of the Die without rolling it again.
	 * @return the number on the die
	 */
	int getTop(){
		return topNumber; //returns the number saved as the top value
	}
	/**
	 * This class takes in an different die class this then returns true if the number of sides are equal and the number on top is the same.
	 * @param Die other
	 * @return Boolean
	 */
	boolean equals(Die other){
		if(this.numberOfSides==other.numberOfSides && this.topNumber ==other.topNumber){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * Overrides the toString method to return the number of sides and the number on top
	 * @return String
	 */
	public String toString(){
		return "I am a D"+ numberOfSides+" and my top number is : " + topNumber;
	}
	/**
	 * Overrides hashCode in order to return a value based on the topNumber and number of sides
	 */
	public int hashCode(){
		return 37*topNumber+numberOfSides;
	}
	/**
	 * Compares the top number to another die and returns if it is greater, less than, or equal
	 * @return int
	 */
	public int compareTo(Die other) {
		if (topNumber < other.topNumber){
			return -1;
		}
		if (topNumber == other.topNumber){
			return 0;
		}
		else {
			return 1;
		}
	}
}
