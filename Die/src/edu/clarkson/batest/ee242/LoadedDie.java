package edu.clarkson.batest.ee242;
/**
 * Created to represent a loaded die
 * This die can be rolled, looked at, and compared with another die
 * @author Troy
 *
 */
public class LoadedDie extends Die{
	private int[] weightedValue; //default weight on die 6
	private int LoadedSide;
	/**
	*Initializes a loaded die, with a default 6 sided die, with a 50% weight on side 6 
	*/
	LoadedDie(){
		this(6,6,50); //gives a default loaded 6 sided die, with a load factor of 50%
	}
	/**
	 * Initializes a loaded die, with n number of sided die, with a loadFactor weight on a loadedSide
	 * @param n
	 * @param loadedSide
	 * @param loadFactor
	 * @throws IllegalArgumentException
	 */
	LoadedDie(int n, int loadedSide, int loadFactor) throws IllegalArgumentException {
		super(n);
		weightedValue= new int[n];
		if (loadedSide<=n && loadFactor > 0 && loadFactor<=100){
			for(int i=0; i<n; i++){
				if(i==loadedSide-1){
					weightedValue[i]=loadFactor; //assigns the load factor to a specific side
				}
				else{
					weightedValue[i]=(100-loadFactor)/(n-1); // assigns all the other sides to a default value
				}
			}
			LoadedSide=loadedSide;
		}
		else
			throw new IllegalArgumentException();
	}
	/**
	 * The roll takes into account the weight of each side
	 * @return topNumber
	 */
	int roll(){
		int tempWeightSize=0;
		int rawRoll;
		for (int i=0; i < weightedValue.length; i++){
			tempWeightSize=tempWeightSize+weightedValue[i];  //adds all the weighted values together
		}
		rawRoll=rand.nextInt(tempWeightSize);
		int tempRawValue=0;
		for(int i=0; i<weightedValue.length; i++){
			tempRawValue=tempRawValue+weightedValue[i]; //adds all the number in order in the array so that it can be checked against the raw roll
			if (rawRoll<tempRawValue){ //checks if the random number is less than the temporary raw value
				topNumber=i+1; 
				break;
			}
		}		
		return topNumber;
	}
	/**
	 * The public string tells you the number of sides, its loaded side, and the number on the top
	 * @return String
	 */
	public String toString(){
		return "I am a Loaded D"+ numberOfSides+" on side "+ LoadedSide +" and my top number is : " + topNumber;
	}

}
